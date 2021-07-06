/*
 * MIT License
 *
 * Copyright (c) 2021 MinecraftOnline
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

/*
 * MIT License
 *
 * Copyright (c) 2020 MinecraftOnline
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.minecraftonline.nope.sponge.command;

import com.google.common.collect.Lists;
import com.minecraftonline.nope.sponge.SpongeNope;
import com.minecraftonline.nope.sponge.command.general.arguments.NopeArguments;
import com.minecraftonline.nope.sponge.command.general.CommandNode;
import com.minecraftonline.nope.sponge.command.general.LambdaCommandNode;
import com.minecraftonline.nope.common.host.Host;
import com.minecraftonline.nope.common.host.VolumeHost;
import com.minecraftonline.nope.common.permission.Permissions;
import com.minecraftonline.nope.sponge.util.Format;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

/**
 * A command to view detailed information about a host.
 */
public class InfoCommand extends LambdaCommandNode {

  InfoCommand(CommandNode parent) {
    super(parent,
        Permissions.COMMAND_INFO,
        Text.of("View detailed information about a host"),
        "info",
        "i");

    addCommandElements(GenericArguments.optional(NopeArguments.host(Text.of("zone"))));
    setExecutor((src, args) -> {

      Host host = args.<Host>getOne("zone").orElse(NopeCommandRoot.inferHost(src).orElse(null));
      if (host == null) {
        return CommandResult.empty();
      }

      List<Text> headerLines = Lists.newLinkedList();

      if (host.getWorldUuid() != null) {
        String worldName = Sponge.getServer()
            .getWorld(host.getWorldUuid())
            .map(World::getName)
            .orElseThrow(() -> new RuntimeException("Sponge cannot find world with UUID: "
                + host.getWorldUuid()));
        headerLines.add(Format.keyValue(TextColors.DARK_GRAY, "world: ", worldName));
      }

      if (host.getParent() != null) {
        headerLines.add(Format.keyValue(TextColors.DARK_GRAY,
            "parent: ",
            Format.host(host.getParent())));
      }

      if (host instanceof VolumeHost) {
        VolumeHost volumeHost = (VolumeHost) host;
        // Volume zones only:
        headerLines.add(Format.keyValue(TextColors.DARK_GRAY, "min: ",
            (volumeHost.getMinX() == Integer.MIN_VALUE ? "-Inf" : volumeHost.getMinX())
                + ", "
                + volumeHost.getMinY()
                + ", "
                + (volumeHost.getMinZ() == Integer.MIN_VALUE ? "-Inf" : volumeHost.getMinZ())));

        headerLines.add(Format.keyValue(TextColors.DARK_GRAY, "max: ",
            (volumeHost.getMaxX() == Integer.MAX_VALUE ? "Inf" : volumeHost.getMaxX())
                + ", "
                + volumeHost.getMaxY()
                + ", "
                + (volumeHost.getMaxZ() == Integer.MAX_VALUE ? "Inf" : volumeHost.getMaxZ())));
      }

      int zonePriority = host.getPriority();
      headerLines.add(Format.keyValue(TextColors.DARK_GRAY,
          "priority: ",
          String.valueOf(zonePriority)));
      headerLines.add(Text.of(TextColors.DARK_GRAY, "--------------"));  // line separator
      headerLines.add(Text.builder()
          .append(Text.of(TextColors.AQUA, "<< Settings >>  "))
          .append(Format.commandSuggest("NEW", SpongeNope.getInstance().getCommandTree()
                  .findNode(SetCommand.class)
                  .orElseThrow(() ->
                      new RuntimeException("SetCommand is not set in Nope command tree!"))
                  .getFullCommand()
                  + String.format(" -z %s ___ ___",
              host.getName()),
              Text.of("Set a new setting on this host.\nUse ",
                  TextColors.GOLD, "/nope settings", TextColors.RESET,
                  " to see all settings")))
          .build());

      Sponge.getScheduler().createTaskBuilder()
          .async()
          .execute(() -> {
            List<Text> contents = host.getAll().entries()
                .stream()
                .sorted(Comparator.comparing(setting -> setting.getKey().getId()))
                .flatMap(setting -> {
                  try {
                    return Format.setting(setting, src, host, SpongeNope.getInstance()
                        .getHostTreeAdapter()
                        .isRedundant(host, setting.getKey())
                    ).get()
                        .stream().map(text -> Text.builder().append(text)
                            .onClick(TextActions.suggestCommand(
                                SpongeNope.getInstance().getCommandTree()
                                    .findNode(UnsetCommand.class)
                                    .orElseThrow(() ->
                                        new RuntimeException("UnsetCommand is not "
                                            + "set in Nope command tree!")).getFullCommand()
                                    + String.format(" -z %s %s", host.getName(), setting.getKey())))
                            .build());
                  } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return Stream.empty();
                  }
                })
                .collect(Collectors.toList());

            Sponge.getServiceManager()
                .provide(PaginationService.class)
                .orElseThrow(() -> new RuntimeException("PaginationService doesn't exist!"))
                .builder()
                .title(Format.info("Zone Info: ", Format.note(host.getName())))
                .header(headerLines.isEmpty()
                    ? Format.note("None")
                    : Text.joinWith(Text.NEW_LINE, headerLines))
                .padding(Text.of(Format.ACCENT, "="))
                .contents(contents.isEmpty()
                    ? Collections.singleton(Format.note("None"))
                    : contents)
                .build()
                .sendTo(src);
          }).submit(SpongeNope.getInstance());

      // Send the message when we have converted uuids.

      return CommandResult.success();
    });
  }

  /**
   * Gets a game profile promise.
   *
   * @param uuid UUID
   * @return CompletableFuture to obtain a game profile.
   */
  private static CompletableFuture<GameProfile> getProfile(UUID uuid) {
    return Sponge.getServer().getGameProfileManager().get(uuid);
  }
}

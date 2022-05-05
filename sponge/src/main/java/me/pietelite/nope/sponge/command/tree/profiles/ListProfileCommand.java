/*
 * MIT License
 *
 * Copyright (c) Pieter Svenson
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

package me.pietelite.nope.sponge.command.tree.profiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import me.pietelite.nope.common.host.Profile;
import me.pietelite.nope.common.permission.Permissions;
import me.pietelite.nope.sponge.SpongeNope;
import me.pietelite.nope.sponge.command.CommandNode;
import me.pietelite.nope.sponge.util.Formatter;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;

public class ListProfileCommand extends CommandNode {

  private static final Parameter.Value<String> ALL = Parameter.choices("all")
      .key("list-host-all")
      .optional()
      .build();

  public ListProfileCommand(CommandNode parent) {
    super(parent, Permissions.PROFILE_INFO,
        "List all profiles",
        "list");
  }

  @Override
  public CommandResult execute(CommandContext context) throws CommandException {
    List<Profile> profiles = new ArrayList<>(SpongeNope.instance()
        .system()
        .profiles()
        .values());

    Sponge.serviceProvider().paginationService().builder()
        .title(Component.text("Hosts").color(Formatter.GOLD))
        .padding(Component.text("=").color(Formatter.THEME))
        .contents(profiles.stream()
            .map(profile -> Component.text()
                .append(Component.text("> ").color(Formatter.DULL))
                .append(Formatter.profile(profile))
                .build())
            .collect(Collectors.toList()))
        .sendTo(context.cause().audience());
    return CommandResult.success();
  }

}
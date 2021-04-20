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

package com.minecraftonline.nope.util;

import com.google.common.collect.Lists;
import com.minecraftonline.nope.Nope;
import com.minecraftonline.nope.command.InfoCommand;
import com.minecraftonline.nope.command.SetCommand;
import com.minecraftonline.nope.command.TargetAddCommand;
import com.minecraftonline.nope.command.UnsetCommand;
import com.minecraftonline.nope.host.Host;
import com.minecraftonline.nope.permission.Permissions;
import com.minecraftonline.nope.setting.Setting;
import com.minecraftonline.nope.setting.SettingKey;
import com.minecraftonline.nope.setting.SettingValue;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public final class Format {

  private Format() {
  }

  public static final TextColor THEME = TextColors.GRAY;
  public static final TextColor ACCENT = TextColors.LIGHT_PURPLE;

  public static Text prefix() {
    return Text.of(THEME, "Nope ", TextColors.DARK_GRAY, "-=- ");
  }

  public static Text success(Object... message) {
    return Text.of(prefix(), TextColors.GREEN, Text.of(message));
  }

  public static Text error(Object... message) {
    return Text.of(prefix(), TextColors.RED, Text.of(message));
  }

  public static Text warn(Object... message) {
    return Text.of(prefix(), TextColors.YELLOW, Text.of(message));
  }

  public static Text info(Object... message) {
    return Text.of(prefix(), TextColors.WHITE, Text.of(message));
  }

  public static Text note(Object... message) {
    return Text.of(TextColors.GRAY, Text.of(message));
  }

  public static Text keyValue(String key, String value) {
    return keyValue(key, Text.of(value));
  }

  public static Text keyValue(String key, Text value) {
    return keyValue(ACCENT, key, value);
  }

  public static Text keyValue(TextColor keyColor, String key, String value) {
    return keyValue(keyColor, key, Text.of(value));
  }

  public static Text keyValue(TextColor keyColor, String key, Text value) {
    return Text.of(keyColor, key, " ", Format.note(value));
  }

  public static Text hover(String label, String onHover) {
    return Text.builder()
        .append(Text.of(TextStyles.ITALIC, label))
        .onHover(TextActions.showText(Format.note(onHover)))
        .build();
  }

  public static Text url(@Nonnull String label, @Nonnull String url) {
    Text.Builder textBuilder = Text.builder();
    textBuilder.append(Text.of(TextColors.BLUE, label));
    textBuilder.onHover(TextActions.showText(Text.of(url)));
    try {
      textBuilder.onClick(TextActions.openUrl(new URL(url)));
    } catch (MalformedURLException ex) {
      textBuilder.onClick(TextActions.suggestCommand(url));
      Nope.getInstance().getLogger().error("A url was not formed correctly for a"
          + " click action: " + url);
    }

    return textBuilder.build();
  }

  public static Text command(@Nonnull String label,
                             @Nonnull String command,
                             @Nullable Text hoverMessage) {
    Text.Builder builder = Text.builder()
        .append(Text.of(TextColors.GOLD, TextStyles.ITALIC, "[",
            Text.of(TextColors.LIGHT_PURPLE, label), "]"))
        .onClick(TextActions.runCommand(command));
    if (hoverMessage != null) {
      builder.onHover(TextActions.showText(Text.of(
          hoverMessage,
          hoverMessage.isEmpty() ? Text.EMPTY : "\n",
          Format.note(command))));
    }
    return builder.build();
  }

  public static Text commandSuggest(@Nonnull String label,
                                    @Nonnull String command,
                                    @Nullable Text hoverMessage) {
    Text.Builder builder = Text.builder()
        .append(Text.of(TextColors.GOLD, TextStyles.ITALIC, "[",
            Text.of(TextColors.GRAY, label), "]"))
        .onClick(TextActions.suggestCommand(command));
    if (hoverMessage != null) {
      builder.onHover(TextActions.showText(Text.of(
          hoverMessage,
          hoverMessage.isEmpty() ? Text.EMPTY : "\n",
          Format.note(command))));
    }
    return builder.build();
  }

  public static Text subtleCommand(@Nonnull String text, @Nonnull String command, @Nullable Text hoverMessage) {
    Text.Builder builder = Text.builder().append(Text.of(Format.ACCENT, text))
        .onClick(TextActions.runCommand(command));

    if (hoverMessage != null) {
      builder.onHover(TextActions.showText(Text.of(
          hoverMessage,
          hoverMessage.isEmpty() ? Text.EMPTY : "\n",
          Format.note(command))));
    }
    return builder.build();
  }

  public static Text host(@Nonnull Host host) {
    String name = host.getName();
    return Format.subtleCommand(
        name,
        Nope.getInstance()
            .getCommandTree()
            .findNode(InfoCommand.class)
            .orElseThrow(() ->
                new RuntimeException("Info command is not part of the command tree"))
            .getFullCommand() + " " + name,
        Text.of("Click for more details about this zone")
    );
  }

  public static <T> Text settingKey(SettingKey<T> key, boolean verbose) {
    Text.Builder builder = Text.builder();

    Text.Builder idText = Text.builder().append(Text.of(Format.ACCENT, key.getId()));

    Text.Builder onHover = Text.builder();

    if (!key.isImplemented()) {
      idText.style(TextStyles.STRIKETHROUGH);
      onHover.append(Text.of(TextColors.RED, "Not implemented yet!"));
      onHover.append(Text.NEW_LINE);
    }

    onHover.append(Format.keyValue("Type:", key.valueType().getSimpleName()));
    onHover.append(Text.NEW_LINE);

    onHover.append(Format.keyValue("Default value:", key.print(key.getDefaultData())));
    onHover.append(Text.NEW_LINE);

    onHover.append(Format.keyValue("Restrictive:", String.valueOf(key.isPlayerRestrictive())));

    if (key.getDescription() != null) {
      onHover.append(Text.NEW_LINE).append(Text.NEW_LINE);
      onHover.append(Text.of(TextColors.WHITE, key.getDescription()));
    }

    builder.onHover(TextActions.showText(onHover.build()));

    builder.append(idText.build());
    if (verbose) {
      builder.append(Text.of(" "));
      builder.append(Format.note(key.getDescription() == null ? "No description" : key.getDescription()));
    }

    return builder.build();
  }

  private static Text settingValue(Text value, boolean redundantOnDefault, Host redundancyController) {
    Text.Builder builder = Text.builder();
    if (redundancyController != null) {
      // Redundant
      builder.append(Text.of(TextColors.GRAY, TextStyles.STRIKETHROUGH, value));
      if (redundantOnDefault) {
        builder.onHover(TextActions.showText(Text.of("This setting is redundant because it is the default value")));
      } else {
        builder.onHover(TextActions.showText(Text.of("This setting is redundant because host ",
            Format.host(redundancyController),
            " has the same setting")));
      }
    } else {
      builder.append(Text.of(TextColors.WHITE, value));
    }
    return builder.build();
  }

  public static <T> CompletableFuture<List<Text>> setting(Setting<T> setting,
                                                          Subject subject,
                                                          @Nonnull Host host,
                                                          @Nullable Host redundancyController) {
    return CompletableFuture.supplyAsync(() -> {
      List<Text> list = Lists.newLinkedList();
      Text.Builder main = Text.builder();

      /* Unset Button */
      UnsetCommand unsetCommand = Nope.getInstance().getCommandTree()
          .findNode(UnsetCommand.class)
          .orElseThrow(() ->
              new RuntimeException("UnsetCommand is not set in Nope command tree!"));
      if (unsetCommand.hasPermission(subject)) {
        main.append(Format.commandSuggest("UNSET",
                unsetCommand.getFullCommand() + String.format(" -z %s %s",
                    host.getName(),
                    setting.getKey()),
                Text.of("Unset the value of this setting on this host")))
            .append(Text.of(" "));
      }

      /* Set Button */
      SetCommand setCommand = Nope.getInstance().getCommandTree()
          .findNode(SetCommand.class)
          .orElseThrow(() ->
              new RuntimeException("SetCommand is not set in Nope command tree!"));
      if (unsetCommand.hasPermission(subject)) {
        main.append(Format.commandSuggest("SET",
                setCommand.getFullCommand() + String.format(" -z %s %s ___",
                    host.getName(),
                    setting.getKey()),
                Text.of("Set this setting on this host with a value")))
            .append(Text.of(" "));
      }

      /* Add Target Button */
      TargetAddCommand targetAddCommand = Nope.getInstance().getCommandTree()
          .findNode(TargetAddCommand.class)
          .orElseThrow(() ->
              new RuntimeException("TargetAddCommand is not set in Nope command tree!"));
      if (targetAddCommand.hasPermission(subject)) {
        main.append(Format.commandSuggest("ADD",
                targetAddCommand.getFullCommand() + String.format(" ___ -z %s %s ___",
                    host.getName(),
                    setting.getKey()),
                Text.of("Add a target condition to this host")))
            .append(Text.of(" "));
      }

      main.append(Format.settingKey(setting.getKey(), false),
          Text.of(" = ", Format.settingValue(setting.getKey().print(setting.getValue().getData()),
              host.equals(redundancyController),
              redundancyController)));

      list.add(main.build());

      if (setting.getValue().getTarget() != null) {
        SettingValue.Target target = setting.getValue().getTarget();
        if (!target.getUsers().isEmpty()) {
          list.add(Text.of(TextColors.GREEN,
              " > ",
              Format.keyValue(target.hasWhitelist() ? "Whitelist:" : "Blacklist:",
                  target.getUsers()
                      .stream()
                      .map(uuid -> {
                        try {
                          return Sponge.getServer().getGameProfileManager()
                              .get(uuid)
                              .get().getName().orElseThrow(() ->
                                  new RuntimeException("Failed to get user profile name "
                                      + "for UUID: " + uuid.toString()));
                        } catch (InterruptedException | ExecutionException e) {
                          e.printStackTrace();
                          return "";
                        }
                      })
                      .filter(s -> !s.isEmpty())
                      .collect(Collectors.joining(", ")))));
        }
        target.forEach((permission, value) ->
            list.add(Text.of(TextColors.GREEN,
                " > ",
                Format.keyValue(permission + ":", String.valueOf(value)))));
        if (target.isForceAffect()) {
          list.add(Text.of(TextColors.GREEN,
              " > ",
              Format.hover("FORCE AFFECT",
                  "When affect is forced, players with the "
                      + Permissions.UNRESTRICTED.get()
                      + " permission may still be targeted")));
        }
      }
      return list;
    });
  }

}

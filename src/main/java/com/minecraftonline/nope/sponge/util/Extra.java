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

package com.minecraftonline.nope.sponge.util;

import com.minecraftonline.nope.sponge.SpongeNope;
import com.minecraftonline.nope.common.setting.SettingKey;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * A utility class to store static methods for managing "extra"
 * miscellaneous behavior.
 */
public final class Extra {

  private Extra() {
  }

  /**
   * Sends a series of messages to the Sponge
   * {@link org.spongepowered.api.command.source.ConsoleSource},
   * notifying the user pleasantly that Nope is loaded.
   */
  public static void printSplashscreen() {
    Text.Builder builder = Text.builder();

    // Line 1
    builder.append(Text.of(TextColors.WHITE, "           "));
    builder.append(Text.of(TextColors.RED, "   ______    "));
    builder.append(Text.of(TextColors.WHITE, " ____    "));
    builder.append(Text.of(TextColors.WHITE, " ______"));
    Sponge.getServer().getConsole().sendMessage(builder.build());

    // Line 2
    builder.removeAll();
    builder.append(Text.of(TextColors.WHITE, " |\\      | "));
    builder.append(Text.of(TextColors.RED, "  / ____ \\   "));
    builder.append(Text.of(TextColors.WHITE, "|    \\   "));
    builder.append(Text.of(TextColors.WHITE, "|"));
    Sponge.getServer().getConsole().sendMessage(builder.build());

    // Line 3
    builder.removeAll();
    builder.append(Text.of(TextColors.WHITE, " | \\     | "));
    builder.append(Text.of(TextColors.RED, " //\\\\    \\\\  "));
    builder.append(Text.of(TextColors.WHITE, "|     \\  "));
    builder.append(Text.of(TextColors.WHITE, "|"));
    Sponge.getServer().getConsole().sendMessage(builder.build());

    // Line 4
    builder.removeAll();
    builder.append(Text.of(TextColors.WHITE, " |  \\    | "));
    builder.append(Text.of(TextColors.RED, "||  \\\\    || "));
    builder.append(Text.of(TextColors.WHITE, "|     /  "));
    builder.append(Text.of(TextColors.WHITE, "|____  "));
    builder.append(Text.of(TextColors.DARK_GRAY, "   by ",
        TextColors.LIGHT_PURPLE, "MinecraftOnline"));
    Sponge.getServer().getConsole().sendMessage(builder.build());

    // Line 5
    builder.removeAll();
    builder.append(Text.of(TextColors.WHITE, " |   \\   | "));
    builder.append(Text.of(TextColors.RED, "||   \\\\   || "));
    builder.append(Text.of(TextColors.WHITE, "|____/   "));
    builder.append(Text.of(TextColors.WHITE, "|      "));
    builder.append(Text.of(TextColors.AQUA, "      v",
        SpongeNope.getInstance()
            .getPluginContainer()
            .getVersion().orElse("0.0.0")));
    Sponge.getServer().getConsole().sendMessage(builder.build());

    // Line 6
    builder.removeAll();
    builder.append(Text.of(TextColors.WHITE, " |    \\  | "));
    builder.append(Text.of(TextColors.RED, "||    \\\\  || "));
    builder.append(Text.of(TextColors.WHITE, "|        "));
    builder.append(Text.of(TextColors.WHITE, "|"));
    Sponge.getServer().getConsole().sendMessage(builder.build());

    // Line 7
    builder.removeAll();
    builder.append(Text.of(TextColors.WHITE, " |     \\ | "));
    builder.append(Text.of(TextColors.RED, " \\\\____\\\\//  "));
    builder.append(Text.of(TextColors.WHITE, "|        "));
    builder.append(Text.of(TextColors.WHITE, "|"));
    Sponge.getServer().getConsole().sendMessage(builder.build());

    // Line 8
    builder.removeAll();
    builder.append(Text.of(TextColors.WHITE, " |      \\| "));
    builder.append(Text.of(TextColors.RED, "  \\______/   "));
    builder.append(Text.of(TextColors.WHITE, "|        "));
    builder.append(Text.of(TextColors.WHITE, "|______"));
    Sponge.getServer().getConsole().sendMessage(builder.build());
    Sponge.getServer().getConsole().sendMessage(Text.of());

  }

  /**
   * Generates a {@link RuntimeException} for not being able to find
   * the location during an event while looking for a setting with
   * a given setting key and player.
   *
   * @param key        the setting key
   * @param eventClass the type of event
   * @param player     the player which is in question. Null if anonymous.
   * @return a supplier for the runtime exception
   */
  public static Supplier<RuntimeException> noLocation(SettingKey<?> key,
                                                      Class<? extends Event> eventClass,
                                                      @Nullable Player player) {
    return () -> new RuntimeException(String.format(
        "The relevant location for the dynamic event listener for "
            + "Setting Key %s and event class %s could not be found.",
        key.getId(),
        eventClass.getName())
        +
        (player == null
            ? ""
            : String.format(" The player is %s at position (%d, %d, %d) in world %s",
            player.getName(),
            player.getLocation().getBlockX(),
            player.getLocation().getBlockY(),
            player.getLocation().getBlockZ(),
            player.getLocation().getExtent().getName())));
  }
}

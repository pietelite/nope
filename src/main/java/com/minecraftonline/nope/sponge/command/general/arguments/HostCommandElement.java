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

package com.minecraftonline.nope.sponge.command.general.arguments;

import com.minecraftonline.nope.sponge.SpongeNope;
import com.minecraftonline.nope.common.host.Host;
import com.minecraftonline.nope.sponge.util.Format;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.StartsWithPredicate;

class HostCommandElement extends CommandElement {

  protected HostCommandElement(@Nullable Text key) {
    super(key);
  }

  @Nonnull
  @Override
  public Text getUsage(@NotNull CommandSource src) {
    return Text.of("<zone>");
  }

  @Nullable
  @Override
  protected Host parseValue(@Nonnull CommandSource source, CommandArgs args)
      throws ArgumentParseException {
    String hostName = args.next().toLowerCase();
    Host host = SpongeNope.getInstance().getHostTreeAdapter().getHosts().get(hostName);

    if (host != null) {
      return host;
    }

    throw new ArgumentParseException(Text.of("Zone ", Format.note(hostName), " does not exist!"),
        hostName,
        hostName.length());
  }

  @Nonnull
  @Override
  public List<String> complete(@Nonnull CommandSource src,
                               CommandArgs args,
                               @Nonnull CommandContext context) {
    final Predicate<String> startsWith = new StartsWithPredicate(args.nextIfPresent().orElse(""));
    return SpongeNope.getInstance().getHostTreeAdapter()
        .getHosts()
        .keySet()
        .stream()
        .filter(startsWith)
        .collect(Collectors.toList());
  }
}

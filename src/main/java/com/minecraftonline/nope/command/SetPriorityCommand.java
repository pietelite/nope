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

package com.minecraftonline.nope.command;

import com.minecraftonline.nope.Nope;
import com.minecraftonline.nope.arguments.NopeArguments;
import com.minecraftonline.nope.command.common.CommandNode;
import com.minecraftonline.nope.command.common.LambdaCommandNode;
import com.minecraftonline.nope.host.Host;
import com.minecraftonline.nope.permission.Permissions;
import com.minecraftonline.nope.util.Format;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class SetPriorityCommand extends LambdaCommandNode {

  SetPriorityCommand(CommandNode parent) {
    super(parent,
        Permissions.COMMAND_REGION_EDIT,
        Text.of("Allows the user to set the priority of a region"),
        "setpriority");

    addCommandElements(
        GenericArguments.flags()
            .valueFlag(NopeArguments.host(Text.of("region")), "r", "-region")
            .buildWith(GenericArguments.none()),
        GenericArguments.integer(Text.of("priority")));
    setExecutor((src, args) -> {
      int priority = args.requireOne("priority");

      Host host = args.<Host>getOne("region").orElse(NopeCommandRoot.inferHost(src).orElse(null));
      if (host == null) {
        return CommandResult.empty();
      }

      try {
        host.setPriority(priority);
      } catch (UnsupportedOperationException e) {
        src.sendMessage(Format.error("This host does not support having its priority set!"));
        return CommandResult.empty();
      } catch (IllegalArgumentException e) {
        src.sendMessage(Format.error(e.getMessage()));
        return CommandResult.empty();
      }

      Nope.getInstance().saveState();
      src.sendMessage(Format.success("Set priority of host ",
          Format.host(host),
          " to ",
          Format.note(priority)));

      return CommandResult.success();
    });
  }
}
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

package com.minecraftonline.nope.sponge.command;

import com.minecraftonline.nope.sponge.SpongeNope;
import com.minecraftonline.nope.sponge.command.general.arguments.NopeParameters;
import com.minecraftonline.nope.sponge.command.general.CommandNode;
import com.minecraftonline.nope.sponge.command.general.FlagDescription;
import com.minecraftonline.nope.common.host.Host;
import com.minecraftonline.nope.common.permission.Permissions;
import com.minecraftonline.nope.common.setting.SettingKey;
import com.minecraftonline.nope.common.setting.SettingValue;
import com.minecraftonline.nope.sponge.util.Format;
import java.util.Optional;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.text.Text;

/**
 * The permission UNAFFECTED in {@link com.minecraftonline.nope.common.permission.Permissions}
 * is given to high ranking players who should not be affected by Nope.
 * A setting can bypass this pass-through feature by setting "force affect"
 * on a Target to simulate the motion that the affecting of a player is being forced.
 */
class TargetForceCommand extends CommandNode {
  public TargetForceCommand(CommandNode parent) {
    super(parent,
        Permissions.COMMAND_EDIT,
        Text.of("Toggle whether the "
            + Permissions.UNRESTRICTED.get()
            + " permission is respected on this setting"),
        "force");
    addCommandElements(GenericArguments.flags()
            .valueFlag(NopeParameters.host(Text.of("zone")), "z", "-zone")
            .buildWith(GenericArguments.none()),
        NopeParameters.settingKey(Text.of("setting")));
    addFlagDescription(FlagDescription.ZONE);
    setExecutor((src, args) -> {
      Host host = args.<Host>getOne("zone").orElse(NopeCommandRoot.inferHost(src).orElse(null));
      if (host == null) {
        return CommandResult.empty();
      }
      SettingKey<Object> key = args.requireOne("setting");

      if (!key.isPlayerRestrictive()) {
        src.sendMessage(Format.error("This setting is not restrictive in nature towards players, ",
            "so unrestricted players are already affected by this setting"));
        return CommandResult.empty();
      }

      Optional<SettingValue<Object>> value = host.get(key);
      if (!value.isPresent()) {
        src.sendMessage(Format.error("The setting ",
            Format.settingKey(key, false),
            " is not set on zone ",
            Format.host(host)));
        return CommandResult.empty();
      }

      value.get().getTarget().setForceAffect(!value.get().getTarget().isForceAffect());

      if (value.get().getTarget().isForceAffect()) {
        src.sendMessage(Format.success("The setting ",
            Format.settingKey(key, false),
            " now bypasses the ",
            Format.note(Permissions.UNRESTRICTED.get()),
            " permission"));
      } else {
        src.sendMessage(Format.success("The setting ",
            Format.settingKey(key, false),
            " now does not bypass the ",
            Format.note(Permissions.UNRESTRICTED.get()),
            " permission"));
      }
      SpongeNope.getInstance().saveState();
      return CommandResult.success();
    });
  }

  @Override
  public CommandResult execute(CommandContext context) throws CommandException {
    return null;
  }
}

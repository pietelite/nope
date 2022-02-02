/*
 * MIT License
 *
 * Copyright (c) Pieter Svenson
 * Copyright (c) MinecraftOnline
 * Copyright (c) contributors
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

package com.minecraftonline.nope.sponge.command.tree;

import com.minecraftonline.nope.common.permission.Permissions;
import com.minecraftonline.nope.sponge.command.CommandNode;
import com.minecraftonline.nope.sponge.command.FunctionlessCommandNode;
import com.minecraftonline.nope.sponge.command.tree.tool.CuboidToolCommand;
import com.minecraftonline.nope.sponge.command.tree.tool.CylinderToolCommand;
import com.minecraftonline.nope.sponge.command.tree.tool.SlabToolCommand;
import com.minecraftonline.nope.sponge.command.tree.tool.SphereToolCommand;

public class ToolCommand extends FunctionlessCommandNode {
  public ToolCommand(CommandNode parent) {
    super(parent, Permissions.CREATE,
        "Spawn a tool for accessibility", "tool");
    addChild(new CuboidToolCommand(this));
    addChild(new CylinderToolCommand(this));
    addChild(new SlabToolCommand(this));
    addChild(new SphereToolCommand(this));
  }
}

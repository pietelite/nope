/*
 *
 * MIT License
 *
 * Copyright (c) 2022 Pieter Svenson
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

package com.minecraftonline.nope.common.setting.sets;

import com.minecraftonline.nope.common.struct.Described;
import com.minecraftonline.nope.common.struct.HashAltSet;

public class BlockChangeSet extends HashAltSet.FewEnum<BlockChangeSet.BlockChange> {

  public BlockChangeSet() {
    super(BlockChangeSet.BlockChange.class);
  }

  /**
   * Enumeration for all explosive types considered by Nope.
   */
  public enum BlockChange implements Described {
    BREAK("Whether blocks can be replaced with air"),
    PLACE("Whether blocks can replace air"),
    MODIFY("Whether blocks can changed to other blocks or change internally"),
    GROW("Whether blocks may be grown"),
    DECAY("Whether blocks may decay");

    private final String description;

    BlockChange(String description) {
      this.description = description;
    }

    @Override
    public String description() {
      return description;
    }

    @Override
    public String toString() {
      return name().toLowerCase();
    }
  }
}

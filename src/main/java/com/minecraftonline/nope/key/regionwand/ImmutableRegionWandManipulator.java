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

package com.minecraftonline.nope.key.regionwand;

import com.minecraftonline.nope.key.NopeKeys;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBooleanData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableRegionWandManipulator extends AbstractImmutableBooleanData<ImmutableRegionWandManipulator, RegionWandManipulator> {
  public ImmutableRegionWandManipulator(boolean value) {
    super(NopeKeys.REGION_WAND, value,  false);
  }

  public ImmutableValue<Boolean> isWand() {
    return this.getValueGetter();
  }

  @Override
  public RegionWandManipulator asMutable() {
    return new RegionWandManipulator(getValue());
  }

  @Override
  public int getContentVersion() {
    return 0;
  }
}

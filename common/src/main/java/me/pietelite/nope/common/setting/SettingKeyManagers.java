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

package me.pietelite.nope.common.setting;

import me.pietelite.nope.common.api.setting.BlockChange;
import me.pietelite.nope.common.api.setting.DamageCause;
import me.pietelite.nope.common.api.setting.Explosive;
import me.pietelite.nope.common.api.setting.Movement;
import me.pietelite.nope.common.setting.manager.BooleanKeyManager;
import me.pietelite.nope.common.setting.manager.IntegerKeyManager;
import me.pietelite.nope.common.setting.manager.OptionalStringKeyManager;
import me.pietelite.nope.common.setting.manager.PolyAllCapsEnumKeyManager;
import me.pietelite.nope.common.setting.manager.PolyStringKeyManager;
import me.pietelite.nope.common.setting.manager.StateKeyManager;
import me.pietelite.nope.common.setting.manager.StringKeyManager;
import me.pietelite.nope.common.setting.manager.ToggleKeyManager;
import me.pietelite.nope.common.setting.sets.BlockChangeSet;
import me.pietelite.nope.common.setting.sets.DamageCauseSet;
import me.pietelite.nope.common.setting.sets.ExplosiveSet;
import me.pietelite.nope.common.setting.sets.MovementSet;

/**
 * A utility class to store constant managers, which are used in defining {@link SettingKey}s.
 */
public final class SettingKeyManagers {

  public static final BooleanKeyManager BOOLEAN_KEY_MANAGER = new BooleanKeyManager();
  public static final IntegerKeyManager INTEGER_KEY_MANAGER = new IntegerKeyManager();
  public static final PolyAllCapsEnumKeyManager<BlockChange, BlockChangeSet>
      POLY_BLOCK_CHANGE_KEY_MANAGER =
      new PolyAllCapsEnumKeyManager<>(BlockChange.class, BlockChangeSet.class, BlockChangeSet::new);
  public static final PolyStringKeyManager POLY_BLOCK_KEY_MANAGER =
      new PolyStringKeyManager();
  public static final PolyAllCapsEnumKeyManager<DamageCause, DamageCauseSet>
      POLY_DAMAGE_SOURCE_KEY_MANAGER =
      new PolyAllCapsEnumKeyManager<>(DamageCause.class, DamageCauseSet.class, DamageCauseSet::new);
  public static final PolyStringKeyManager POLY_ENTITY_KEY_MANAGER =
      new PolyStringKeyManager();
  public static final PolyAllCapsEnumKeyManager<Explosive, ExplosiveSet>
      POLY_EXPLOSIVE_KEY_MANAGER =
      new PolyAllCapsEnumKeyManager<>(Explosive.class, ExplosiveSet.class, ExplosiveSet::new);
  public static final PolyStringKeyManager POLY_GROWABLE_KEY_MANAGER =
      new PolyStringKeyManager();
  public static final PolyAllCapsEnumKeyManager<Movement, MovementSet> POLY_MOVEMENT_KEY_MANAGER =
      new PolyAllCapsEnumKeyManager<>(Movement.class, MovementSet.class, MovementSet::new);
  public static final PolyStringKeyManager POLY_PLUGIN_MANAGER =
      new PolyStringKeyManager();
  public static final StateKeyManager STATE_KEY_MANAGER = new StateKeyManager();
  public static final StringKeyManager STRING_KEY_MANAGER = new StringKeyManager();
  public static final OptionalStringKeyManager OPTIONAL_STRING_KEY_MANAGER = new OptionalStringKeyManager();
  public static final ToggleKeyManager TOGGLE_KEY_MANAGER = new ToggleKeyManager();

  private SettingKeyManagers() {
  }
}

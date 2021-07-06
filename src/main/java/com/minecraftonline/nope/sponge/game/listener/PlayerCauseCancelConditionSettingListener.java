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

package com.minecraftonline.nope.sponge.game.listener;

import com.minecraftonline.nope.common.setting.SettingKey;
import java.util.function.BiPredicate;
import javax.annotation.Nonnull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;

/**
 * An accessibility class for cancelling events if a player is in the cause stack
 * of an event and they are found to have a specific state on a specific
 * setting.
 *
 * @param <E> the event type for which to listen and cancel
 */
class PlayerCauseCancelConditionSettingListener<E extends Event & Cancellable>
    extends PlayerCauseSettingListener<E> {

  public PlayerCauseCancelConditionSettingListener(@Nonnull SettingKey<?> key,
                                                   @Nonnull Class<E> eventClass,
                                                   @Nonnull BiPredicate<E, Player> canceler) {
    super(key,
        eventClass,
        (event, player) -> {
          if (canceler.test(event, player)) {
            event.setCancelled(true);
          }
        });
  }
}

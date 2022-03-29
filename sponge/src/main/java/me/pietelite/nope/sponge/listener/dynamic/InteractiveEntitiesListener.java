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

package me.pietelite.nope.sponge.listener.dynamic;

import me.pietelite.nope.common.struct.AltSet;
import me.pietelite.nope.sponge.api.event.SettingEventContext;
import me.pietelite.nope.sponge.api.event.SettingEventListener;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.entity.InteractEntityEvent;

/**
 * Implements {@link me.pietelite.nope.common.setting.SettingKeys#INTERACTIVE_ENTITIES}.
 */
public class InteractiveEntitiesListener implements
    SettingEventListener<AltSet<String>, InteractEntityEvent.Secondary> {
  @Override
  public void handle(SettingEventContext<AltSet<String>, InteractEntityEvent.Secondary> context) {
    if (context.event().source() instanceof ServerPlayer) {
      final ServerPlayer player = (ServerPlayer) context.event().source();
      final String entityName = EntityTypes.registry().valueKey(context.event().entity().type()).value();
      if (!context.lookup(player, context.event().entity().serverLocation()).contains(entityName)
          || !context.lookup(player, player.serverLocation()).contains(entityName)) {
        context.event().setCancelled(true);
      }
    }
  }
}
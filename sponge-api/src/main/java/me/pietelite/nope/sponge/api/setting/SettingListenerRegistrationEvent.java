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

package me.pietelite.nope.sponge.api.setting;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.EventContext;
import org.spongepowered.api.event.lifecycle.LifecycleEvent;

/**
 * A lifecycle event with which to register {@link SettingEventListener}s by wrapping them
 * in {@link SettingListenerRegistration}s.
 */
public class SettingListenerRegistrationEvent implements LifecycleEvent {

  private final SettingListenerRegistrar registrar;
  private final Game game;
  private final Cause cause;
  private final Object source;
  private final EventContext context;

  /**
   * Generic constructor.
   *
   * @param registrar the registrar with which to actually register the listeners
   * @param game      the game, for the {@link LifecycleEvent}
   * @param cause     the cause, for the {@link LifecycleEvent}
   * @param source    the source, for the {@link LifecycleEvent}
   * @param context   the context, for the {@link LifecycleEvent}
   */
  public SettingListenerRegistrationEvent(SettingListenerRegistrar registrar,
                                          Game game, Cause cause,
                                          Object source, EventContext context) {
    this.registrar = registrar;
    this.game = game;
    this.cause = cause;
    this.source = source;
    this.context = context;
  }

  public SettingListenerRegistrar registrar() {
    return registrar;
  }

  @Override
  public Game game() {
    return game;
  }

  @Override
  public Cause cause() {
    return cause;
  }

  @Override
  public Object source() {
    return source;
  }

  @Override
  public EventContext context() {
    return context;
  }
}

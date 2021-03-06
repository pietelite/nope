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

package me.pietelite.nope.sponge.config;

import me.pietelite.nope.common.setting.SettingKey;
import me.pietelite.nope.common.setting.SettingValue;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

/**
 * A serializer for serializing values stored in {@link me.pietelite.nope.common.setting.Setting}s
 * for the purpose of persisting data in configuration files.
 *
 * @param <M> the manager type
 */
@SuppressWarnings("checkstyle:LineLength")
public interface SettingValueConfigSerializer<M extends SettingKey.Manager<?, ?>> {

  Class<M> managerClass();

  <X, Y extends SettingValue<X>, Z extends SettingKey.Manager<X, Y>> CommentedConfigurationNode serialize(Z manager, Y value) throws SerializationException;

  <X, Y extends SettingValue<X>, Z extends SettingKey.Manager<X, Y>> Y deserialize(Z manager, ConfigurationNode configNode) throws SerializationException;

}

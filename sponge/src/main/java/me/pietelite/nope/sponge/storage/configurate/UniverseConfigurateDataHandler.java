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

package me.pietelite.nope.sponge.storage.configurate;

import me.pietelite.nope.common.Nope;
import me.pietelite.nope.common.host.Universe;
import me.pietelite.nope.common.storage.UniverseDataHandler;
import me.pietelite.nope.sponge.api.config.SettingValueConfigSerializerRegistrar;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.ConfigurationLoader;

/**
 * The data handler for the {@link Universe} using Configurate.
 */
public class UniverseConfigurateDataHandler
    extends SettingsConfigurateDataHandler implements UniverseDataHandler {

  private final ConfigurationLoader<CommentedConfigurationNode> loader;

  public UniverseConfigurateDataHandler(ConfigurationLoader<CommentedConfigurationNode> loader,
                                        SettingValueConfigSerializerRegistrar serializerRegistrar) {
    super(serializerRegistrar);
    this.loader = loader;
  }

  @Override
  public void save(Universe universe) {
    try {
      CommentedConfigurationNode root = settingCollectionRoot(universe);
      loader.save(root);
    } catch (ConfigurateException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Universe load() {
    Universe universe = new Universe(Nope.GLOBAL_HOST_NAME);
    try {
      CommentedConfigurationNode node = loader.load();
      if (!node.virtual()) {
        universe.setAll(deserializeSettings(node.node("settings").childrenMap()));
      }
      return universe;
    } catch (ConfigurateException e) {
      e.printStackTrace();
      return null;
    }
  }

}

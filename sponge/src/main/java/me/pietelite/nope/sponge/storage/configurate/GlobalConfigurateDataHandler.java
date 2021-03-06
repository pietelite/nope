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

import java.util.List;
import me.pietelite.nope.common.Nope;
import me.pietelite.nope.common.host.Global;
import me.pietelite.nope.common.host.HostedProfile;
import me.pietelite.nope.common.storage.UniverseDataHandler;
import me.pietelite.nope.sponge.config.SettingValueConfigSerializerRegistrar;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.ConfigurationLoader;

/**
 * The data handler for the {@link Global} using Configurate.
 */
public class GlobalConfigurateDataHandler
    extends SettingsConfigurateDataHandler implements UniverseDataHandler {

  private final ConfigurationLoader<CommentedConfigurationNode> loader;

  public GlobalConfigurateDataHandler(ConfigurationLoader<CommentedConfigurationNode> loader,
                                      SettingValueConfigSerializerRegistrar serializerRegistrar) {
    super(serializerRegistrar);
    this.loader = loader;
  }

  @Override
  public void save(Global global) {
    try {
      CommentedConfigurationNode root = loader.load();
      root.node("profiles").setList(HostedProfile.class, global.hostedProfiles());
      loader.save(root);
    } catch (ConfigurateException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Global load() {
    Global global = new Global(Nope.GLOBAL_ID);
    try {
      CommentedConfigurationNode node = loader.load();
      if (!node.node("profiles").virtual()) {
        List<HostedProfile> profiles = node.node("profiles").getList(HostedProfile.class);
        if (profiles != null) {
          for (HostedProfile p : profiles) {
            if (!p.profile().name().equals(Nope.GLOBAL_ID)) {
              global.hostedProfiles().add(p);
            }
          }
        }
      }
      return global;
    } catch (ConfigurateException e) {
      e.printStackTrace();
      return null;
    }
  }

}

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

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import me.pietelite.nope.common.Nope;
import me.pietelite.nope.common.host.HostedProfile;
import me.pietelite.nope.common.host.Scene;
import me.pietelite.nope.common.math.Volume;
import me.pietelite.nope.common.storage.SceneDataHandler;
import me.pietelite.nope.sponge.SpongeNope;
import me.pietelite.nope.sponge.storage.configurate.loader.DynamicIndividualConfigurationLoader;
import me.pietelite.nope.sponge.storage.configurate.loader.DynamicIndividualFilePath;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;

/**
 * The {@link SceneDataHandler} implemented with Configurate.
 */
public class SceneConfigurateDataHandler implements SceneDataHandler {

  private final DynamicIndividualConfigurationLoader loader;
  private final DynamicIndividualFilePath filePath;
  private final Collection<ConfigurationLoader<CommentedConfigurationNode>> allLoaders;

  public SceneConfigurateDataHandler(DynamicIndividualConfigurationLoader loader,
                                     DynamicIndividualFilePath filePath,
                                     Collection<ConfigurationLoader<CommentedConfigurationNode>> allLoaders) {
    this.loader = loader;
    this.filePath = filePath;
    this.allLoaders = allLoaders;
  }

  @Override
  public void destroy(Scene scene) {
    File file = filePath.path(scene.scope(), scene.name()).toFile();
    if (file.exists()) {
      if (!file.delete()) {
        SpongeNope.instance().logger().error("Error when trying to destroy scene "
            + scene.name()
            + " by deleting its configuration file");
      }
    }
  }

  @Override
  public void save(Scene scene) {
    try {
      ConfigurationLoader<CommentedConfigurationNode> loader = this.loader.loader(scene.scope(),
          scene.name());
      CommentedConfigurationNode root = loader.load();
      root.node("scope").set(scene.scope());
      root.node("name").set(scene.name());
      root.node("priority").set(scene.priority());
      root.node("profiles").setList(HostedProfile.class, scene.hostedProfiles());
      root.node("zones").setList(Volume.class, scene.volumes());
      loader.save(root);
    } catch (ConfigurateException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Collection<Scene> load() {
    LinkedList<Scene> scenes = new LinkedList<>();
    // Need to queue other loaders if the one given does not have their parent loaded yet
    for (ConfigurationLoader<CommentedConfigurationNode> loader : allLoaders) {
      ConfigurationNode root;
      String scope;
      String name;
      try {
        root = loader.load();
        scope = root.node("scope").get(String.class);
        name = root.node("name").get(String.class);
      } catch (ConfigurateException e) {
        Nope.instance().logger().error("Error loading Scene: " + e.getMessage());
        e.printStackTrace();
        continue;
      }
      if (name == null) {
        continue;
      }

      try {
        int priority = root.node("priority").getInt();
        Scene scene = new Scene(scope, name, priority);
        try {
          if (!root.node("profiles").virtual()) {
            List<HostedProfile> profiles = root.node("profiles").getList(HostedProfile.class);
            if (profiles != null) {
              scene.hostedProfiles().addAll(profiles);
            }
          }
        } catch (Exception e) {
          Nope.instance().logger().error("Failed parsing profiles for scene: " + name);
          throw e;
        }
        try {
          if (!root.node("zones").virtual()) {
            List<Volume> volumes = root.node("zones").getList(Volume.class);
            if (volumes != null) {
              scene.volumes().addAll(volumes);
            }
          }
        } catch (Exception e) {
          Nope.instance().logger().error("Failed parsing zones for scene: " + name);
          throw e;
        }
        scenes.add(scene);
      } catch (ConfigurateException e) {
        Nope.instance().logger().error(String.format("Error loading Scene %s: " + e.getMessage()
            + ". Is it in the right format?", name));
        e.printStackTrace();
      }
    }
    return scenes;
  }

}

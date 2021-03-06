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

package me.pietelite.nope.sponge.command.parameters;

import io.leangen.geantyref.TypeToken;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import me.pietelite.nope.common.api.setting.SettingCategory;
import me.pietelite.nope.common.host.Host;
import me.pietelite.nope.common.host.Profile;
import me.pietelite.nope.common.host.Scene;
import me.pietelite.nope.common.math.Cuboid;
import me.pietelite.nope.common.math.Cylinder;
import me.pietelite.nope.common.math.Sphere;
import me.pietelite.nope.common.setting.SettingKey;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.world.server.ServerWorld;

/**
 * Utility class to store various {@link Parameter.Key}s for commands.
 */
@SuppressWarnings("checkstyle:LineLength")
public final class ParameterKeys {

  public static final Parameter.Key<Cuboid> CUBOID = Parameter.key("cuboid", Cuboid.class);
  public static final Parameter.Key<Cylinder> CYLINDER = Parameter.key("cylinder", Cylinder.class);
  public static final Parameter.Key<String> DESCRIPTION = Parameter.key("parameter", String.class);
  public static final Parameter.Key<Host> HOST = Parameter.key("host", Host.class);
  public static final Parameter.Key<String> ID = Parameter.key("name", String.class);
  public static final Parameter.Key<String> PERMISSION = Parameter.key("permission", String.class);
  public static final Parameter.Key<Boolean> PERMISSION_VALUE = Parameter.key("permission-value", Boolean.class);
  public static final Parameter.Key<ServerPlayer> PLAYER = Parameter.key("player", ServerPlayer.class);
  public static final Parameter.Key<Set<CompletableFuture<GameProfile>>> PLAYER_LIST = Parameter.key("player-list", new TypeToken<Set<CompletableFuture<GameProfile>>>() {
  });
  public static final Parameter.Key<ServerPlayer> PLAYER_OPTIONAL = Parameter.key("player", ServerPlayer.class);
  public static final Parameter.Key<Double> POS_X = Parameter.key("position-x", Double.class);
  public static final Parameter.Key<Double> POS_X_1 = Parameter.key("position-x-1", Double.class);
  public static final Parameter.Key<Double> POS_X_2 = Parameter.key("position-x-2", Double.class);
  public static final Parameter.Key<Double> POS_Y = Parameter.key("position-y", Double.class);
  public static final Parameter.Key<Double> POS_Y_1 = Parameter.key("position-y-1", Double.class);
  public static final Parameter.Key<Double> POS_Y_2 = Parameter.key("position-y-2", Double.class);
  public static final Parameter.Key<Double> POS_Z = Parameter.key("position-z", Double.class);
  public static final Parameter.Key<Double> POS_Z_1 = Parameter.key("position-z-1", Double.class);
  public static final Parameter.Key<Double> POS_Z_2 = Parameter.key("position-z-2", Double.class);
  public static final Parameter.Key<Integer> PRIORITY = Parameter.key("priority", Integer.class);
  public static final Parameter.Key<Profile> PROFILE = Parameter.key("profile", Profile.class);
  public static final Parameter.Key<Double> RADIUS = Parameter.key("radius", Double.class);
  public static final Parameter.Key<String> REGEX = Parameter.key("regex", String.class);
  public static final Parameter.Key<Scene> SCENE = Parameter.key("zone", Scene.class);
  public static final Parameter.Key<SettingCategory> SETTING_CATEGORY = Parameter.key("setting-category", SettingCategory.class);
  public static final Parameter.Key<SettingKey<?, ?, ?>> SETTING_KEY = Parameter.key("setting-key", new TypeToken<SettingKey<?, ?, ?>>() {
  });
  public static final Parameter.Key<String> SETTING_VALUE = Parameter.key("setting-value", String.class);
  public static final Parameter.Key<ParameterValueTypes.SettingValueAlterType> SETTING_VALUE_ALTER_TYPE = Parameter.key("alter-type", ParameterValueTypes.SettingValueAlterType.class);
  public static final Parameter.Key<Sphere> SPHERE = Parameter.key("sphere", Sphere.class);
  public static final Parameter.Key<TargetOption> TARGET_OPTION = Parameter.key("target-option", TargetOption.class);
  public static final Parameter.Key<String> VALUE = Parameter.key("value", String.class);
  public static final Parameter.Key<Integer> VOLUME_INDEX = Parameter.key("index", Integer.class);
  public static final Parameter.Key<ServerWorld> WORLD = Parameter.key("world", ServerWorld.class);

  private ParameterKeys() {
  }
}

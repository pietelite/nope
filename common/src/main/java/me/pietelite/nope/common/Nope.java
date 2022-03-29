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

package me.pietelite.nope.common;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.pietelite.nope.common.debug.DebugManager;
import me.pietelite.nope.common.host.HostSystem;
import me.pietelite.nope.common.permission.Permission;
import me.pietelite.nope.common.setting.SettingKeyStore;
import me.pietelite.nope.common.storage.DataHandler;
import me.pietelite.nope.common.util.Logger;

/**
 * Generic plugin file to manage global plugin state.
 */
@RequiredArgsConstructor
public abstract class Nope {

  /* CONSTANTS */
  public static final String GLOBAL_HOST_NAME = "_global";
  public static final String REPO_URL = "https://github.com/pietelite/nope/";
  public static final int WORLD_DEPTH = 512;
  public static final int WORLD_RADIUS = 100000;
  public static final String ZONE_CONFIG_BACKUP_FILENAME = "zones-backup.conf";
  public static final String ZONE_CONFIG_FILENAME = "zones.conf";
  @Setter
  @Getter
  @Accessors(fluent = true)
  private static Nope instance;

  @Getter
  @Accessors(fluent = true)
  private final Logger logger;
  @Getter
  @Accessors(fluent = true)
  private final SettingKeyStore settingKeys = new SettingKeyStore();
  @Getter
  @Accessors(fluent = true)
  private final DebugManager debugManager = new DebugManager();
  @Getter
  @Setter
  @Accessors(fluent = true)
  private DataHandler data;
  @Getter
  @Setter
  @Accessors(fluent = true)
  private Path path;
  @Getter
  @Setter
  @Accessors(fluent = true)
  private HostSystem hostSystem;

  public final boolean hasPermission(UUID playerUuid, Permission permission) {
    return hasPermission(playerUuid, permission.get());
  }

  public abstract boolean hasPermission(UUID playerUuid, String permission);

  public abstract void scheduleAsyncIntervalTask(Runnable runnable, int interval, TimeUnit intervalUnit);

}
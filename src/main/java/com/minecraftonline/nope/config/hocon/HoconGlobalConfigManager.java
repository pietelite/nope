package com.minecraftonline.nope.config.hocon;

import com.minecraftonline.nope.config.GlobalConfigManager;
import com.minecraftonline.nope.config.supplier.HoconLoaderSupplier;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;

import java.nio.file.Path;

public class HoconGlobalConfigManager extends GlobalConfigManager {

  public HoconGlobalConfigManager(Path configDir) {
    super(configDir, new HoconLoaderSupplier(getTypeSerializers()));
  }
}
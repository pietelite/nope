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

package com.minecraftonline.nope.common.setting.keys;

import com.google.gson.Gson;
import com.minecraftonline.nope.common.setting.SettingKey;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Setting to store an enum.
 *
 * @param <E> the enum type
 */
public class EnumSettingKey<E extends Enum<E>> extends SettingKey<E> {

  private final Class<E> enumClass;

  public EnumSettingKey(String id, E defaultData, Class<E> enumClass) {
    super(id, defaultData);
    this.enumClass = enumClass;
  }

  @Override
  protected Object serializeDataGenerified(E data) {
    return new Gson().toJsonTree(data.name().toLowerCase());
  }

  @Override
  public E deserializeDataGenerified(Object serialized) {
    return parse(serialized.getAsString());
  }

  @Override
  public E parse(String s) throws ParseSettingException {
    try {
      return Enum.valueOf(enumClass, s.toUpperCase());
    } catch (IllegalArgumentException ex) {
      throw new ParseSettingException(s + " is not a valid "
          + enumClass.getSimpleName()
          + " type. "
          + (
          (enumClass.getEnumConstants().length <= 8)
              ? "Allowed types: "
              + Arrays.stream(enumClass.getEnumConstants()).map(e ->
              e.toString().toLowerCase()).collect(Collectors.joining(", "))
              : ""));
    }
  }

  @Override
  public Optional<List<String>> getParsable() {
    return Optional.of(Arrays.stream(enumClass.getEnumConstants())
        .map(E::toString)
        .map(String::toLowerCase)
        .collect(Collectors.toList()));
  }
}

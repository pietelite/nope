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

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.minecraftonline.nope.common.setting.SettingLibrary;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * A setting key to store a set of strings.
 */
public class StringSetSettingKey extends SetSettingKey<String> {

  public StringSetSettingKey(String id, Set<String> defaultValue) {
    super(id, defaultValue);
  }

  @Override
  public JsonElement elementToJsonGenerified(String value) {
    return new JsonPrimitive(value);
  }

  @Override
  public String elementFromJsonGenerified(JsonElement jsonElement) {
    return jsonElement.getAsString();
  }

  @Override
  public Set<String> parse(String s) throws ParseSettingException {
    return new HashSet<>(Arrays.asList(s.split(SettingLibrary.SET_SPLIT_REGEX)));
  }

  @NotNull
  @Override
  public String printElement(String element) {
    return element;
  }
}

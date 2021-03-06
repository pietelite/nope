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

package me.pietelite.nope.common.setting.manager;

import java.util.Map;
import java.util.stream.Collectors;
import me.pietelite.nope.common.setting.SettingKey;
import org.jetbrains.annotations.NotNull;

/**
 * A toggling version of the {@link BooleanKeyManager}.
 * The values are either "on" or "off".
 */
public class ToggleKeyManager extends BooleanKeyManager {

  private final Map<String, Object> suggestions;

  public ToggleKeyManager() {
    super();
    this.suggestions = this.options.entrySet().stream()
        .filter(entry -> entry.getKey().equals("on") || entry.getKey().equals("off"))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Override
  public @NotNull Map<String, Object> elementSuggestions() {
    return suggestions;
  }

  @Override
  public @NotNull String printData(@NotNull Boolean data) {
    return data ? "on" : "off";
  }

  @Override
  public String dataTypeName() throws SettingKey.ParseSettingException {
    return "Toggle (Boolean)";
  }
}

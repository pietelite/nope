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

import java.util.Optional;
import me.pietelite.nope.common.setting.SettingKey;
import org.jetbrains.annotations.NotNull;

/**
 * A manager for unary setting keys that evaluate to an {@link Optional} storing a {@link String}.
 */
public class OptionalStringKeyManager extends SettingKey.Manager.Unary<Optional<String>> {

  @Override
  @SuppressWarnings("unchecked")
  public Class<Optional<String>> dataType() throws SettingKey.ParseSettingException {
    return (Class<Optional<String>>) (Class<?>) Optional.class;
  }

  @Override
  public Optional<String> createAlternate(Optional<String> data) {
    if (data.isPresent()) {
      return Optional.empty();
    } else {
      return Optional.of(":)");
    }
  }

  @Override
  public Optional<String> parseData(String data) throws SettingKey.ParseSettingException {
    if (data.equals("")) {
      return Optional.empty();
    }
    return Optional.of(data);
  }

  @Override
  public @NotNull String printData(@NotNull Optional<String> value) {
    return value.orElse("");
  }

  @Override
  public String dataTypeName() throws SettingKey.ParseSettingException {
    return "Optional of String";
  }
}

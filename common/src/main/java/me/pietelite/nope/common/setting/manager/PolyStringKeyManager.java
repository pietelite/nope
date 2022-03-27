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
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.pietelite.nope.common.setting.SettingKey;
import me.pietelite.nope.common.struct.HashAltSet;
import org.jetbrains.annotations.NotNull;

/**
 * A manager for keys that store multiple strings.
 *
 * @param <S> the special type of set of strings
 */
@Accessors(fluent = true)
public class PolyStringKeyManager<S extends HashAltSet<String>> extends SettingKey.Manager.Poly<String, S> {

  private final Supplier<? extends S> setConstructor;
  @Setter
  private Supplier<Map<String, Object>> elementOptions = null;
  @Setter
  private Function<String, String> parser = null;

  public PolyStringKeyManager(Supplier<? extends S> setConstructor) {
    this.setConstructor = setConstructor;
  }

  @Override
  @NotNull
  public String printElement(String element) {
    return element;
  }

  @Override
  public String parseElement(String element) {
    if (parser == null) {
      return element;
    } else {
      return parser.apply(element);
    }
  }

  @Override
  public S createSet() {
    return this.setConstructor.get();
  }

  @Override
  public @NotNull Map<String, Object> elementOptionsWithoutGroups() {
    if (this.elementOptions == null) {
      return super.elementOptions();
    } else {
      return this.elementOptions.get();
    }
  }

}

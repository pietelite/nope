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

package me.pietelite.nope.common.api.edit;

import java.util.NoSuchElementException;
import me.pietelite.nope.common.Nope;
import me.pietelite.nope.common.host.Profile;
import me.pietelite.nope.common.setting.SettingKey;
import me.pietelite.nope.common.setting.SettingValue;

/**
 * Implementation of the {@link SingleValueSettingEditor}.
 *
 * @param <T> the value type of the setting
 */
public class SingleValueSettingEditorImpl<T> extends SettingEditorImpl
    implements SingleValueSettingEditor<T> {

  private final Class<T> type;

  public SingleValueSettingEditorImpl(Profile profile, String setting, Class<T> type) {
    super(profile, setting);
    this.type = type;
  }

  @Override
  public void set(T value) {
    profile.verifyExistence();
    SettingKey.Unary<T> key = Nope.instance().settingKeys().getUnarySetting(setting, type);
    profile.setValue(key, SettingValue.Unary.of(value));
  }

  @Override
  public T get() {
    profile.verifyExistence();
    SettingKey.Unary<T> key = Nope.instance().settingKeys().getUnarySetting(setting, type);
    return profile.getValue(key).map(SettingValue.Unary::get).orElseThrow(NoSuchElementException::new);
  }

}

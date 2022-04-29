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

package me.pietelite.nope.common.api;

import java.util.UUID;
import me.pietelite.nope.common.api.edit.SystemEditor;
import me.pietelite.nope.common.api.evaluate.Evaluator;
import me.pietelite.nope.common.api.register.SettingManagers;
import me.pietelite.nope.common.api.struct.AltSet;
import org.jetbrains.annotations.Nullable;

/**
 * A developer API for all platform implementations of Nope.
 */
public interface NopeService {

  /**
   * A tool for retrieving Nope's managers that handle the data for setting keys
   * and provide their builders.
   *
   * @return the managers
   */
  SettingManagers settingManagers();

  SystemEditor editSystem();

  Evaluator evaluator();

}
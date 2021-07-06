/*
 * MIT License
 *
 * Copyright (c) 2020 MinecraftOnline
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

package com.minecraftonline.nope.common.setting;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The key object associated inside a {@link Setting}.
 * One or more {@link SettingValue} is normally associated with
 * a single {@link SettingKey} instantiated in multiple {@link Setting}s.
 *
 * <p>This object is abstract because every different type of data stored
 * in the {@link SettingValue} may have unique serializing logic, so
 * every different generic type will require its own implementation.
 *
 * @param <T> the type of data which is ultimately keyed under this key
 */
public abstract class SettingKey<T> {

  /**
   * The global unique identifier for this key.
   */
  @Getter
  private final String id;

  /**
   * The default data of a setting keyed with this object.
   * This is what determines the behavior associated with this key
   * if the user does not specify something different.
   */
  @Getter
  private final T defaultData;
  @Getter
  @Setter
  @Nullable
  private String description = null;
  @Getter
  @Setter
  @Nullable
  private String blurb = null;
  @Getter
  @Setter
  @NotNull
  private CategoryType category = CategoryType.MISC;
  @Getter
  @Setter
  private boolean implemented = true;
  @Getter
  @Setter
  private boolean unnaturalDefault = false;
  @Getter
  @Setter
  private boolean global = false;
  @Getter
  @Setter
  private boolean playerRestrictive = false;

  protected SettingKey(String id, T defaultData) {
    this.id = id;
    this.defaultData = defaultData;
  }

  /**
   * Convert some data into a Json structure.
   * Data must be of the type of this object's generic type.
   * Uses {@link #dataToJsonGenerified(Object)}.
   *
   * @param data the encoding data
   * @return Json structure representing data
   */
  public final JsonElement dataToJson(Object data) {
    return dataToJsonGenerified(cast(data));
  }

  /**
   * Convert some data into a Json structure.
   *
   * @param data the encoding data
   * @return json structure representing data
   */
  protected JsonElement dataToJsonGenerified(T data) {
    return new Gson().toJsonTree(data);
  }

  /**
   * Parse some data from a Json structure.
   * Data will be of the type of this object's generic type.
   * Uses {@link #dataFromJsonGenerified(JsonElement)}.
   *
   * @param json Json structure
   * @return the data represented by the Json structure
   */
  public final Object dataFromJson(JsonElement json) throws ParseSettingException {
    return dataFromJsonGenerified(json);
  }

  /**
   * Parse some data from a Json structure.
   *
   * @param json Json structure
   * @return the data represented by the Json structure
   */
  public T dataFromJsonGenerified(JsonElement json) throws ParseSettingException {
    return new Gson().fromJson(json, valueType());
  }

  /**
   * Create a readable String version of the data.
   *
   * @param data the data to print
   * @return data in a readable form
   */
  @NotNull
  public String print(T data) {
    JsonElement asJson = dataToJson(data);
    if (asJson.isJsonPrimitive() && asJson.getAsJsonPrimitive().isString()) {
      return asJson.getAsString();
    }
    return asJson.toString();
  }

  /**
   * Parse some data in some custom format.
   * Used for dealing with data from in-game usages of
   * declaring data.
   *
   * @param data string representation of data
   * @return the data object
   * @throws ParseSettingException if data cannot be parsed
   */
  public T parse(String data) throws ParseSettingException {
    return new Gson().fromJson(data, valueType());
  }

  /**
   * Get a list of all parsable strings for data stored
   * under this SettingKey.
   *
   * @return a list of parsable strings only if there are finite possibilities
   */
  public Optional<List<String>> getParsable() {
    return Optional.empty();
  }

  /**
   * Get the class type of this object's generic type.
   *
   * @return the generic class
   */
  @SuppressWarnings("unchecked")
  public final Class<T> valueType() {
    return (Class<T>) defaultData.getClass();
  }

  /**
   * Cast the object to this object's generic type.
   *
   * @param object the object to convert
   * @return the cast value
   */
  public final T cast(Object object) {
    if (!valueType().isInstance(object)) {
      throw new IllegalArgumentException(String.format(
          "input %s must be of type %s",
          object.getClass().getName(),
          valueType().getName()));
    }
    return valueType().cast(object);
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    return (other instanceof SettingKey) && ((SettingKey<?>) other).id.equals(this.id);
  }

  @Override
  public String toString() {
    return this.id;
  }

  /**
   * Type of {@link SettingKey} for ordering purposes.
   */
  public enum CategoryType {
    BLOCKS,
    DAMAGE,
    ENTITIES,
    MISC,
    MOVEMENT,
    GLOBAL,
  }

  /**
   * An exception to throw when a setting could not be parsed.
   */
  public static class ParseSettingException extends IllegalArgumentException {
    public ParseSettingException() {
      super();
    }

    public ParseSettingException(String s) {
      super(s);
    }
  }
}

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

package me.pietelite.nope.common.util.formatting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility enumeration of characters with their lengths in the standard Minecraft font.
 */
public enum MinecraftCharacter {

  A('A', 5),
  a('a', 5),
  B('B', 5),
  b('b', 5),
  C('C', 5),
  c('c', 5),
  D('D', 5),
  d('d', 5),
  E('E', 5),
  e('e', 5),
  F('F', 5),
  f('f', 4),
  G('G', 5),
  g('g', 5),
  H('H', 5),
  h('h', 5),
  I('I', 3),
  i('i', 1),
  J('J', 5),
  j('j', 5),
  K('K', 5),
  k('k', 4),
  L('L', 5),
  l('l', 2),
  M('M', 5),
  m('m', 5),
  N('N', 5),
  n('n', 5),
  O('O', 5),
  o('o', 5),
  P('P', 5),
  p('p', 5),
  Q('Q', 5),
  q('q', 5),
  R('R', 5),
  r('r', 5),
  S('S', 5),
  s('s', 5),
  T('T', 5),
  t('t', 4),
  U('U', 5),
  u('u', 5),
  V('V', 5),
  v('v', 5),
  W('W', 5),
  w('w', 5),
  X('X', 5),
  x('x', 5),
  Y('Y', 5),
  y('y', 5),
  Z('Z', 5),
  z('z', 5),
  NUM_1('1', 5),
  NUM_2('2', 5),
  NUM_3('3', 5),
  NUM_4('4', 5),
  NUM_5('5', 5),
  NUM_6('6', 5),
  NUM_7('7', 5),
  NUM_8('8', 5),
  NUM_9('9', 5),
  NUM_0('0', 5),
  EXCLAMATION_POINT('!', 1),
  AT_SYMBOL('@', 6),
  NUM_SIGN('#', 5),
  DOLLAR_SIGN('$', 5),
  PERCENT('%', 5),
  UP_ARROW('^', 5),
  AMPERSAND('&', 5),
  ASTERISK('*', 5),
  LEFT_PARENTHESIS('(', 4),
  RIGHT_PERENTHESIS(')', 4),
  MINUS('-', 5),
  UNDERSCORE('_', 5),
  PLUS_SIGN('+', 5),
  EQUALS_SIGN('=', 5),
  LEFT_CURL_BRACE('{', 4),
  RIGHT_CURL_BRACE('}', 4),
  LEFT_BRACKET('[', 3),
  RIGHT_BRACKET(']', 3),
  COLON(':', 1),
  SEMI_COLON(';', 1),
  DOUBLE_QUOTE('"', 3),
  SINGLE_QUOTE('\'', 1),
  LEFT_ARROW('<', 4),
  RIGHT_ARROW('>', 4),
  QUESTION_MARK('?', 5),
  SLASH('/', 5),
  BACK_SLASH('\\', 5),
  LINE('|', 1),
  TILDE('~', 5),
  TICK('`', 2),
  PERIOD('.', 1),
  COMMA(',', 1),
  SPACE(' ', 3),
  DEFAULT('a', 4);

  private static final Map<Character, MinecraftCharacter> map = new HashMap<>();

  static {
    Arrays.stream(values()).forEach(c -> map.put(c.character, c));
  }

  private final char character;
  private final int length;

  MinecraftCharacter(char character, int length) {
    this.character = character;
    this.length = length;
  }

  /**
   * Get the Minecraft-specific object storing information for this character.
   *
   * @param c the character
   * @return the Minecraft character
   */
  public static MinecraftCharacter characterOf(char c) {
    if (map.containsKey(c)) {
      return map.get(c);
    }
    return MinecraftCharacter.DEFAULT;
  }

  /**
   * Gives a string of spaces that are required to extend the given
   * string to a certain pixel length.
   *
   * @param string the original string
   * @param length the length in pixels
   * @return the string of spaces
   */
  public static String spacesRequiredToExtend(String string, int length) {
    int currentLength = pixelLengthOf(string);
    int spaces = (length - currentLength) / (SPACE.length + 1);
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < spaces; i++) {
      builder.append(" ");
    }
    return builder.toString();
  }

  /**
   * Get the longest length among the lengths of the given series of strings.
   *
   * @param strings the strings of which to check lengths
   * @return the length
   */
  public static int longestPixelLength(Iterable<String> strings) {
    int maxLength = 0;
    int cur;
    for (String string : strings) {
      cur = pixelLengthOf(string);
      if (cur > maxLength) {
        maxLength = cur;
      }
    }
    return maxLength;
  }

  private static int pixelLengthOf(String string) {
    int size = 0;
    for (char c : string.toCharArray()) {
      size += characterOf(c).length;
    }
    // have to add string.length() because there is one pixel between each character
    return size + string.length();
  }

}
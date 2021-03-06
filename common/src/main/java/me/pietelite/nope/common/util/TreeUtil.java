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

package me.pietelite.nope.common.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * A utility class to provide methods to deal with tree structures.
 */
public final class TreeUtil {

  private TreeUtil() {
  }

  /**
   * Given the root of the tree, get the list of all nodes and retrieve their values.
   *
   * @param root             the root
   * @param valueFunction    the function to retrieve the value from a node
   * @param childrenFunction the function retrieve a node's children
   * @param <N>              the node type
   * @param <T>              the value type
   * @return the list of values
   */
  public static <N, T> List<T> getAllInTree(N root,
                                            Function<N, T> valueFunction,
                                            Function<N, Collection<N>> childrenFunction) {
    final LinkedList<T> all = new LinkedList<>();

    // add all entities that are riding each other to the entities list
    final LinkedList<N> queue = new LinkedList<>();
    queue.add(root);
    N current;
    while (!queue.isEmpty()) {
      current = queue.pop();
      queue.addAll(childrenFunction.apply(current));
      all.add(valueFunction.apply(current));
    }
    return all;
  }
}

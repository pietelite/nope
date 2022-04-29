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

import me.pietelite.nope.common.host.Domain;
import me.pietelite.nope.common.host.Scene;
import me.pietelite.nope.common.math.Sphere;

public class SphereEditorImpl extends ZoneEditorImpl<Sphere> implements SphereEditor {
  public SphereEditorImpl(Scene scene, int index) {
    super(scene, index, Sphere.class);
  }

  @Override
  public Alteration setDimensions(float x, float y, float z, float radius) {
    update(new Sphere(volume.domain(), x, y, z, radius));
    return AlterationImpl.success("Updated the dimensions of the sphere at index "
        + index + " for host " + scene.name());
  }

  @Override
  public float x() {
    return volume.posX();
  }

  @Override
  public float y() {
    return volume.posY();
  }

  @Override
  public float z() {
    return volume.posZ();
  }

  @Override
  public float radius() {
    return volume.radius();
  }

  @Override
  protected Alteration setDomainObject(Domain domain) {
    update(new Sphere(domain, volume.posX(), volume.posY(), volume.posZ(), volume.radius()));
    return AlterationImpl.success("Updated the domain of the cuboid at index " + index);
  }
}
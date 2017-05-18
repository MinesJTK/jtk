/****************************************************************************
Copyright 2009, Colorado School of Mines and others.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
****************************************************************************/
package edu.mines.jtk.interp;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Tests {@link edu.mines.jtk.interp.LasserreVolume}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2009.06.10
 */
public class LasserreVolumeTest {

  private LasserreVolume lv2 = new LasserreVolume(2);
  private LasserreVolume lv3 = new LasserreVolume(3);

  @Test
  public void testTriangle() {
    lv2.clear();
    lv2.addHalfSpace( 1.0, 1.0, 1.0); // make a simple
    lv2.addHalfSpace(-1.0, 0.0, 0.0); // triangle with
    lv2.addHalfSpace( 0.0,-1.0, 0.0); // 3 half-planes
    assertEquals(0.5,lv2.getVolume());
  }

  @Test
  public void testRedundant() {
    lv2.clear();
    lv2.addHalfSpace(-1.0, 1.0, 0.0); // make a simple
    lv2.addHalfSpace( 1.0, 0.0, 1.0); // triangle with
    lv2.addHalfSpace( 0.0,-1.0, 0.0); // 3 half-planes
    assertEquals(0.5,lv2.getVolume());
    lv2.addHalfSpace(-1.0, 0.0, 0.0); // redundant, not parallel
    assertEquals(0.5,lv2.getVolume());
    lv2.addHalfSpace( 1.0, 1.0, 2.0); // redundant, not parallel
    assertEquals(0.5,lv2.getVolume());
    lv2.addHalfSpace( 2.0,-1.0, 2.0); // redundant, not parallel
    assertEquals(0.5,lv2.getVolume());
    lv2.addHalfSpace( 2.0,-1.0, 4.0); // redundant, not parallel
    assertEquals(0.5,lv2.getVolume());
    lv2.addHalfSpace( 2.0, 0.0, 2.0); // redundant, parallel
    assertEquals(1.0,lv2.getVolume()); // WRONG ANSWER!
  }

  @Test
  public void testUnitSquare() {
    lv2.clear();
    lv2.addHalfSpace( 1.0, 0.0, 1.0);
    lv2.addHalfSpace(-1.0, 0.0, 0.0);
    lv2.addHalfSpace( 0.0, 1.0, 1.0);
    lv2.addHalfSpace( 0.0,-1.0, 0.0);
    assertEquals(1.0,lv2.getVolume());
    lv2.addHalfSpace( 1.0, 0.0, 2.0); // redundant, parallel
    assertEquals(2.0,lv2.getVolume()); // WRONG ANSWER!
  }

  @Test
  public void testOctahedron() {
    lv2.clear();
    lv2.addHalfSpace( 1.0, 0.0, 1.0);
    lv2.addHalfSpace(-1.0, 0.0, 1.0);
    lv2.addHalfSpace( 0.0, 1.0, 1.0);
    lv2.addHalfSpace( 0.0,-1.0, 1.0);
    lv2.addHalfSpace( 1.0, 1.0, 1.5);
    lv2.addHalfSpace(-1.0, 1.0, 1.5);
    lv2.addHalfSpace( 1.0,-1.0, 1.5);
    lv2.addHalfSpace(-1.0,-1.0, 1.5);
    assertEquals(3.5,lv2.getVolume());
  }

  @Test
  public void testUnitCube() {
    lv3.clear();
    lv3.addHalfSpace( 1.0, 0.0, 0.0, 1.0);
    lv3.addHalfSpace(-1.0, 0.0, 0.0, 0.0);
    lv3.addHalfSpace( 0.0, 1.0, 0.0, 1.0);
    lv3.addHalfSpace( 0.0,-1.0, 0.0, 0.0);
    lv3.addHalfSpace( 0.0, 0.0, 1.0, 1.0);
    lv3.addHalfSpace( 0.0, 0.0,-1.0, 0.0);
    assertEquals(1.0,lv3.getVolume());
  }

}

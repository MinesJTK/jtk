/****************************************************************************
Copyright 2011, Colorado School of Mines and others.
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
package edu.mines.jtk.mosaic;


import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class ProjectorTest {
  private static final double eps = 1.0e-10;

  @Test
  public void testMergeA () {
    Projector pa = new Projector(0, 1);
    Projector pb = new Projector(0, 1);
    pa.merge(pb);

    Projector expected = new Projector(0,1);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMergeB () {
    Projector pa = new Projector(0, 1);
    Projector pb = new Projector(1, 0);
    pa.merge(pb);

    Projector expected = new Projector(0,1);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMergeC () {
    Projector pa = new Projector(1, 0);
    Projector pb = new Projector(0, 1);
    pa.merge(pb);

    Projector expected = new Projector(1,0);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMergeD () {
    Projector pa = new Projector(1, 0);
    Projector pb = new Projector(1, 0);
    pa.merge(pb);

    Projector expected = new Projector(1,0);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMergeE () {
    Projector pa = new Projector(10,  0);
    Projector pb = new Projector( 1, 11);
    pa.merge(pb);

    Projector expected = new Projector(11,0);
    assertVeryClose(expected, pa);
  }

  @Test
  public void testMergeF () {
    Projector pa = new Projector(10,  5);
    Projector pb = new Projector( 1, 11);
    pa.merge(pb);

    Projector expected = new Projector(11,1);
    assertVeryClose(expected, pa);
  }

  @Test
  public void testMergeG () {
    Projector pa = new Projector( 1, 11);
    Projector pb = new Projector(10,  0);
    pa.merge(pb);

    Projector expected = new Projector(0,11);
    assertVeryClose(expected, pa);
  }

  @Test
  public void testMergeH () {
    Projector pa = new Projector( 1.5, 1.4);
    Projector pb = new Projector( 1, 2);
    pa.merge(pb);

    Projector expected = new Projector(2,1);
    assertVeryClose(expected, pa);
  }

  @Test
  public void testMerge1 () {
    Projector pa = new Projector(10, 20, 0.1, 0.8);
    Projector pb = new Projector(10, 20, 0.0, 1.0);
    pa.merge(pb);

    Projector expected = new Projector(10, 20, 0.1, 0.8);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge1r () {
    Projector pa = new Projector(10, 20, 0.0, 1.0);
    Projector pb = new Projector(10, 20, 0.1, 0.8);
    pa.merge(pb);

    Projector expected = new Projector(10, 20, 0.1, 0.8);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge2 () {
    Projector pa = new Projector(10, 20, 0.1, 0.8);
    Projector pb = new Projector(20, 10, 0.0, 1.0);
    pa.merge(pb);

    Projector expected = new Projector(10, 20, 0.1, 0.8);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge2r () {
    Projector pa = new Projector(20, 10, 0.0, 1.0);
    Projector pb = new Projector(10, 20, 0.1, 0.8);
    pa.merge(pb);

    Projector expected = new Projector(20, 10, 0.2, 0.9);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge3 () {
    Projector pa = new Projector(10, 20, 0.0, 1.0);
    Projector pb = new Projector(20, 10, 0.1, 0.8);
    pa.merge(pb);

    Projector expected = new Projector(10, 20, 0.2, 0.9);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge3r () {
    Projector pa = new Projector(20, 10, 0.1, 0.8);
    Projector pb = new Projector(10, 20, 0.0, 1.0);
    pa.merge(pb);

    Projector expected = new Projector(20, 10, 0.1, 0.8);
    assertVeryClose(expected,pa);
  }

  private static void assertVeryClose (Projector expected, Projector actual) {
    boolean success = true;
    success &= Math.abs(expected.u0()-actual.u0()) <= eps;
    success &= Math.abs(expected.u1()-actual.u1()) <= eps;
    success &= Math.abs(expected.v0()-actual.v0()) <= eps;
    success &= Math.abs(expected.v1()-actual.v1()) <= eps;
    assertTrue(success);
  }
  
  public static void assertVeryClose (double expected, double actual) {
    boolean success = true;
    success &= Math.abs(expected-actual) <= eps;
    assertTrue(success);
  }
  
  
  // The same set of tests as above, except this time for a LOG scale projector
  // with the addition of a functional test of log projection

  @Test
  public void testProjectionLog () {
    Projector p = new Projector(0.1, 100, 0.0, 1.0, AxisScale.LOG10);
    assertVeryClose(0.1, p.v(p.u(0.1)));
    assertVeryClose(2, p.v(p.u(2)));
    assertVeryClose(56.7785, p.v(p.u(56.7785)));
    assertVeryClose(0.0, p.u(p.v(0.0)));
    assertVeryClose(0.25, p.u(p.v(0.25)));
    assertVeryClose(0.6173, p.u(p.v(0.6173)));
  }
  
  @Test
  public void testAutoLinear() {
    Projector p = new Projector(0.0, 100, 0.0, 1.0);
    assertTrue(p.getScale() == AxisScale.LINEAR);
  }

  @Test
  public void testMergeALog () {
    Projector pa = new Projector(0, 1, AxisScale.LOG10);
    Projector pb = new Projector(0, 1, AxisScale.LOG10);
    pa.merge(pb);
    Projector expected = new Projector(0,1, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMergeBLog () {
    Projector pa = new Projector(0, 1, AxisScale.LOG10);
    Projector pb = new Projector(1, 0, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(0,1, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMergeCLog () {
    Projector pa = new Projector(1, 0, AxisScale.LOG10);
    Projector pb = new Projector(0, 1, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(1,0, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMergeDLog () {
    Projector pa = new Projector(1, 0, AxisScale.LOG10);
    Projector pb = new Projector(1, 0, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(1,0, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMergeELog () {
    Projector pa = new Projector(10,  0, AxisScale.LOG10);
    Projector pb = new Projector( 1, 11, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(11,0, AxisScale.LOG10);
    assertVeryClose(expected, pa);
  }

  @Test
  public void testMergeFLog () {
    Projector pa = new Projector(10,  5, AxisScale.LOG10);
    Projector pb = new Projector( 1, 11, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(11,1, AxisScale.LOG10);
    assertVeryClose(expected, pa);
  }

  @Test
  public void testMergeGLog () {
    Projector pa = new Projector( 1, 11, AxisScale.LOG10);
    Projector pb = new Projector(10,  0, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(0,11, AxisScale.LOG10);
    assertVeryClose(expected, pa);
  }

  @Test
  public void testMergeHLog () {
    Projector pa = new Projector( 1.5, 1.4, AxisScale.LOG10);
    Projector pb = new Projector( 1, 2, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(2,1, AxisScale.LOG10);
    assertVeryClose(expected, pa);
  }

  @Test
  public void testMerge1Log () {
    Projector pa = new Projector(10, 20, 0.1, 0.8, AxisScale.LOG10);
    Projector pb = new Projector(10, 20, 0.0, 1.0, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(10, 20, 0.1, 0.8, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge1rLog () {
    Projector pa = new Projector(10, 20, 0.0, 1.0, AxisScale.LOG10);
    Projector pb = new Projector(10, 20, 0.1, 0.8, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(10, 20, 0.1, 0.8, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge2Log () {
    Projector pa = new Projector(10, 20, 0.1, 0.8, AxisScale.LOG10);
    Projector pb = new Projector(20, 10, 0.0, 1.0, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(10, 20, 0.1, 0.8, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge2rLog () {
    Projector pa = new Projector(20, 10, 0.0, 1.0, AxisScale.LOG10);
    Projector pb = new Projector(10, 20, 0.1, 0.8, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(20, 10, 0.2, 0.9, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge3Log () {
    Projector pa = new Projector(10, 20, 0.0, 1.0, AxisScale.LOG10);
    Projector pb = new Projector(20, 10, 0.1, 0.8, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(10, 20, 0.2, 0.9, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }

  @Test
  public void testMerge3rLog () {
    Projector pa = new Projector(20, 10, 0.1, 0.8, AxisScale.LOG10);
    Projector pb = new Projector(10, 20, 0.0, 1.0, AxisScale.LOG10);
    pa.merge(pb);

    Projector expected = new Projector(20, 10, 0.1, 0.8, AxisScale.LOG10);
    assertVeryClose(expected,pa);
  }
}

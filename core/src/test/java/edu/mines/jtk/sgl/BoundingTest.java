/****************************************************************************
Copyright 2004, Colorado School of Mines and others.
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
package edu.mines.jtk.sgl;

import org.testng.annotations.Test;

import java.util.Random;

import static edu.mines.jtk.util.MathPlus.DBL_EPSILON;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests classes for bounding box and sphere.
 * @author Dave Hale
 * @version 2005.05.23
 */
public class BoundingTest {

  @Test
  public void testBox() {
    BoundingBox bb = new BoundingBox();
    bb.expandBy(0,0,0);
    bb.expandBy(1,1,1);
    double a = 10.0*DBL_EPSILON;
    double b = 1.0-a;
    assertTrue(bb.contains(new Point3(a,a,a)));
    assertTrue(bb.contains(new Point3(a,a,b)));
    assertTrue(bb.contains(new Point3(a,b,a)));
    assertTrue(bb.contains(new Point3(a,b,b)));
    assertTrue(bb.contains(new Point3(b,a,a)));
    assertTrue(bb.contains(new Point3(b,a,b)));
    assertTrue(bb.contains(new Point3(b,b,a)));
    assertTrue(bb.contains(new Point3(b,b,b)));
    a = -10.0*DBL_EPSILON;
    b = 1.0-a;
    assertTrue(!bb.contains(new Point3(a,a,a)));
    assertTrue(!bb.contains(new Point3(a,a,b)));
    assertTrue(!bb.contains(new Point3(a,b,a)));
    assertTrue(!bb.contains(new Point3(a,b,b)));
    assertTrue(!bb.contains(new Point3(b,a,a)));
    assertTrue(!bb.contains(new Point3(b,a,b)));
    assertTrue(!bb.contains(new Point3(b,b,a)));
    assertTrue(!bb.contains(new Point3(b,b,b)));
  }

  @Test
  public void testBoxExpand() {
    int ntrial = 100;
    for (int itrial=0; itrial<ntrial; ++itrial) {
      BoundingBox bb = new BoundingBox();
      assertTrue(bb.isEmpty());
      int nexpand = 100;
      for (int iexpand=0; iexpand<nexpand; ++iexpand) {
        Point3 c = randomPoint3();
        double r = randomDouble();
        BoundingSphere bs = new BoundingSphere(c,r);
        bb.expandBy(bs);
        assertTrue(!bb.isEmpty());
        int npoint=100;
        for (int ipoint=0; ipoint<npoint; ++ipoint) {
          Point3 p = randomPoint3();
          if (bs.contains(p))
            assertTrue(bb.contains(p));
        }
      }
    }
  }

  @Test
  public void testSphere() {
    BoundingSphere bs = new BoundingSphere();
    bs.expandBy(0,0,0);
    bs.expandBy(1,1,1);
    double a = 10.0*DBL_EPSILON;
    double b = 1.0-a;
    assertTrue(bs.contains(new Point3(a,a,a)));
    assertTrue(bs.contains(new Point3(a,a,b)));
    assertTrue(bs.contains(new Point3(a,b,a)));
    assertTrue(bs.contains(new Point3(a,b,b)));
    assertTrue(bs.contains(new Point3(b,a,a)));
    assertTrue(bs.contains(new Point3(b,a,b)));
    assertTrue(bs.contains(new Point3(b,b,a)));
    assertTrue(bs.contains(new Point3(b,b,b)));
    a = -10.0*DBL_EPSILON;
    b = 1.0-a;
    assertTrue(!bs.contains(new Point3(a,a,a)));
    assertTrue(!bs.contains(new Point3(a,a,b)));
    assertTrue(!bs.contains(new Point3(a,b,a)));
    assertTrue(!bs.contains(new Point3(a,b,b)));
    assertTrue(!bs.contains(new Point3(b,a,a)));
    assertTrue(!bs.contains(new Point3(b,a,b)));
    assertTrue(!bs.contains(new Point3(b,b,a)));
    assertTrue(!bs.contains(new Point3(b,b,b)));
  }

  @Test
  public void testSphereExpand() {
    int ntrial = 100;
    for (int itrial=0; itrial<ntrial; ++itrial) {
      BoundingSphere bs = new BoundingSphere();
      assertTrue(bs.isEmpty());
      int nexpand = 100;
      for (int iexpand=0; iexpand<nexpand; ++iexpand) {
        Point3 p = randomPoint3();
        Point3 q = randomPoint3();
        BoundingBox bb = new BoundingBox(p,q);
        if (randomDouble()>0.5) {
          bs.expandBy(bb);
        } else {
          bs.expandRadiusBy(bb);
        }
        assertTrue(!bs.isEmpty());
        int npoint=100;
        for (int ipoint=0; ipoint<npoint; ++ipoint) {
          Point3 r = randomPoint3();
          if (bb.contains(r))
            assertTrue(bs.contains(r));
        }
      }
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // private

  private static Random _random = new Random(314159);

  private static double randomDouble() {
    return _random.nextDouble();
  }

  private static Point3 randomPoint3() {
    double x = _random.nextDouble();
    double y = _random.nextDouble();
    double z = _random.nextDouble();
    return new Point3(x,y,z);
  }

}

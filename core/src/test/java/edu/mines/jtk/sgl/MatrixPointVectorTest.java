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
import static org.junit.Assert.assertEquals;

/**
 * Tests classes for matrix, point, and vector math.
 * @author Dave Hale
 * @version 2005.05.20
 */
public class MatrixPointVectorTest {

  @Test
  public void testMatrix() {
    int ntrial = 10;
    for (int itrial=0; itrial<ntrial; ++itrial) {
      Matrix44 i = Matrix44.identity();
      Matrix44 a = randomMatrix44();

      Matrix44 at = a.transpose();
      assertMatrix44Equals(a,at.transpose());

      Matrix44 ai = a.inverse();
      assertMatrix44Equals(a,ai.inverse());

      assertMatrix44Equals(i,a.times(ai));
      assertMatrix44Equals(i,a.transpose().timesTranspose(ai));
      assertMatrix44Equals(i,a.transposeTimes(ai.transpose()));

      Matrix44 ac = new Matrix44(a);
      assertMatrix44Equals(i,ac.timesEquals(ai));
      ac = new Matrix44(a);
      assertMatrix44Equals(i,ac.transposeEquals().timesTranspose(ai));
      ac = new Matrix44(a);
      assertMatrix44Equals(i,ac.transposeTimesEquals(ai.transpose()));
    }
  }

  @Test
  public void testVector() {
    int ntrial = 10;
    for (int itrial=0; itrial<ntrial; ++itrial) {
      Vector3 u = randomVector3();
      Vector3 v = randomVector3();
      Vector3 vc = new Vector3(v);
      assertTuple3Equals(v,v.negate().negate());
      assertTuple3Equals(v,vc.negateEquals().negateEquals());
      assertEquals(1.0,v.normalize().length(),TOLERANCE);
      assertEquals(1.0,vc.normalizeEquals().length(),TOLERANCE);
      assertEquals(1.0,v.normalize().lengthSquared(),TOLERANCE);
      assertEquals(v.dot(v),v.lengthSquared(),TOLERANCE);
      assertEquals(0.0,u.cross(v).dot(u),TOLERANCE);
      assertEquals(0.0,u.cross(v).dot(v),TOLERANCE);
    }
  }

  @Test
  public void testPoint() {
    int ntrial = 10;
    for (int itrial=0; itrial<ntrial; ++itrial) {
      Point3 p = randomPoint3();
      Point3 pc = new Point3(p);
      Vector3 v = randomVector3();
      assertTuple3Equals(p,p.plus(v).minus(v));
      assertTuple3Equals(p,pc.plusEquals(v).minusEquals(v));
      Point3 q = p.minus(v);
      assertEquals(q.distanceTo(p),v.length(),TOLERANCE);
    }
  }

  @Test
  public void testMatrixVector() {
    int ntrial = 10;
    for (int itrial=0; itrial<ntrial; ++itrial) {
      Vector3 v = randomVector3();
      Matrix44 a = randomMatrix33();
      Matrix44 ata = a.transposeTimes(a);
      assertTuple3Equals(ata.times(v),a.transposeTimes(a.times(v)));
      Matrix44 aat = a.timesTranspose(a);
      assertTuple3Equals(aat.times(v),a.times(a.transposeTimes(v)));
    }
  }

  @Test
  public void testMatrixPoint() {
    int ntrial = 10;
    for (int itrial=0; itrial<ntrial; ++itrial) {
      Matrix44 a,ata,aat;
      a = randomMatrix33();
      ata = a.transposeTimes(a);
      aat = a.timesTranspose(a);
      Point3 p3 = randomPoint3();
      assertTuple3Equals(ata.times(p3),a.transposeTimes(a.times(p3)));
      assertTuple3Equals(aat.times(p3),a.times(a.transposeTimes(p3)));
      a = randomMatrix44();
      ata = a.transposeTimes(a);
      aat = a.timesTranspose(a);
      Point4 p4 = randomPoint4();
      assertTuple3Equals(ata.times(p4),a.transposeTimes(a.times(p4)));
      assertTuple3Equals(aat.times(p4),a.times(a.transposeTimes(p4)));
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // private

  private static Random _random = new Random(314159);

  private static final double TOLERANCE = 100*DBL_EPSILON;

  /**
   * Returns a diagonally dominant random 3x3 matrix.
   */
  private static Matrix44 randomMatrix33() {
    double[] m = new double[16];
    for (int i=0; i<16; ++i)
      m[i] = _random.nextDouble();
    m[ 0] += 4.0;
    m[ 5] += 4.0;
    m[10] += 4.0;
    m[ 3] = m[12] = 0.0;
    m[ 7] = m[13] = 0.0;
    m[11] = m[14] = 0.0;
    m[15] = 1.0;
    return new Matrix44(m);
  }

  /**
   * Returns Makes a diagonally dominant random 4x4 matrix.
   */
  private static Matrix44 randomMatrix44() {
    double[] m = new double[16];
    for (int i=0; i<16; ++i)
      m[i] = _random.nextDouble();
    m[ 0] += 4.0;
    m[ 5] += 4.0;
    m[10] += 4.0;
    m[15] += 4.0;
    return new Matrix44(m);
  }

  private static Point3 randomPoint3() {
    double x = _random.nextDouble();
    double y = _random.nextDouble();
    double z = _random.nextDouble();
    return new Point3(x,y,z);
  }

  private static Vector3 randomVector3() {
    double x = _random.nextDouble();
    double y = _random.nextDouble();
    double z = _random.nextDouble();
    return new Vector3(x,y,z);
  }

  private static Point4 randomPoint4() {
    double x = _random.nextDouble();
    double y = _random.nextDouble();
    double z = _random.nextDouble();
    double w = _random.nextDouble();
    return new Point4(x,y,z,w);
  }

  private static void assertMatrix44Equals(Matrix44 e, Matrix44 a) {
    double[] em = e.m;
    double[] am = a.m;
    for (int i=0; i<16; ++i)
      assertEquals(em[i],am[i],TOLERANCE);
  }

  private static void assertTuple3Equals(Tuple3 e, Tuple3 a) {
    assertEquals(e.x,a.x,TOLERANCE);
    assertEquals(e.y,a.y,TOLERANCE);
    assertEquals(e.z,a.z,TOLERANCE);
  }

  private static void assertTuple3Equals(Tuple4 e, Tuple4 a) {
    assertEquals(e.x,a.x,TOLERANCE);
    assertEquals(e.y,a.y,TOLERANCE);
    assertEquals(e.z,a.z,TOLERANCE);
    assertEquals(e.w,a.w,TOLERANCE);
  }

}

/****************************************************************************
 Copyright 2017, Colorado School of Mines and others.
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
package edu.mines.jtk.util;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static edu.mines.jtk.util.ArrayMath.*;
import static edu.mines.jtk.util.ArrayMathTest.assertAlmostEqual;
import static edu.mines.jtk.util.ArrayMathTest.assertEqual;
import static org.testng.Assert.*;

/**
 * Tests double array operations in {@link edu.mines.jtk.util.ArrayMath}.
 * @author Chris Engelsma
 * @version 2017.06.12
 */
public class ArrayMathCDoubleTest {

  private static final int n3 = 8;
  private static final int n2 = 6;
  private static final int n1 = 4;

  @BeforeMethod
  public void setUp() {
    a1 = crampdouble(c0,c1,n1);
    a2 = crampdouble(c0,c1,c10,n1,n2);
    a3 = crampdouble(c0,c1,c10,c100,n1,n2,n3);
    b1 = czerodouble(n1);
    b2 = czerodouble(n1,n2);
    b3 = czerodouble(n1,n2,n3);
  }

  @Test
  public void testEqualityComparators() {
    czero(a3);
    assertTrue(equal(a3,b3));

    cfill(new Cdouble(1f),a3);
    assertFalse(cequal(a3,b3));

    assertTrue(cequal(1.0,a3,b3));
    assertFalse(cequal(0.99f,a3,b3));
  }

  @Test
  public void testRampDoubles() {
    assertArraySize(a1,2*n1);
    assertArraySize(a2,2*n1,n2);
    assertArraySize(a3,2*n1,n2,n3);

    for (int i1=0; i1<n1; ++i1) {
      double ex = (double)i1;
      assertEquals(ex,a3[0][0][2*i1  ]);
      assertEquals(0d,a3[0][0][2*i1+1]);

      assertEquals(ex,a2[0][2*i1  ]);
      assertEquals(0d,a2[0][2*i1+1]);

      assertEquals(ex,a1[2*i1  ]);
      assertEquals(0d,a1[2*i1+1]);
    }
  }

  @Test
  public void testFillDoubles() {

    assertEqual(czerodouble(n1,n2,n3),cfilldouble(c0,n1,n2,n3));

    a1 = cfilldouble(c1,n1);
    a2 = cfilldouble(c1,n1,n2);
    a3 = cfilldouble(c1,n1,n2,n3);

    assertArraySize(a1,2*n1);
    assertArraySize(a2,2*n1,n2);
    assertArraySize(a3,2*n1,n2,n3);

    assertOnlyContains(c1,a3);
    assertOnlyContains(c1,a2);
    assertOnlyContains(c1,a1);
  }

  @Test
  public void testZero() {
    czero(a1);
    czero(a2);
    czero(a3);

    assertOnlyContains(c0,a1);
    assertOnlyContains(c0,a2);
    assertOnlyContains(c0,a3);
  }

  @Test
  public void testRand() {
    crand(a1); crand(a2); crand(a3);

    assertArraySize(a1,2*n1);
    assertArraySize(a2,2*n1,n2);
    assertArraySize(a3,2*n1,n2,n3);

    b1 = cranddouble(n1);
    b2 = cranddouble(n1,n2);
    b3 = cranddouble(n1,n2,n3);

    assertArraySize(b1,2*n1);
    assertArraySize(b2,2*n1,n2);
    assertArraySize(b3,2*n1,n2,n3);

    assertFalse(cequal(a1,b1));
    assertFalse(cequal(a2,b2));
    assertFalse(cequal(a3,b3));
  }
  @Test
  public void testCopySimple() {
    b1 = ccopy(a1);
    b2 = ccopy(a2);
    b3 = ccopy(a3);
    assertEqual(b1,a1);
    assertEqual(b2,a2);
    assertEqual(b3,a3);

    ccopy(a1,b1); assertEqual(b1,a1);
    ccopy(a2,b2); assertEqual(b2,a2);
    ccopy(a3,b3); assertEqual(b3,a3);

    b1 = ccopy(n1-1,a1);
    b2 = ccopy(n1-1,n2-1,a2);
    b3 = ccopy(n1-1,n2-1,n3-1,a3);
    assertEqual(b1,crampdouble(c0,c1,n1-1));
    assertEqual(b2,crampdouble(c0,c1,c10,n1-1,n2-1));
    assertEqual(b3,crampdouble(c0,c1,c10,c100,n1-1,n2-1,n3-1));

    ccopy(n1-1,a1,b1);
    ccopy(n1-1,n2-1,a2,b2);
    ccopy(n1-1,n2-1,n3-1,a3,b3);
    assertEqual(b1,crampdouble(c0,c1,n1-1));
    assertEqual(b2,crampdouble(c0,c1,c10,n1-1,n2-1));
    assertEqual(b3,crampdouble(c0,c1,c10,c100,n1-1,n2-1,n3-1));

    b1 = ccopy(n1-1,1,a1);
    b2 = ccopy(n1-2,n2-1,2,1,a2);
    b3 = ccopy(n1-3,n2-2,n3-1,3,2,1,a3);
    assertEqual(b1,crampdouble(c1,c1,n1-1));
    assertEqual(b2,crampdouble(c12,c1,c10,n1-1,n2-1));
    assertEqual(b3,crampdouble(c123,c1,c10,c100,n1-1,n2-1,n3-1));

    ccopy(n1-1,1,a1,0,b1);
    ccopy(n1-2,n2-1,2,1,a2,0,0,b2);
    ccopy(n1-3,n2-2,n3-1,3,2,1,a3,0,0,0,b3);
    assertEqual(b1,crampdouble(c1,c1,n1-1));
    assertEqual(b2,crampdouble(c12,c1,c10,n1-1,n2-1));
    assertEqual(b3,crampdouble(c123,c1,c10,c100,n1-1,n2-1,n3-1));

    Cdouble c2 = new Cdouble(2f);
    Cdouble c20 = new Cdouble(20);
    Cdouble c200 = new Cdouble(200);

    b1 = ccopy(n1/2,0,2,a1);
    b2 = ccopy(n1/2,n2/2,0,0,2,2,a2);
    b3 = ccopy(n1/2,n2/2,n3/2,0,0,0,2,2,2,a3);
    assertEqual(b1,crampdouble(c0,c2,n1/2));
    assertEqual(b2,crampdouble(c0,c2,c20,n1/2,n2/2));
    assertEqual(b3,crampdouble(c0,c2,c20,c200,n1/2,n2/2,n3/2));
  }

  @Test
  public void testReverse() {
    b1 = creverse(reverse(a1));
    assertEqual(b1,a1);
  }

  @Test
  public void testReshape() {
    b2 = creshape(n1,n2,cflatten(a2));
    b3 = creshape(n1,n2,n3,cflatten(a3));
    assertEqual(a2,b2);
    assertEqual(a3,b3);
  }

  @Test
  public void testTranspose() {
    b2 = ctranspose(ctranspose(a2));
    assertEqual(a2,b2);
  }

  @Test
  public void testAddition() {
    b1 = crampdouble(c1,c1,n1);                assertEqual(b1,cadd(c1,a1));
                                               assertEqual(b1,cadd(a1,c1));
    b2 = crampdouble(c1,c1,c10,n1,n2);         assertEqual(b2,cadd(c1,a2));
                                               assertEqual(b2,cadd(a2,c1));
    b3 = crampdouble(c1,c1,c10,c100,n1,n2,n3); assertEqual(b3,cadd(c1,a3));
                                               assertEqual(b3,cadd(a3,c1));

    b1 = crampdouble(c0,c2,n1);                assertEqual(b1,cadd(a1,a1));
    b2 = crampdouble(c0,c2,c20,n1,n2);         assertEqual(b2,cadd(a2,a2));
    b3 = crampdouble(c0,c2,c20,c200,n1,n2,n3); assertEqual(b3,cadd(a3,a3));

    cadd(a1,a1,a1); assertEquals(b1,a1);
    cadd(a2,a2,a2); assertEquals(b2,b2);
    cadd(a3,a3,a3); assertEquals(b3,a3);
  }

  @Test
  public void testSubtraction() {
    b1 = crampdouble(cm1,c1,n1);                assertEqual(b1,csub(a1,c1));
    b2 = crampdouble(cm1,c1,c10,n1,n2);         assertEqual(b2,csub(a2,c1));
    b3 = crampdouble(cm1,c1,c10,c100,n1,n2,n3); assertEqual(b3,csub(a3,c1));

    b1 = crampdouble(c1,cm1,n1);                  assertEqual(b1,csub(c1,a1));
    b2 = crampdouble(c1,cm1,cm10,n1,n2);          assertEqual(b2,csub(c1,a2));
    b3 = crampdouble(c1,cm1,cm10,cm100,n1,n2,n3); assertEqual(b3,csub(c1,a3));

    czero(b1); assertEqual(b1,csub(a1,a1));
    czero(b2); assertEqual(b2,csub(a2,a2));
    czero(b3); assertEqual(b3,csub(a3,a3));

    csub(a1,a1,a1); assertEqual(b1,a1);
    csub(a2,a2,a2); assertEqual(b2,a2);
    csub(a3,a3,a3); assertEqual(b3,a3);
  }

  @Test
  public void testMultiplication() {
    b1 = crampdouble(c0,c2,n1);                assertEqual(b1,cmul(c2,a1));
                                               assertEqual(b1,cmul(a1,c2));
    b2 = crampdouble(c0,c2,c20,n1,n2);         assertEqual(b2,cmul(c2,a2));
                                               assertEqual(b2,cmul(a2,c2));
    b3 = crampdouble(c0,c2,c20,c200,n1,n2,n3); assertEqual(b3,cmul(c2,a3));
                                               assertEqual(b3,cmul(a3,c2));

    b1 = new double[] {
      pow(0.0,2.0),0,
      pow(1.0,2.0),0,
      pow(2.0,2.0),0,
      pow(3.0,2.0),0
    };

    b2 = new double[n2][2*n1];     a2 = new double[n2][2*n1];
    b3 = new double[n3][n2][2*n1]; a3 = new double[n3][n2][2*n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = ccopy(b1);
      a2[i2] = ccopy(a1);
    }

    for (int i3 = 0; i3 < n3; ++i3) {
      b3[i3] = ccopy(b2);
      a3[i3] = ccopy(a2);
    }

    assertEqual(b1,cmul(a1,a1));
    assertEqual(b2,cmul(a2,a2));
    assertEqual(b3,cmul(a3,a3));

    cmul(a1,a1,a1); assertEqual(b1,a1);
    cmul(a2,a2,a2); assertEqual(b2,a2);
    cmul(a3,a3,a3); assertEqual(b3,a3);
  }

  @Test
  public void testNaturalLog() {
    b1 = new double[] {
      Double.NEGATIVE_INFINITY,0,
      log(1),0,
      log(2),0,
      log(3),0
    };

    b2 = new double[n2][2*n1];     a2 = new double[n2][2*n1];
    b3 = new double[n3][n2][2*n1]; a3 = new double[n3][n2][2*n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = ccopy(b1);
      a2[i2] = ccopy(a1);
    }

    for (int i3 = 0; i3<n3; ++i3) {
      b3[i3] = ccopy(b2);
      a3[i3] = ccopy(a2);
    }

    assertAlmostEqual(b1,clog(a1));
    assertAlmostEqual(a1,cexp(b1));

    assertAlmostEqual(b2,clog(a2));
    assertAlmostEqual(a2,cexp(b2));

    assertAlmostEqual(b3,clog(a3));
    assertAlmostEqual(a3,cexp(b3));
  }

  @Test
  public void testSquareRoot() {
    b1 = new double[] {
      sqrt(0.0),0,
      sqrt(1.0),0,
      sqrt(2.0),0,
      sqrt(3.0),0
    };
    b2 = new double[n2][2*n1];     a2 = new double[n2][2*n1];
    b3 = new double[n3][n2][2*n1]; a3 = new double[n3][n2][2*n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = ccopy(b1);
      a2[i2] = ccopy(a1);
    }

    for (int i3 = 0; i3 < n3; ++i3) {
      b3[i3] = ccopy(b2);
      a3[i3] = ccopy(a2);
    }

    assertEqual(b1,csqrt(a1));  assertAlmostEqual(a1,cmul(b1,b1));
    assertEqual(b2,csqrt(a2));  assertAlmostEqual(a2,cmul(b2,b2));
    assertEqual(b3,csqrt(a3));  assertAlmostEqual(a3,cmul(b3,b3));
  }

  @Test
  public void testPow() {
    b1 = new double[] {
      pow(0.0,2.0),0,
      pow(1.0,2.0),0,
      pow(2.0,2.0),0,
      pow(3.0,2.0),0
    };

    b2 = new double[n2][2*n1];     a2 = new double[n2][2*n1];
    b3 = new double[n3][n2][2*n1]; a3 = new double[n3][n2][2*n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = ccopy(b1);
      a2[i2] = ccopy(a1);
    }

    for (int i3 = 0; i3 < n3; ++i3) {
      b3[i3] = ccopy(b2);
      a3[i3] = ccopy(a2);
    }

    assertEqual(b1,cpow(a1,2.0)); assertAlmostEqual(a1,csqrt(b1));
    assertEqual(b2,cpow(a2,2.0)); assertAlmostEqual(a2,csqrt(b2));
    assertEqual(b3,cpow(a3,2.0)); assertAlmostEqual(a3,csqrt(b3));
  }

  private static void assertOnlyContains(Cdouble val,double[][][] a) {
    for (int i3=0; i3<a.length; ++i3)
      assertOnlyContains(val,a[i3]);
  }

  private static void assertOnlyContains(Cdouble val,double[][] a) {
    for (int i2=0; i2<a.length; ++i2)
      assertOnlyContains(val,a[i2]);
  }

  private static void assertOnlyContains(Cdouble val,double[] a) {
    for (int i1=0; i1<a.length/2; ++i1) {
      assertEquals(val.r,a[2*i1]);
      assertEquals(val.i,a[2*i1+1]);
    }
  }

  private static void assertArraySize(double[][][] a,int n1,int n2,int n3) {
    assertEquals(n3,a.length);
    assertEquals(n2,a[0].length);
    assertEquals(n1,a[0][0].length);
  }

  private static void assertArraySize(double[][] a,int n1,int n2) {
    assertEquals(n2,a.length);
    assertEquals(n1,a[0].length);
  }

  private static void assertArraySize(double[] a,int n) {
    assertEquals(n,a.length);
  }


  private double[]     a1;
  private double[][]   a2;
  private double[][][] a3;

  private double[]     b1;
  private double[][]   b2;
  private double[][][] b3;

  private static final Cdouble c0    = new Cdouble(   0.0);
  private static final Cdouble c1    = new Cdouble(   1.0);
  private static final Cdouble c10   = new Cdouble(  10.0);
  private static final Cdouble c100  = new Cdouble( 100.0);
  private static final Cdouble c12   = new Cdouble(  12.0);
  private static final Cdouble c123  = new Cdouble( 123.0);
  private static final Cdouble c2    = new Cdouble(   2.0);
  private static final Cdouble c20   = new Cdouble(  20.0);
  private static final Cdouble c200  = new Cdouble( 200.0);
  private static final Cdouble cm1   = new Cdouble(  -1.0);
  private static final Cdouble cm10  = new Cdouble( -10.0);
  private static final Cdouble cm100 = new Cdouble(-100.0);
}

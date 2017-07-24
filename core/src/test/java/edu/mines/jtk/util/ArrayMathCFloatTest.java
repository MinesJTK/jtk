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

import java.util.Random;

import static edu.mines.jtk.util.ArrayMath.*;
import static edu.mines.jtk.util.ArrayMathTest.*;
import static org.testng.Assert.*;

/**
 * Tests float array operations in {@link ArrayMath}.
 * @author Chris Engelsma
 * @version 2017.05.31
 */
public class ArrayMathCFloatTest {

  private static final int n3 = 8;
  private static final int n2 = 6;
  private static final int n1 = 4;

  @BeforeMethod
  public void setUp() {
    a1 = crampfloat(c0,c1,n1);
    a2 = crampfloat(c0,c1,c10,n1,n2);
    a3 = crampfloat(c0,c1,c10,c100,n1,n2,n3);
    b1 = czerofloat(n1);
    b2 = czerofloat(n1,n2);
    b3 = czerofloat(n1,n2,n3);
  }

  @Test
  public void testEqualityComparators() {
    czero(a3);
    assertTrue(equal(a3,b3));

    cfill(new Cfloat(1f),a3);
    assertFalse(cequal(a3,b3));

    assertTrue(cequal(1.0f,a3,b3));
    assertFalse(cequal(0.99f,a3,b3));
  }

  @Test
  public void testRampFloats() {
    assertArraySize(a1,2*n1);
    assertArraySize(a2,2*n1,n2);
    assertArraySize(a3,2*n1,n2,n3);

    for (int i1=0; i1<n1; ++i1) {
      float ex = (float)i1;
      assertEquals(ex,a3[0][0][2*i1  ]);
      assertEquals(0f,a3[0][0][2*i1+1]);

      assertEquals(ex,a2[0][2*i1  ]);
      assertEquals(0f,a2[0][2*i1+1]);

      assertEquals(ex,a1[2*i1  ]);
      assertEquals(0f,a1[2*i1+1]);
    }
  }

  @Test
  public void testFillFloats() {

    assertEqual(czerofloat(n1,n2,n3),cfillfloat(c0,n1,n2,n3));

    a1 = cfillfloat(c1,n1);
    a2 = cfillfloat(c1,n1,n2);
    a3 = cfillfloat(c1,n1,n2,n3);

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

    b1 = crandfloat(n1);
    b2 = crandfloat(n1,n2);
    b3 = crandfloat(n1,n2,n3);

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
    assertEqual(b1,crampfloat(c0,c1,n1-1));
    assertEqual(b2,crampfloat(c0,c1,c10,n1-1,n2-1));
    assertEqual(b3,crampfloat(c0,c1,c10,c100,n1-1,n2-1,n3-1));

    ccopy(n1-1,a1,b1);
    ccopy(n1-1,n2-1,a2,b2);
    ccopy(n1-1,n2-1,n3-1,a3,b3);
    assertEqual(b1,crampfloat(c0,c1,n1-1));
    assertEqual(b2,crampfloat(c0,c1,c10,n1-1,n2-1));
    assertEqual(b3,crampfloat(c0,c1,c10,c100,n1-1,n2-1,n3-1));

    b1 = ccopy(n1-1,1,a1);
    b2 = ccopy(n1-2,n2-1,2,1,a2);
    b3 = ccopy(n1-3,n2-2,n3-1,3,2,1,a3);
    assertEqual(b1,crampfloat(c1,c1,n1-1));
    assertEqual(b2,crampfloat(c12,c1,c10,n1-1,n2-1));
    assertEqual(b3,crampfloat(c123,c1,c10,c100,n1-1,n2-1,n3-1));

    ccopy(n1-1,1,a1,0,b1);
    ccopy(n1-2,n2-1,2,1,a2,0,0,b2);
    ccopy(n1-3,n2-2,n3-1,3,2,1,a3,0,0,0,b3);
    assertEqual(b1,crampfloat(c1,c1,n1-1));
    assertEqual(b2,crampfloat(c12,c1,c10,n1-1,n2-1));
    assertEqual(b3,crampfloat(c123,c1,c10,c100,n1-1,n2-1,n3-1));

    Cfloat c2 = new Cfloat(2f);
    Cfloat c20 = new Cfloat(20f);
    Cfloat c200 = new Cfloat(200f);

    b1 = ccopy(n1/2,0,2,a1);
    b2 = ccopy(n1/2,n2/2,0,0,2,2,a2);
    b3 = ccopy(n1/2,n2/2,n3/2,0,0,0,2,2,2,a3);
    assertEqual(b1,crampfloat(c0,c2,n1/2));
    assertEqual(b2,crampfloat(c0,c2,c20,n1/2,n2/2));
    assertEqual(b3,crampfloat(c0,c2,c20,c200,n1/2,n2/2,n3/2));
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
    b1 = crampfloat(c1,c1,n1);                assertEqual(b1,cadd(c1,a1));
                                              assertEqual(b1,cadd(a1,c1));
    b2 = crampfloat(c1,c1,c10,n1,n2);         assertEqual(b2,cadd(c1,a2));
                                              assertEqual(b2,cadd(a2,c1));
    b3 = crampfloat(c1,c1,c10,c100,n1,n2,n3); assertEqual(b3,cadd(c1,a3));
                                              assertEqual(b3,cadd(a3,c1));

    b1 = crampfloat(c0,c2,n1);                assertEqual(b1,cadd(a1,a1));
    b2 = crampfloat(c0,c2,c20,n1,n2);         assertEqual(b2,cadd(a2,a2));
    b3 = crampfloat(c0,c2,c20,c200,n1,n2,n3); assertEqual(b3,cadd(a3,a3));

    cadd(a1,a1,a1); assertEquals(b1,a1);
    cadd(a2,a2,a2); assertEquals(b2,b2);
    cadd(a3,a3,a3); assertEquals(b3,a3);
  }

  @Test
  public void testSubtraction() {
    b1 = crampfloat(cm1,c1,n1);                assertEqual(b1,csub(a1,c1));
    b2 = crampfloat(cm1,c1,c10,n1,n2);         assertEqual(b2,csub(a2,c1));
    b3 = crampfloat(cm1,c1,c10,c100,n1,n2,n3); assertEqual(b3,csub(a3,c1));

    b1 = crampfloat(c1,cm1,n1);                  assertEqual(b1,csub(c1,a1));
    b2 = crampfloat(c1,cm1,cm10,n1,n2);          assertEqual(b2,csub(c1,a2));
    b3 = crampfloat(c1,cm1,cm10,cm100,n1,n2,n3); assertEqual(b3,csub(c1,a3));

    czero(b1); assertEqual(b1,csub(a1,a1));
    czero(b2); assertEqual(b2,csub(a2,a2));
    czero(b3); assertEqual(b3,csub(a3,a3));

    csub(a1,a1,a1); assertEqual(b1,a1);
    csub(a2,a2,a2); assertEqual(b2,a2);
    csub(a3,a3,a3); assertEqual(b3,a3);
  }

  @Test
  public void testMultiplication() {
    b1 = crampfloat(c0,c2,n1);                assertEqual(b1,cmul(c2,a1));
                                              assertEqual(b1,cmul(a1,c2));
    b2 = crampfloat(c0,c2,c20,n1,n2);         assertEqual(b2,cmul(c2,a2));
                                              assertEqual(b2,cmul(a2,c2));
    b3 = crampfloat(c0,c2,c20,c200,n1,n2,n3); assertEqual(b3,cmul(c2,a3));
                                              assertEqual(b3,cmul(a3,c2));

    b1 = new float[] {
      pow(0.0f,2.0f),0,
      pow(1.0f,2.0f),0,
      pow(2.0f,2.0f),0,
      pow(3.0f,2.0f),0
    };

    b2 = new float[n2][2*n1];     a2 = new float[n2][2*n1];
    b3 = new float[n3][n2][2*n1]; a3 = new float[n3][n2][2*n1];

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
    b1 = new float[] {
      Float.NEGATIVE_INFINITY,0,
      log(1),0,
      log(2),0,
      log(3),0
    };

    b2 = new float[n2][2*n1];     a2 = new float[n2][2*n1];
    b3 = new float[n3][n2][2*n1]; a3 = new float[n3][n2][2*n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = ccopy(b1);
      a2[i2] = ccopy(a1);
    }

    for (int i3 = 0; i3<n3; ++i3) {
      b3[i3] = ccopy(b2);
      a3[i3] = ccopy(a2);
    }

    assertEqual(b1,clog(a1));  assertEqual(a1,cexp(b1));
    assertEqual(b2,clog(a2));  assertEqual(a2,cexp(b2));
    assertEqual(b3,clog(a3));  assertEqual(a3,cexp(b3));
  }

  @Test
  public void testSquareRoot() {
    b1 = new float[] {
      sqrt(0.0f),0,
      sqrt(1.0f),0,
      sqrt(2.0f),0,
      sqrt(3.0f),0
    };
    b2 = new float[n2][2*n1];     a2 = new float[n2][2*n1];
    b3 = new float[n3][n2][2*n1]; a3 = new float[n3][n2][2*n1];

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
    b1 = new float[] {
      pow(0.0f,2.0f),0,
      pow(1.0f,2.0f),0,
      pow(2.0f,2.0f),0,
      pow(3.0f,2.0f),0
    };

    b2 = new float[n2][2*n1];     a2 = new float[n2][2*n1];
    b3 = new float[n3][n2][2*n1]; a3 = new float[n3][n2][2*n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = ccopy(b1);
      a2[i2] = ccopy(a1);
    }

    for (int i3 = 0; i3 < n3; ++i3) {
      b3[i3] = ccopy(b2);
      a3[i3] = ccopy(a2);
    }

    assertEqual(b1,cpow(a1,2.0f)); assertAlmostEqual(a1,csqrt(b1));
    assertEqual(b2,cpow(a2,2.0f)); assertAlmostEqual(a2,csqrt(b2));
    assertEqual(b3,cpow(a3,2.0f)); assertAlmostEqual(a3,csqrt(b3));
  }

  private static void assertOnlyContains(Cfloat val,float[][][] a) {
    for (int i3=0; i3<a.length; ++i3)
      assertOnlyContains(val,a[i3]);
  }

  private static void assertOnlyContains(Cfloat val,float[][] a) {
    for (int i2=0; i2<a.length; ++i2)
      assertOnlyContains(val,a[i2]);
  }

  private static void assertOnlyContains(Cfloat val,float[] a) {
    for (int i1=0; i1<a.length/2; ++i1) {
      assertEquals(val.r,a[2*i1]);
      assertEquals(val.i,a[2*i1+1]);
    }
  }

  private static void assertArraySize(float[][][] a,int n1,int n2,int n3) {
    assertEquals(n3,a.length);
    assertEquals(n2,a[0].length);
    assertEquals(n1,a[0][0].length);
  }

  private static void assertArraySize(float[][] a,int n1,int n2) {
    assertEquals(n2,a.length);
    assertEquals(n1,a[0].length);
  }

  private static void assertArraySize(float[] a,int n) {
    assertEquals(n,a.length);
  }


  private float[]     a1;
  private float[][]   a2;
  private float[][][] a3;

  private float[]     b1;
  private float[][]   b2;
  private float[][][] b3;

  private static final Cfloat c0    = new Cfloat(   0.0f);
  private static final Cfloat c1    = new Cfloat(   1.0f);
  private static final Cfloat c10   = new Cfloat(  10.0f);
  private static final Cfloat c100  = new Cfloat( 100.0f);
  private static final Cfloat c12   = new Cfloat(  12.0f);
  private static final Cfloat c123  = new Cfloat( 123.0f);
  private static final Cfloat c2    = new Cfloat(   2.0f);
  private static final Cfloat c20   = new Cfloat(  20.0f);
  private static final Cfloat c200  = new Cfloat( 200.0f);
  private static final Cfloat cm1   = new Cfloat(  -1.0f);
  private static final Cfloat cm10  = new Cfloat( -10.0f);
  private static final Cfloat cm100 = new Cfloat(-100.0f);
}

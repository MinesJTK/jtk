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
package edu.mines.jtk.util;

import org.testng.annotations.Test;

import static edu.mines.jtk.util.ArrayMath.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

import java.util.Random;

/**
 * Tests {@link edu.mines.jtk.util.ArrayMath}.
 * @author Dave Hale, Colorado School of Mines
 * @author Chris Engelsma
 * @version 2017.05.15
 */
public class ArrayMathTest {

  // Adapted from Bentley, J.L., and McIlroy, M.D., 1993, Engineering a sort
  // function, Software -- Practice and Experience, v. 23(11), p. 1249-1265.
  protected static final int SAWTOOTH=0,RAND=1,STAGGER=2,PLATEAU=3,SHUFFLE=4;
  protected static final int COPY=0,REV=1,REVHALF1=2,REVHALF2=3,SORT=4,DITHER=5;

  @Test
  public void testCFloatArraysInits() {
    int n3 = 3, n2 = 5, n1 = 7;

    Cfloat val = new Cfloat(1.0f,-1.0f);

    float[]     a1 = cfillfloat(val,n1);
    float[][]   z2 = cfillfloat(val,n1,n2);
    float[][][] z3 = cfillfloat(val,n1,n2,n3);

    assertEquals(n3,z3.length);
    assertEquals(n2,z2.length);
    assertEquals(2*n1,a1.length);

    assertEquals(n2,z3[0].length);
    assertEquals(2*n1,z2[0].length);

    assertEquals(2*n1,z3[0][0].length);

    for (int i1=0; i1<n1; ++i1) {
      assertEq(val.r, z3[0][0][2*i1]);
      assertEq(val.i, z3[0][0][2*i1+1]);
      assertEq(val.r, z2[0][2*i1]);
      assertEq(val.i, z2[0][2*i1+1]);
      assertEq(val.r, a1[2*i1]);
      assertEq(val.i, a1[2*i1+1]);
    }

    czero(a1); czero(z2); czero(z3);

    assertArrayEquals(a1, czerofloat(n1), FLT_EPSILON);
    assertArrayEquals(z2, czerofloat(n1,n2));
    assertArrayEquals(z3, czerofloat(n1,n2,n3));

    // Rand
    a1 = crandfloat(2*n1);
    z2 = crandfloat(2*n1,2*n2);
    z3 = crandfloat(2*n1,2*n2,2*n3);

    assertEquals(2*n3,z3.length);
    assertEquals(2*n2,z2.length);
    assertEquals(2*n2,z3[0].length);
    assertEquals(4*n1,a1.length);
    assertEquals(4*n1,z2[0].length);
    assertEquals(4*n1,z3[0][0].length);

    crand(a1); crand(z2); crand(z3);

    assertEquals(2*n3,z3.length);
    assertEquals(2*n2,z2.length);
    assertEquals(2*n2,z3[0].length);
    assertEquals(4*n1,a1.length);
    assertEquals(4*n1,z2[0].length);
    assertEquals(4*n1,z3[0][0].length);
  }

  @Test
  public void testCDoubleArraysInits() {
    int n3 = 3, n2 = 5, n1 = 7;

    Cdouble val = new Cdouble(1.0,-1.0);

    double[]     a1 = cfilldouble(val,n1);
    double[][]   z2 = cfilldouble(val,n1,n2);
    double[][][] z3 = cfilldouble(val,n1,n2,n3);

    assertEquals(n3,z3.length);
    assertEquals(n2,z2.length);
    assertEquals(2*n1,a1.length);

    assertEquals(n2,z3[0].length);
    assertEquals(2*n1,z2[0].length);

    assertEquals(2*n1,z3[0][0].length);

    for (int i1=0; i1<n1; ++i1) {
      assertEq(val.r, z3[0][0][2*i1]);
      assertEq(val.i, z3[0][0][2*i1+1]);
      assertEq(val.r, z2[0][2*i1]);
      assertEq(val.i, z2[0][2*i1+1]);
      assertEq(val.r, a1[2*i1]);
      assertEq(val.i, a1[2*i1+1]);
    }

    czero(a1); czero(z2); czero(z3);

    assertArrayEquals(a1, czerodouble(n1), FLT_EPSILON);
    assertArrayEquals(z2, czerodouble(n1,n2));
    assertArrayEquals(z3, czerodouble(n1,n2,n3));

    // Rand
    a1 = cranddouble(2*n1);
    z2 = cranddouble(2*n1,2*n2);
    z3 = cranddouble(2*n1,2*n2,2*n3);

    assertEquals(2*n3,z3.length);
    assertEquals(2*n2,z2.length);
    assertEquals(2*n2,z3[0].length);
    assertEquals(4*n1,a1.length);
    assertEquals(4*n1,z2[0].length);
    assertEquals(4*n1,z3[0][0].length);

    crand(a1); crand(z2); crand(z3);

    assertEquals(2*n3,z3.length);
    assertEquals(2*n2,z2.length);
    assertEquals(2*n2,z3[0].length);
    assertEquals(4*n1,a1.length);
    assertEquals(4*n1,z2[0].length);
    assertEquals(4*n1,z3[0][0].length);
  }

  @Test
  public void testShortArraysInits() {
    int n3 = 3, n2 = 5, n1 = 7;

    short val = (short)1;

    short[]     zs1 = fillshort(val,n1);
    short[][]   zs2 = fillshort(val,n1,n2);
    short[][][] zs3 = fillshort(val,n1,n2,n3);

    assertEquals(n3,zs3.length);
    assertEquals(n2,zs2.length);
    assertEquals(n1,zs1.length);

    assertEquals(n2,zs3[0].length);
    assertEquals(n1,zs2[0].length);

    assertEquals(n1,zs3[0][0].length);

    for (int i1=0; i1<n1; ++i1) {
      assertEq(val,zs3[0][0][i1]);
      assertEq(val,zs2[0][i1]);
      assertEq(val,zs1[i1]);
    }

    zero(zs1); zero(zs2); zero(zs3);

    assertArrayEquals(zs1, zeroshort(n1));
    assertArrayEquals(zs2, zeroshort(n1,n2));
    assertArrayEquals(zs3, zeroshort(n1,n2,n3));

  }

  @Test
  public void testByteArraysInits() {
    int n3 = 3, n2 = 5, n1 = 7;

    byte val = (byte)1;

    byte[]     zb1 = fillbyte(val,n1);
    byte[][]   zb2 = fillbyte(val,n1,n2);
    byte[][][] zb3 = fillbyte(val,n1,n2,n3);

    assertEquals(n3,zb3.length);
    assertEquals(n2,zb2.length);
    assertEquals(n1,zb1.length);

    assertEquals(n2,zb3[0].length);
    assertEquals(n1,zb2[0].length);

    assertEquals(n1,zb3[0][0].length);


    for (int i1=0; i1<n1; ++i1) {
      assertEquals(val, zb3[0][0][i1]);
      assertEquals(val, zb2[0][i1]);
      assertEquals(val, zb1[i1]);
    }

    zero(zb1); zero(zb2); zero(zb3);

    assertArrayEquals(zb1, zerobyte(n1));
    assertArrayEquals(zb2, zerobyte(n1,n2));
    assertArrayEquals(zb3, zerobyte(n1,n2,n3));

  }

  @Test
  public void testSort() {
    Random r = new Random(314159);
    int[] ntest = {100,1023,1024,1025};
    for (int n:ntest) {
      float[] x = new float[n];
      for (int m = 1; m<2*n; m*=2) {
        for (int dist=0; dist<5; ++dist) {
          for (int i=0,j=0,k=1; i<n; ++i) {
            int ix = 0;
            switch(dist) {
              case SAWTOOTH: ix = i%m; break;
              case RAND:     ix = r.nextInt()%m; break;
              case STAGGER:  ix = (i*m+i)%n; break;
              case PLATEAU:  ix = min(i,m); break;
              case SHUFFLE:  ix = r.nextInt()%m!=0?(j+=2):(k+=2); break;
            }
            x[i] = (float)ix;
          }
          for (int order=0; order<6; ++order) {
            float[] y = null;
            float[] z;
            switch(order) {
            case COPY:
              y = copy(x);
              break;
            case REV:
              y = reverse(x);
              break;
            case REVHALF1:
              y = copy(x);
              z = reverse(copy(n/2,x));
              copy(n/2,0,z,0,y);
              break;
            case REVHALF2:
              y = copy(x);
              z = reverse(copy(n/2,n/2,x));
              copy(n/2,0,z,n/2,y);
              break;
            case SORT:
              y = copy(x);
              java.util.Arrays.sort(y);
              break;
            case DITHER:
              y = copy(x);
              for (int i=0; i<n; ++i)
                y[i] += (float)(i%5);
              break;
            }
            sortAndCheck(y);
          }
        }
      }
    }
  }

  @Test
  public void testCfloat1() {
    int n1 = 8;
    int n2 = 6;
    int n3 = 4;
    Cfloat c0 = new Cfloat(0.0f,0.0f);
    Cfloat c1 = new Cfloat(1.0f,0.0f);
    Cfloat c2 = new Cfloat(2.0f,0.0f);
    Cfloat c10 = new Cfloat(10.0f,0.0f);
    Cfloat c12 = new Cfloat(12.0f,0.0f);
    Cfloat c20 = new Cfloat(20.0f,0.0f);
    Cfloat c100 = new Cfloat(100.0f,0.0f);
    Cfloat c123 = new Cfloat(123.0f,0.0f);
    Cfloat c200 = new Cfloat(200.0f,0.0f);
    float[] a1 = crampfloat(c0,c1,n1);
    float[][] a2 = crampfloat(c0,c1,c10,n1,n2);
    float[][][] a3 = crampfloat(c0,c1,c10,c100,n1,n2,n3);
    float[] b1;
    float[][] b2;
    float[][][] b3;

    b1 = ccopy(a1);
    b2 = ccopy(a2);
    b3 = ccopy(a3);
    assertEqual(b1,a1);
    assertEqual(b2,a2);
    assertEqual(b3,a3);

    ccopy(a1,b1);
    ccopy(a2,b2);
    ccopy(a3,b3);
    assertEqual(b1,a1);
    assertEqual(b2,a2);
    assertEqual(b3,a3);

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

    b1 = ccopy(n1/2,0,2,a1);
    b2 = ccopy(n1/2,n2/2,0,0,2,2,a2);
    b3 = ccopy(n1/2,n2/2,n3/2,0,0,0,2,2,2,a3);
    assertEqual(b1,crampfloat(c0,c2,n1/2));
    assertEqual(b2,crampfloat(c0,c2,c20,n1/2,n2/2));
    assertEqual(b3,crampfloat(c0,c2,c20,c200,n1/2,n2/2,n3/2));

    b1 = ccopy(a1);
    b2 = ccopy(a2);
    b3 = ccopy(a3);
    ccopy(n1-1,1,a1,1,b1);
    ccopy(n1-2,n2-1,2,1,a2,2,1,b2);
    ccopy(n1-3,n2-2,n3-1,3,2,1,a3,3,2,1,b3);
    assertEqual(b1,crampfloat(c0,c1,n1));
    assertEqual(b2,crampfloat(c0,c1,c10,n1,n2));
    assertEqual(b3,crampfloat(c0,c1,c10,c100,n1,n2,n3));

    b1 = creverse(creverse(a1));
    assertEqual(b1,a1);

    b2 = creshape(n1,n2,cflatten(a2));
    b3 = creshape(n1,n2,n3,cflatten(a3));
    assertEqual(a2,b2);
    assertEqual(a3,b3);

    b2 = ctranspose(ctranspose(a2));
    assertEqual(a2,b2);
  }

  @Test
  public void testCfloat2() {
    int n1 = 3;
    int n2 = 4;
    int n3 = 5;
    Cfloat c0 = new Cfloat();
    Cfloat ca = new Cfloat(1.0f,2.0f);
    Cfloat cb1 = new Cfloat(2.0f,3.0f);
    Cfloat cb2 = new Cfloat(3.0f,4.0f);
    Cfloat cb3 = new Cfloat(4.0f,5.0f);
    float[][][] cx,cy,cz;

    assertEqual(czerofloat(n1,n2,n3),cfillfloat(c0,n1,n2,n3));

    cx = crampfloat(ca,cb1,cb2,cb3,n1,n2,n3);
    assertEqual(cx,csub(cadd(cx,cx),cx));
    assertEqual(cx,csub(cadd(cx,ca),ca));
    assertEqual(cfillfloat(ca,n1,n2,n3),csub(cadd(ca,cx),cx));

    cx = crampfloat(ca,cb1,cb2,cb3,n1,n2,n3);
    assertEqual(cx,cdiv(cmul(cx,cx),cx));
    assertEqual(cx,cdiv(cmul(cx,ca),ca));
    assertEqual(cfillfloat(ca,n1,n2,n3),cdiv(cmul(ca,cx),cx));

    cx = crampfloat(ca,cb1,cb2,cb3,n1,n2,n3);
    assertEqual(cnorm(cx),cabs(cmul(cx,cconj(cx))));

    float[][][] rr = fillfloat(1.0f,n1,n2,n3);
    float[][][] ra = rampfloat(0.0f,1.0f,1.0f,1.0f,n1,n2,n3);
    cx = polar(rr,ra);
    float[][][] rx = cos(ra);
    float[][][] ry = sin(ra);
    cy = cmplx(rx,ry);
    assertEqual(cx,cy);
    Cfloat ci = new Cfloat(0.0f,1.0f);
    float[][][] ciw = crampfloat(c0,ci,ci,ci,n1,n2,n3);
    cz = cexp(ciw);
    assertEqual(cx,cz);
  }

  @Test
  public void testMonotonic() {
    double[] a = {};
    assertTrue(isMonotonic(a));
    assertTrue(isIncreasing(a));
    assertTrue(isDecreasing(a));

    double[] a0 = {0};
    assertTrue(isMonotonic(a0));
    assertTrue(isIncreasing(a0));
    assertTrue(isDecreasing(a0));

    double[] a01 = {0,1};
    assertTrue(isMonotonic(a01));
    assertTrue(isIncreasing(a01));
    assertTrue(!isDecreasing(a01));

    double[] a10 = {1,0};
    assertTrue(isMonotonic(a10));
    assertTrue(!isIncreasing(a10));
    assertTrue(isDecreasing(a10));

    double[] a101 = {1,0,1};
    assertTrue(!isMonotonic(a101));
    assertTrue(!isIncreasing(a101));
    assertTrue(!isDecreasing(a101));

    double[] a010 = {0,1,0};
    assertTrue(!isMonotonic(a010));
    assertTrue(!isIncreasing(a010));
    assertTrue(!isDecreasing(a010));
  }

  @Test
  public void testBinarySearch() {
    double[] a = {};
    checkSearch(a,1);

    double[] a0 = {2};
    checkSearch(a0,1);
    checkSearch(a0,2);
    checkSearch(a0,3);

    double[] a13 = {1,3};
    checkSearch(a13,0);
    checkSearch(a13,1);
    checkSearch(a13,2);
    checkSearch(a13,3);
    checkSearch(a13,4);

    double[] a31 = {3,1};
    checkSearch(a31,0);
    checkSearch(a31,1);
    checkSearch(a31,2);
    checkSearch(a31,3);
    checkSearch(a31,4);

    double[] a135 = {1,3,5};
    checkSearch(a135,0);
    checkSearch(a135,1);
    checkSearch(a135,2);
    checkSearch(a135,3);
    checkSearch(a135,4);
    checkSearch(a135,5);
    checkSearch(a135,6);

    double[] a531 = {5,3,1};
    checkSearch(a531,0);
    checkSearch(a531,1);
    checkSearch(a531,2);
    checkSearch(a531,3);
    checkSearch(a531,4);
    checkSearch(a531,5);
    checkSearch(a531,6);
  }

  @Test
  public void testSin() {
    assertEq(0.0f,sin(FLT_PI));
    assertEq(0.0d,sin(DBL_PI));
  }

  @Test
  public void testCos() {
    assertEq(1.0f,cos(2.0f*FLT_PI));
    assertEq(1.0d,cos(2.0d*DBL_PI));
  }

  @Test
  public void testTan() {
    assertEq(1.0f,tan(FLT_PI/4.0f));
    assertEq(1.0d,tan(DBL_PI/4.0d));
  }

  @Test
  public void testASin() {
    assertEq(FLT_PI/2.0f,asin(1.0f));
    assertEq(DBL_PI/2.0d,asin(1.0d));
  }

  @Test
  public void testACos() {
    assertEq(FLT_PI/2.0f,acos(0.0f));
    assertEq(DBL_PI/2.0d,acos(0.0d));
  }

  @Test
  public void testATan() {
    assertEq(FLT_PI/4.0f,atan(1.0f));
    assertEq(DBL_PI/4.0d,atan(1.0d));
  }

  @Test
  public void testATan2() {
    assertEq(FLT_PI/2.0f,atan2(1.0f,0.0f));
    assertEq(DBL_PI/2.0d,atan2(1.0d,0.0d));
    assertEq(-3.0f*FLT_PI/4.0f,atan2(-1.0f,-1.0f));
    assertEq(-3.0d*DBL_PI/4.0d,atan2(-1.0d,-1.0d));
  }

  @Test
  public void testAngleConversion() {
    assertEq(FLT_PI,toRadians(180.0f));
    assertEq(DBL_PI,toRadians(180.0d));

    assertEq(180.0f,toDegrees(FLT_PI));
    assertEq(180.0d,toDegrees(DBL_PI));
  }

  @Test
  public void testLog() {
    assertEq(1.0f,log(exp(1.0f)));
    assertEq(1.0d,log(exp(1.0d)));
  }

  @Test
  public void testHyperbolicTrig() {
    assertEq(tanh(1.0f),sinh(1.0f)/cosh(1.0f));
    assertEq(tanh(1.0d),sinh(1.0d)/cosh(1.0d));
  }

  @Test
  public void testSqrtPow() {
    assertEq(3.0f,sqrt(pow(3.0f,2.0f)));
    assertEq(3.0d,sqrt(pow(3.0d,2.0d)));
  }

  @Test
  public void testCeil() {
    assertEq(4.0f,ceil(FLT_PI));
    assertEq(4.0d,ceil(DBL_PI));
    assertEq(-3.0f,ceil(-FLT_PI));
    assertEq(-3.0d,ceil(-DBL_PI));
  }

  @Test
  public void testFloor() {
    assertEq(3.0f,floor(FLT_PI));
    assertEq(3.0d,floor(DBL_PI));
    assertEq(-4.0f,floor(-FLT_PI));
    assertEq(-4.0d,floor(-DBL_PI));
  }

  @Test
  public void testRoundInt() {
    assertEq(3.0f,rint(FLT_PI));
    assertEq(3.0d,rint(DBL_PI));
    assertEq(-3.0f,rint(-FLT_PI));
    assertEq(-3.0d,rint(-DBL_PI));
  }

  @Test
  public void testRound() {
    assertEq(3,round(FLT_PI));
    assertEq(3,round(DBL_PI));
    assertEq(-3,round(-FLT_PI));
    assertEq(-3,round(-DBL_PI));

    assertEq(3,round(FLT_E));
    assertEq(3,round(DBL_E));
    assertEq(-3,round(-FLT_E));
    assertEq(-3,round(-DBL_E));
  }

  @Test
  public void testSignum() {
    assertEq(1.0f,signum(FLT_PI));
    assertEq(1.0d,signum(DBL_PI));
    assertEq(-1.0f,signum(-FLT_PI));
    assertEq(-1.0d,signum(-DBL_PI));
    assertEq(0.0f,signum(0.0f));
    assertEq(0.0d,signum(0.0d));

  }

  @Test
  public void testAbs() {
    assertEq(2,abs(2));
    assertEq(2L,abs(2L));
    assertEq(2.0f,abs(2.0f));
    assertEq(2.0d,abs(2.0d));
    assertEq(2,abs(-2));
    assertEq(2L,abs(-2L));
    assertEq(2.0f,abs(-2.0f));
    assertEq(2.0d,abs(-2.0d));
    assertEquals(0, Float.floatToIntBits(abs(0.0f)));
    assertEquals(0, Double.doubleToLongBits(abs(0.0d)));
  }

  @Test void testMax() {
    assertEq(4,max(1,3,4,2));
    assertEq(4L,max(1L,3L,4L,2L));
    assertEq(4.0f,max(1.0f,3.0f,4.0f,2.0f));
    assertEq(4.0d,max(1.0d,3.0d,4.0d,2.0d));

    assertEq(4,max(1,3,4));
    assertEq(4L,max(1L,3L,4L));
    assertEq(4.0f,max(1.0f,3.0f,4.0f));
    assertEq(4.0d,max(1.0d,3.0d,4.0d));
  }

  @Test
  public void testMin() {
    assertEq(1,min(3,1,4,2));
    assertEq(1L,min(3L,1L,4L,2L));
    assertEq(1.0f,min(3.0f,1.0f,4.0f,2.0f));
    assertEq(1.0d,min(3.0d,1.0d,4.0d,2.0d));

    assertEq(1,min(3,1,4));
    assertEq(1L,min(3L,1L,4L));
    assertEq(1.0f,min(3.0f,1.0f,4.0f));
    assertEq(1.0d,min(3.0d,1.0d,4.0d));
  }

  @Test
  public void testCubicRoot() {
    assertEq(2.0, cbrt(8.0));
    assertEq(2.0f, cbrt(8.0f));
  }

  @Test
  public void testHypotenuse() {
    assertEq(5.0, hypot(3.0, 4.0));
    assertEq(5.0f, hypot(3.0f, 4.0f));
  }

  @Test
  public void testExponentialMinusOne() {
    assertEq(22025.46579480, expm1(10.0));
    assertEq(22025.46579480f, expm1(10.0f));
  }

  @Test
  public void testLnPlusOne() {
    assertEq(2.39789527279837 , log1p(10.0 ));
    assertEq(2.39789527279837f, log1p(10.0f));
  }

  @Test
  public void testLog10() {
    float fi = 1.0f;
    double di = 1.0;
    for (int i=1; i<=10; ++i) {
      fi *= 10.0f; di *= 10.0;
      assertEq((float)i, log10(fi));
      assertEq((double)i, log10(di));
    }
  }

  @Test
  public void testUlp() {
    assertEq(FLT_EPSILON, ulp(1.2345f));
    assertEq(DBL_EPSILON, ulp(1.2345));
  }

  @Test
  public void testRandomBounds() {
    for (int i=0; i<100; ++i) {
      double rd  = randomDouble();
      double rd2 = random();
      double rf  = randomFloat();
      assertTrue(rd  >= 0.0d && rd  < 1.0d);
      assertTrue(rd2 >= 0.0d && rd2 < 1.0d);
      assertTrue(rf  >= 0.0d && rf  < 1.0d);
    }
  }

  @Test
  public void testIEEERemainder() {
    assertEq(-1.0d, IEEEremainder(3.0d,2.0d));
    assertEq(-1.0f, IEEEremainder(3.0f,2.0f));
  }

  @Test
  public void testRampBytes() {
    int n1 = 7; int n2 = 5; int n3 = 3;
    byte f = (byte)0; byte r1 = (byte)1; byte r2 = (byte)2;
    byte[]     arr1 = rampbyte(f,r1,n3);
    byte[][]   arr2 = rampbyte(f,r1,r2,n3,n2);
    byte[][][] arr3 = rampbyte(f,r1,r2,r1,n3,n2,n1);

    for (int i3=0; i3<n3; ++i3) {
      for (int i2=0; i2<n2; ++i2) {
        for (int i1=0; i1<n1; ++i1) {
          assertEquals((byte)(i3+2*i2+i1), arr3[i1][i2][i3]);
        }
        assertEquals((byte) (2*i2 + i3), arr2[i2][i3]);
      }
      assertEquals((byte)i3, arr1[i3]);
    }
  }

  @Test
  public void testRampShorts() {
    int n1 = 7; int n2 = 5; int n3 = 3;
    short f = 0; short r1 = 1; short r2 = 2;
    short[]     arr1 = rampshort(f,r1,n3);
    short[][]   arr2 = rampshort(f,r1,r2,n3,n2);
    short[][][] arr3 = rampshort(f,r1,r2,r1,n3,n2,n1);

    for (int i3=0; i3<n3; ++i3) {
      for (int i2 = 0; i2 < n2; ++i2) {
        for (int i1 = 0; i1 < n1; ++i1) {
          assertEquals((short)(i3+2*i2+i1), arr3[i1][i2][i3]);
        }
        assertEquals((short)(2*i2+i3), arr2[i2][i3]);
      }
      assertEquals((short)i3, arr1[i3]);
    }
  }

  @Test
  public void testRampInts() {
    int n1 = 7; int n2 = 5; int n3 = 3;
    int f = 0; int r1 = 1; int r2 = 2;
    int[]     arr1 = rampint(f,r1,n3);
    int[][]   arr2 = rampint(f,r1,r2,n3,n2);
    int[][][] arr3 = rampint(f,r1,r2,r1,n3,n2,n1);

    for (int i3=0; i3<n3; ++i3) {
      for (int i2=0; i2<n2; ++i2) {
        for (int i1=0; i1<n1; ++i1) {
          assertEquals((i3+2*i2+i1), arr3[i1][i2][i3]);
        }
        assertEquals((2*i2+i3), arr2[i2][i3]);
      }
      assertEquals(i3, arr1[i3]);
    }
  }

  @Test
  public void testRampLongs() {
    int n1 = 7; int n2 = 5; int n3 = 3;
    long f = 0L; long r1 = 1L; long r2 = 2L;
    long[]     arr1 = ramplong(f,r1,n3);
    long[][]   arr2 = ramplong(f,r1,r2,n3,n2);
    long[][][] arr3 = ramplong(f,r1,r2,r1,n3,n2,n1);

    for (int i3=0; i3<n3; ++i3) {
      for (int i2=0; i2<n2; ++i2) {
        for (int i1=0; i1<n1; ++i1) {
          assertEquals((long)(i3+2*i2+i1), arr3[i1][i2][i3]);
        }
        assertEquals((long)(2*i2+i3), arr2[i2][i3]);
      }
      assertEquals((long)i3, arr1[i3]);
    }
  }

  @Test
  public void testRampDoubles() {
    int n1 = 7; int n2 = 5; int n3 = 3;
    double f = 0.0d; double r1 = 1.0d; double r2 = 2.0d;
    double[]     arr1 = rampdouble(f,r1,n3);
    double[][]   arr2 = rampdouble(f,r1,r2,n3,n2);
    double[][][] arr3 = rampdouble(f,r1,r2,r1,n3,n2,n1);

    for (int i3=0; i3<n3; ++i3) {
      for (int i2=0; i2<n2; ++i2) {
        for (int i1=0; i1<n1; ++i1) {
          assertEquals((double)(i3+2*i2+i1), arr3[i1][i2][i3]);
        }
        assertEquals((double)(2*i2+i3), arr2[i2][i3]);
      }
      assertEquals((double)i3, arr1[i3]);
    }
  }

//////////////////////////////////////////////////////////////////////////////
// protected

  protected static int n3;
  protected static int n2;
  protected static int n1;

  protected void assertEq(float expected, float actual) {
    float small = 1.0e-6f*max(abs(expected),abs(actual),1.0f);
    assertEquals(expected,actual,small);
  }

  protected void assertEq(double expected, double actual) {
    double small = 1.0e-12f*max(abs(expected),abs(actual),1.0d);
    assertEquals(expected,actual,small);
  }

  protected void assertEq(byte expected, byte actual) {
    assertEquals(expected,actual);
  }

  protected void assertEq(int expected, int actual) {
    assertEquals(expected,actual);
  }

  protected void assertEq(long expected, long actual) {
    assertEquals(expected,actual);
  }

  protected void assertEqual(float[] rx, float[] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(float[][] rx, float[][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(float[][][] rx, float[][][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(double[] rx, double[] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(double[][] rx, double[][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(double[][][] rx, double[][][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(int[] rx, int[] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(int[][] rx, int[][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(int[][][] rx, int[][][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(long[] rx, long[] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(long[][] rx, long[][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(long[][][] rx, long[][][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(short[] rx, short[] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(short[][] rx, short[][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(short[][][] rx, short[][][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(byte[] rx, byte[] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(byte[][] rx, byte[][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertEqual(byte[][][] rx, byte[][][] ry) {
    assertTrue(equal(rx,ry));
  }

  protected void assertAlmostEqual(float[][][] rx, float[][][] ry) {
    assertTrue(equal(FLT_TOLERANCE,rx,ry));
  }

  protected void assertAlmostEqual(float[][] rx, float[][] ry) {
    assertTrue(equal(FLT_TOLERANCE,rx,ry));
  }

  protected void assertAlmostEqual(float[] rx, float[] ry) {
    assertTrue(equal(FLT_TOLERANCE,rx,ry));
  }

  protected void assertAlmostEqual(double[][][] rx, double[][][] ry) {
    assertTrue(equal(DBL_TOLERANCE,rx,ry));
  }

  protected void assertAlmostEqual(double[][] rx, double[][] ry) {
    assertTrue(equal(DBL_TOLERANCE,rx,ry));
  }

  protected void assertAlmostEqual(double[] rx, double[] ry) {
    assertTrue(equal(DBL_TOLERANCE,rx,ry));
  }

  protected void checkSearch(double[] a, double x) {
    int n = a.length;
    int i = binarySearch(a,x);
    validateSearch(a,x,i);
    for (int is=-2; is<n+2; ++is) {
      i = binarySearch(a,x,is);
      validateSearch(a,x,i);
    }
  }

  protected void validateSearch(double[] a, double x, int i) {
    int n = a.length;
    if (i>=0) {
      assertTrue(a[i]==x);
    } else {
      i = -(i+1);
      if (n==0) {
        assertTrue(i==0);
      } else if (n<2 || a[0]<a[n-1]) {
        if (i==0) {
          assertTrue(x<a[i]);
        } else if (i==n) {
          assertTrue(a[i-1]<x);
        } else {
          assertTrue(a[i-1]<x && x<a[i]);
        }
      } else {
        if (i==0) {
          assertTrue(x>a[i]);
        } else if (i==n) {
          assertTrue(a[i-1]>x);
        } else {
          assertTrue(a[i-1]>x && x>a[i]);
        }
      }
    }
  }

  protected void sortAndCheck(float[] x) {
    int n = x.length;
    float[] x1 = copy(x);
    for (int k=0; k<n; k+=n/4) {
      quickPartialSort(k,x1);
      for (int i=0; i<k; ++i)
        assertTrue(x1[i]<=x1[k]);
      for (int i=k; i<n; ++i)
        assertTrue(x1[k]<=x1[i]);
    }
    float[] x2 = copy(x);
    quickSort(x2);
    for (int i=1; i<n; ++i)
      assertTrue(x2[i-1]<=x2[i]);
    int[] i1 = rampint(0,1,n);
    for (int k=0; k<n; k+=n/4) {
      quickPartialIndexSort(k,x,i1);
      for (int j=0; j<k; ++j)
        assertTrue(x[i1[j]]<=x[i1[k]]);
      for (int j=k+1; j<n; ++j)
        assertTrue(x[i1[k]]<=x[i1[j]]);
    }
    int[] i2 = rampint(0,1,n);
    quickIndexSort(x,i2);
    for (int j=1; j<n; ++j)
      assertTrue(x[i2[j-1]]<=x[i2[j]]);
  }

  private static final float FLT_TOLERANCE = 1.0E-4f;
  private static final double DBL_TOLERANCE = 1.0E-4d;
}

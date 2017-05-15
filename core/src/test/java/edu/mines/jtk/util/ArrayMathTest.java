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

import static edu.mines.jtk.util.ArrayMath.*;

import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link edu.mines.jtk.util.ArrayMath}.
 * @author Dave Hale, Colorado School of Mines
 * @author Chris Engelsma
 * @version 2017.05.15
 */
public class ArrayMathTest {

  // Adapted from Bentley, J.L., and McIlroy, M.D., 1993, Engineering a sort
  // function, Software -- Practice and Experience, v. 23(11), p. 1249-1265.
  private static final int SAWTOOTH=0,RAND=1,STAGGER=2,PLATEAU=3,SHUFFLE=4;
  private static final int COPY=0,REV=1,REVHALF1=2,REVHALF2=3,SORT=4,DITHER=5;

  @Test
  public void testCFloatArrays() {
    int n3 = 3, n2 = 5, n1 = 7;

    Cfloat val = new Cfloat(1.0f,-1.0f);

    float[]     z1 = cfillfloat(val,n1);
    float[][]   z2 = cfillfloat(val,n1,n2);
    float[][][] z3 = cfillfloat(val,n1,n2,n3);

    assertEquals(n3,z3.length);
    assertEquals(n2,z2.length);
    assertEquals(2*n1,z1.length);

    assertEquals(n2,z3[0].length);
    assertEquals(2*n1,z2[0].length);

    assertEquals(2*n1,z3[0][0].length);

    for (int i1=0; i1<n1; ++i1) {
      assertEq(val.r, z3[0][0][2*i1]);
      assertEq(val.i, z3[0][0][2*i1+1]);
      assertEq(val.r, z2[0][2*i1]);
      assertEq(val.i, z2[0][2*i1+1]);
      assertEq(val.r, z1[2*i1]);
      assertEq(val.i, z1[2*i1+1]);
    }

    czero(z1); czero(z2); czero(z3);

    assertArrayEquals(z1, czerofloat(n1), FLT_EPSILON);
    assertArrayEquals(z2, czerofloat(n1,n2));
    assertArrayEquals(z3, czerofloat(n1,n2,n3));

    // Rand
    z1 = crandfloat(2*n1);
    z2 = crandfloat(2*n1,2*n2);
    z3 = crandfloat(2*n1,2*n2,2*n3);

    assertEquals(2*n3,z3.length);
    assertEquals(2*n2,z2.length);
    assertEquals(2*n2,z3[0].length);
    assertEquals(4*n1,z1.length);
    assertEquals(4*n1,z2[0].length);
    assertEquals(4*n1,z3[0][0].length);
  }

  @Test
  public void testCDoubleArrays() {
    int n3 = 3, n2 = 5, n1 = 7;

    Cdouble val = new Cdouble(1.0,-1.0);

    double[]     z1 = cfilldouble(val,n1);
    double[][]   z2 = cfilldouble(val,n1,n2);
    double[][][] z3 = cfilldouble(val,n1,n2,n3);

    assertEquals(n3,z3.length);
    assertEquals(n2,z2.length);
    assertEquals(2*n1,z1.length);

    assertEquals(n2,z3[0].length);
    assertEquals(2*n1,z2[0].length);

    assertEquals(2*n1,z3[0][0].length);

    for (int i1=0; i1<n1; ++i1) {
      assertEq(val.r, z3[0][0][2*i1]);
      assertEq(val.i, z3[0][0][2*i1+1]);
      assertEq(val.r, z2[0][2*i1]);
      assertEq(val.i, z2[0][2*i1+1]);
      assertEq(val.r, z1[2*i1]);
      assertEq(val.i, z1[2*i1+1]);
    }

    czero(z1); czero(z2); czero(z3);

    assertArrayEquals(z1, czerodouble(n1), FLT_EPSILON);
    assertArrayEquals(z2, czerodouble(n1,n2));
    assertArrayEquals(z3, czerodouble(n1,n2,n3));

    // Rand
    z1 = cranddouble(2*n1);
    z2 = cranddouble(2*n1,2*n2);
    z3 = cranddouble(2*n1,2*n2,2*n3);

    assertEquals(2*n3,z3.length);
    assertEquals(2*n2,z2.length);
    assertEquals(2*n2,z3[0].length);
    assertEquals(4*n1,z1.length);
    assertEquals(4*n1,z2[0].length);
    assertEquals(4*n1,z3[0][0].length);
  }

  @Test
  public void testDoubleArrays() {
    int n3 = 3, n2 = 5, n1 = 7;

    double val = 1.0;

    double[]     zd1 = filldouble(val,n1);
    double[][]   zd2 = filldouble(val,n1,n2);
    double[][][] zd3 = filldouble(val,n1,n2,n3);

    assertEquals(n3,zd3.length);
    assertEquals(n2,zd2.length);
    assertEquals(n1,zd1.length);

    assertEquals(n2,zd3[0].length);
    assertEquals(n1,zd2[0].length);

    assertEquals(n1,zd3[0][0].length);

    for (int i1=0; i1<n1; ++i1) {
      assertEq(val,zd3[0][0][i1]);
      assertEq(val,zd2[0][i1]);
      assertEq(val,zd1[i1]);
    }

    zero(zd1); zero(zd2); zero(zd3);

    assertArrayEquals(zd1, zerodouble(n1), FLT_EPSILON);
    assertArrayEquals(zd2, zerodouble(n1,n2));
    assertArrayEquals(zd3, zerodouble(n1,n2,n3));

    // Rand
    zd1 = randdouble(2*n1);
    zd2 = randdouble(2*n1,2*n2);
    zd3 = randdouble(2*n1,2*n2,2*n3);

    assertEquals(2*n3,zd3.length);
    assertEquals(2*n2,zd2.length);
    assertEquals(2*n2,zd3[0].length);
    assertEquals(2*n1,zd1.length);
    assertEquals(2*n1,zd2[0].length);
    assertEquals(2*n1,zd3[0][0].length);

  }

  @Test
  public void testFloatArrays() {
    int n3 = 3, n2 = 5, n1 = 7;

    float val = 1.0f;

    float[]     zf1 = fillfloat(val,n1);
    float[][]   zf2 = fillfloat(val,n1,n2);
    float[][][] zf3 = fillfloat(val,n1,n2,n3);

    assertEquals(n3,zf3.length);
    assertEquals(n2,zf2.length);
    assertEquals(n1,zf1.length);

    assertEquals(n2,zf3[0].length);
    assertEquals(n1,zf2[0].length);

    assertEquals(n1,zf3[0][0].length);

    for (int i1=0; i1<n1; ++i1) {
      assertEq(val,zf3[0][0][i1]);
      assertEq(val,zf2[0][i1]);
      assertEq(val,zf1[i1]);
    }

    zero(zf1); zero(zf2); zero(zf3);

    assertArrayEquals(zf1, zerofloat(n1), FLT_EPSILON);
    assertArrayEquals(zf2, zerofloat(n1,n2));
    assertArrayEquals(zf3, zerofloat(n1,n2,n3));

    // Rand
    zf1 = randfloat(2*n1);
    zf2 = randfloat(2*n1,2*n2);
    zf3 = randfloat(2*n1,2*n2,2*n3);

    assertEquals(2*n3,zf3.length);
    assertEquals(2*n2,zf2.length);
    assertEquals(2*n2,zf3[0].length);
    assertEquals(2*n1,zf1.length);
    assertEquals(2*n1,zf2[0].length);
    assertEquals(2*n1,zf3[0][0].length);
  }

  @Test
  public void testLongArrays() {
    int n3 = 3, n2 = 5, n1 = 7;

    long val = 1L;

    long[]     zl1 = filllong(val,n1);
    long[][]   zl2 = filllong(val,n1,n2);
    long[][][] zl3 = filllong(val,n1,n2,n3);

    assertEquals(n3,zl3.length);
    assertEquals(n2,zl2.length);
    assertEquals(n1,zl1.length);

    assertEquals(n2,zl3[0].length);
    assertEquals(n1,zl2[0].length);

    assertEquals(n1,zl3[0][0].length);

    for (int i1=0; i1<n1; ++i1) {
      assertEq(val,zl3[0][0][i1]);
      assertEq(val,zl2[0][i1]);
      assertEq(val,zl1[i1]);
    }

    zero(zl1); zero(zl2); zero(zl3);

    assertArrayEquals(zl1, zerolong(n1));
    assertArrayEquals(zl2, zerolong(n1,n2));
    assertArrayEquals(zl3, zerolong(n1,n2,n3));

    // Rand
    zl1 = randlong(2*n1);
    zl2 = randlong(2*n1,2*n2);
    zl3 = randlong(2*n1,2*n2,2*n3);

    assertEquals(2*n3,zl3.length);
    assertEquals(2*n2,zl2.length);
    assertEquals(2*n2,zl3[0].length);
    assertEquals(2*n1,zl1.length);
    assertEquals(2*n1,zl2[0].length);
    assertEquals(2*n1,zl3[0][0].length);
  }

  @Test
  public void testIntArrays() {
    int n3 = 3, n2 = 5, n1 = 7;

    int val = 1;

    int[]     zi1 = fillint(val,n1);
    int[][]   zi2 = fillint(val,n1,n2);
    int[][][] zi3 = fillint(val,n1,n2,n3);

    assertEquals(n3,zi3.length);
    assertEquals(n2,zi2.length);
    assertEquals(n2,zi3[0].length);
    assertEquals(n1,zi1.length);
    assertEquals(n1,zi2[0].length);
    assertEquals(n1,zi3[0][0].length);

    for (int i1=0; i1<n1; ++i1) {
      assertEq(val,zi3[0][0][i1]);
      assertEq(val,zi2[0][i1]);
      assertEq(val,zi1[i1]);
    }

    zero(zi1); zero(zi2); zero(zi3);

    assertArrayEquals(zi1, zeroint(n1));
    assertArrayEquals(zi2, zeroint(n1,n2));
    assertArrayEquals(zi3, zeroint(n1,n2,n3));

    // Rand
    zi1 = randint(2*n1);
    zi2 = randint(2*n1,2*n2);
    zi3 = randint(2*n1,2*n2,2*n3);

    assertEquals(2*n3,zi3.length);
    assertEquals(2*n2,zi2.length);
    assertEquals(2*n2,zi3[0].length);
    assertEquals(2*n1,zi1.length);
    assertEquals(2*n1,zi2[0].length);
    assertEquals(2*n1,zi3[0][0].length);

  }

  @Test
  public void testShortArrays() {
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

  @Test public void testByteArrays() {
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


    for (int i3=0; i3<n3; ++i3) {
      for (int i2=0; i2<n2; ++i2) {
        for (int i1=0; i1<n1; ++i1) {
          assertEquals(val,zb3[i3][i2][i1]);
        }
        assertEquals(val,zb2[i3][i2]);
      }
      assertEquals(val,zb1[i3]);
    }

    zero(zb1); zero(zb2); zero(zb3);

    assertArrayEquals(zb1, zerobyte(n1));
    assertArrayEquals(zb2, zerobyte(n1,n2));
    assertArrayEquals(zb3, zerobyte(n1,n2,n3));

  }

  @Test
  public void testComparatorFunctions() {
    assertEquals(9,  max(1,9,8,6));
    assertEquals(9,  max(1,9,8));
    assertEquals(9,  max(1,9));

    assertEquals(1,  min(1,9,8,6));
    assertEquals(1,  min(1,9,8));
    assertEquals(1,  min(1,9));

    assertEquals(1,  min(1,9,8,6));
    assertEquals(1,  min(1,9,8,6));

    assertEquals(9L, max(1L,9L,8L,6L));
    assertEquals(9L, max(1L,9L,8L));
    assertEquals(9L, max(1L,9L));

    assertEquals(1L, min(1L,9L,8L,6L));
    assertEquals(1L, min(1L,9L,8L));
    assertEquals(1L, min(1L,9L));

    assertEq(1.0, min(1.0,9.0,8.0,6.0));
    assertEq(1.0, min(1.0,9.0,8.0));
    assertEq(1.0, min(1.0,9.0));

    assertEq(9.0, max(1.0,9.0,8.0,6.0));
    assertEq(9.0, max(1.0,9.0,8.0));
    assertEq(9.0, max(1.0,9.0));

    assertEq(1.0f, min(1.0f,9.0f,8.0f,6.0f));
    assertEq(1.0f, min(1.0f,9.0f,8.0f));
    assertEq(1.0f, min(1.0f,9.0f));

    assertEq(9.0f, max(1.0f,9.0f,8.0f,6.0f));
    assertEq(9.0f, max(1.0f,9.0f,8.0f));
    assertEq(9.0f, max(1.0f,9.0f));

  }

  @Test
  public void testAlgebraicFunctions() {
    // Cubic root
    assertEq(2.0 , cbrt(8.0 ));
    assertEq(2.0f, cbrt(8.0f));

    // Hypotenuse
    assertEq(5.0 , hypot(3.0 , 4.0 ));
    assertEq(5.0f, hypot(3.0f, 4.0f));

    // (e^x)-1
    assertEq(22025.46579480 , expm1(10.0 ));
    assertEq(22025.46579480f, expm1(10.0f));

    // ln(x+1)
    assertEq(2.39789527279837 , log1p(10.0 ));
    assertEq(2.39789527279837f, log1p(10.0f));
  }

  @Test
  public void testRoundingFunctions() {
    Random r = new Random();
    for (int i=0; i<10; ++i) {
      double d = r.nextDouble();
      float f = (float)d;

      assertEq(1.0 , ceil(d));
      assertEq(1.0f, ceil(f));

      assertEq(0.0 , floor(d));
      assertEq(0.0f, floor(f));

      assertEq( (d<=0.5 ) ? 0.0 : 1.0 , rint(d));
      assertEq( (f<=0.5f) ? 0.0f: 1.0f, rint(f));

      assertEq( (d<=0.5 ) ? 0.0 : 1.0 , round(d));
      assertEq( (f<=0.5f) ? 0.0f: 1.0f, round(f));
    }
  }

  @Test
  public void testAbsFunctions() {
    Random r = new Random();
    for (int i=0; i<10; ++i) {
      double d = 2*r.nextDouble()-1.0;
      float f  = (float)d;
      assertEq( (d<0.0 ) ? -1.0*d  : d, abs(d));
      assertEq( (f<0.0f) ? -1.0f*f : f, abs(f));
    }

    assertEq(-1.0 , signum(-100.0 ));
    assertEq(-1.0f, signum(-100.0f));
    assertEq( 0.0 , signum(   0.0 ));
    assertEq( 0.0f, signum(   0.0f));
    assertEq( 1.0 , signum( 100.0 ));
    assertEq( 1.0f, signum( 100.0f));
  }

  @Test
  public void testTrigFunctions() {
    double sqrt2  = sqrt(2.0);
    double sqrt3  = sqrt(3.0);
    double sqrt22 = sqrt2/2.0;
    double sqrt32 = sqrt3/2.0;
    double sqrt33 = sqrt3/3.0;

    double[] rads    = { 0.0,    PI/6.0, PI/4.0,   PI/3.0, PI/2.0 };
    double[] sinAns  = { 0.0,       0.5, sqrt22,   sqrt32,    1.0 };
    double[] cosAns  = { 1.0,    sqrt32, sqrt22,      0.5,    0.0 };
    double[] tanAns  = { 0.0,    sqrt33,    1.0,    sqrt3,    0.0 };
    double sinh, cosh, tanh;

    for (int i=0; i<rads.length; ++i) {
      double drad =        rads[i];
      float  frad = (float)rads[i];

      assertEq(       sinAns[i], sin(drad));
      assertEq((float)sinAns[i], sin(frad));

      assertEq(drad, asin(       sinAns[i]));
      assertEq(frad, asin((float)sinAns[i]));

      assertEq(       cosAns[i], cos(drad));
      assertEq((float)cosAns[i], cos(frad));

      assertEq(drad, acos(       cosAns[i]));
      assertEq(frad, acos((float)cosAns[i]));

      if (drad!=PI/2.0) {
        assertEq(        tanAns[i], tan(drad));
        assertEq((float) tanAns[i], tan(frad));

        assertEq(drad, atan(       tanAns[i]));
        assertEq(frad, atan((float)tanAns[i]));
      }

      sinh = (1.0-pow(E,-2*drad))/(2.0*pow(E,-drad));
      cosh = (1.0+pow(E,-2*drad))/(2.0*pow(E,-drad));
      tanh = (1.0-pow(E,-2*drad))/(1.0+pow(E,-2*drad));

      assertEq(       sinh, sinh(drad));
      assertEq((float)sinh, sinh(frad));

      assertEq(       cosh, cosh(drad));
      assertEq((float)cosh, cosh(frad));

      assertEq(       tanh, tanh(drad));
      assertEq((float)tanh, tanh(frad));

    }

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
  public void testFloat1() {
    int n1 = 8;
    int n2 = 6;
    int n3 = 4;
    float[] a1 = rampfloat(0,1,n1);
    float[][] a2 = rampfloat(0,1,10,n1,n2);
    float[][][] a3 = rampfloat(0,1,10,100,n1,n2,n3);
    float[] b1;
    float[][] b2;
    float[][][] b3;

    b1 = copy(a1);
    b2 = copy(a2);
    b3 = copy(a3);
    assertEqual(b1,a1);
    assertEqual(b2,a2);
    assertEqual(b3,a3);

    copy(a1,b1);
    copy(a2,b2);
    copy(a3,b3);
    assertEqual(b1,a1);
    assertEqual(b2,a2);
    assertEqual(b3,a3);

    b1 = copy(n1-1,a1);
    b2 = copy(n1-1,n2-1,a2);
    b3 = copy(n1-1,n2-1,n3-1,a3);
    assertEqual(b1,rampfloat(0,1,n1-1));
    assertEqual(b2,rampfloat(0,1,10,n1-1,n2-1));
    assertEqual(b3,rampfloat(0,1,10,100,n1-1,n2-1,n3-1));

    copy(n1-1,a1,b1);
    copy(n1-1,n2-1,a2,b2);
    copy(n1-1,n2-1,n3-1,a3,b3);
    assertEqual(b1,rampfloat(0,1,n1-1));
    assertEqual(b2,rampfloat(0,1,10,n1-1,n2-1));
    assertEqual(b3,rampfloat(0,1,10,100,n1-1,n2-1,n3-1));

    b1 = copy(n1-1,1,a1);
    b2 = copy(n1-2,n2-1,2,1,a2);
    b3 = copy(n1-3,n2-2,n3-1,3,2,1,a3);
    assertEqual(b1,rampfloat(1,1,n1-1));
    assertEqual(b2,rampfloat(12,1,10,n1-1,n2-1));
    assertEqual(b3,rampfloat(123,1,10,100,n1-1,n2-1,n3-1));
    
    copy(n1-1,1,a1,0,b1);
    copy(n1-2,n2-1,2,1,a2,0,0,b2);
    copy(n1-3,n2-2,n3-1,3,2,1,a3,0,0,0,b3);
    assertEqual(b1,rampfloat(1,1,n1-1));
    assertEqual(b2,rampfloat(12,1,10,n1-1,n2-1));
    assertEqual(b3,rampfloat(123,1,10,100,n1-1,n2-1,n3-1));

    b1 = copy(n1/2,0,2,a1);
    b2 = copy(n1/2,n2/2,0,0,2,2,a2);
    b3 = copy(n1/2,n2/2,n3/2,0,0,0,2,2,2,a3);
    assertEqual(b1,rampfloat(0,2,n1/2));
    assertEqual(b2,rampfloat(0,2,20,n1/2,n2/2));
    assertEqual(b3,rampfloat(0,2,20,200,n1/2,n2/2,n3/2));

    b1 = copy(a1);
    b2 = copy(a2);
    b3 = copy(a3);
    copy(n1-1,1,a1,1,b1);
    copy(n1-2,n2-1,2,1,a2,2,1,b2);
    copy(n1-3,n2-2,n3-1,3,2,1,a3,3,2,1,b3);
    assertEqual(b1,rampfloat(0,1,n1));
    assertEqual(b2,rampfloat(0,1,10,n1,n2));
    assertEqual(b3,rampfloat(0,1,10,100,n1,n2,n3));

    b1 = reverse(reverse(a1));
    assertEqual(b1,a1);

    b2 = reshape(n1,n2,flatten(a2));
    b3 = reshape(n1,n2,n3,flatten(a3));
    assertEqual(a2,b2);
    assertEqual(a3,b3);

    b2 = transpose(transpose(a2));
    assertEqual(a2,b2);
  }

  @Test
  public void testFloat2() {
    int n1 = 3;
    int n2 = 4;
    int n3 = 5;
    float r0 = 0.0f;
    float ra = 2.0f;
    float rb1 = 1.0f;
    float rb2 = 2.0f;
    float rb3 = 4.0f;
    float[][][] rx;

    assertEqual(zerofloat(n1,n2,n3),fillfloat(r0,n1,n2,n3));

    rx = rampfloat(ra,rb1,rb2,rb3,n1,n2,n3);
    assertEqual(rx,sub(add(rx,rx),rx));
    assertEqual(rx,sub(add(rx,ra),ra));
    assertEqual(fillfloat(ra,n1,n2,n3),sub(add(ra,rx),rx));

    rx = rampfloat(ra,rb1,rb2,rb3,n1,n2,n3);
    assertEqual(rx,div(mul(rx,rx),rx));
    assertEqual(rx,div(mul(rx,ra),ra));
    assertEqual(fillfloat(ra,n1,n2,n3),div(mul(ra,rx),rx));

    rx = rampfloat(ra,rb1,rb2,rb3,n1,n2,n3);
    assertEqual(rx,log(exp(rx)));

    rx = rampfloat(ra,rb1,rb2,rb3,n1,n2,n3);
    assertAlmostEqual(rx,mul(sqrt(rx),sqrt(rx)));

    rx = rampfloat(ra,rb1,rb2,rb3,n1,n2,n3);
    assertAlmostEqual(rx,pow(sqrt(rx),2.0f));

    rx = rampfloat(ra,rb1,rb2,rb3,n1,n2,n3);
    int[] imax = {-1,-1,-1};
    float rmax = max(rx,imax);
    assertTrue(rmax==rx[n3-1][n2-1][n1-1]);
    assertEq(n1-1,imax[0]);
    assertEq(n2-1,imax[1]);
    assertEq(n3-1,imax[2]);

    rx = rampfloat(ra,rb1,rb2,rb3,n1,n2,n3);
    int[] imin = {-1,-1,-1};
    float rmin = min(rx,imin);
    assertTrue(rmin==rx[0][0][0]);
    assertEq(0,imin[0]);
    assertEq(0,imin[1]);
    assertEq(0,imin[2]);
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
  public void testMath() {
    assertEq(0.0f,sin(FLT_PI));
    assertEq(0.0d,sin(DBL_PI));

    assertEq(1.0f,cos(2.0f*FLT_PI));
    assertEq(1.0d,cos(2.0d*DBL_PI));

    assertEq(1.0f,tan(FLT_PI/4.0f));
    assertEq(1.0d,tan(DBL_PI/4.0d));

    assertEq(FLT_PI/2.0f,asin(1.0f));
    assertEq(DBL_PI/2.0d,asin(1.0d));

    assertEq(FLT_PI/2.0f,acos(0.0f));
    assertEq(DBL_PI/2.0d,acos(0.0d));

    assertEq(FLT_PI/4.0f,atan(1.0f));
    assertEq(DBL_PI/4.0d,atan(1.0d));

    assertEq(FLT_PI/2.0f,atan2(1.0f,0.0f));
    assertEq(DBL_PI/2.0d,atan2(1.0d,0.0d));

    assertEq(-3.0f*FLT_PI/4.0f,atan2(-1.0f,-1.0f));
    assertEq(-3.0d*DBL_PI/4.0d,atan2(-1.0d,-1.0d));

    assertEq(FLT_PI,toRadians(180.0f));
    assertEq(DBL_PI,toRadians(180.0d));

    assertEq(180.0f,toDegrees(FLT_PI));
    assertEq(180.0d,toDegrees(DBL_PI));

    assertEq(1.0f,log(exp(1.0f)));
    assertEq(1.0d,log(exp(1.0d)));

    assertEq(3.0f,sqrt(pow(3.0f,2.0f)));
    assertEq(3.0d,sqrt(pow(3.0d,2.0d)));

    assertEq(tanh(1.0f),sinh(1.0f)/cosh(1.0f));
    assertEq(tanh(1.0d),sinh(1.0d)/cosh(1.0d));

    assertEq(4.0f,ceil(FLT_PI));
    assertEq(4.0d,ceil(DBL_PI));
    assertEq(-3.0f,ceil(-FLT_PI));
    assertEq(-3.0d,ceil(-DBL_PI));

    assertEq(3.0f,floor(FLT_PI));
    assertEq(3.0d,floor(DBL_PI));
    assertEq(-4.0f,floor(-FLT_PI));
    assertEq(-4.0d,floor(-DBL_PI));

    assertEq(3.0f,rint(FLT_PI));
    assertEq(3.0d,rint(DBL_PI));
    assertEq(-3.0f,rint(-FLT_PI));
    assertEq(-3.0d,rint(-DBL_PI));

    assertEq(3,round(FLT_PI));
    assertEq(3,round(DBL_PI));
    assertEq(-3,round(-FLT_PI));
    assertEq(-3,round(-DBL_PI));

    assertEq(3,round(FLT_E));
    assertEq(3,round(DBL_E));
    assertEq(-3,round(-FLT_E));
    assertEq(-3,round(-DBL_E));

    assertEq(1.0f,signum(FLT_PI));
    assertEq(1.0d,signum(DBL_PI));
    assertEq(-1.0f,signum(-FLT_PI));
    assertEq(-1.0d,signum(-DBL_PI));
    assertEq(0.0f,signum(0.0f));
    assertEq(0.0d,signum(0.0d));

    assertEq(2,abs(2));
    assertEq(2L,abs(2L));
    assertEq(2.0f,abs(2.0f));
    assertEq(2.0d,abs(2.0d));
    assertEq(2,abs(-2));
    assertEq(2L,abs(-2L));
    assertEq(2.0f,abs(-2.0f));
    assertEq(2.0d,abs(-2.0d));
    assertEquals("abs(float) changed sign of 0",
                 0, Float.floatToIntBits(abs(0.0f)));
    assertEquals("abs(double) changed sign of 0",
                 0, Double.doubleToLongBits(abs(0.0d)));

    assertEq(4,max(1,3,4,2));
    assertEq(4L,max(1L,3L,4L,2L));
    assertEq(4.0f,max(1.0f,3.0f,4.0f,2.0f));
    assertEq(4.0d,max(1.0d,3.0d,4.0d,2.0d));

    assertEq(1,min(3,1,4,2));
    assertEq(1L,min(3L,1L,4L,2L));
    assertEq(1.0f,min(3.0f,1.0f,4.0f,2.0f));
    assertEq(1.0d,min(3.0d,1.0d,4.0d,2.0d));
  }

//////////////////////////////////////////////////////////////////////////////
// private

  private void assertEq(float expected, float actual) {
    float small = 1.0e-6f*max(abs(expected),abs(actual),1.0f);
    assertEquals(expected,actual,small);
  }

  private void assertEq(double expected, double actual) {
    double small = 1.0e-12f*max(abs(expected),abs(actual),1.0d);
    assertEquals(expected,actual,small);
  }

  private void assertEqual(float[] rx, float[] ry) {
    assertTrue(equal(rx,ry));
  }

  private void assertEqual(float[][] rx, float[][] ry) {
    assertTrue(equal(rx,ry));
  }

  private void assertEqual(float[][][] rx, float[][][] ry) {
    assertTrue(equal(rx,ry));
  }

  private void assertAlmostEqual(float[][][] rx, float[][][] ry) {
    float tolerance = 100.0f*FLT_EPSILON;
    assertTrue(equal(tolerance,rx,ry));
  }
    private void checkSearch(double[] a, double x) {
    int n = a.length;
    int i = binarySearch(a,x);
    validateSearch(a,x,i);
    for (int is=-2; is<n+2; ++is) {
      i = binarySearch(a,x,is);
      validateSearch(a,x,i);
    }
  }
  private void validateSearch(double[] a, double x, int i) {
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
  private void sortAndCheck(float[] x) {
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
}

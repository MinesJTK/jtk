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
import static org.testng.Assert.*;

/**
 * Tests simple double array operations in
 * {@link edu.mines.jtk.util.ArrayMath}.
 * @author Chris Engelsma
 * @version 2017.05.31
 */
public class ArrayMathTestDouble extends ArrayMathTest {

  @BeforeMethod
  public void setUp() {
    n3 = 8; n2 = 6; n1 = 4;
    a1 = rampdouble(0d,1d,n1);
    a2 = rampdouble(0d,1d,10d,n1,n2);
    a3 = rampdouble(0d,1d,10d,100d,n1,n2,n3);
    b1 = zerodouble(n1);
    b2 = zerodouble(n1,n2);
    b3 = zerodouble(n1,n2,n3);
  }

  @Test
  public void testEqualityComparators() {
    zero(a3);
    assertTrue(equal(a3,b3));

    fill(1,a3);
    assertFalse(equal(a3,b3));

    assertTrue(equal(1.0,a3,b3));
    assertFalse(equal(0.99,a3,b3));
  }

  @Test
  public void testRampDoubles() {
    assertArraySize(a1,n1);
    assertArraySize(a2,n1,n2);
    assertArraySize(a3,n1,n2,n3);

    for (int i1=0; i1<n1; ++i1) {
      double expected = (double)i1;
      assertEquals(expected,a3[0][0][i1]);
      assertEquals(expected,a2[0][i1]);
      assertEquals(expected,a1[i1]);
    }
  }

  @Test
  public void testFillDoubles() {
    double val = 1.0d;

    assertEqual(zerodouble(n1,n2,n3),filldouble(0.0,n1,n2,n3));

    a1 = filldouble(val,n1);
    a2 = filldouble(val,n1,n2);
    a3 = filldouble(val,n1,n2,n3);

    assertArraySize(a1,n1);
    assertArraySize(a2,n1,n2);
    assertArraySize(a3,n1,n2,n3);

    assertOnlyContains(val,a3);
    assertOnlyContains(val,a2);
    assertOnlyContains(val,a1);
  }

  @Test
  public void testZero() {
    zero(a1);
    zero(a2);
    zero(a3);

    assertOnlyContains(0,a1);
    assertOnlyContains(0,a2);
    assertOnlyContains(0,a3);
  }

  @Test
  public void testRand() {
    rand(a1); rand(a2); rand(a3);

    assertArraySize(a1,n1);
    assertArraySize(a2,n1,n2);
    assertArraySize(a3,n1,n2,n3);

    b1 = randdouble(n1);
    b2 = randdouble(n1,n2);
    b3 = randdouble(n1,n2,n3);

    assertArraySize(b1,n1);
    assertArraySize(b2,n1,n2);
    assertArraySize(b3,n1,n2,n3);

    assertFalse(equal(a1,b1));
    assertFalse(equal(a2,b2));
    assertFalse(equal(a3,b3));
  }

  @Test
  public void testCopySimple() {
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

    b1 = copy(n1 - 1,a1);
    b2 = copy(n1 - 1,n2 - 1,a2);
    b3 = copy(n1 - 1,n2 - 1,n3 - 1,a3);
    assertEqual(b1,rampdouble(0,1,n1 - 1));
    assertEqual(b2,rampdouble(0,1,10,n1 - 1,n2 - 1));
    assertEqual(b3,rampdouble(0,1,10,100,n1 - 1,n2 - 1,n3 - 1));

    copy(n1 - 1,a1,b1);
    copy(n1 - 1,n2 - 1,a2,b2);
    copy(n1 - 1,n2 - 1,n3 - 1,a3,b3);
    assertEqual(b1,rampdouble(0,1,n1 - 1));
    assertEqual(b2,rampdouble(0,1,10,n1 - 1,n2 - 1));
    assertEqual(b3,rampdouble(0,1,10,100,n1 - 1,n2 - 1,n3 - 1));

    b1 = copy(n1 - 1,1,a1);
    b2 = copy(n1 - 2,n2 - 1,2,1,a2);
    b3 = copy(n1 - 3,n2 - 2,n3 - 1,3,2,1,a3);
    assertEqual(b1,rampdouble(1,1,n1 - 1));
    assertEqual(b2,rampdouble(12,1,10,n1 - 1,n2 - 1));
    assertEqual(b3,rampdouble(123,1,10,100,n1 - 1,n2 - 1,n3 - 1));

    copy(n1 - 1,1,a1,0,b1);
    copy(n1 - 2,n2 - 1,2,1,a2,0,0,b2);
    copy(n1 - 3,n2 - 2,n3 - 1,3,2,1,a3,0,0,0,b3);
    assertEqual(b1,rampdouble(1,1,n1 - 1));
    assertEqual(b2,rampdouble(12,1,10,n1 - 1,n2 - 1));
    assertEqual(b3,rampdouble(123,1,10,100,n1 - 1,n2 - 1,n3 - 1));

    b1 = copy(n1 / 2,0,2,a1);
    b2 = copy(n1 / 2,n2 / 2,0,0,2,2,a2);
    b3 = copy(n1 / 2,n2 / 2,n3 / 2,0,0,0,2,2,2,a3);
    assertEqual(b1,rampdouble(0,2,n1 / 2));
    assertEqual(b2,rampdouble(0,2,20,n1 / 2,n2 / 2));
    assertEqual(b3,rampdouble(0,2,20,200,n1 / 2,n2 / 2,n3 / 2));

    b1 = copy(a1);
    b2 = copy(a2);
    b3 = copy(a3);
    copy(n1 - 1,1,a1,1,b1);
    copy(n1 - 2,n2 - 1,2,1,a2,2,1,b2);
    copy(n1 - 3,n2 - 2,n3 - 1,3,2,1,a3,3,2,1,b3);
    assertEqual(b1,rampdouble(0,1,n1));
    assertEqual(b2,rampdouble(0,1,10,n1,n2));
    assertEqual(b3,rampdouble(0,1,10,100,n1,n2,n3));
  }

  @Test
  public void testReverse() {
    b1 = reverse(reverse(a1));
    assertEqual(b1,a1);
  }

  @Test
  public void testReshape() {
    b2 = reshape(n1,n2,flatten(a2));
    b3 = reshape(n1,n2,n3,flatten(a3));
    assertEqual(a2,b2);
    assertEqual(a3,b3);
  }

  @Test
  public void testTranspose() {
    b2 = transpose(transpose(a2));
    assertEqual(a2,b2);
  }

  @Test
  public void testDistinct() {
    a3 = b3;
    a2 = b2;
    a1 = b1;
    assertFalse(distinct(a3,b3));
    assertFalse(distinct(a2,b2));
    assertFalse(distinct(a1,b1));

    b3 = new double[n3][n2][n1];
    b2 = new double[n2][n1];
    b1 = new double[n1];
    assertTrue(distinct(a3,b3));
    assertTrue(distinct(a2,b2));
    assertTrue(distinct(a1,b1));

    b3[1] = a3[1];
    b2[1] = a2[1];
    assertFalse(distinct(a3,b3));
    assertFalse(distinct(a2,b2));
  }

  @Test
  public void testAddition() {
    b1 = rampdouble(1,1,n1);                assertEqual(b1,add(1,a1));
                                            assertEqual(b1,add(a1,1));
    b2 = rampdouble(1,1,10,n1,n2);          assertEqual(b2,add(1,a2));
                                            assertEqual(b2,add(a2,1));
    b3 = rampdouble(1,1,10,100,n1,n2,n3);   assertEqual(b3,add(1,a3));
                                            assertEqual(b3,add(a3,1));

    b1 = rampdouble(0,2,n1);                assertEqual(b1,add(a1,a1));
    b2 = rampdouble(0,2,20,n1,n2);          assertEqual(b2,add(a2,a2));
    b3 = rampdouble(0,2,20,200,n1,n2,n3);   assertEqual(b3,add(a3,a3));

    add(a1,a1,a1);                          assertEquals(b1,a1);
    add(a2,a2,a2);                          assertEquals(b2,b2);
    add(a3,a3,a3);                          assertEquals(b3,a3);
  }

  @Test
  public void testSubtraction() {
    b1 = rampdouble(-1,1,n1);               assertEqual(b1,sub(a1,1));
    b2 = rampdouble(-1,1,10,n1,n2);         assertEqual(b2,sub(a2,1));
    b3 = rampdouble(-1,1,10,100,n1,n2,n3);  assertEqual(b3,sub(a3,1));

    b1 = rampdouble(1,-1,n1);               assertEqual(b1,sub(1,a1));
    b2 = rampdouble(1,-1,-10,n1,n2);        assertEqual(b2,sub(1,a2));
    b3 = rampdouble(1,-1,-10,-100,n1,n2,n3);assertEqual(b3,sub(1,a3));

    zero(b1);                               assertEqual(b1,sub(a1,a1));
    zero(b2);                               assertEqual(b2,sub(a2,a2));
    zero(b3);                               assertEqual(b3,sub(a3,a3));

    sub(a1,a1,a1);                          assertEqual(b1,a1);
    sub(a2,a2,a2);                          assertEqual(b2,a2);
    sub(a3,a3,a3);                          assertEqual(b3,a3);
  }

  @Test
  public void testMultiplication() {
    b1 = rampdouble(0,2,n1);                assertEqual(b1,mul(2,a1));
                                            assertEqual(b1,mul(a1,2));
    b2 = rampdouble(0,2,20,n1,n2);          assertEqual(b2,mul(2,a2));
                                            assertEqual(b2,mul(a2,2));
    b3 = rampdouble(0,2,20,200,n1,n2,n3);   assertEqual(b3,mul(2,a3));
                                            assertEqual(b3,mul(a3,2));

    b1 = new double[] {
      pow(0.0d,2.0d),
      pow(1.0d,2.0d),
      pow(2.0d,2.0d),
      pow(3.0d,2.0d)
    };

    b2 = new double[n2][n1];     a2 = new double[n2][n1];
    b3 = new double[n3][n2][n1]; a3 = new double[n3][n2][n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = copy(b1);
      a2[i2] = copy(a1);
    }

    for (int i3 = 0; i3 < n3; ++i3) {
      b3[i3] = copy(b2);
      a3[i3] = copy(a2);
    }

    assertEqual(b1,mul(a1,a1));
    assertEqual(b2,mul(a2,a2));
    assertEqual(b3,mul(a3,a3));

    mul(a1,a1,a1); assertEqual(b1,a1);
    mul(a2,a2,a2); assertEqual(b2,a2);
    mul(a3,a3,a3); assertEqual(b3,a3);
  }

  @Test
  public void testNaturalLog() {
    b1 = new double[] { Double.NEGATIVE_INFINITY,log(1),log(2),log(3) };
    b2 = new double[n2][n1];     a2 = new double[n2][n1];
    b3 = new double[n3][n2][n1]; a3 = new double[n3][n2][n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = copy(b1);
      a2[i2] = copy(a1);
    }

    for (int i3 = 0; i3 < n3; ++i3) {
      b3[i3] = copy(b2);
      a3[i3] = copy(a2);
    }

    assertAlmostEqual(b1,log(a1));
    assertAlmostEqual(a1,exp(b1));

    assertAlmostEqual(b2,log(a2));
    assertAlmostEqual(a2,exp(b2));

    assertAlmostEqual(b3,log(a3));
    assertAlmostEqual(a3,exp(b3));
  }

  @Test
  public void testSquareRoot() {
    b1 = new double[] { sqrt(0.0d), sqrt(1.0d), sqrt(2.0d), sqrt(3.0d) };
    b2 = new double[n2][n1];     a2 = new double[n2][n1];
    b3 = new double[n3][n2][n1]; a3 = new double[n3][n2][n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = copy(b1);
      a2[i2] = copy(a1);
    }

    for (int i3 = 0; i3 < n3; ++i3) {
      b3[i3] = copy(b2);
      a3[i3] = copy(a2);
    }

    assertAlmostEqual(b1,sqrt(a1));  assertAlmostEqual(a1,mul(b1,b1));
    assertAlmostEqual(b2,sqrt(a2));  assertAlmostEqual(a2,mul(b2,b2));
    assertAlmostEqual(b3,sqrt(a3));  assertAlmostEqual(a3,mul(b3,b3));
  }

  @Test
  public void testPow() {
    b1 = new double[] {
      pow(0.0d,2.0d),
      pow(1.0d,2.0d),
      pow(2.0d,2.0d),
      pow(3.0d,2.0d)
    };

    b2 = new double[n2][n1];     a2 = new double[n2][n1];
    b3 = new double[n3][n2][n1]; a3 = new double[n3][n2][n1];

    for (int i2=0; i2<n2; ++i2) {
      b2[i2] = copy(b1);
      a2[i2] = copy(a1);
    }

    for (int i3 = 0; i3 < n3; ++i3) {
      b3[i3] = copy(b2);
      a3[i3] = copy(a2);
    }

    assertAlmostEqual(b1,pow(a1,2.0d)); assertAlmostEqual(a1,sqrt(b1));
    assertAlmostEqual(b2,pow(a2,2.0d)); assertAlmostEqual(a2,sqrt(b2));
    assertAlmostEqual(b3,pow(a3,2.0d)); assertAlmostEqual(a3,sqrt(b3));
  }

  @Test
  public void testMinMax() {
    Random r = new Random();
    double min = -1d;
    double max = 1000d;
    int j3 = r.nextInt(n3-1)+1;
    int j2 = r.nextInt(n2-1)+1;
    int j1 = r.nextInt(n1-1)+1;
    int k1 = r.nextInt(n1-1)+1;
    while (j1==k1) k1 = r.nextInt(n1-1)+1;

    int[] imin1 = new int[1]; int[] imax1 = new int[1];
    int[] imin2 = new int[2]; int[] imax2 = new int[2];
    int[] imin3 = new int[3]; int[] imax3 = new int[3];

    a3[j3][j2][j1] = min; a3[j3][j2][k1] = max;
    a2[j2][j1]     = min; a2[j2][k1]     = max;
    a1[j1]         = min; a1[k1]         = max;

    double min3 = min(a3,imin3), max3 = max(a3,imax3);
    double min2 = min(a2,imin2), max2 = max(a2,imax2);
    double min1 = min(a1,imin1), max1 = max(a1,imax1);

    assertEq(min,min3);    assertEq(min,min2);    assertEq(min,min1);
    assertEq(j1,imin3[0]); assertEq(j1,imin2[0]); assertEq(j1,imin1[0]);
    assertEq(j2,imin3[1]); assertEq(j2,imin2[1]);
    assertEq(j3,imin3[2]);

    assertEq(max,max3);    assertEq(max,max2);    assertEq(max,max1);
    assertEq(k1,imax3[0]); assertEq(k1,imax2[0]); assertEq(k1,imax1[0]);
    assertEq(j2,imax3[1]); assertEq(j2,imax2[1]);
    assertEq(j3,imax3[2]);

    assertEq(max,max(a3)); assertEq(max,max(a2)); assertEq(max,max(a1));
    assertEq(min,min(a3)); assertEq(min,min(a2)); assertEq(min,min(a1));
  }

  @Test
  public void testRegularity() {
    assertTrue(isRegular(a2));
    assertTrue(isRegular(a3));

    a2[0] = new double[3];
    a3[0] = a2;

    assertFalse(isRegular(a2));
    assertFalse(isRegular(a3));
  }

  @Test
  public void testIncreasingDecreasingMonotonic() {
    assertTrue(isIncreasing(a1));
    assertFalse(isDecreasing(a1));
    assertTrue(isMonotonic(a1));

    a1 = reverse(a1);

    assertFalse(isIncreasing(a1));
    assertTrue(isDecreasing(a1));
    assertTrue(isMonotonic(a1));

    zero(a1);

    assertFalse(isIncreasing(a1));
    assertFalse(isDecreasing(a1));
    assertFalse(isMonotonic(a1));
  }

  protected static void assertOnlyContains(double val,double[][][] a) {
    for (int i3=0; i3<a.length; ++i3)
      assertOnlyContains(val,a[i3]);
  }

  protected static void assertOnlyContains(double val,double[][] a) {
    for (int i2=0; i2<a.length; ++i2)
      assertOnlyContains(val,a[i2]);
  }

  protected static void assertOnlyContains(double val,double[] a) {
    for (int i1=0; i1<a.length; ++i1)
      assertEquals(val,a[i1]);
  }

  protected static void assertArraySize(double[][][] a,int n1,int n2,int n3) {
    assertEquals(n3,a.length);
    assertEquals(n2,a[0].length);
    assertEquals(n1,a[0][0].length);
  }

  protected static void assertArraySize(double[][] a,int n1,int n2) {
    assertEquals(n2,a.length);
    assertEquals(n1,a[0].length);
  }

  protected static void assertArraySize(double[] a,int n) {
    assertEquals(n,a.length);
  }


  protected double[]     a1;
  protected double[][]   a2;
  protected double[][][] a3;

  protected double[]     b1;
  protected double[][]   b2;
  protected double[][][] b3;

}

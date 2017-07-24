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

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertFalse;


/**
 * Tests {@link edu.mines.jtk.util.AtomicDouble}.
 * TODO: Test thread safety by wrapping each method in a runnable.
 * @author Chris Engelsma
 * @version 2017.05.15
 */
public class AtomicDoubleTest {

  public static Random RANDOM = new Random();
  public static double epsilon = 1.0E-6;
  private AtomicDouble ad;

  @BeforeMethod
  public void setUp() {
    ad = new AtomicDouble();
  }

  @Test
  public void testDefaultConstructor() {
    assertEquals(0.0,ad.get(),epsilon);
  }

  @Test
  public void testConstructorSingleParameter() {
    double expected = RANDOM.nextDouble();
    AtomicDouble ad = new AtomicDouble(expected);
    assertEquals(expected,ad.get(),epsilon);
  }

  @Test
  public void testSet() {
    double expected = RANDOM.nextDouble();
    ad.set(expected);
    assertEquals(expected, ad.get(),epsilon);
  }

  @Test
  public void testAddAndGet() {
    double expected = 0.0;
    for (int i=0; i<10; ++i) {
      double nd = RANDOM.nextDouble();
      expected += nd;
      double actual = ad.addAndGet(nd);
      assertEquals(expected, ad.get(),epsilon);
    }
  }

  @Test
  public void testCompareAndSet() {
    ad.compareAndSet(1.0,2.0);
    assertEquals(0.0,ad.get());
    ad.compareAndSet(0.0,1.0);
    assertEquals(1.0,ad.get());
  }

  @Test
  public void testWeakCompareAndSet() {
    ad.weakCompareAndSet(0.0,1.0);
    assertEquals(1.0,ad.get());
  }

  @Test
  public void testIncrementAndGet() {
    double val;
    for (int i=0; i<10; ++i) {
      val = ad.get();             assertEquals((double)(i  ),val,1.0E-8);
      val = ad.incrementAndGet(); assertEquals((double)(i+1),val,1.0E-8);
      val = ad.get();             assertEquals((double)(i+1),val,1.0E-8);
    }
  }

  @Test
  public void testDecrementAndGet() {
    double val = 10.0;
    ad.set(val);
    for (int i=10; i>0; --i) {
      val = ad.get();             assertEquals((double)(i  ),val,1.0E-8);
      val = ad.decrementAndGet(); assertEquals((double)(i-1),val,1.0E-8);
      val = ad.get();             assertEquals((double)(i-1),val,1.0E-8);
    }
  }

  @Test
  public void testGetAndIncrement() {
    double val;
    for (int i=0; i<10; ++i) {
      val = ad.get();             assertEquals((double)(i  ),val,1.0E-8);
      val = ad.getAndIncrement(); assertEquals((double)(i  ),val,1.0E-8);
      val = ad.get();             assertEquals((double)(i+1),val,1.0E-8);
    }
  }

  @Test
  public void testGetAndDecrement() {
    double val = 10.0;
    ad.set(val);
    for (int i=10; i>0; --i) {
      val = ad.get();             assertEquals((double)(i  ),val,1.0E-8);
      val = ad.getAndDecrement(); assertEquals((double)(i  ),val,1.0E-8);
      val = ad.get();             assertEquals((double)(i-1),val,1.0E-8);
    }
  }

  @Test
  public void testTypeReturns() {
    ad.set(20.0);
    assertEquals(20 ,ad.intValue());
    assertEquals(20L,ad.longValue());
    assertEquals(20.,ad.doubleValue());
    assertEquals(20f,ad.floatValue());
    assertEquals("20.0",ad.toString());
  }

  @Test
  public void testGetAndSet() {
    double expected0 = RANDOM.nextDouble();
    double expected1 = RANDOM.nextDouble();

    AtomicDouble ad = new AtomicDouble(expected0);
    double actual0 = ad.getAndSet(expected1);
    double actual1 = ad.get();

    assertEquals(expected0,actual0,epsilon);
    assertEquals(expected1,actual1,epsilon);
  }
}

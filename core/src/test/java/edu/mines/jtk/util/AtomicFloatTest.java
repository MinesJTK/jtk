/****************************************************************************
 Copyright 2017, Colorafo School of Mines and others.
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


/**
 * Tests {@link edu.mines.jtk.util.AtomicFloat}.
 * TODO: Test threaf safety by wrapping each method in a runnable.
 * @author Chris Engelsma
 * @version 2017.05.15
 */
public class AtomicFloatTest {

  public static Random RANDOM = new Random();
  public static double epsilon = 1.0E-6;
  private AtomicFloat af;

  @BeforeMethod
  public void setUp() {
    af = new AtomicFloat();
  }

  @Test
  public void testDefaultConstructor() {
    assertEquals(0.0,af.get(),epsilon);
  }

  @Test
  public void testConstructorSingleParameter() {
    float expected = RANDOM.nextFloat();
    AtomicFloat af = new AtomicFloat(expected);
    assertEquals(expected,af.get(),epsilon);
  }

  @Test
  public void testSet() {
    float expected = RANDOM.nextFloat();
    af.set(expected);
    assertEquals(expected, af.get(),epsilon);
  }

  @Test
  public void testAddAndGet() {
    float expected = 0.0f;
    float actual;
    for (int i=0; i<10; ++i) {
      float nf = RANDOM.nextFloat();
      expected += nf;
      actual = af.addAndGet(nf);
      assertEquals(expected, actual,epsilon);
    }
  }

  @Test
  public void testCompareAndSet() {
    af.compareAndSet(1.0f,2.0f);
    assertEquals(0.0f,af.get());
    af.compareAndSet(0.0f,1.0f);
    assertEquals(1.0f,af.get());
  }

  @Test
  public void testWeakCompareAndSet() {
    af.weakCompareAndSet(0.0f,1.0f);
    assertEquals(1.0f,af.get());
  }

  @Test
  public void testIncrementAndGet() {
    float val;
    for (int i=0; i<10; ++i) {
      val = af.get();             assertEquals((float)(i  ),val,1.0E-8);
      val = af.incrementAndGet(); assertEquals((float)(i+1),val,1.0E-8);
      val = af.get();             assertEquals((float)(i+1),val,1.0E-8);
    }
  }

  @Test
  public void testDecrementAndGet() {
    float val = 10.0f;
    af.set(val);
    for (int i=10; i>0; --i) {
      val = af.get();             assertEquals((float)(i  ),val,1.0E-8);
      val = af.decrementAndGet(); assertEquals((float)(i-1),val,1.0E-8);
      val = af.get();             assertEquals((float)(i-1),val,1.0E-8);
    }
  }

  @Test
  public void testGetAndIncrement() {
    float val;
    for (int i=0; i<10; ++i) {
      val = af.get();             assertEquals((float)(i  ),val,1.0E-8);
      val = af.getAndIncrement(); assertEquals((float)(i  ),val,1.0E-8);
      val = af.get();             assertEquals((float)(i+1),val,1.0E-8);
    }
  }

  @Test
  public void testGetAndDecrement() {
    float val = 10.0f;
    af.set(val);
    for (int i=10; i>0; --i) {
      val = af.get();             assertEquals((float)(i  ),val,1.0E-8);
      val = af.getAndDecrement(); assertEquals((float)(i  ),val,1.0E-8);
      val = af.get();             assertEquals((float)(i-1),val,1.0E-8);
    }
  }

  @Test
  public void testTypeReturns() {
    af.set(20.0f);
    assertEquals(20 ,af.intValue());
    assertEquals(20L,af.longValue());
    assertEquals(20.,af.doubleValue());
    assertEquals(20f,af.floatValue());
    assertEquals("20.0",af.toString());
  }

  @Test
  public void testGetAndSet() {
    float expected0 = RANDOM.nextFloat();
    float expected1 = RANDOM.nextFloat();

    AtomicFloat af = new AtomicFloat(expected0);
    float actual0 = af.getAndSet(expected1);
    float actual1 = af.get();

    assertEquals(expected0,actual0,epsilon);
    assertEquals(expected1,actual1,epsilon);
  }
}

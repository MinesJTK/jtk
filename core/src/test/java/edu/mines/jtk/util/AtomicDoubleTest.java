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

import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests {@link edu.mines.jtk.util.AtomicDouble}.
 * @author Chris Engelsma
 * @version 2017.05.15
 */
public class AtomicDoubleTest {

  public static Random RANDOM = new Random();
  public static double epsilon = 1.0E-6;

  @Test
  public void testDefaultConstructor() {
    AtomicDouble ad = new AtomicDouble();
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
    AtomicDouble ad = new AtomicDouble();

    double expected = RANDOM.nextDouble();
    ad.set(expected);
    assertEquals(expected, ad.get(),epsilon);
  }

  @Test
  public void testAddAndGet() {
    AtomicDouble ad = new AtomicDouble();
    double expected = 0.0;
    for (int i=0; i<10; ++i) {
      double nd = RANDOM.nextDouble();
      expected += nd;
      double actual = ad.addAndGet(nd);
      assertEquals(expected, ad.get(),epsilon);
    }
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

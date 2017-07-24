/****************************************************************************
Copyright 2008, Colorado School of Mines and others.
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

import java.util.NoSuchElementException;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.util.ArrayQueue}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2017.05.15
 */
public class ArrayQueueTest {

  private ArrayQueue<Integer> aq;

  @BeforeMethod
  public void setUp() {
    aq = new ArrayQueue<>();
  }

  @Test
  public void testConstructorWithInitialCapacity() {
    aq = new ArrayQueue<>(10);
    assertTrue(aq.isEmpty());
    assertEquals(aq.size(),0);
  }

  @Test(expectedExceptions = NoSuchElementException.class)
  public void testEmptyQueueFirstElementThrowsException() {
    aq.first();
  }

  @Test(expectedExceptions = NoSuchElementException.class)
  public void testEmptyQueueRemoveElementThrowsException() {
    aq.remove();
  }

  @Test
  public void testSizingBehavior() {
    aq = new ArrayQueue<>(10);

    assertTrue(aq.isEmpty());
    assertEquals(aq.size(),0);

    aq.ensureCapacity(10);

    for (int i=0; i<10; ++i) {
      aq.add(i);
      assertEquals(aq.size(),i+1);
    }

    for (int i=10; i>0; --i) {
      aq.remove();
      assertEquals(aq.size(), i-1);
    }

    aq.add(999);
    assertEquals(aq.size(),1);

    aq.add(999);
    aq.trimToSize();
    assertEquals(aq.size(),2);

    assertEquals(aq.first().intValue(),999);
  }

  @Test
  public void testRandom() {
    Random r = new Random();
    int niter = 1000;
    int nadd = 0;
    int nrem = 0;
    for (int iter=0; iter<niter; ++iter) {
      if (r.nextFloat()>0.5f) {
        int n = r.nextInt(100);
        for (int i=0; i<n; ++i) {
          aq.add(nadd);
          ++nadd;
        }
      } else if (aq.size()>0) {
        int n = aq.size();
        if (r.nextFloat()>0.05f) 
          n = r.nextInt(n);
        for (int i=0; i<n; ++i) {
          assertEquals(nrem,aq.remove().intValue());
          ++nrem;
        }
      }
    }
  }
}

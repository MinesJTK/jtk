/****************************************************************************
Copyright 2003, Landmark Graphics and others.
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
package edu.mines.jtk.opt;

import java.util.Random;

import edu.mines.jtk.util.Almost;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.opt.VectArray}.
 */
public class VectArrayTest {

  @Test
  public void testAll () {
    Random random = new Random(32525);
    VectArray vm = new VectArray(5);
    for (int index=0; index<5; ++index) {
      double[] a = new double[7*index];
      for (int i=0; i<a.length; ++i) {a[i] = random.nextDouble();}
      Vect v = new ArrayVect1(a, 2.);
      vm.put(index, v);
      assertTrue(vm.containsKey(index));
    }
    assertFalse(vm.containsKey(99));
    VectUtil.test(vm);
    int[] keys = vm.getKeys();
    assertEquals(5,keys.length);
    for (int i=0; i<5; ++i) {
      assertEquals(i,keys[i]);
    }
    for (int index=0; index<5; ++index) {
      ArrayVect1 value = (ArrayVect1) vm.get(index);
      assertTrue(value != null);
      assertTrue(value.getData() != null);
      assertTrue(value.getSize() == 7*index);
      assertTrue(value.getData().length == 7*index);
    }
    // test inverse covariance
    vm = new VectArray(5);
    for (int index=0; index<5; ++index) {
      double[] a = new double[7*index+1];
      for (int i=0; i<a.length; ++i) {a[i] = 1;}
      Vect v = new ArrayVect1(a, 1.);
      vm.put(index, v);
    }
    Vect wm = vm.clone();
    wm.multiplyInverseCovariance();
    assertTrue(Almost.FLOAT.equal(1., wm.dot(vm)));
  }
}

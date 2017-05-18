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

import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.opt.ArrayVect2f}.
 */
public class ArrayVect2fTest {

  @Test
  public void testAll () {
    {
      float[][] a = new float[31][21];
      for (int i=0; i<a.length; ++i) {
        for (int j=0; j<a[i].length; ++j) {
          a[i][j] = i+2.5f*j;
        }
      }
      Vect v = new ArrayVect2f(a, 2.);
      VectUtil.test(v);

      // test inverse covariance
      for (int i=0; i<a.length; ++i) {
        for (int j=0; j<a[i].length; ++j) {
          a[i][j] = 1;
        }
      }
      v = new ArrayVect2f(a, 3.);
      Vect w = v.clone();
      w.multiplyInverseCovariance();
      assertTrue(Almost.FLOAT.equal(1./3., v.dot(w)));
      assertTrue(Almost.FLOAT.equal(1./3., v.magnitude()));
    }

    {
      Random random = new Random(352);
      float[][] a = new float[201][];
      boolean oneWasShort = false;
      boolean oneWasLong = false;
      for (int i=0; i<a.length; ++i) {
        a[i] = new float[random.nextInt(11)];
        if (a[i].length ==0) oneWasShort = true;
        for (int j=0; j<a[i].length; ++j) {
          oneWasLong = true;
          a[i][j] = 5*random.nextFloat()-2;
        }
      }
      assertTrue(oneWasShort);
      assertTrue(oneWasLong);
      Vect v = new ArrayVect2f(a, 2.5);
      VectUtil.test(v);
    }
  }
}

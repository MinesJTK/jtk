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

import edu.mines.jtk.util.Almost;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

/** Unit tests for edu.mines.jtk.opt.ArrayVect3f.
*/
public class ArrayVect3fTest {

  @Test
  public void testAll () {
    float[][][] a = new float[13][17][11];
    for (int i=0; i<a.length; ++i) {
      for (int j=0; j<a[i].length; ++j) {
        for (int k=0; k<a[i][j].length; ++k) {
          a[i][j][k] = i+2.5f*j - 1.7f*k;
        }
      }
    }
    Vect v = new ArrayVect3f(a, 2.2);
    VectUtil.test(v);

    // test inverse covariance
    for (int i=0; i<a.length; ++i) {
      for (int j=0; j<a[i].length; ++j) {
        for (int k=0; k<a[i][j].length; ++k) {
          a[i][j][k] = 1;
        }
      }
    }
    v = new ArrayVect3f(a, 7.);
    Vect w = v.clone();
    w.multiplyInverseCovariance();
    assertTrue(Almost.FLOAT.equal(1./7., v.dot(w)));
    assertTrue(Almost.FLOAT.equal(1./7., v.magnitude()));
  }
}

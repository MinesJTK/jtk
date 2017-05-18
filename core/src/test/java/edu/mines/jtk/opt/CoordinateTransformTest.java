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

import java.util.Arrays;

import edu.mines.jtk.util.Almost;
import org.testng.annotations.Test;

import static edu.mines.jtk.util.ArrayMath.copy;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.opt.CoordinateTransform}.
 */
public class CoordinateTransformTest {

  @Test
  public void testAll() throws Exception {
    double[][] in = new double[][] {
      {1.,1.},
      {2.,2.},
      {3.,4.},
    };
    double[][] out = new double[][] {
      {2.},
      {4.},
      {7.},
    };
    double[][] inCopy = copy(in);
    double[][] outCopy = copy(out);

    CoordinateTransform ls = new CoordinateTransform(1, 2);
    for (int j=0; j<out.length; ++j) {
      ls.add(out[j], in[j]);
    }
    Almost almost = new Almost();
    double a, b;

    a = 1.; b = 1.;
    assertTrue(almost.equal(a+b, ls.get(new double[]{a,b})[0]));
    a = 2.; b = 2.;
    assertTrue(almost.equal(a+b, ls.get(new double[]{a,b})[0]));
    a = 3.; b = 4.;
    assertTrue(almost.equal(a+b, ls.get(new double[]{a,b})[0]));
    a = 1.; b = 3.;
    assertTrue(almost.equal(a+b, ls.get(new double[]{a,b})[0]));
    a = 3.; b = 7;
    assertTrue(almost.equal(a+b, ls.get(new double[]{a,b})[0]));
    assertTrue(Arrays.deepEquals(in,inCopy));
    assertTrue(Arrays.deepEquals(out,outCopy));
  }
}

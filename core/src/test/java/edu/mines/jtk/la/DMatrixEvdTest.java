/****************************************************************************
Copyright 2005, Colorado School of Mines and others.
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
package edu.mines.jtk.la;

import org.testng.annotations.Test;

import static edu.mines.jtk.la.DMatrixTest.assertEqualFuzzy;

/**
 * Tests {@link edu.mines.jtk.la.DMatrixEvd}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2005.12.08
 */
public class DMatrixEvdTest {

  @Test
  public void testSymmetric() {
    DMatrix a = new DMatrix(new double[][]{
      {4,1,1},
      {1,2,3},
      {1,3,6}
    });
    DMatrixEvd evd = new DMatrixEvd(a);
    DMatrix d = evd.getD();
    DMatrix v = evd.getV();
    assertEqualFuzzy(a.times(v),v.times(d));
  }

  @Test
  public void testAsymmetric() {
    DMatrix a = new DMatrix(new double[][]{
      {      0,       1,       0,     0},
      {      1,       0,  2.0e-7,     0},
      {      0, -2.0e-7,       0,     1},
      {      0,       0,       1,     0}
    });
    DMatrixEvd evd = new DMatrixEvd(a);
    DMatrix d = evd.getD();
    DMatrix v = evd.getV();
    assertEqualFuzzy(a.times(v),v.times(d));
  }
}

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
package edu.mines.jtk.lapack;

import org.testng.annotations.Test;
import static org.testng.Assert.assertFalse;

import static edu.mines.jtk.lapack.DMatrixTest.assertEqualFuzzy;

/**
 * Tests {@link edu.mines.jtk.lapack.DMatrixLud}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2005.12.12
 */
public class DMatrixLudTest {

  @Test
  public void testSimple() {
    DMatrix a = new DMatrix(new double[][]{
      {0.0,  2.0},
      {3.0,  4.0},
    });
    test(a);
    DMatrixLud lud = new DMatrixLud(a);
    assertEqualFuzzy(-6.0,lud.det());
  }

  @Test
  public void testRandom() {
    test(DMatrix.random(100,100));
    test(DMatrix.random(101,100));
    test(DMatrix.random(100,101));
  }

  ///////////////////////////////////////////////////////////////////////////
  // private

  private void test(DMatrix a) {
    int m = a.getM();
    int n = a.getN();

    DMatrixLud lud = new DMatrixLud(a);
    assertFalse(lud.isSingular());
    int[] pi = lud.getPivotIndices();
    DMatrix p = lud.getP();
    DMatrix l = lud.getL();
    DMatrix u = lud.getU();
    DMatrix lu = l.times(u);
    DMatrix plu = p.times(lu);
    DMatrix ap = a.get(pi,null);
    assertEqualFuzzy(ap,lu);
    assertEqualFuzzy(a,plu);

    if (m==n) {
      int nrhs = 10;
      DMatrix b = DMatrix.random(m,nrhs);
      DMatrix x = lud.solve(b);
      DMatrix ax = a.times(x);
      assertEqualFuzzy(ax,b);
    }
  }
}

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
import static org.testng.Assert.assertTrue;

import static edu.mines.jtk.lapack.DMatrixTest.assertEqualExact;
import static edu.mines.jtk.lapack.DMatrixTest.assertEqualFuzzy;

/**
 * Tests {@link edu.mines.jtk.lapack.DMatrixChd}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2005.12.12
 */
public class DMatrixChdTest {

  @Test
  public void testSimple() {
    DMatrix a = new DMatrix(new double[][]{
      {1.0,  1.0},
      {1.0,  4.0},
    });
    test(a);
    DMatrixChd chd = new DMatrixChd(a);
    assertEqualFuzzy(3.0,chd.det());
  }

  @Test
  public void testSimple2() {
    DMatrix a = new DMatrix(new double[][]{
      {4.0, 1.0, 1.0},
      {1.0, 2.0, 3.0},
      {1.0, 3.0, 6.0},
    });
    test(a);
  }

  @Test
  public void testNotPositiveDefinite() {
    DMatrix a = new DMatrix(new double[][]{
      {0.0, 1.0, 1.0},
      {0.0, 2.0, 3.0},
      {0.0, 3.0, 6.0},
    });
    DMatrixChd chd = new DMatrixChd(a);
    assertFalse(chd.isPositiveDefinite());
    assertEqualExact(0.0,chd.det());
  }

  @Test
  public void testRandom() {
    int n = 10;
    DMatrix a = DMatrix.random(n,n);
    a.plusEquals(a.transpose());
    DMatrix d = DMatrix.identity(n,n);
    d.timesEquals(n*a.norm1());
    a.plusEquals(d);
    test(a);
  }

  ///////////////////////////////////////////////////////////////////////////
  // private

  private void test(DMatrix a) {
    int m = a.getM();

    DMatrixChd chd = new DMatrixChd(a);
    assertTrue(chd.isPositiveDefinite());
    DMatrix l = chd.getL();
    DMatrix lt = l.transpose();
    DMatrix llt = l.times(lt);
    assertEqualFuzzy(a,llt);

    int nrhs = 10;
    DMatrix b = DMatrix.random(m,nrhs);
    DMatrix x = chd.solve(b);
    DMatrix ax = a.times(x);
    assertEqualFuzzy(ax,b);
  }
}

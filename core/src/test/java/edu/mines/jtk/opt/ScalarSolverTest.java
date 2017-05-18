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

import edu.mines.jtk.opt.ScalarSolver.Function;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.opt.ScalarSolver}.
 */
public class ScalarSolverTest {

  @Test
  public void testLinearObjFunc() throws Exception {
    // test linear objective function
    final double answer = 1./3.;
    final int[] calls = new int[]{0};
    ScalarSolver solver = new ScalarSolver(new Function() {
        public double function(double scalar) {
          ++calls[0];
          return Math.abs(scalar - answer);
        }
      });
    // s_LOG.fine = true;
    double xmin = solver.solve(0., 1., 0.001, 0.001, 20, null);
    assertTrue(xmin > answer - 0.001 );
    assertTrue(xmin > answer*(1. - 0.001) );
    assertTrue(xmin < answer + 0.001 );
    assertTrue(xmin < answer*(1. + 0.001) );
    // LOG.fine("1. result="+answer+"="+xmin+" calls="+calls[0]);
    assertEquals(14,calls[0]);
  }

  @Test
  public void testNonUnitScalarRange() throws Exception {
    // test non-unit scalar range
    final double answer = 1./3.;
    final int[] calls = new int[]{0};
    ScalarSolver solver = new ScalarSolver(new Function() {
        public double function(double scalar) {
          ++calls[0];
          return Math.abs(scalar - answer);
        }
      });
    double xmin = solver.solve(-1., 2., 0.001, 0.001, 20, null);
    assertTrue(xmin > answer - 0.001 );
    assertTrue(xmin > answer*(1. - 0.001) );
    assertTrue(xmin < answer + 0.001 );
    assertTrue(xmin < answer*(1. + 0.001) );
    // LOG.fine("2. result="+answer+"="+xmin+" calls="+calls[0]);
    assertEquals(15,calls[0]);
  }

  @Test
  public void testRightHandSide() throws Exception {
    // test right hand side
    final double answer = 0.03;
    final int[] calls = new int[]{0};
    ScalarSolver solver = new ScalarSolver(new Function() {
        public double function(double scalar) {
          ++calls[0];
          return Math.abs(scalar - answer);
        }
      });
    double xmin = solver.solve(0., 1., 0.001, 0.001, 20, null);
    assertTrue(xmin > answer - 0.001 );
    assertTrue(xmin > answer*(1. - 0.001) );
    assertTrue(xmin < answer + 0.001 );
    assertTrue(xmin < answer*(1. + 0.001) );
    // LOG.fine("3. result="+answer+"="+xmin+" calls="+calls[0]);
    assertEquals(16,calls[0]);
  }

  @Test
  public void testLeftHandSide2() throws Exception {
    final double answer = 0.98;
    final int[] calls = new int[]{0};
    ScalarSolver solver = new ScalarSolver(new Function() {
        public double function(double scalar) {
          ++calls[0];
          return Math.abs(scalar - answer);
        }
      });
    double xmin = solver.solve(0., 1., 0.001, 0.001, 20, null);
    assertTrue(xmin > answer - 0.001 );
    assertTrue(xmin > answer*(1. - 0.001) );
    assertTrue(xmin < answer + 0.001 );
    assertTrue(xmin < answer*(1. + 0.001) );
    // LOG.fine("4. result="+answer+"="+xmin+" calls="+calls[0]);
    assertEquals(12,calls[0]);
  }

  @Test
  public void testParabola() throws Exception {
    final double answer = 1./3.;
    final int[] calls = new int[]{0};
    ScalarSolver solver = new ScalarSolver(new Function() {
        public double function(double scalar) {
          ++calls[0];
          return (scalar - answer)*(scalar - answer);
        }
      });
    double xmin = solver.solve(0., 1., 0.001, 0.001, 7, null); // fewest iterations
    assertTrue(xmin > answer - 0.001 );
    assertTrue(xmin > answer*(1. - 0.001) );
    assertTrue(xmin < answer + 0.001 );
    assertTrue(xmin < answer*(1. + 0.001) );
    // LOG.fine("5. result="+answer+"="+xmin+" calls="+calls[0]);
    assertEquals(6,calls[0]);
  }

  @Test
  public void testPositiveCurvature() throws Exception {
    final double answer = 1./3.;
    final int[] calls = new int[]{0};
    ScalarSolver solver = new ScalarSolver(new Function() {
        public double function(double scalar) {
          ++calls[0];
          return Math.sqrt(Math.abs(scalar - answer));
        }
      });
    double xmin = solver.solve(0., 1., 0.001, 0.001, 20, null);
    assertTrue(xmin > answer - 0.001 );
    assertTrue(xmin > answer*(1. - 0.001) );
    assertTrue(xmin < answer + 0.001 );
    assertTrue(xmin < answer*(1. + 0.001) );
    // LOG.fine("6. result="+answer+"="+xmin+" calls="+calls[0]);
    assertEquals(16,calls[0]);
  }

  @Test
  public void testStepFunction() throws Exception {
    final double answer = 1./3.;
    final int[] calls = new int[]{0};
    ScalarSolver solver = new ScalarSolver(new Function() {
        public double function(double scalar) {
          ++calls[0];
          if (scalar < answer) return 3.;
          return scalar - answer;
        }
      });
    double xmin = solver.solve(0., 1., 0.001, 0.001, 50, null);
    assertTrue(xmin > answer - 0.001 );
    assertTrue(xmin > answer*(1. - 0.001) );
    assertTrue(xmin < answer + 0.001 );
    assertTrue(xmin < answer*(1. + 0.001) );
    // LOG.fine("6. result="+answer+"="+xmin+" calls="+calls[0]);
    assertEquals(29,calls[0]);
  }
}

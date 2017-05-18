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

import java.util.*;

import edu.mines.jtk.util.Almost;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.opt.QuadraticSolver}.
 */
public class QuadraticSolverTest {
  private static final String NL = System.getProperty("line.separator");

  @Test
  public void testQS() {
    /*
      Minimize  0.5 x'Hx + b'x = 0, where H = |2 4 | and b = (2 1)'
                                              |4 11|
      solution is x = (-3 1)'
    */
    Quadratic q = new Quadratic() {
        public void multiplyHessian(Vect x) {
          double[] data = ((ArrayVect1)x).getData();
          double[] newData = new double[data.length];
          newData[0] = 2.*data[0] + 4.*data[1];
          newData[1] = 4.*data[0] + 11.*data[1];
          data[0] = newData[0];
          data[1] = newData[1];
        }
        public void inverseHessian(Vect x) {}
        public Vect getB() {
          return new TestVect(new double[] {2.,1.}, 1.);
        }
      };
    QuadraticSolver qs = new QuadraticSolver(q);

    { // not enough iterations
      ArrayVect1 result = (ArrayVect1) qs.solve(1, null);
      assertFalse(Almost.FLOAT.equal(-3., result.getData()[0]));
      assertFalse(Almost.FLOAT.equal(1., result.getData()[1]));
      result.dispose();
    }
    { // just barely enough iterations
      ArrayVect1 result = (ArrayVect1) qs.solve(2, null);
      assertTrue(Almost.FLOAT.equal(-3., result.getData()[0]));
      assertTrue(Almost.FLOAT.equal(1., result.getData()[1]));
      result.dispose();
    }
    { // Does not blow up with too many iterations
      ArrayVect1 result = (ArrayVect1) qs.solve(20, null);
      assertTrue(Almost.FLOAT.equal(-3., result.getData()[0]));
      assertTrue(Almost.FLOAT.equal(1., result.getData()[1]));
      result.dispose();
    }
    assertEquals(0,TestVect.undisposed.size());
    assertTrue(TestVect.max <= 5);
  }

  private static class TestVect extends ArrayVect1 {
    private static final long serialVersionUID = 1L;
    /** Visible only for tests */
    public static int max = 0;
    /** Visible only for tests. */
    public static Map<Object,String> undisposed =
      Collections.synchronizedMap(new HashMap<Object,String>());

    /** Constructor
       @param data
       @param variance
     */
    public TestVect(double[] data, double variance) {
      super (data,variance);
      remember(this);
    }
    @Override
        public TestVect clone() {
      TestVect result = (TestVect) super.clone();
      remember(result);
      return result;
    }
    private void remember(Object tv) { // remember where allocated
      synchronized (undisposed) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        new Exception("This vector was never disposed").printStackTrace(pw);
        pw.flush();
        undisposed.put(tv, sw.toString());
        max = Math.max(max, undisposed.size());
      }
    }
    @Override
        public void dispose() {
      synchronized (undisposed) {
        super.dispose();
        undisposed.remove(this);
      }
    }
    /** @return traces for debugging
     */
    public static String getTraces() {
      StringBuilder sb = new StringBuilder();
      for (String s : undisposed.values()) {
        sb.append(s);
        sb.append(NL);
      }
      return sb.toString();
    }
  }
}

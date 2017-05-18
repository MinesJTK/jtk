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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import edu.mines.jtk.util.Almost;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

/**
 *  Tests {@link edu.mines.jtk.opt.GaussNewtonSolver}.
 */
public class GaussNewtonSolverTest {

  private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
  private static boolean printedUndisposed = false;
  private static boolean projectWasTested = false;
  private static final String NL = System.getProperty("line.separator");

  // make sure Vects are disposed
  private static class TestVect extends ArrayVect1 {
    private static final long serialVersionUID = 1L;
    /** Visible only for tests. */
    public static int max = 0;
    /**  Visible only for tests. */
    public static int total = 0;
    /**  Visible only for tests. */
    public static Map<Object,String> undisposed =
      Collections.synchronizedMap(new HashMap<Object,String>());

    /** Visible only for tests. */
    public String identity = "default";

    @Override
        public void add(double scaleThis, double scaleOther, VectConst other) {
      assertSameType(other);
      super.add(scaleThis, scaleOther, other);
    }

    @Override public void project(double scaleThis, double scaleOther, VectConst other) {
      TestVect tv = (TestVect) other;
      if (!identity.equals(tv.identity)) {projectWasTested = true;}
      super.add(scaleThis, scaleOther, other);
    }

    @Override public double dot(VectConst other) {
      assertSameType(other);
      return super.dot(other);
    }

    private void assertSameType(VectConst other) {
      TestVect tv = (TestVect) other;
      if (!identity.equals(tv.identity)) {
        throw new IllegalArgumentException("different types");
      }
    }

    /** Constructor.
       @param data
       @param variance
       @param identity
     */
    public TestVect(double[] data, double variance, String identity) {
      super (data,variance);
      this.identity = identity;
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
        //LOG.info("**********************************************");
        //LOG.info(sw.toString());
        max = Math.max(max, undisposed.size());
        total += 1;
        if (undisposed.size() > 12 && !printedUndisposed) {
          LOG.severe("**********************************************");
          LOG.severe(getTraces());
          LOG.severe("**********************************************");
          printedUndisposed = true;
        }
      }
    }
    @Override
        public void dispose() {
      synchronized (undisposed) {
        super.dispose();
        undisposed.remove(this);
      }
    }
    /** View traces for debugging
       @return printable version of traces
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

  @Test
  public void testMain() throws Exception {
    GaussNewtonSolver.setExpensiveDebug(true);
    /* fit straight line to points (0,0) (1,8) (3,8) (4,20) */
    final double[] coord = new double[]         {0., 1., 3., 4.};
    TestVect data =
      new TestVect(new double[] {0., 8., 8., 20.}, 0.0001, "data");

    // model will be intercept and gradient
    LinearTransform linearTransform = new LinearTransform() {
        public void forward(Vect data1, VectConst model) {
          VectUtil.zero(data1);
          double[] d = ((ArrayVect1)data1).getData();
          double[] m = ((ArrayVect1)model).getData();
          for (int i=0; i< coord.length; ++i) {
            d[i] += m[0];
            d[i] += coord[i]*m[1];
          }
        }
        public void addTranspose(VectConst data1, Vect model) {
          double[] d = ((ArrayVect1)data1).getData();
          double[] m = ((ArrayVect1)model).getData();
          for (int i=0; i< coord.length; ++i) {
            m[0] += d[i];
            m[1] += coord[i]*d[i];
          }
        }
        public void inverseHessian(Vect model) {}
        public void adjustRobustErrors(Vect dataError) {}
      };

    { // bad starting model, damp full model
      TestVect model = new TestVect(new double[]{-1., -1.}, 1., "model");
      boolean dampOnlyPerturbation = false;
      int conjugateGradIterations = 2;
      ArrayVect1 result = (ArrayVect1) QuadraticSolver.solve
        (data, model, linearTransform,
         dampOnlyPerturbation, conjugateGradIterations, null);
      LOG.fine("data = "+data);
      LOG.fine("model = "+model);
      LOG.fine("result = "+result);

      assertTrue((new Almost(4)).equal(1., result.getData()[0]));
      assertTrue((new Almost(5)).equal(4., result.getData()[1]));

      model.dispose();
      result.dispose();
    }
    double[] dampPerturb = null;
    { // good starting model, damp perturbations only
      TestVect model = new TestVect(new double[]{0.9, 3.9}, 1., "model");
      boolean dampOnlyPerturbation = true;
      int conjugateGradIterations = 2;
      ArrayVect1 result = (ArrayVect1) QuadraticSolver.solve
        (data, model, linearTransform,
         dampOnlyPerturbation, conjugateGradIterations, null);
      LOG.fine("data = "+data);
      LOG.fine("model = "+model);
      LOG.fine("result = "+result);

      dampPerturb = result.getData();
      assertTrue((new Almost(4)).equal(1., result.getData()[0]));
      assertTrue((new Almost(5)).equal(4., result.getData()[1]));
      model.dispose();
      result.dispose();
    }
    { // good starting model, damp whole model, and compare to previous
      TestVect model = new TestVect(new double[]{0.9, 3.9}, 1., "model");
      boolean dampOnlyPerturbation = false;
      int conjugateGradIterations = 2;
      ArrayVect1 result = (ArrayVect1) QuadraticSolver.solve
        (data, model, linearTransform,
         dampOnlyPerturbation, conjugateGradIterations, null);
      LOG.fine("data = "+data);
      LOG.fine("model = "+model);
      LOG.fine("result = "+result);

      double[] dampAll = result.getData();
      assertTrue((new Almost(4)).equal(1., result.getData()[0]));
      assertTrue((new Almost(5)).equal(4., result.getData()[1]));
      assertTrue(dampAll[0] > dampPerturb[0]);
      assertTrue(dampAll[1] < dampPerturb[1]);
      { //
        double dampAll2 = 0.;
        double dampPerturb2 = 0.;
        for (int i=0; i<2; ++i) {
          dampAll2 += dampAll[i]*dampAll[i];
          dampPerturb2 += dampPerturb[i]*dampPerturb[i];
        }
        LOG.fine ("dampAll2="+dampAll2+" dampPerturb2="+dampPerturb2);
        assertTrue(dampAll2 < dampPerturb2);
      }
      model.dispose();
      result.dispose();
    }
    assertTrue(TestVect.max <=10);
    // use full interface
    for (int twice=0; twice<2; ++twice) {
      boolean project = (twice==1);
      TestVect perturb = new TestVect(new double[2], 1., "perturb");
      { // Steepest descent: One conjugate gradient iteration and a line search
        TestVect model = new TestVect(new double[]{0.9, 3.9}, 1., "model");
        boolean dampOnlyPerturbation = false;
        int linearizationIterations = 3;
        int lineSearchIterations = 20;
        double lineSearchError = 0.000001;
        int conjugateGradIterations = 1;
        Transform transform =
          new LinearTransformWrapper(linearTransform);
        ArrayVect1 result = (ArrayVect1) GaussNewtonSolver.solve
          (data, model, (project) ? perturb : null, transform,
           dampOnlyPerturbation,
           conjugateGradIterations, lineSearchIterations,
           linearizationIterations, lineSearchError, null);
        LOG.fine("data = "+data);
        LOG.fine("model = "+model);
        LOG.fine("result = "+result);
        assertTrue((new Almost(3)).equal(1., result.getData()[0]));
        assertTrue((new Almost(4)).equal(4., result.getData()[1]));
        model.dispose();
        result.dispose();
      }
      { // Make sure unnecessary iterations are not a problem
        TestVect model = new TestVect(new double[]{0.9, 3.9}, 1., "model");
        boolean dampOnlyPerturbation = true;
        int linearizationIterations = 3;
        int lineSearchIterations = 30;
        double lineSearchError = 0.000001;
        int conjugateGradIterations = 2;
        Transform transform
          = new LinearTransformWrapper(linearTransform);
        ArrayVect1 result = (ArrayVect1) GaussNewtonSolver.solve
          (data, model, project ? perturb : null, transform,
           dampOnlyPerturbation,
           conjugateGradIterations, lineSearchIterations,
           linearizationIterations, lineSearchError,
           null); // new LogMonitor("Test inversion",LOG)
        LOG.fine("data = "+data);
        LOG.fine("model = "+model);
        LOG.fine("result = "+result);

        assertTrue((new Almost(4)).equal(1., result.getData()[0]));
        assertTrue((new Almost(5)).equal(4., result.getData()[1]));
        model.dispose();
        result.dispose();
      }
      perturb.dispose();
    }
    data.dispose();

    if (TestVect.undisposed.size() > 0) {
      throw new IllegalStateException(TestVect.getTraces());
    }
    assertTrue(TestVect.max <=10);

    assertTrue(projectWasTested);

    GaussNewtonSolver.setExpensiveDebug(false);
  }
}

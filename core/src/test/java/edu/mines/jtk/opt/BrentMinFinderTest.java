/****************************************************************************
Copyright 2004, Colorado School of Mines and others.
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

import org.testng.annotations.Test;

import static edu.mines.jtk.util.MathPlus.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.opt.BrentMinFinder}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2004.09.15, 2006.08.14
 */
public class BrentMinFinderTest {

  @Test
  public void testSimple() {
    BrentMinFinder bmf = new BrentMinFinder(new BrentMinFinder.Function() {
      public double evaluate(double x) {
        return x*(x*x-2.0)-5.0;
      }
    });
    double xmin = bmf.findMin(0.0,1.0,1.0e-5);
    trace("xmin="+xmin);
    assertEquals(0.81650,xmin,0.00001);
  }
  
  private void trace(String s) {
    //System.out.println(s);
  }
  
  // Test functions with results published by Forsythe et al.
  abstract class BrentTestFunc implements BrentMinFinder.Function {
    void findMin(double a, double b) {
      _count = 0;
      BrentMinFinder minFinder = new BrentMinFinder(this);
      double xmin = minFinder.findMin(a,b,DBL_EPSILON);
      double ymin = eval(xmin);
      checkMin(xmin);
      checkFunc(ymin);
      checkCount(_count);
    }
    public double evaluate(double x) {
      ++_count;
      return eval(x);
    }
    abstract double eval(double x);
    abstract void checkMin(double x);
    abstract void checkFunc(double y);
    abstract void checkCount(int count);
    private int _count;
  }
  class MinFunc1 extends BrentTestFunc {
    double eval(double x) {
      return (pow(x,2)-2.0)*x-5.0;
    }
    void checkMin(double x) {
      assertEqual(x,8.164965811e-01);
    }
    void checkFunc(double y) {
      assertEqual(y,-6.0887e+00);
    }
    void checkCount(int count) {
      assertEqual(count,11);
    }
  }
  class MinFunc2 extends BrentTestFunc {
    double eval(double x) {
      return pow((pow(x,2)-2.0)*x-5.0,2);
    }
    void checkMin(double x) {
      assertEqual(x,2.094551483e+00);
    }
    void checkFunc(double y) {
      assertEqual(y,2.7186e-16);
    }
    void checkCount(int count) {
      assertEqual(count,13);
    }
  }
  class MinFunc3 extends BrentTestFunc {
    double eval(double x) {
      return pow(cos(x)-x,2)-2;
    }
    void checkMin(double x) {
      assertEqual(x,7.390851269e-01);
    }
    void checkFunc(double y) {
      assertEqual(y,-2.0);
    }
    void checkCount(int count) {
      assertEqual(count,13);
    }
  }
  class MinFunc4 extends BrentTestFunc {
    double eval(double x) {
      return pow(sin(x)-x,2)+1;
    }
    void checkMin(double x) {
      assertEqual(x,-3.125827630e-04);
    }
    void checkFunc(double y) {
      assertEqual(y,1.0);
    }
    void checkCount(int count) {
      assertEqual(count,47);
    }
  }

  @Test
  public void testMinFinder() {
    MinFunc1 f1 = new MinFunc1();
    f1.findMin(0.0,1.0);
    MinFunc2 f2 = new MinFunc2();
    f2.findMin(2.0,3.0);
    MinFunc3 f3 = new MinFunc3();
    f3.findMin(-1.0,3.0);
    MinFunc4 f4 = new MinFunc4();
    f4.findMin(-1.0,3.0);
  }

  private static void assertEqual(double x, double y) {
    double ax = abs(x);
    double ay = abs(y);
    assertTrue(abs(x-y)<=0.0001*max(ax,ay));
  }
  
}

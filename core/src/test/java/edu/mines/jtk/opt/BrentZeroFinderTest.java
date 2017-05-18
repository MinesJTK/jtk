/****************************************************************************
Copyright 2006, Colorado School of Mines and others.
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
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.opt.BrentZeroFinder}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2001.07.10, 2006.07.12
 */
public class BrentZeroFinderTest {

  @Test
  public void testForsythe() {
    ZeroFunc1 f1 = new ZeroFunc1();
    f1.findZero(2.0,3.0);
    ZeroFunc2 f2 = new ZeroFunc2();
    f2.findZero(-1.0,3.0);
    ZeroFunc3 f3 = new ZeroFunc3();
    f3.findZero(-1.0,3.0);
  }

  // Test functions with results published by Forsythe et al.
  abstract class BrentTestFunc implements BrentZeroFinder.Function {
    void findZero(double a, double b) {
      _count = 0;
      BrentZeroFinder zeroFinder = new BrentZeroFinder(this);
      double xzero = zeroFinder.findZero(a,b,DBL_EPSILON);
      double yzero = evaluate(xzero);
      checkRoot(xzero);
      checkFunc(yzero);
      checkCount(_count);
    }
    public double evaluate(double x) {
      ++_count;
      return eval(x);
    }
    abstract double eval(double x);
    abstract void checkRoot(double x);
    abstract void checkFunc(double y);
    abstract void checkCount(int count);
    private int _count;
  }
  class ZeroFunc1 extends BrentTestFunc {
    double eval(double x) {
      return (pow(x,2.0)-2.0)*x-5.0;
    }
    void checkRoot(double x) {
      assertEqual(x,2.094551482e+00);
    }
    void checkFunc(double y) {
      assertEqual(y,-1.7764e-15);
    }
    void checkCount(int count) {
      assertEqual(count,11);
    }
  }
  class ZeroFunc2 extends BrentTestFunc {
    double eval(double x) {
      return cos(x)-x;
    }
    void checkRoot(double x) {
      assertEqual(x,7.390851332e-01);
    }
    void checkFunc(double y) {
      assertEqual(y,0.0);
    }
    void checkCount(int count) {
      assertEqual(count,11);
    }
  }
  class ZeroFunc3 extends BrentTestFunc {
    double eval(double x) {
      return sin(x)-x;
    }
    void checkRoot(double x) {
      assertEqual(x,-1.643737357e-08);
    }
    void checkFunc(double y) {
      assertEqual(y,0.0);
    }
    void checkCount(int count) {
      assertEqual(count,58);
    }
  }

  private static void assertEqual(double x, double y) {
    double ax = abs(x);
    double ay = abs(y);
    assertTrue(abs(x-y)<=0.0001*max(ax,ay));
  }
}

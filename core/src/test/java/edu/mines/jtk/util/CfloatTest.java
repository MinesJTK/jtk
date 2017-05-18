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
package edu.mines.jtk.util;

import org.testng.annotations.Test;

import static edu.mines.jtk.util.Cfloat.*;
import static edu.mines.jtk.util.MathPlus.*;
import static org.testng.Assert.assertEquals;

/**
 * Tests {@link edu.mines.jtk.util.Cfloat}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2004.12.04
 */
public class CfloatTest {

  @Test
  public void test() {

    Cfloat a = new Cfloat(FLT_PI,FLT_E);
    Cfloat b = new Cfloat(FLT_E,FLT_PI);

    assertNear(a,sub(add(a,b),b));
    assertNear(a,div(mul(a,b),b));

    assertNear(a,conj(conj(a)));

    assertNear(a,polar(abs(a),arg(a)));

    assertNear(a,exp(log(a)));

    assertNear(a,pow(sqrt(a),2.0f));

    assertNear(pow(a,b),exp(b.times(log(a))));

    assertNear(pow(a,b),exp(b.times(log(a))));

    assertNear(sin(FLT_I.times(a)),
                 FLT_I.times(sinh(a)));

    assertNear(cos(FLT_I.times(a)),cosh(a));

    assertNear(tan(FLT_I.times(a)),
                 FLT_I.times(tanh(a)));
  }

  private void assertNear(float expected, float actual) {
    float small = 1.0e-6f*max(abs(expected),abs(actual),1.0f);
    assertEquals(expected,actual,small);
  }

  private void assertNear(Cfloat expected, Cfloat actual) {
    assertNear(expected.r,actual.r);
    assertNear(expected.i,actual.i);
  }
}

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

import static edu.mines.jtk.util.MathPlus.*;
import static org.testng.Assert.assertEquals;

/**
 * Tests {@link edu.mines.jtk.util.MathPlus}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2004.12.04
 */
public class MathPlusTest {

  @Test
  public void test() {
    assertNear(0.0f,sin(FLT_PI));
    assertNear(0.0d,sin(DBL_PI));

    assertNear(1.0f,cos(2.0f*FLT_PI));
    assertNear(1.0d,cos(2.0d*DBL_PI));

    assertNear(1.0f,tan(FLT_PI/4.0f));
    assertNear(1.0d,tan(DBL_PI/4.0d));

    assertNear(FLT_PI/2.0f,asin(1.0f));
    assertNear(DBL_PI/2.0d,asin(1.0d));

    assertNear(FLT_PI/2.0f,acos(0.0f));
    assertNear(DBL_PI/2.0d,acos(0.0d));

    assertNear(FLT_PI/4.0f,atan(1.0f));
    assertNear(DBL_PI/4.0d,atan(1.0d));

    assertNear(FLT_PI/2.0f,atan2(1.0f,0.0f));
    assertNear(DBL_PI/2.0d,atan2(1.0d,0.0d));

    assertNear(-3.0f*FLT_PI/4.0f,atan2(-1.0f,-1.0f));
    assertNear(-3.0d*DBL_PI/4.0d,atan2(-1.0d,-1.0d));

    assertNear(FLT_PI,toRadians(180.0f));
    assertNear(DBL_PI,toRadians(180.0d));

    assertNear(180.0f,toDegrees(FLT_PI));
    assertNear(180.0d,toDegrees(DBL_PI));

    assertNear(1.0f,log(exp(1.0f)));
    assertNear(1.0d,log(exp(1.0d)));

    assertNear(3.0f,sqrt(pow(3.0f,2.0f)));
    assertNear(3.0d,sqrt(pow(3.0d,2.0d)));

    assertNear(tanh(1.0f),sinh(1.0f)/cosh(1.0f));
    assertNear(tanh(1.0d),sinh(1.0d)/cosh(1.0d));

    assertNear(4.0f,ceil(FLT_PI));
    assertNear(4.0d,ceil(DBL_PI));
    assertNear(-3.0f,ceil(-FLT_PI));
    assertNear(-3.0d,ceil(-DBL_PI));

    assertNear(3.0f,floor(FLT_PI));
    assertNear(3.0d,floor(DBL_PI));
    assertNear(-4.0f,floor(-FLT_PI));
    assertNear(-4.0d,floor(-DBL_PI));

    assertNear(3.0f,rint(FLT_PI));
    assertNear(3.0d,rint(DBL_PI));
    assertNear(-3.0f,rint(-FLT_PI));
    assertNear(-3.0d,rint(-DBL_PI));

    assertNear(3,round(FLT_PI));
    assertNear(3,round(DBL_PI));
    assertNear(-3,round(-FLT_PI));
    assertNear(-3,round(-DBL_PI));

    assertNear(3,round(FLT_E));
    assertNear(3,round(DBL_E));
    assertNear(-3,round(-FLT_E));
    assertNear(-3,round(-DBL_E));

    assertNear(1.0f,signum(FLT_PI));
    assertNear(1.0d,signum(DBL_PI));
    assertNear(-1.0f,signum(-FLT_PI));
    assertNear(-1.0d,signum(-DBL_PI));
    assertNear(0.0f,signum(0.0f));
    assertNear(0.0d,signum(0.0d));

    assertNear(2,abs(2));
    assertNear(2L,abs(2L));
    assertNear(2.0f,abs(2.0f));
    assertNear(2.0d,abs(2.0d));
    assertNear(2,abs(-2));
    assertNear(2L,abs(-2L));
    assertNear(2.0f,abs(-2.0f));
    assertNear(2.0d,abs(-2.0d));
    assertNear(0, Float.floatToIntBits(abs(0.0f)));
    assertNear(0, Double.doubleToLongBits(abs(0.0d)));

    assertNear(4,max(1,3,4,2));
    assertNear(4L,max(1L,3L,4L,2L));
    assertNear(4.0f,max(1.0f,3.0f,4.0f,2.0f));
    assertNear(4.0d,max(1.0d,3.0d,4.0d,2.0d));

    assertNear(1,min(3,1,4,2));
    assertNear(1L,min(3L,1L,4L,2L));
    assertNear(1.0f,min(3.0f,1.0f,4.0f,2.0f));
    assertNear(1.0d,min(3.0d,1.0d,4.0d,2.0d));
  }

  private void assertNear(float expected, float actual) {
    float small = 1.0e-6f*max(abs(expected),abs(actual),1.0f);
    assertEquals(expected,actual,small);
  }

  private void assertNear(double expected, double actual) {
    double small = 1.0e-12f*max(abs(expected),abs(actual),1.0d);
    assertEquals(expected,actual,small);
  }
}

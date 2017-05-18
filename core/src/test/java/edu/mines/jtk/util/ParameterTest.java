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
package edu.mines.jtk.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.util.Parameter}.
 * @author Dave Hale, Colorado School of Mines
 * @version 02/21/2000, 08/24/2006.
 */
public class ParameterTest {

  @Test
  public void testParameter() {
    Parameter par = new Parameter("fo<o","Hello");
    assertEquals("Hello",par.getString());

    par.setString("true");
    assertTrue(par.getBoolean());

    par.setString("3141");
    assertEquals(3141,par.getInt());

    par.setString("3141.0");
    assertEquals(3141.0f,par.getFloat());

    par.setString("3.141");
    assertEquals(3.141,par.getDouble());

    double[] empty = new double[0];
    par.setDoubles(empty);
    assertEquals(Parameter.DOUBLE,par.getType());

    par.setFloats(null);
    assertEquals(Parameter.FLOAT,par.getType());
    float[] fvalues = {1.2f,3.4f};

    par.setFloats(fvalues);
    fvalues = par.getFloats();
    assertEquals(1.2f,fvalues[0]);
    assertEquals(3.4f,fvalues[1]);

    par.setUnits("km/s");
    assertEquals("km/s",par.getUnits());

    boolean[] bvalues = {true,false};
    par.setBooleans(bvalues);
    bvalues = par.getBooleans();
    assertTrue(bvalues[0]);
    assertFalse(bvalues[1]);

    par.setUnits(null);
    assertNull(par.getUnits());
  }
}

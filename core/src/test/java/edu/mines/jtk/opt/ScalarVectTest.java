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

import org.testng.annotations.Test;

/**
 * Tests {@link edu.mines.jtk.opt.ScalarVect}.
 */
public class ScalarVectTest {

  @Test
  public void testAll () {
    {
      Vect v = new ScalarVect(.266, 3.);
      VectUtil.test(v);
    }
    {
      Vect v = new ScalarVect(-666.266, 8.);
      VectUtil.test(v);
    }
  }
}

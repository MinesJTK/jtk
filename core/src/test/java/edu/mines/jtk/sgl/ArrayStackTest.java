/****************************************************************************
 Copyright 2017, Colorado School of Mines and others.
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
package edu.mines.jtk.sgl;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;

/**
 * Tests {@link edu.mines.jtk.sgl.ArrayStack}.
 * @author Chris Engelsma
 * @version 2017.05.02
 */
public class ArrayStackTest extends TestCase {
  public static void main(String[] args) {
    TestSuite suite = new TestSuite(ArrayStackTest.class);
    junit.textui.TestRunner.run(suite);
  }

  public void testStandardFunctionality() {
    int np = 10;
    ArrayStack<Integer> as = new ArrayStack<>();
    Assert.assertEquals(0,as.size());

    // Test push
    for (int i=0; i<np; ++i) {
      Assert.assertEquals(i,as.push(i).intValue());
    }
    Assert.assertEquals(np,as.size());

    // Test get
    for (int i=0; i<np; ++i) {
      Assert.assertEquals(i,as.get(i).intValue());
    }
    Assert.assertEquals(np,as.size());

    // Test peek
    Assert.assertEquals((Integer)(np-1),as.peek());
    Assert.assertEquals(np, as.size()); // No size change.

    // Test pop
    for (int i=0; i<np; ++i) {
      int j = as.pop();
      int expected = np-i-1;
      Assert.assertEquals(expected,j);
      Assert.assertEquals(np-i-1,as.size());
    }
  }

}
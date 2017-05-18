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

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.logging.Logger;

import edu.mines.jtk.util.Almost;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.opt.ArrayVect1f}.
 */
public class ArrayVect1fTest {
  private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt.test");

  @Test
  public void testAll () throws Exception {
    { // check Vect properties
      float[] a = new float[31];
      for (int i=0; i<a.length; ++i) {a[i] = i;}
      Vect v = new ArrayVect1f(a, 0, 3.);
      VectUtil.test(v);
      v = new ArrayVect1f(a, 10, 3.);
      VectUtil.test(v);

      // test inverse covariance
      for (int i=0; i<a.length; ++i) {a[i] = 1;}
      v = new ArrayVect1f(a, 0, 3.);
      Vect w = v.clone();
      w.multiplyInverseCovariance();
      assertTrue(Almost.FLOAT.equal(1./3., v.dot(w)));
      assertTrue(Almost.FLOAT.equal(1./3., v.magnitude()));
    }

    { // test size of serialization
      float[] data = new float[501];
      Arrays.fill(data, 1.f);
      int minimumSize = data.length*4;
      int externalSize = 10*minimumSize;
      int writeObjectSize = 10*minimumSize;
      LOG.fine("minimum size = "+ minimumSize);
      ArrayVect1f v = new ArrayVect1f(data, 0, 1.);
      {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(v);
        oos.close();
        byte[] result = baos.toByteArray();
        externalSize = result.length;
        LOG.fine("externalizable size = "+externalSize);
      }
      {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(v);
        oos.close();
        byte[] result = baos.toByteArray();
        writeObjectSize = result.length;
        LOG.fine("writeObject size = "+writeObjectSize);
      }
      assertTrue(externalSize <= minimumSize + 308);
      assertTrue(writeObjectSize <= minimumSize + 308);
    }
  }
}

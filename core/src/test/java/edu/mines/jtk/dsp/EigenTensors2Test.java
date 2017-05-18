/****************************************************************************
Copyright 2008, Colorado School of Mines and others.
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
package edu.mines.jtk.dsp;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/**
 * Tests {@link edu.mines.jtk.dsp.EigenTensors2}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2008.06.09
 */
public class EigenTensors2Test {

  @Test
  public void testRandom() {
    testRandom(0.1,1.0e-6);
  }
  private void testRandom(double errorAngle, double errorCoeff) {
    int n1 = 13, n2 = 14;
    EigenTensors2 et = new EigenTensors2(n1,n2);
    for (int i2=0; i2<n2; ++i2) {
      for (int i1=0; i1<n1; ++i1) {
        float[] a = makeRandomEigenvalues();
        float[] u = makeRandomEigenvector();
        et.setEigenvalues(i1,i2,a);
        et.setEigenvectorU(i1,i2,u);
        float[] c;
        c = et.getEigenvectorU(i1,i2); checkEigenvectors(u,c,errorAngle);
        c = et.getEigenvalues(i1,i2); checkEigenvalues(c,a,errorCoeff);
        et.setTensor(i1,i2,et.getTensor(i1,i2));
        c = et.getEigenvectorU(i1,i2); checkEigenvectors(u,c,errorAngle);
        c = et.getEigenvalues(i1,i2); checkEigenvalues(c,a,errorCoeff);
      }
    }
  }

  private void checkEigenvalues(float[] c, float[] a, double e) {
    assertEquals(c[0],a[0],e);
    assertEquals(c[1],a[1],e);
  }

  private static void checkEigenvectors(float[] u, float[] v, double e) {
    float uv = u[0]*v[0]+u[1]*v[1];
    double ca = Math.max(-1.0,Math.min(uv,1.0f));
    double a = Math.toDegrees(Math.acos(ca));
    if (a>90.0f)
      a -= 180.0f;
    assertEquals(0.0,a,e);
  }

  private static java.util.Random r = new java.util.Random();

  // Random eigenvalues with eu>=ev.
  private static float[] makeRandomEigenvalues() {
    float a1 = r.nextFloat();
    float a2 = r.nextFloat();
    float au = Math.max(a1,a2);
    float av = Math.min(a1,a2);
    return new float[]{au,av};
  }

  // Random unit vector with non-negative 3rd component.
  private static float[] makeRandomEigenvector() {
    float a = r.nextFloat()-0.5f;
    float b = r.nextFloat()-0.5f;
    float s = 1.0f/(float)Math.sqrt(a*a+b*b);
    return new float[]{a*s,b*s};
  }
}

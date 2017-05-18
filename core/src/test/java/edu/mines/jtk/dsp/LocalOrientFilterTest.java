/****************************************************************************
Copyright 2007, Colorado School of Mines and others.
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

import static edu.mines.jtk.util.ArrayMath.*;

/**
 * Tests {@link edu.mines.jtk.dsp.LocalOrientFilter}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2007.02.12
 */
public class LocalOrientFilterTest {

  @Test
  public void test2() {
    double sigma = 8.0;
    int n1 = 1+4*(int)(3*sigma);
    int n2 = n1+2;
    LocalOrientFilter lof = new LocalOrientFilter(sigma);
    float pi = FLT_PI;
    float[] dips = {-0.49f*pi,-0.20f*pi,-0.01f,0.01f,0.20f*pi,0.49f*pi};
    for (float dip:dips) {
      float k = 0.3f;
      float c = k*cos(dip);
      float s = k*sin(dip);
      float[][] x = sin(rampfloat(0.0f,c,s,n1,n2));
      float[][] theta = new float[n2][n1];
      float[][] u1 = new float[n2][n1];
      float[][] u2 = new float[n2][n1];
      float[][] v1 = new float[n2][n1];
      float[][] v2 = new float[n2][n1];
      float[][] eu = new float[n2][n1];
      float[][] ev = new float[n2][n1];
      float[][] el = new float[n2][n1];
      lof.apply(x,theta,u1,u2,v1,v2,eu,ev,el);
      assertEqualsLocal(dip,theta,0.01);
      assertEqualsLocal(cos(dip),u1,0.01);
      assertEqualsLocal(sin(dip),u2,0.01);
      assertEqualsLocal(-sin(dip),v1,0.01);
      assertEqualsLocal(cos(dip),v2,0.01);
      assertEqualsLocal(1.0f,el,0.01);
    }
  }

  @Test
  public void test3Planar() {
    double sigma = 6.0;
    int n1 = 1+2*(int)(3*sigma);
    int n2 = n1+2;
    int n3 = n2+2;
    LocalOrientFilter lof = new LocalOrientFilter(sigma);
    float pi = FLT_PI;
    float[] azis = {-0.50f*pi,0.25f*pi,0.99f*pi};
    float[] dips = { 0.01f*pi,0.20f*pi,0.49f*pi};
    for (float azi:azis) {
      for (float dip:dips) {
        //System.out.println("planar: azi="+azi+" dip="+dip);
        float k = 0.3f;
        float ku1 = k*cos(dip);
        float ku2 = k*sin(dip)*cos(azi);
        float ku3 = k*sin(dip)*sin(azi);
        float[][][] x = sin(rampfloat(0.0f,ku1,ku2,ku3,n1,n2,n3));
        float[][][] theta = new float[n3][n2][n1];
        float[][][] phi = new float[n3][n2][n1];
        float[][][] u1 = new float[n3][n2][n1];
        float[][][] u2 = new float[n3][n2][n1];
        float[][][] u3 = new float[n3][n2][n1];
        float[][][] v1 = new float[n3][n2][n1];
        float[][][] v2 = new float[n3][n2][n1];
        float[][][] v3 = new float[n3][n2][n1];
        float[][][] w1 = new float[n3][n2][n1];
        float[][][] w2 = new float[n3][n2][n1];
        float[][][] w3 = new float[n3][n2][n1];
        float[][][] eu = new float[n3][n2][n1];
        float[][][] ev = new float[n3][n2][n1];
        float[][][] ew = new float[n3][n2][n1];
        float[][][] ep = new float[n3][n2][n1];
        float[][][] el = new float[n3][n2][n1];
        lof.apply(x,theta,phi,u1,u2,u3,v1,v2,v3,w1,w2,w3,eu,ev,ew,ep,el);
        assertEqualsLocal(dip,theta,0.02);
        assertEqualsLocal(azi,phi,0.02);
        assertEqualsLocal(cos(dip),u1,0.02);
        assertEqualsLocal(sin(dip)*cos(azi),u2,0.02);
        assertEqualsLocal(sin(dip)*sin(azi),u3,0.02);
        assertEqualsLocal(1.0,ep,0.02);
        assertEqualsLocal(0.0,el,0.02);
      }
    }
  }

  @Test
  public void test3Linear() {
    double sigma = 6.0;
    int n1 = 1+2*(int)(3*sigma);
    int n2 = n1+2;
    int n3 = n2+2;
    LocalOrientFilter lof = new LocalOrientFilter(sigma);
    float pi = FLT_PI;
    float[] azis = {-0.50f*pi,0.25f*pi,0.99f*pi};
    float[] dips = { 0.01f*pi,0.20f*pi,0.49f*pi};
    for (float azi:azis) {
      for (float dip:dips) {
        //System.out.println("linear: azi="+azi+" dip="+dip);
        float a1 = (n1-1)/2;
        float a2 = (n2-1)/2;
        float a3 = (n3-1)/2;
        float b1 = cos(dip);
        float b2 = sin(dip)*cos(azi);
        float b3 = sin(dip)*sin(azi);
        float[][][] x = new float[n3][n2][n1];
        for (int i3=0; i3<n3; ++i3) {
          float x3 = i3-a3;
          for (int i2=0; i2<n2; ++i2) {
            float x2 = i2-a2;
            for (int i1=0; i1<n1; ++i1) {
              float x1 = i1-a1;
              float t = x1*b1+x2*b2+x3*b3;
              float d1 = x1-t*b1;
              float d2 = x2-t*b2;
              float d3 = x3-t*b3;
              x[i3][i2][i1] = exp(-0.125f*(d1*d1+d2*d2+d3*d3));
            }
          }
        }
        float[][][] theta = new float[n3][n2][n1];
        float[][][] phi = new float[n3][n2][n1];
        float[][][] u1 = new float[n3][n2][n1];
        float[][][] u2 = new float[n3][n2][n1];
        float[][][] u3 = new float[n3][n2][n1];
        float[][][] v1 = new float[n3][n2][n1];
        float[][][] v2 = new float[n3][n2][n1];
        float[][][] v3 = new float[n3][n2][n1];
        float[][][] w1 = new float[n3][n2][n1];
        float[][][] w2 = new float[n3][n2][n1];
        float[][][] w3 = new float[n3][n2][n1];
        float[][][] eu = new float[n3][n2][n1];
        float[][][] ev = new float[n3][n2][n1];
        float[][][] ew = new float[n3][n2][n1];
        float[][][] ep = new float[n3][n2][n1];
        float[][][] el = new float[n3][n2][n1];
        lof.apply(x,theta,phi,u1,u2,u3,v1,v2,v3,w1,w2,w3,eu,ev,ew,ep,el);
        /*
        print("eu=",eu);
        print("ev=",ev);
        print("ew=",ew);
        print("ep=",ep);
        print("el=",el);
        */
        assertAbsEqual(cos(dip),w1,0.10);
        assertAbsEqual(sin(dip)*cos(azi),w2,0.10);
        assertAbsEqual(sin(dip)*sin(azi),w3,0.10);
        assertEqualsLocal(0.0,ep,0.2);
        assertEqualsLocal(1.0,el,0.2);
      }
    }
  }

  private static void assertEqualsLocal(double e, float[][] a, double tol) {
    int n1 = a[0].length;
    int n2 = a.length;
    assertEquals(e,a[n2/2][n1/2],tol);
  }

  private static void assertEqualsLocal(double e, float[][][] a, double tol) {
    int n1 = a[0][0].length;
    int n2 = a[0].length;
    int n3 = a.length;
    assertEquals(e,a[n3/2][n2/2][n1/2],tol);
  }

  private static void assertAbsEqual(double e, float[][][] a, double tol) {
    int n1 = a[0][0].length;
    int n2 = a[0].length;
    int n3 = a.length;
    assertEquals(abs(e),abs(a[n3/2][n2/2][n1/2]),tol);
  }

  /*
  private static void print(String s, float[][][] a) {
    int n1 = a[0][0].length;
    int n2 = a[0].length;
    int n3 = a.length;
    System.out.println(s+a[n3/2][n2/2][n1/2]);
  }
  */
}

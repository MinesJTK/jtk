/****************************************************************************
Copyright 2012, Colorado School of Mines and others.
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
package edu.mines.jtk.interp;

import java.util.Random;

import org.testng.annotations.Test;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import edu.mines.jtk.dsp.Sampling;
import static edu.mines.jtk.util.ArrayMath.*;

/**
 * Tests {@link edu.mines.jtk.interp.TricubicInterpolator3}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2012.12.27
 */
public class TricubicInterpolator3Test {

  @Test
  public void testSingleValues() {
    float[][][][] xy = sampleTestFunction(11,12,13);
    float[] x1 = xy[0][0][0];
    float[] x2 = xy[0][0][1];
    float[] x3 = xy[0][0][2];
    float[][][] y = xy[1];
    float x1min = min(x1), x1max = max(x1);
    float x2min = min(x2), x2max = max(x2);
    float x3min = min(x3), x3max = max(x3);
    TricubicInterpolator3 ti = makeInterpolator(x1,x2,x3,y);
    int n = 100;
    Random r = new Random(5);
    for (int i=0; i<n; ++i) {
      float x1i = x1min+(x1max-x1min)*r.nextFloat();
      float x2i = x2min+(x2max-x2min)*r.nextFloat();
      float x3i = x3min+(x3max-x3min)*r.nextFloat();
      float y000 = ti.interpolate000(x1i,x2i,x3i);
      float y100 = ti.interpolate100(x1i,x2i,x3i);
      float y010 = ti.interpolate010(x1i,x2i,x3i);
      float y001 = ti.interpolate001(x1i,x2i,x3i);
      float z000 = testFunction000(x1i,x2i,x3i);
      float z100 = testFunction100(x1i,x2i,x3i);
      float z010 = testFunction010(x1i,x2i,x3i);
      float z001 = testFunction001(x1i,x2i,x3i);
      assertNear(z000,y000);
      assertNear(z100,y100);
      assertNear(z010,y010);
      assertNear(z001,y001);
    }
  }

  @Test
  public void testArrayValues() {
    float[][][][] xy = sampleTestFunction(11,12,13);
    float[] x1 = xy[0][0][0];
    float[] x2 = xy[0][0][1];
    float[] x3 = xy[0][0][2];
    float[][][] y = xy[1];
    float x1min = min(x1), x1max = max(x1);
    float x2min = min(x2), x2max = max(x2);
    float x3min = min(x3), x3max = max(x3);
    TricubicInterpolator3 ti = makeInterpolator(x1,x2,x3,y);
    int n1i = 51;
    int n2i = 52;
    int n3i = 53;
    float d1i = (x1max-x1min)/(n1i-1);
    float d2i = (x2max-x2min)/(n2i-1);
    float d3i = (x3max-x3min)/(n3i-1);
    float f1i = x1min;
    float f2i = x2min;
    float f3i = x3min;
    float[] x1i = rampfloat(f1i,d1i,n1i);
    float[] x2i = rampfloat(f2i,d2i,n2i);
    float[] x3i = rampfloat(f3i,d3i,n3i);
    float[][][] yi = ti.interpolate(x1i,x2i,x3i);
    for (int i3i=0; i3i<n3i; ++i3i) {
      for (int i2i=0; i2i<n2i; ++i2i) {
        for (int i1i=0; i1i<n1i; ++i1i) {
          float zi = testFunction000(x1i[i1i],x2i[i2i],x3i[i3i]);
          assertNear(zi,yi[i3i][i2i][i1i]);
        }
      }
    }
  }

  @Test
  public void testSampleValues() {
    float[][][][] xy = sampleTestFunction(11,12,13);
    float[] x1 = xy[0][0][0];
    float[] x2 = xy[0][0][1];
    float[] x3 = xy[0][0][2];
    float[][][] y = xy[1];
    float x1min = min(x1), x1max = max(x1);
    float x2min = min(x2), x2max = max(x2);
    float x3min = min(x3), x3max = max(x3);
    TricubicInterpolator3 ti = makeInterpolator(x1,x2,x3,y);
    int n1i = 51;
    int n2i = 52;
    int n3i = 53;
    float d1i = (x1max-x1min)/(n1i-1);
    float d2i = (x2max-x2min)/(n2i-1);
    float d3i = (x3max-x3min)/(n3i-1);
    float f1i = x1min;
    float f2i = x2min;
    float f3i = x3min;
    Sampling s1i = new Sampling(n1i,d1i,f1i);
    Sampling s2i = new Sampling(n2i,d2i,f2i);
    Sampling s3i = new Sampling(n3i,d3i,f3i);
    float[][][] yi = ti.interpolate(s1i,s2i,s3i);
    for (int i3i=0; i3i<n3i; ++i3i) {
      float x3i = (float)s3i.getValue(i3i);
      for (int i2i=0; i2i<n2i; ++i2i) {
        float x2i = (float)s2i.getValue(i2i);
        for (int i1i=0; i1i<n1i; ++i1i) {
          float x1i = (float)s1i.getValue(i1i);
          float zi = testFunction000(x1i,x2i,x3i);
          assertNear(zi,yi[i3i][i2i][i1i]);
        }
      }
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // private

  private static TricubicInterpolator3 makeInterpolator(
    float[] x1, float[] x2, float[] x3, float[][][] y)
  {
    return new TricubicInterpolator3(
      TricubicInterpolator3.Method.MONOTONIC,
      TricubicInterpolator3.Method.SPLINE,
      TricubicInterpolator3.Method.SPLINE,
      x1.length,x2.length,x3.length,x1,x2,x3,y);
  }

  private static float[][][][] sampleTestFunction(int n1, int n2, int n3) {
    Random r = new Random(3);
    float[] x1 = mul(2.0f,randfloat(r,n1));
    float[] x2 = mul(2.0f,randfloat(r,n2));
    float[] x3 = mul(2.0f,randfloat(r,n3));
    quickSort(x1);
    quickSort(x2);
    quickSort(x3);
    float[][][] y = new float[n3][n2][n1];
    for (int i3=0; i3<n3; ++i3)
      for (int i2=0; i2<n2; ++i2)
        for (int i1=0; i1<n1; ++i1)
          y[i3][i2][i1] = testFunction000(x1[i1],x2[i2],x3[i3]);
    return new float[][][][]{new float[][][]{{x1,x2,x3}},y};
  }
  private static float testFunction000(float x1, float x2, float x3) {
    return (1.1f+x1)*(1.2f+x2)*(1.3f+x3);
  }
  private static float testFunction100(float x1, float x2, float x3) {
    return (1.2f+x2)*(1.3f+x3);
  }
  private static float testFunction010(float x1, float x2, float x3) {
    return (1.1f+x1)*(1.3f+x3);
  }
  private static float testFunction001(float x1, float x2, float x3) {
    return (1.1f+x1)*(1.2f+x2);
  }

  private static void assertNear(float x, float y) {
    float ax = abs(x);
    float ay = abs(y);
    assertTrue(abs(x-y)<=0.001f*max(ax,ay));
  }
  
}

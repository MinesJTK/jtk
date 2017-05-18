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
package edu.mines.jtk.dsp;

import static java.lang.Math.*;
import java.util.Random;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests {@link SincInterpolator}.
 * @author Dave Hale, Colorado School of Mines
 * @author Bill Harlan, Landmark Graphics
 * @version 2012.12.21
 */
public class SincInterpolatorTest {

  private double[] _emaxs = {0.1,0.01,0.001};
  private double[] _fmaxs = {0.10,0.30,0.40,0.45};
  private int[] _lmaxs = {8,10,12,14,16};

  @Test
  public void testExtrapolation() {
    SincInterpolator si = new SincInterpolator();
    Random random = new Random();
    int lmax = si.getMaximumLength();
    int nxu = 2*lmax;
    int npad = lmax;
    double dxu = 1.0;
    double fxu = npad;
    int nx = npad+nxu+npad;
    double dx = 0.999;
    double fx = npad;
    float[] yi = new float[nxu];
    float[] yz = new float[nx];
    float[] yc = new float[nx];
    float[] yo = new float[nx];
    float[] yt = new float[nx];
    for (int ixu=0; ixu<nxu; ++ixu)
      yi[ixu] = yz[ixu+npad] = yc[ixu+npad] = random.nextFloat();
    for (int ipad=0; ipad<npad; ++ipad) {
      yc[ipad] = yc[npad];
      yc[npad+nxu+ipad] = yc[npad+nxu-1];
    }
    si.setExtrapolation(SincInterpolator.Extrapolation.ZERO);
    si.interpolate(nxu,dxu,fxu,yi,nx,dx,fx,yo);
    si.interpolate(npad+nxu+npad,dxu,0.0,yz,nx,dx,fx,yt);
    for (int ix=0; ix<nx; ++ix)
      assertEquals(yo[ix],yt[ix],0.0);
    si.setExtrapolation(SincInterpolator.Extrapolation.CONSTANT);
    si.interpolate(nxu,dxu,fxu,yi,nx,dx,fx,yo);
    si.interpolate(npad+nxu+npad,dxu,0.0,yc,nx,dx,fx,yt);
    for (int ix=0; ix<nx; ++ix)
      assertEquals(yo[ix],yt[ix],0.0);
  }

  @Test
  public void testComplex() {
    SincInterpolator si = new SincInterpolator();
    Random random = new Random();
    int nxu = 100;
    double dxu = 3.14159;
    double fxu = 1.23456;
    float[] yr = new float[nxu];
    float[] yi = new float[nxu];
    float[] yc = new float[2*nxu];
    for (int ixu=0; ixu<nxu; ++ixu) {
      yr[ixu] = yc[2*ixu  ] = random.nextFloat();
      yi[ixu] = yc[2*ixu+1] = random.nextFloat();
    }
    int nx = 200;
    double dx = -0.9*dxu;
    double fx = fxu+(nxu+30)*dxu;
    float[] zr = new float[nx];
    float[] zi = new float[nx];
    float[] zc = new float[2*nx];
    si.setExtrapolation(SincInterpolator.Extrapolation.ZERO);
    si.interpolate(nxu,dxu,fxu,yr,nx,dx,fx,zr);
    si.interpolate(nxu,dxu,fxu,yi,nx,dx,fx,zi);
    si.interpolateComplex(nxu,dxu,fxu,yc,nx,dx,fx,zc);
    for (int ix=0; ix<nx; ++ix) {
      assertEquals(zr[ix],zc[2*ix  ],0.0);
      assertEquals(zi[ix],zc[2*ix+1],0.0);
    }
    si.setExtrapolation(SincInterpolator.Extrapolation.CONSTANT);
    si.interpolate(nxu,dxu,fxu,yr,nx,dx,fx,zr);
    si.interpolate(nxu,dxu,fxu,yi,nx,dx,fx,zi);
    si.interpolateComplex(nxu,dxu,fxu,yc,nx,dx,fx,zc);
    for (int ix=0; ix<nx; ++ix) {
      assertEquals(zr[ix],zc[2*ix  ],0.0);
      assertEquals(zi[ix],zc[2*ix+1],0.0);
    }
  }

  @Test
  public void testErrorAndFrequency() {
    for (double emax:_emaxs) {
      for (double fmax:_fmaxs) {
        SincInterpolator si =
          SincInterpolator.fromErrorAndFrequency(emax,fmax);
        testInterpolator(si);
      }
    }
  }

  @Test
  public void testErrorAndLength() {
    for (double emax:_emaxs) {
      for (int lmax:_lmaxs) {
        SincInterpolator si =
          SincInterpolator.fromErrorAndLength(emax,lmax);
        testInterpolator(si);
      }
    }
  }

  @Test
  public void testFrequencyAndLength() {
    for (double fmax:_fmaxs) {
      for (int lmax:_lmaxs) {
        if ((1.0-2.0*fmax)*lmax>1.0) {
          SincInterpolator si =
            SincInterpolator.fromFrequencyAndLength(fmax,lmax);
          testInterpolator(si);
        }
      }
    }
  }

  @Test
  public void testAccumulate() {
    // test that accumulate is a true transpose of interpolate
    Random random = new Random(123456); // avoid unreasonable accidents
    for (int repeat=0; repeat<5; ++repeat) {
      for (SincInterpolator.Extrapolation extrapolation:
             SincInterpolator.Extrapolation.values()) {
        int nxu = 201;
        double fxu = Math.PI;
        double dxu = Math.E;
        double exu = fxu + dxu*(nxu-1);
        float[] yu = new float[nxu];
        for (int i=0; i<nxu; ++i) {
          yu[i] = 2*random.nextFloat() - 1;
        }
        // random locations extending outside range
        int nx = 2*nxu;
        float[] x = new float[nx];
        float[] y = new float[nx];
        for (int i=0; i<nxu; ++i) {
          x[i] = (float)((1.2*random.nextFloat()-0.1)*(exu-fxu) + fxu);
          y[i] = 2*random.nextFloat() - 1;
        }

        // Same interpolator for both directions
        SincInterpolator si = new SincInterpolator();
        si.setExtrapolation(extrapolation);

        // forward interpolation
        float[] yi = new float[nx]; // interpolated
        si.interpolate(nxu, dxu, fxu, yu, nx, x, yi);

        // transpose accumulation
        float[] ya = new float[nxu]; // accumulated
        si.accumulate(nx, x, y, nxu, dxu, fxu, ya);

        // Check transpose with dot product: yu.ya = y.yi
        double yuYa = 0;
        for (int ixu=0; ixu<nxu; ++ixu) {
          yuYa += yu[ixu]*ya[ixu];
        }
        double yYi = 0;
        for (int ix=0; ix<nx; ++ix) {
          yYi += y[ix]*yi[ix];
        }
        double ratio = yuYa/yYi;
        String message =
          "yu.ya="+yuYa+" y.yi="+yYi+" ratio="+ratio;
        trace(message);
        assertTrue(ratio > 0.99999);
        assertTrue(ratio < 1.00001);
      }
    }
  }

  private void testInterpolator(SincInterpolator si) {
    testInterpolatorWithSweep(si);
  }

  private void testInterpolatorWithSweep(SincInterpolator si) {
    double emax = si.getMaximumError();
    double fmax = si.getMaximumFrequency();
    int lmax = si.getMaximumLength();
    long nbytes = si.getTableBytes();
    trace("lmax="+lmax+" fmax="+fmax+" emax="+emax+" nbytes="+nbytes);
    
    // Uniformly-sampled signal is an up-down sweep. (See below.)
    int nmax = (int)(1000*fmax);
    double xmax = PI*nmax/fmax;
    double dxu = 1.0;
    double fxu = 0.0;
    int nxu = 1+(int)((xmax-fxu)/dxu);
    dxu = (xmax-fxu)/(nxu-1);
    float[] yu = new float[nxu];
    for (int ixu=0; ixu<nxu; ++ixu) {
      double x = fxu+ixu*dxu;
      yu[ixu] = (float)sweep(fmax,nmax,x);
    }
    si.setExtrapolation(SincInterpolator.Extrapolation.CONSTANT);
    //trace("xmax="+xmax+" nmax="+nmax+" nxu="+nxu);

    // Interpolate.
    double dx = 0.01*dxu;
    double fx = 0.0;
    int nx = 1+(int)((xmax-fx)/dx);
    dx = (xmax-fx)/(nx-1);
    float[] y = new float[nx];
    si.interpolate(nxu,dxu,fxu,yu,nx,dx,fx,y);

    // Compute the maximum error and compare with emax.
    double error = 0.0;
    for (int ix=0; ix<nx; ++ix) {
      double x = fx+ix*dx;
      double yi = y[ix];
      double ys = sweep(fmax,nmax,x);
      double ei = abs(yi-ys);
      if (ei>emax)
        trace("    x="+x+" ys="+ys+" yi="+yi);
      error = max(error,ei);
      assertEquals(ys,yi,emax);
    }
    trace("  error="+error);
    if (error>emax)
      trace("  WARNING: error = "+error+" > emax = "+emax);

    // Repeat for a simple shift of 1/2 the input sampling interval.
    double shift = 0.5*dxu;
    nx = nxu;
    dx = dxu;
    fx = fxu+shift;
    si.interpolate(nxu,dxu,fxu,yu,nx,dx,fx,y);
    error = 0.0;
    for (int ix=0; ix<nx; ++ix) {
      double x = fx+ix*dx;
      double yi = y[ix];
      double ys = sweep(fmax,nmax,x);
      double ei = abs(yi-ys);
      if (ei>emax)
        trace("    x="+x+" ys="+ys+" yi="+yi);
      error = max(error,ei);
      assertEquals(ys,yi,emax);
    }
    trace("  error="+error);
  }

  // An up-down sweep signal that begins with zero frequency, increases to
  // frequency fmax (in cycles/sample) at x = xmax/2, then decreases to zero 
  // frequency again at x = xmax, where xmax = PI*nmax/fmax. The frequency
  // changes continuously and changes most slowly near x = xmax/2, where 
  // frequencies are highest, and where interpolation errors may be largest.
  private double sweep(double fmax, int nmax, double x) {
    return cos(2.0*PI*nmax*cos(x*fmax/nmax));
  }

  private static void trace(String s) {
    //System.out.println(s);
  }
}

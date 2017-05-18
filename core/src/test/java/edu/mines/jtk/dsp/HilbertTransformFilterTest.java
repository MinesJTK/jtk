/****************************************************************************
Copyright 2010, Colorado School of Mines and others.
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
import static org.testng.Assert.assertTrue;

import static edu.mines.jtk.util.ArrayMath.*;

/**
 * Tests {@link edu.mines.jtk.dsp.HilbertTransformFilter}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2010.06.01
 */
public class HilbertTransformFilterTest {

  @Test
  public void testApply() {
    int[] nmax_test   = {NMAX_DEFAULT,100000,100000,100000,100000};
    float[] emax_test = {EMAX_DEFAULT,0.010f,0.010f,0.001f,0.001f};
    float[] fmin_test = {FMIN_DEFAULT,0.050f,0.025f,0.100f,0.010f};
    float[] fmax_test = {FMAX_DEFAULT,0.475f,0.450f,0.400f,0.490f};
    int ntest = emax_test.length;
    
    // Loop over tests.
    for (int itest=0; itest<ntest; ++itest) {
      
      // Construct transformer.
      int nmax = nmax_test[itest];
      float emax = emax_test[itest];
      float fmin = fmin_test[itest];
      float fmax = fmax_test[itest];
      HilbertTransformFilter htf = 
        new HilbertTransformFilter(nmax,emax,fmin,fmax);
      int lhtf = htf.length();
      
      // Apply it to get impulse response.
      int nxy = lhtf;
      float[] x = new float[nxy];
      x[(lhtf-1)/2] = 1.0f;
      float[] y = new float[nxy];
      htf.apply(nxy,x,y);
      
      // Check amplitude spectrum.
      int nfft = FftReal.nfftSmall(16*nxy);
      FftReal fft = new FftReal(nfft);
      int mfft = nfft/2+1;
      float[] hfft = new float[2*mfft];
      for (int i=0; i<nxy; ++i) hfft[i] = y[i];
      fft.realToComplex(1,hfft,hfft);
      int jfmin = 1+(int)(fmin*nfft);
      int jfmax = (int)(fmax*nfft);
      float[] afft = abs(sub(cabs(hfft),1.0f));
      float error = max(copy(1+jfmax-jfmin,jfmin,afft));
      /*
      System.out.println(" emax="+emax +
                         " fmin="+fmin +
                         " fmax="+fmax +
                         " length="+lhtf +
                         " error="+error);
      */
      assertTrue(error<=emax,"actual error less than maximum expected error");
    }
  }

  private static final int NMAX_DEFAULT = 100000; // default max length.
  private static final float EMAX_DEFAULT = 0.010f; // default max error.
  private static final float FMIN_DEFAULT = 0.025f; // default min frequency.
  private static final float FMAX_DEFAULT = 0.475f; // default max frequency.
}


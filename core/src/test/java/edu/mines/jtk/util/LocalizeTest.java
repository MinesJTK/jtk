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
package edu.mines.jtk.util;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

import static edu.mines.jtk.util.Localize.timeWords;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests {@link edu.mines.jtk.util.Localize}.
 */
public class LocalizeTest {
  private static final Logger LOG = Logger.getLogger(LocalizeTest.class.getName());

  @Test
  // TODO split this test up.
  public void testLocalize() {
    { // This is the normal way to call it. Gets LocalizeTest.properties, unless we are in France.
      final Localize dfault = new Localize(LocalizeTest.class);
      final String sDefault = dfault.format("msg1", 3.14, 42);
      assertEquals("A number 3.14000 here, and another #42",sDefault);
    }
    { // for testing, we specify a specific locale.  Gets LocalizeTest_fr.properties.
      final Localize fr = new Localize(LocalizeTest.class, null, Locale.FRENCH);
      final int i = 42;
      final String sFr = fr.format("msg1", 3.14, i);
      assertTrue("Un nombre 3,14000 ici, et un autre #42".equals(sFr)
              || "Un nombre 3.14000 ici, et un autre #42".equals(sFr));
    }
    { // for testing, we specify an alternate file LocalizeTestAlt[_*].properties
      final Localize alt = new Localize(LocalizeTest.class, "LocalizeTestAlt");
      final String s = alt.format("msg1", 3.14, 42);
      assertEquals("A custom file with number 3.14000, and another #42",s);
    }
    { // Specifying format instead of key for format.
      final Localize alt = new Localize(LocalizeTest.class, "LocalizeTestAlt");
      final String s = alt.format("No property just a format with number %g.", 3.14);
      assertEquals("No property just a format with number 3.14000.",s);
    }
    { // Specifying non-existent resource file.
      final Localize alt = new Localize(LocalizeTest.class, "DoesNotExist");
      final String s = alt.format("A number %g.", 3.14);
      assertEquals("A number 3.14000.",s);
    }
    { // Specifying non-existent resource file.
      final Localize alt = new Localize(LocalizeTest.class);
      final String s = alt.format("Ignored number.", 3.14);
      assertEquals("Ignored number.",s);
    }
  }

  @Test
  public void testLocale() {
    assertFalse(Locale.getDefault().equals(Locale.FRANCE));
  }

  @Test
  public static void testLocalizeThrowable() {
    final IOException ioException = new IOException("ioe");
    assertEquals("ioe",Localize.getMessage(ioException));
    {
      final IllegalArgumentException e = new IllegalArgumentException(ioException);
      assertEquals("ioe",Localize.getMessage(e));
    }
    {
      final String better = "Bad argument: " + ioException.getLocalizedMessage();
      final IllegalArgumentException e = new IllegalArgumentException(better, ioException);
      assertEquals(better,(Localize.getMessage(e)));
    }
    {
      final IllegalArgumentException e = new IllegalArgumentException(null,ioException);
      assertEquals("ioe",Localize.getMessage(e));
    }
    {
      final IllegalArgumentException e = new IllegalArgumentException("foo",ioException);
      assertEquals("foo",Localize.getMessage(e));
    }
    {
      final IllegalArgumentException e = new IllegalArgumentException("foo",null);
      assertEquals("foo",Localize.getMessage(e));
    }
    {
      final IllegalArgumentException e = new IllegalArgumentException();
      assertEquals("java.lang.IllegalArgumentException",Localize.getMessage(e));
    }
    {
      final IllegalArgumentException e = new IllegalArgumentException(null,null);
      assertEquals("java.lang.IllegalArgumentException",Localize.getMessage(e));
    }
  }

  @Test
  public static void testLocalizeOld() throws Exception {
    {
      long seconds =(29L + 60*(9));
      String words = timeWords(seconds);
      assertEquals(words,"9 minutes 29 seconds");
    }
    {
      long seconds =(29L + 60*(10));
      String words = timeWords(seconds);
      assertEquals(words,"10 minutes");
    }
    {
      long seconds =(30L + 60*(10));
      String words = timeWords(seconds);
      assertEquals(words,"11 minutes");
    }
    {
      long seconds =(29L + 60*(29 + 60*(9)));
      String words = timeWords(seconds);
      assertEquals(words,"9 hours 29 minutes");
    }
    {
      long seconds =(30L + 60*(30 + 60*(9)));
      String words = timeWords(seconds);
      assertEquals(words,"9 hours 31 minutes");
    }
    {
      long seconds =(30L + 60*(30 + 60*(10)));
      String words = timeWords(seconds);
      assertEquals(words,"11 hours");
    }
    {
      long seconds =(30L + 60*(30 + 60*(11 +24*9)));
      String words = timeWords(seconds);
      assertEquals(words,"9 days 12 hours");
    }
    {
      long seconds =(30L + 60*(30 + 60*(11 +24*10)));
      String words = timeWords(seconds);
      assertEquals(words,"10 days");
    }
    {
      long seconds =(0L + 60*(0 + 60*(12 +24*10)));
      String words = timeWords(seconds);
      assertEquals(words,"11 days");
    }
    {
        // 2 hours.
      long seconds = 3600L * 2;
      String words = timeWords(seconds);
      assertEquals(words,"2 hours");
    }
    {
        // 1 second less than 2 hours.
      long seconds = 3600L * 2 - 1;
      String words = timeWords(seconds);
      assertEquals(words,"2 hours");
    }
    {
        // 2 days.
      long seconds = 3600L * 24 * 2;
      String words = timeWords(seconds);
      assertEquals(words,"2 days");
    }
    {
        // 1 second less than 2 days.
      long seconds = 3600L * 24 * 2 - 1;
      String words = timeWords(seconds);
      assertEquals(words,"2 days");
    }
  }
}

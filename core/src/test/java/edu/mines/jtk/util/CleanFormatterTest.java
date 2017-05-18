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

import java.util.logging.*;

import static org.testng.AssertJUnit.assertTrue;


/**
 * Tests {@link edu.mines.jtk.util.CleanFormatter}.
 */
public class CleanFormatterTest {

  /** Line separator */
  private static final String NL = System.getProperty("line.separator");

  @Test
  public void testFormatter() {
    CleanHandler.setDefaultHandler();
    Logger logger = Logger.getLogger("edu.mines.jtk.util.CleanFormatter");
    CleanFormatter cf = new CleanFormatter();
    String[] messages = new String[] {"one", "two", "three"};
    Level[] levels = new Level[] {Level.INFO, Level.WARNING, Level.SEVERE};
    String[] s = new String[3];
    for (int i=0; i<messages.length; ++i) {
      LogRecord lr = new LogRecord(levels[i], messages[i]);
      lr.setSourceClassName("Class");
      lr.setSourceMethodName("method");
      s[i] = cf.format(lr);
      assertTrue(s[i].endsWith(messages[i]+NL));
      logger.fine("|"+s[i]+"|");
    }
    assertTrue(s[0].equals("one"+NL));
    assertTrue(s[1].equals("WARNING: two"+NL));
    assertTrue(s[2].matches("^\\*\\*\\*\\* SEVERE WARNING \\*\\*\\*\\* "+
                        "\\(Class.method \\d+-\\d+ #.*\\)"+NL+
                        "SEVERE: three"+NL+"$"));
  }

  @Test
  public void testPrepend() {
    String lines = CleanFormatter.prependToLines("a","bbb"+NL+"ccc");
    assertTrue(lines.equals("abbb"+NL+"accc"));
  }
}

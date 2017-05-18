/****************************************************************************
Copyright 2006, Colorado School of Mines and others.
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

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;


/**
 * Tests {@link edu.mines.jtk.util.ArgsParser}.
 * @author Dave Hale, Colorado School of Mines
 * @author Chris Engelsma
 * @version 2017.05.15
 */
public class ArgsParserTest {

  @Test
  public void testBooleanConverter() throws Exception {
    String[] args = { "-atrue", "-bTrue", "-cfalse", "-dFalse", "-eFoo" };
    String shortOpts = "a:b:c:d:e:";
    ArgsParser ap = new ArgsParser(args,shortOpts);
    String[] values = ap.getValues();
    assertTrue(ap.toBoolean(values[0]));
    assertTrue(ap.toBoolean(values[1]));
    assertFalse(ap.toBoolean(values[2]));
    assertFalse(ap.toBoolean(values[3]));
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testBooleanConverterShouldThrowException() throws Exception {
    ArgsParser.toBoolean("true"); // Should work
    ArgsParser.toBoolean("Foo");  // Shouldn't.
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testDoubleConverterShouldThrowException() throws Exception {
    ArgsParser.toDouble("1.986"); // Should work
    ArgsParser.toDouble("Foo");   // Shouldn't.
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testFloatConverterShouldThrowException() throws Exception {
    ArgsParser.toFloat("1.986"); // Should work
    ArgsParser.toFloat("Foo");   // Shouldn't.
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testIntConverterShouldThrowException() throws Exception {
    ArgsParser.toInt("1986"); // Should work.
    ArgsParser.toInt("Foo");  // Shouldn't.
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testLongConverterShouldThrowException() throws Exception {
    ArgsParser.toLong("1986"); // Should work.
    ArgsParser.toLong("Foo");  // Shouldn't.
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testShortOptionNoValueThrowsException() throws Exception {
    String[] args = { "-a3.14", "-b" };
    String shortOpts = "a:d";
    ArgsParser ap = new ArgsParser(args,shortOpts);
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testDoLongOptionNoValueThrowsException() throws Exception {
    String[] args = { "--alpha" };
    String shortOpts = "a:";
    String[] longOpts = { "alpha=" };
    ArgsParser ap = new ArgsParser(args,shortOpts,longOpts);
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testDoLongOptionUnexpectedValueThrowsException() throws Exception {
    String[] args = { "--a=3.14" };
    String shortOpts = "a";
    String[] longOpts = { "alpha" };
    ArgsParser ap = new ArgsParser(args,shortOpts,longOpts);
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testDoShortOptionNoValueThrowsException() throws Exception {
    String[] args = { "-a" };
    String shortOpts = "a:";
    ArgsParser ap = new ArgsParser(args,shortOpts);
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testLongOptionNonUniquePrefixThrowsException() throws Exception {
    String[] args = { "--a=3.14", "--a=6.25" };
    String shortOpts = "a:a:";
    String[] longOpts = { "alpha=", "alpha=" };
    ArgsParser ap = new ArgsParser(args,shortOpts,longOpts);
  }

  @Test(expectedExceptions = ArgsParser.OptionException.class)
  public void testLongOptionNotRecognizedThrowsException() throws Exception {
    String[] args = { "--a=3.14", "--b=6.25" };
    String shortOpts = "a:d:";
    String[] longOpts = { "alpha=", "delta=" };
    ArgsParser ap = new ArgsParser(args,shortOpts,longOpts);
  }

  @Test
  public void testArgsParserConstructorShortOptsOnly() throws Exception {
    String[] args = { "-a3.14", "-b", "foo" };
    String shortOpts = "ha:b";
    ArgsParser ap = new ArgsParser(args,shortOpts);
    String[] options = ap.getOptions();
    String[] values = ap.getValues();
    String[] others = ap.getOtherArgs();
    assertArrayEquals(new String[] { "-a", "-b"}, options);
    assertArrayEquals(new String[] { "3.14", ""}, values);
    assertArrayEquals(new String[] { "foo" }, others);
  }

  @Test
  public void testSkipsMalformedStartingArgs() throws Exception {
    String[] args = { "--", "-", "-a3.14" };
    String shortOpts = "a:";
    ArgsParser ap = new ArgsParser(args, shortOpts);
  }

  @Test
  public void testArgsParser() {
    String[][] args = {
      {"-a3.14","-b","foo"},
      {"-a","3.14","-b","foo"},
      {"--alpha","3.14","--beta","foo"},
      {"--a=3.14","--b","foo"},
      {"--a=3.14","--b","foo"},
    };
    for (String[] arg:args) {
      float a = 0.0f;
      boolean b = false;
      try {
        String shortOpts = "ha:b";
        String[] longOpts = {"help","alpha=","beta"};
        ArgsParser ap = new ArgsParser(arg,shortOpts,longOpts);
        String[] opts = ap.getOptions();
        String[] vals = ap.getValues();
        for (int i=0; i<opts.length; ++i) {
          String opt = opts[i];
          String val = vals[i];
          if (opt.equals("-h") || opt.equals("--help")) {
            assertTrue(false);
          } else if (opt.equals("-a") || opt.equals("--alpha")) {
            a = ArgsParser.toFloat(val);
          } else if (opt.equals("-b") || opt.equals("--beta")) {
            b = true;
          }
        }
        String[] otherArgs = ap.getOtherArgs();
        assertTrue(otherArgs.length==1);
        assertTrue(otherArgs[0].equals("foo"));
        assertTrue(a==3.14f);
        assertTrue(b);
      } catch (ArgsParser.OptionException e) {
        assertTrue("no exceptions: e="+e.getMessage(),false);
      }
    }
  }
}

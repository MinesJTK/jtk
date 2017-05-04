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

import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import static edu.mines.jtk.ogl.Gl.*;

/**
 * Tests {@link edu.mines.jtk.sgl.ColorState}.
 * @author Chris Engelsma
 * @version 2017.05.04
 */
public class ColorStateTest extends TestCase {
  public static void main(String[] args) {
    TestSuite suite = new TestSuite(ColorStateTest.class);
    junit.textui.TestRunner.run(suite);
  }

  public void testDefaultConstructorParameters() {
    ColorState cs = new ColorState();
    Assert.assertFalse(cs.hasColor());
    Assert.assertEquals(new Color(1.0f,1.0f,1.0f,1.0f),cs.getColor());
    Assert.assertFalse(cs.hasShadeModel());
    Assert.assertEquals(GL_SMOOTH, cs.getShadeModel());
    Assert.assertEquals(0, cs.getAttributeBits());
  }

  public void testColorSetsAndUnsetsCorrectly() {
    ColorState cs = new ColorState();
    Color expected = randomColor();

    // Test setting color.
    cs.setColor(expected);
    Assert.assertEquals(expected,cs.getColor());
    Assert.assertTrue(cs.hasColor());
    Assert.assertEquals(GL_CURRENT_BIT, cs.getAttributeBits());

    // Test unsetting color.
    cs.unsetColor();
    Assert.assertEquals(new Color(1.0f,1.0f,1.0f,1.0f),cs.getColor());
    Assert.assertFalse(cs.hasColor());
    Assert.assertEquals(0, cs.getAttributeBits());
  }

  public void testShadeModelSetsAndUnsetsCorrectly() {
    ColorState cs = new ColorState();
    int expected = GL_FLAT;

    // Test setting shade model
    cs.setShadeModel(expected);
    Assert.assertEquals(cs.getShadeModel(), expected);
    Assert.assertTrue(cs.hasShadeModel());
    Assert.assertEquals(GL_LIGHTING_BIT, cs.getAttributeBits());

    // Test unsetting shade model
    cs.unsetShadeModel();
    Assert.assertEquals(cs.getShadeModel(), GL_SMOOTH);
    Assert.assertFalse(cs.hasShadeModel());
    Assert.assertEquals(0, cs.getAttributeBits());
  }

  public void testAppliesToGlState() {
    initGL();

    // Set up
    ColorState cs = new ColorState();
    Color color = randomColor();
    cs.setColor(color);
    cs.setShadeModel(GL_FLAT);
    cs.apply();

    // Test shade model is set appropriately
    int[] actualShadeModel = new int[1];
    glGetIntegerv(GL_SHADE_MODEL,actualShadeModel,0);
    Assert.assertEquals(GL_FLAT,actualShadeModel[0]);

    // Test color is set appropriately
    float[] c = new float[4];
    glGetFloatv(GL_CURRENT_COLOR,c,0);
    Color actualColor = new Color(c[0],c[1],c[2],c[3]);
    Assert.assertEquals(color, actualColor);

    tearDownGL();
  }

  private static Color randomColor() {
    Random random = new Random();
    return new Color(random.nextFloat(),
                     random.nextFloat(),
                     random.nextFloat(),
                     random.nextFloat());
  }

  private static void initGL() {
    _window = NewtFactory.createWindow(
      new GLCapabilities(GLProfile.getDefault()));
    assertNotNull(_window);
    _glWindow = GLWindow.create(_window);
    assertNotNull(_glWindow);
    _glWindow.setVisible(true);
    _glContext = _glWindow.getContext();
    assertNotNull(_glContext);
    _glContext.makeCurrent();
  }

  private static void tearDownGL() {
    _glContext.release();
    _glWindow.destroy();
    _window.destroy();

    _glContext = null;
    _glWindow = null;
    _window = null;

    assertNull(_glContext);
    assertNull(_glWindow);
    assertNull(_window);
  }

  private static GLContext _glContext;
  private static GLWindow _glWindow;
  private static com.jogamp.newt.Window _window;


}

package edu.mines.jtk.sgl;

import org.testng.annotations.Test;

import java.awt.*;
import java.util.Random;

import static org.testng.Assert.assertEquals;

/**
 * Tests {@link edu.mines.jtk.sgl.Annotation}.
 * @author Chris Engelsma
 * @version 2017.03.15
 */
public class AnnotationTest {

  @Test
  public void testConstructorsAllEqual() {
    Random r = new Random();
    int x = (int)(r.nextFloat()*100);
    int y = (int)(r.nextFloat()*100);
    int z = (int)(r.nextFloat()*100);
    Point3 p = new Point3(x,y,z);

    Annotation[] ans = new Annotation[4];
    ans[0] = new Annotation(x,y,z);
    ans[1] = new Annotation(p);
    ans[2] = new Annotation(x,y,z,"");
    ans[3] = new Annotation(p,"");

    for (int i=0; i<ans.length; ++i) {
      assertEquals(Color.WHITE,ans[i].getColor());
      assertEquals(p,ans[i].getLocation());
      assertEquals("",ans[i].getText());
      assertEquals(Annotation.Alignment.EAST,ans[i].getAlignment());
      assertEquals(
        new Font("SansSerif",Font.PLAIN,18),ans[i].getFont());
    }
  }

  @Test
  public void testParametersGetSet() {
    Random r = new Random();
    int x = (int)(r.nextFloat()*100);
    int y = (int)(r.nextFloat()*100);
    int z = (int)(r.nextFloat()*100);

    Annotation actual = new Annotation(0,0,0);
    actual.setLocation(x,y,z);
    actual.setColor(Color.RED);
    actual.setFont(new Font("Impact",Font.BOLD,24));
    actual.setText("Hello World");
    actual.setAlignment(Annotation.Alignment.NORTH);

    assertEquals(new Point3(x,y,z),actual.getLocation());
    assertEquals(Color.RED,actual.getColor());
    assertEquals(new Font("Impact",Font.BOLD,24),actual.getFont());
    assertEquals("Hello World", actual.getText());
    assertEquals(Annotation.Alignment.NORTH,actual.getAlignment());
  }

}

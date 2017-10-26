package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
  @Test
          public void testDistance() {

    Point p1 = new Point(1, 2);
    Point p2 = new Point(3, 4);
    Assert.assertEquals(p1.distance(p2), 2.8284271247461903);

    Point p3 = new Point(-1, 2);
    Point p4 = new Point(3, 4);
    Assert.assertEquals(p3.distance(p4), 4.47213595499958);

    Point p5 = new Point(0, 0);
    Point p6 = new Point(0, 0);
    Assert.assertEquals(p5.distance(p6), 0.0);
  }
}

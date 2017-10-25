package ru.stqa.pft.sandbox;

public class DistanceBetweenPoints {

  public static void main(String[] args) {
    Point p1 = new Point (4.0, 2.0);
    Point p2 = new Point (3.0, 4.0);
    double d = p1.distance(p2);
    System.out.println("Координаты точки p1: " + p1.x + ", " + p1.y);
    System.out.println("Координаты точки p2: " + p2.x + ", " + p2.y);
    System.out.println("Расстояние между точками = " + d);

  }

}
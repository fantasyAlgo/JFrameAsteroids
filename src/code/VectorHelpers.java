/**
 * VectorHelpers
 */
public class VectorHelpers {

  public static Point add(Point p1, Point p2){
    return new Point(p1.x + p2.x, p1.y + p2.y);
  }
  public static Point sub(Point p1, Point p2){
    return new Point(p1.x - p2.x, p1.y - p2.y);
  }
  public static Point div(Point p1, float d){
    return new Point(p1.x/d, p1.y/d);
  }
  public static Point mult(Point p1, float d){
    return new Point(p1.x*d, p1.y*d);
  }
  public static Point setMagnitude(Point p, float magn) {
    p.normalize();
    p = mult(p, magn);
    return p;
  }
  public static Point limit(Point p1, float limit){
    if (p1.magnitude() > limit){
      p1.normalize();
      p1 = mult(p1, limit);
    }
    return p1;
  }



}

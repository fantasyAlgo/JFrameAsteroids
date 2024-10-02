
/**
 * CollisionHandler
 */
public class CollisionHandler {
  // Here Point can be seen as a vector, so yeah Point = Vector and |-----.   | == |     .   |
  public static Point getProjectionMinMax(Point axis, Point[] points) {
    return new Point(0, 0);
  }

  public static Point getNormal(Point p1, Point p2) {
    return new Point(0, 0);
  }
  public static Point[] getAxis(Shape shape){
    int length = shape.baseVertices.length;
    Point[] axis = new Point[length];
    for (int i = 0; i < length;i++) {
      axis[i] = getNormal(shape.baseVertices[i], shape.baseVertices[i+1 >= length ? 0 : i+1]);
    }
    return axis;
  }
  public static float howMuchOverlap(Point p1, Point p2){
      if (p1.x <= p2.x && p2.x <= p1.y && p1.x <= p2.y && p2.y <= p1.y)
        return Math.abs(p2.y - p2.x);
      else if (p1.x <= p2.x && p2.x <= p1.y)
        return Math.min(Math.abs(p2.y - p1.x), Math.abs(p2.y - p1.y));
      else if (p1.x <= p2.y && p2.y <= p1.y)
        return Math.min(Math.abs(p2.y - p1.x), Math.abs(p2.y - p1.y));
      else if (p2.x <= p1.x && p1.x <= p2.y && p2.x <= p1.y && p1.y <= p2.y)
        return Math.abs(p1.y - p1.x);
      else return -1;
  }
  // Implementing the SAT algorithm
  public static float collisionSAT(Shape shape1, Shape shape2){
    Point[] axis1 = getAxis(shape1);
    Point[] axis2 = getAxis(shape2);
    Point p1;
    Point p2;
    float overlapValue;
    float overlapFinalValue = 10000.0f;
    for (Point axis: axis1) {
      p1 = getProjectionMinMax(axis, shape1.getPoints());
      p2 = getProjectionMinMax(axis, shape2.getPoints());
      overlapValue = howMuchOverlap(p1, p2);
      if (overlapValue == -1) return 10000.0f;
      else overlapFinalValue = Math.min(overlapValue, overlapFinalValue);
    }
    for (Point axis: axis2) {
      p1 = getProjectionMinMax(axis, shape1.getPoints());
      p2 = getProjectionMinMax(axis, shape2.getPoints());
      overlapValue = howMuchOverlap(p1, p2);
      if (overlapValue == -1) return 10000.0f;
      else overlapFinalValue = Math.min(overlapValue, overlapFinalValue);
    }
    return overlapFinalValue;
  }


}

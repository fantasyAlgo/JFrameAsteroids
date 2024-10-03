import java.util.Arrays;

/**
 * CollisionHandler
 */
public class CollisionHandler {
  // Here Point can be seen as a vector, so yeah Point = Vector and |-----.   | == |     .   |
  private static Point getProjectionMinMax(Point axis, Point[] points) {
    int length = points.length;
    float smallest = 10000.0f;
    float biggest = -10000.0f;
    float curr;
    for (int i = 0; i < length; i++){
      curr = axis.dot(points[i]);
      smallest = Math.min(curr, smallest);
      biggest = Math.max(curr, biggest);
    }
    return new Point(smallest, biggest);
  }

  private static Point getNormal(Point p1, Point p2) {
    Point diff = new Point(p2.x - p1.x, p2.y - p1.y);
    diff.normalize();
    return new Point(diff.y, -diff.x);
  }
  private static Point[] getAxis(Point[] vertices){
    int length = vertices.length;
    Point[] axis = new Point[length];
    for (int i = 0; i < length;i++) {
      axis[i] = getNormal(vertices[i], vertices[i+1 >= length ? 0 : i+1]);
    }
    return axis;
  }
  private static float howMuchOverlap(Point p1, Point p2){
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
  // Here i'm using Entity classes to identify the position and rotation of the objects
  public static float collisionSAT(Shape shape1, Shape shape2, Entity entity1, Entity entity2){
    Point[] vertices1 = shape1.getTransformedPoints(entity1);
    Point[] vertices2 = shape2.getTransformedPoints(entity2);

    Point[] axis1 = getAxis(vertices1);
    Point[] axis2 = getAxis(vertices2);
    /*System.out.println(Arrays.toString(axis1));
    System.out.println(Arrays.toString(axis2));
    System.out.println("#################");*/
    Point p1;
    Point p2;
    float overlapValue;
    float overlapFinalValue = 10000.0f;
    for (Point axis: axis1) {
      p1 = getProjectionMinMax(axis, vertices1);
      p2 = getProjectionMinMax(axis, vertices2);
      overlapValue = howMuchOverlap(p1, p2);
      if (overlapValue == -1) return 10000.0f;
      else overlapFinalValue = Math.min(overlapValue, overlapFinalValue);
    }
    for (Point axis: axis2) {
      p1 = getProjectionMinMax(axis, vertices1);
      p2 = getProjectionMinMax(axis, vertices2);
      overlapValue = howMuchOverlap(p1, p2);
      if (overlapValue == -1) return 10000.0f;
      else overlapFinalValue = Math.min(overlapValue, overlapFinalValue);
    }
    return overlapFinalValue;
  }


}

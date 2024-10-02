import com.sun.tools.javac.util.Pair;

/**
 * CollisionHandler
 */
public class CollisionHandler {
  // Here Point can be seen as a vector, so yeah Point = Vector and |-----.   | == |     .   |
  //

  //public static Pair<float, float> getProjectionMinMax(Point[] axis, ) {}

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
  // Implementing the SAT algorithm
  public static boolean collisionSAT(Shape shape1, Shape shape2){
    Point[] axis1 = getAxis(shape1);
    Point[] axis2 = getAxis(shape2);
    int axLength = axis1.length;
    for (int i = 0; i < axLength; i++) {
      
    }
    return false;
  }


}

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


/**
 * Helpers
 */
public class Helpers {
  static int[] rectVerticesX = {0, 1};
  static int[] rectVerticesY = {0, 1};
  public static boolean isInside(int WIDTH, int HEIGHT, int x, int y){
    return !(x <= 0 || y <= 0 || x >= WIDTH || y >= HEIGHT);
  }
  public static float linearFunction(float s, float x, Point pos){
    return s*x + (pos.y - s*pos.x);
  }
  public static boolean isInRange(float val, float constraint, float error){
    return val > constraint-error && val < constraint+error;
  }

  private static int ccw(Point a, Point b, Point c) {
		/*
		 * determinant (the cross product) calculates the signed area of parallelogram
		 */
		float area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
		if (area < 0) return -1; // clockwise
		if (area > 0) return 1; // counter-clockwise
		return 0; // collinear
	}

  private static int compareByAngle(Point ref, Point b, Point c){
    /*
     * the ref point should always be pushed to the beginning
     */
    if (b == ref) return -1;
    if (c == ref) return 1;
    
    int ccw = ccw(ref, b, c);
    
    if (ccw == 0) {
      /*
       * Handle collinear points. We can just use the x coordinate and not 
       * bother with the y since the ratio of y/x is going to be the same
       */
      if (Float.compare(b.x, c.x) == 0) {
        /*
         * rare case of floats matching up in a vertical line, we want 
         * the closer point to be first
         */
        return b.y < c.y ? -1 : 1;				
      } else {
        return b.x < c.x ? -1 : 1;				
      }				
    } else {
      return ccw * -1;
    }
  }
  private static Point getMinY(Point[] vertices){
    int size = vertices.length;
    Point lowest = new Point(0, Integer.MAX_VALUE);
    for (int i = 0; i < size;i++) if (vertices[i].y < lowest.y) lowest = vertices[i];
    return lowest;
  }
  public static Point[] convexhull(Point[] vertices ){
    Stack<Point> stack = new Stack<>();

    int size = vertices.length;
    Point[] verticesSorted = Arrays.copyOf(vertices, size);
    Point minPoint = getMinY(vertices);
    Arrays.sort(verticesSorted, (p1, p2) -> compareByAngle(minPoint, p1, p2));
    stack.push(minPoint);
    stack.push(verticesSorted[1]);
    for (int i = 2; i < size; i++) {
      Point next = verticesSorted[i];
			Point p = stack.pop();			
			
			while (stack.peek() != null && ccw(stack.peek(), p, next) <= 0) { 
				p = stack.pop(); // delete points that create clockwise turn
			}
			stack.push(p);
			stack.push(verticesSorted[i]);
    }
    return stack.toArray(new Point[stack.size()]);
  }

}

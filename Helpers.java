
/**
 * Helpers
 */
public class Helpers {
  static int[] rectVerticesX = {0, 1};
  static int[] rectVerticesY = {0, 1};
  public static boolean isInside(int WIDTH, int HEIGHT, int x, int y){
    return !(x <= 0 || y <= 0 || x >= WIDTH || y >= HEIGHT);
  }
  public boolean intersect(Shape shape1, Shape shape2){
    return false;
  }
}

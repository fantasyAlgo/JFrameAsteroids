/**
 * Helpers
 */
public class Helpers {
  static int[] rectVerticesX = {0, 1};
  static int[] rectVerticesY = {0, 1};
  public static boolean isInside(int WIDTH, int HEIGHT, int x, int y){
    return x < 0 || y < 0 || x > WIDTH || y > HEIGHT;
  }
  public static makeRectangle(int sizeX, int sizeY, int angle){

  }
  public static Shape rotatedVertices(Shape shape, float angle){
    double x, y;
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    float[] verticesX = new float[shape.size];
    float[] verticesY = new float[shape.size];
    for (int i = 0; i < shape.size; i++){
      x = shape.centerX - shape.verticesX[i];
      y = shape.centerY - shape.verticesY[i];
      verticesX[i] = (int) (-(x*cos - y*sin) + shape.centerX);
      verticesY[i] = (int) (-(x*sin + y*cos) + shape.centerY);
    }
    Shape newShape = new Shape(shape.centerX, shape.centerY, verticesX, verticesY);
    return newShape;
  }
}

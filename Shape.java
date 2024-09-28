/**
 * Shape
 */
public class Shape {
  float[] verticesX;
  float[] verticesY;
  int size;
  int centerX;
  int centerY;
  public Shape(int centerX, int centerY, float[] verticesX, float[] verticesY){
    this.verticesX = verticesX;
    this.verticesY = verticesY;
    this.centerX = centerX;
    this.centerY = centerY;
    this.size = verticesY.length;
  }
  // Make it!
  public void Draw(int posX, int posY, int angle){
  }
}

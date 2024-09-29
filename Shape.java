import java.awt.Graphics2D;

/**
 * Shape
 */
public class Shape {
  float[] baseVerticesX;
  float[] baseVerticesY;
  int size;
  double centerX;
  double centerY;
  int[] verticesX;
  int[] verticesY;
  public Shape(int centerX, int centerY, float[] verticesX, float[] verticesY){
    this.baseVerticesX = verticesX;
    this.baseVerticesY = verticesY;
    this.centerX = centerX;
    this.centerY = centerY;
    this.size = verticesY.length;

    this.verticesX = new int[this.size];
    this.verticesY = new int[this.size];
  }
  // Make it!
  public void Draw(Graphics2D g2d, int posX, int posY, float angle){
    double x, y;
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    
    for (int i = 0; i < size; i++){
      x = this.centerX - this.baseVerticesX[i];
      y = this.centerY - this.baseVerticesY[i];
      this.verticesX[i] = (int) (-(x*cos - y*sin) + this.centerX);
      this.verticesY[i] = (int) (-(x*sin + y*cos) + this.centerY);
    }
    for (int i = 0; i < size; i++) {
      this.verticesX[i] += posX;
      this.verticesY[i] += posY;
    }
    g2d.drawPolygon(this.verticesX, this.verticesY, size);
  }
}

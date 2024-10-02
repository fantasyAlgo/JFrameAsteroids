import java.awt.Graphics2D;

/**
 * Shape
 */
public class Shape {
  Point[] baseVertices;
  int size;
  double centerX;
  double centerY;
  int[] verticesX;
  int[] verticesY;
  public Shape(int centerX, int centerY, Point[] vertices){
    this.baseVertices = vertices;
    this.centerX = centerX;
    this.centerY = centerY;
    this.size = vertices.length;

    this.verticesX = new int[this.size];
    this.verticesY = new int[this.size];
  }
  // Make it!
  public void Draw(Graphics2D g2d, int posX, int posY, float angle){
    double x, y;
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    
    for (int i = 0; i < size; i++){
      x = this.centerX - this.baseVertices[i].x;
      y = this.centerY - this.baseVertices[i].y;
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

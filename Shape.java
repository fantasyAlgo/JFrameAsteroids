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
  public Point[] getPoints(){
    return this.baseVertices;
  }
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
  public Point[] getTransformedPoints(Entity entity){
    double x, y;
    double cos = Math.cos(entity.angle);
    double sin = Math.sin(entity.angle);
    Point[] vertices = new Point[this.size];
    for (int i = 0; i < this.size; i++){
      x = this.centerX - this.baseVertices[i].x;
      y = this.centerY - this.baseVertices[i].y;
      vertices[i] = new Point((float)(-(x*cos - y*sin) + this.centerX), (float)(-(x*sin + y*cos) + this.centerY));
    }
    for (int i = 0; i < this.size; i++) {
      vertices[i].x += entity.x;
      vertices[i].y += entity.y;
    }
    return vertices;
  }


}

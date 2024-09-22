import java.awt.Graphics2D;

/**
 * PlayerHandler
 */
public class PlayerHandler {
  final int[] baseVerticesX = {10, 20, 30};
  final int[] baseVerticesY = {50, 25, 50};
  final int NVertices = 3;
  int[] verticesX = {baseVerticesX[0], baseVerticesX[1], baseVerticesX[2]};
  int[] verticesY = {baseVerticesY[0], baseVerticesY[1], baseVerticesY[2]};
  double[] verticesXD = new double[3];
  double[] verticesYD = new double[3];
  int pos_x = 0;
  int pos_y = 0;
  float angle = 0.0f;
  float current_angle = 0.0f;

  int speed;
  int size;

  public PlayerHandler(int speed, int size){
    this.speed = speed;
    this.size = size;
    this.Move(50, 50);
    this.applyRotation();
  }
  public void Draw(Graphics2D g2d){
    g2d.drawPolygon(verticesX, verticesY, NVertices);
  }
  public void Move(int pos_x, int pos_y){
    this.pos_x += pos_x;
    this.pos_y += pos_x;
    for (int i = 0; i < NVertices; i++) {
      verticesX[i] += pos_x;
      verticesY[i] += pos_y;
    }
  }
  public void Transform(int mov_x, int mov_y, float angle){
    float rot = angle - this.current_angle;
    if (rot == 0) return;
    this.current_angle = angle;
    double centerX = this.baseVerticesX[1];
    double centerY = this.baseVerticesY[1];
    double x, y;
    double cos = Math.cos(this.current_angle);
    double sin = Math.sin(this.current_angle);
    //System.out.println(this.current_angle + " | " +  Math.floor(cos * 100) / 100 + " | " +  Math.floor(sin* 100) / 100 + " | " +  this.baseVerticesY[0]);
    for (int i = 0; i < NVertices; i++){
      x = centerX - this.baseVerticesX[i];
      y = centerY - this.baseVerticesY[i];
      this.verticesX[i] = (int) (-(x*cos - y*sin) + centerX);
      this.verticesY[i] = (int) (-(x*sin + y*cos) + centerY);
    }
    this.pos_x += mov_x;
    this.pos_y += mov_y;
    for (int i = 0; i < NVertices; i++) {
      this.verticesX[i] += this.pos_x;
      this.verticesY[i] += this.pos_y;
    }
  }
  public void applyRotation(){
    this.Transform(0, 0, this.angle);
  }
  public void React(boolean leftPressed, boolean rightPressed, boolean upPressed, boolean spacePressed){
    int mov_x = 0;
    int mov_y = 0;
    if (rightPressed) this.angle += 0.01f;
    if (leftPressed) this.angle -= 0.01f;
    if (upPressed){
      mov_x = (int) (10.0*Math.cos(this.angle));
      mov_y = (int) (10.0*Math.sin(this.angle));
    }
    Transform(mov_x, mov_y, this.angle);
  }


  public void setPos_x(int pos_x) {
    this.pos_x = pos_x;
  }
  public void setPos_y(int pos_y) {
    this.pos_y = pos_y;
  }
  public int getPos_x() {
    return pos_x;
  }
  public int getPos_y() {
    return pos_y;
  }

}

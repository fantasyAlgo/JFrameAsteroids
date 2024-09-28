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

  float acc_x = 0;
  float acc_y = 0;

  float current_angle = 0.0f;

  float speed;
  int size;

  int windowWidth;
  int windowHeight;

  BallotsHandler ballotsHandler;

  public PlayerHandler(int windowWidth, int windowHeight, float speed, int size){
    this.speed = speed;
    this.size = size;
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;

    this.Move(50, 50);
    this.applyRotation();

    ballotsHandler = new BallotsHandler(10, this);
  }
  public void Draw(Graphics2D g2d){
    g2d.drawPolygon(verticesX, verticesY, NVertices);
  }
  public  void DrawBullets(Graphics2D g2d){

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
    //if (rot == 0) return;
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
    if (rightPressed) this.angle += 0.02f;
    if (leftPressed) this.angle -= 0.02f;
    if (upPressed){
      acc_x += this.speed*Math.sin(this.angle);
      acc_y += -(this.speed*Math.cos(this.angle));
    }
    acc_x *= 0.95;
    acc_y *= 0.95;
    if (this.pos_x + acc_x > this.windowWidth) this.pos_x = 0;
    if (this.pos_x + acc_x < 0) this.pos_x = this.windowWidth;
    if (this.pos_y + acc_y > this.windowHeight) this.pos_y = 0;
    if (this.pos_y + acc_y < 0) this.pos_y = this.windowHeight;

    if (spacePressed){
      ballotsHandler.add_bullet(x, y, Math.cos(angle), Math.sin(angle));
    }

    Transform((int) acc_x, (int) acc_y, this.angle);
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

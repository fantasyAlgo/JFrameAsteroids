import java.awt.Graphics2D;

/**
 * PlayerHandler
 */
public class PlayerHandler {
  Shape shape = new Shape(20, 25, new float[]{10, 20, 30}, new float[]{50, 25, 50});

  int pos_x = 0;
  int pos_y = 0;
  float angle = 0.0f;
  float current_angle = 0.0f;

  float acc_x = 0;
  float acc_y = 0;

  float speed;
  int size;

  int windowWidth;
  int windowHeight;

  BallotsHandler ballotsHandler;

  boolean isSpacePressed = false;

  public PlayerHandler(int windowWidth, int windowHeight, float speed, int size){
    this.speed = speed;
    this.size = size;
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;

    this.Move(50, 50);
    this.applyRotation();

    ballotsHandler = new BallotsHandler(1000, this.windowWidth, this.windowHeight);
  }
  public void Draw(Graphics2D g2d){ 
    shape.Draw(g2d, this.pos_x, this.pos_y, this.current_angle);
  }
  public  void DrawBullets(Graphics2D g2d){
    ballotsHandler.Draw(g2d);
  }
  public void Move(int pos_x, int pos_y){
    this.pos_x += pos_x;
    this.pos_y += pos_x;
  }
  public void applyRotation(){
    this.current_angle = angle;
  }
  public void Transform(int acc_x, int acc_y, float angle){
    this.pos_x += acc_x;
    this.pos_y += acc_y;
    applyRotation();
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

    Transform((int) acc_x, (int) acc_y, this.angle);
    if (spacePressed && !isSpacePressed){
      ballotsHandler.add_bullet((int) this.shape.centerX + this.pos_x, (int) this.shape.centerY + this.pos_y, this.current_angle);
      isSpacePressed = true;
    } else if (!spacePressed) isSpacePressed = false;
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

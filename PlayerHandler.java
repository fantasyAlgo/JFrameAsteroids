import java.awt.Graphics2D;


/**
 * PlayerHandler
 */
public class PlayerHandler extends Entity {
  Shape shape = new Shape(20, 25, new Point[]{
    new Point(10, 50),
    new Point(20, 25),
    new Point(30, 50),
  });
  Shape rayShape = new Shape(20, 25, new Point[]{
    new Point(13, 50),
    new Point(20, 50),
    new Point(27, 50)
  });
  boolean isAlive = true;

  float acc_x = 0;
  float acc_y = 0;

  float speed;
  int size;

  int windowWidth;
  int windowHeight;

  BallotsHandler ballotsHandler;

  boolean isSpacePressed = false;

  public PlayerHandler(int windowWidth, int windowHeight, float speed, int size){
    super(windowWidth/2, windowHeight/2);
    this.speed = speed;
    this.size = size;
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;
    this.isAlive = true;

    this.angle = 0;

    ballotsHandler = new BallotsHandler(4, this.windowWidth, this.windowHeight);
  }
  public void kill(){
    this.setCoord(10000, 10000);
    this.isAlive = false;
  }
  public void Draw(Graphics2D g2d){ 
    if (!this.isAlive) return;
    shape.Draw(g2d, (int)this.x, (int)this.y, this.angle);
    float magnitude = Math.min((float)Math.sqrt(this.acc_x*this.acc_x+ this.acc_y*this.acc_y)*5.0f, 15.0f);

    rayShape.setPoint(1, new Point(rayShape.getPoints()[1].x, rayShape.getPoints()[1].y + magnitude));
    rayShape.Draw(g2d, (int)this.x, (int)this.y, this.angle);
    rayShape.setPoint(1, new Point(rayShape.getPoints()[1].x, rayShape.getPoints()[1].y - magnitude));
  }
  public  void DrawBullets(Graphics2D g2d){
    ballotsHandler.Draw(g2d);
  }
  public void Transform(float acc_x, float acc_y){
    this.x += acc_x;
    this.y += acc_y;
  }
  public void React(boolean leftPressed, boolean rightPressed, boolean upPressed, boolean spacePressed){
    if (!this.isAlive) return;
    if (rightPressed) this.angle += 0.035f;
    if (leftPressed) this.angle -= 0.035f;
    if (upPressed){
      acc_x += this.speed*Math.sin(this.angle);
      acc_y += -(this.speed*Math.cos(this.angle));
    }
    acc_x *= 0.97;
    acc_y *= 0.97;
    if (this.x+ acc_x > this.windowWidth) this.x= 0;
    if (this.x + acc_x < 0) this.x = this.windowWidth;
    if (this.y + acc_y > this.windowHeight) this.y = 0;
    if (this.y + acc_y < 0) this.y = this.windowHeight;

    Transform(acc_x, acc_y);
    if (spacePressed && !isSpacePressed){
      ballotsHandler.add_bullet((int) (this.shape.centerX + this.x), (int) (this.shape.centerY + this.y), this.angle);
      isSpacePressed = true;
    } else if (!spacePressed) isSpacePressed = false;
  }
  public void reset(){
    this.isAlive = true;
    this.setCoord(this.windowWidth/2, this.windowHeight/2);
    this.setDirection(0, 0F, 1F);
  }

}




/**
 * Entity
 */
public class Entity {
  float x;
  float y;
  float angle;
  float dirX;
  float dirY;
  public Entity(float x, float y){
    this.x = x;
    this.y = y;
  }
  public void setCoord(float x, float y){
    this.x = x;
    this.y = y;
  }

  public void setDirection(float angle, float dirX, float dirY){
    this.angle = angle;
    this.dirX = dirX;
    this.dirY = dirY;
  }

  public void update(){
    this.x += this.dirX;
    this.y += this.dirY;
  }

}

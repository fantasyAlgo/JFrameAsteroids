/**
 * Ballot
 */
public class Ballot {
  int x;
  int y;
  float dirX;
  float dirY;
  boolean active;

  Shape bulletShape; 
  public Ballot(int x, int y, float angle){
    this.x = x;
    this.y = y;
    bulletShape = new Shape();
  }
  public void setCoord(int x, int y){
    this.x = x;
    this.y = y;
  }
  public void setDirection(float dirX, float dirY){
    this.dirX = dirX;
    this.dirY = dirY;
  }
  public void update(){
    this.x += this.dirX;
    this.y += this.dirY;
  }
}

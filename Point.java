/**
 * Point
 */
public class Point {
  float x = 0;
  float y = 0;
  public Point(float x, float y){
    this.x = x;
    this.y = y;
  }
  @Override
  public String toString(){
    return "{" + this.x + ", " + this.y + "}";
  }
}

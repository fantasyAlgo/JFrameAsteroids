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

  // Vector style functions
  public void normalize(){
    float magnitude = (float) Math.sqrt(x*x + y*y);
    this.x /= magnitude;
    this.y /= magnitude;
  }
  public float dot(Point p){
    return x*p.x + y*p.y;
  }
  @Override
  public String toString(){
    return "{" + this.x + ", " + this.y + "}";
  }
}

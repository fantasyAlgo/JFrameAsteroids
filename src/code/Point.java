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
  public float distance(Point p2){
    return (float)Math.sqrt((p2.x - x)*(p2.x-x) + (p2.y - y)*(p2.y - y));
  }
  public float magnitude() {
    return (float) Math.sqrt(x*x + y*y);
  }
  public void normalize(){
    float magnitude = this.magnitude();
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


/**
 * Alien
 */
public class Alien extends Entity{
  final int MAX_BALLOTS = 2;
  final int perception_rad = 100;
  final float MAX_FORCE = 1f;
  final float MAGNITUDE = 0.3f;
  BallotsHandler ballotHandler;
  boolean active;
  float force_x = 0;
  float force_y = 0;
  public Alien(){
    super(0, 0);
    active = false;
    ballotHandler = new BallotsHandler(MAX_BALLOTS, GamePanel.screenWidth, GamePanel.screenHeight); }
  public void activate(){
    this.active = true;
    this.setCoord(GamePanel.screenWidth/2 - 100, GamePanel.screenHeight/2);
    //this.setCoord((Math.random() > .5 ? 0 : (float)GamePanel.screenHeight), (float)Math.random()*GamePanel.HEIGHT);
  }
  public Point getDisplacementForce(Asteroid[] asteroids, Entity player){
    Point pos = new Point(x, y);
    Point base_dir = new Point(0, 0);
    Point diff;
    float d;
    int howMany = 0;
    for (Asteroid asteroid : asteroids) {
      if (asteroid == null) break;
      d = pos.distance(new Point(asteroid.x, asteroid.y));
      if (d > perception_rad) continue;
      diff = VectorHelpers.sub(pos, new Point(asteroid.x, asteroid.y));
      diff = VectorHelpers.div(diff, d);
      base_dir = VectorHelpers.add(base_dir, diff);
      howMany += 1;
    }
    d = pos.distance(new Point(player.x, player.y));
    if (d < perception_rad){
      diff = VectorHelpers.sub(pos, new Point(player.x, player.y));
      diff = VectorHelpers.div(diff, d);
      base_dir = VectorHelpers.add(base_dir, diff);
      howMany += 1;
    }

    if (howMany > 0){
      base_dir = VectorHelpers.div(base_dir, (float)howMany);
      base_dir = VectorHelpers.setMagnitude(base_dir, MAGNITUDE);
      base_dir = VectorHelpers.sub(base_dir, new Point(this.dirX, this.dirY));
      base_dir = VectorHelpers.limit(base_dir, MAX_FORCE);
    }
    return base_dir;

  }
  public void update(Asteroid[] asteroids, Entity player){
    Point force = this.getDisplacementForce(asteroids, player);
    //System.out.println(force.x + " " + force.y);
    this.dirX += force.x;
    this.dirY += force.y;
    super.update();
    if (this.x > GamePanel.screenWidth) this.x = 0;
    if (this.x < 0) this.x = GamePanel.screenWidth;
    if (this.y > GamePanel.screenHeight) this.y = 0;
    if (this.y < 0) this.y = GamePanel.screenHeight;
  }
}

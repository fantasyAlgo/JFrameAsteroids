import java.awt.Graphics2D;

/**
 * AsteroidsHandler
 */
public class AsteroidsHandler {
  final int MAX_ASTEROIDS = 10;
  Asteroid[] asteroids = new Asteroid[MAX_ASTEROIDS];
  int WIDTH_WINDOW;
  int HEIGHT_WINDOW;

  public AsteroidsHandler(int WIDTH, int HEIGHT){
    this.WIDTH_WINDOW = WIDTH;
    this.HEIGHT_WINDOW = HEIGHT;
    for (int i = 0; i < MAX_ASTEROIDS; i++) 
      asteroids[i] = new Asteroid(0,0);
  }

  public boolean add_asteroid(float size, Point coord){
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      if (!asteroids[i].active){
        asteroids[i].make_shape(size);
        asteroids[i].setCoord(coord.x, coord.y);
        asteroids[i].setDirection(0, (float)(1 - Math.random()*2)/2.0f, (float)Math.random());
        asteroids[i].active = true;
        return true;
      }
    }
    return false;
  }
  public boolean add_asteroid(float size){ return this.add_asteroid(size, new Point((float)Math.random()*this.WIDTH_WINDOW, 0));}
  public boolean splitAsteroid(Asteroid asteroid){
    boolean ast1 = this.add_asteroid(asteroid.size_shape/2.0f, new Point(asteroid.x, asteroid.y));
    boolean ast2 = this.add_asteroid(asteroid.size_shape/2.0f, new Point(asteroid.x, asteroid.y));
    return ast1 || ast2;
  }
  public void update(){
    //boolean addAsteroid = Math.random() > 0.1f;
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      //asteroids[i].active = Helpers.isInside(WIDTH_WINDOW, HEIGHT_WINDOW, (int) asteroids[i].x, (int) asteroids[i].y) || asteroids[i].hasEndedSpawn();
      if (asteroids[i].active){
        asteroids[i].update(WIDTH_WINDOW, HEIGHT_WINDOW);
      }
    }
    //if (addAsteroid) this.add_asteroid(Math.random())
  }

  public void Draw(Graphics2D g2d){
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      if (asteroids[i].active){
        asteroids[i].Draw(g2d);
      }
    }
  }

  public int active_asteroids() {
    int active_ast = 0;
    for (Asteroid asteroid : asteroids) 
      if (asteroid.active) active_ast++;
    return active_ast;
  }

}

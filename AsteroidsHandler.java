import java.awt.Graphics2D;

/**
 * AsteroidsHandler
 */
public class AsteroidsHandler {
  final int MAX_ASTEROIDS = 5;
  Asteroid[] asteroids = new Asteroid[MAX_ASTEROIDS];
  int WIDTH_WINDOW;
  int HEIGHT_WINDOW;

  public AsteroidsHandler(int WIDTH, int HEIGHT){
    this.WIDTH_WINDOW = WIDTH;
    this.HEIGHT_WINDOW = HEIGHT;
    for (int i = 0; i < MAX_ASTEROIDS; i++) 
      asteroids[i] = new Asteroid(0,0);
  }

  public boolean add_asteroid(float size){
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      if (!asteroids[i].active){
        asteroids[i].make_shape(size);
        asteroids[i].setCoord((float)(Math.random()*this.WIDTH_WINDOW), 0);
        asteroids[i].setDirection(0, (float)(1 - Math.random()*2), (float)Math.random()*2.0f);
        asteroids[i].active = true;
        return true;
      }
    }
    return false;
  }
  public void splitAsteroid(int index){}
  public void update(){
    //boolean addAsteroid = Math.random() > 0.1f;
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      asteroids[i].active = Helpers.isInside(WIDTH_WINDOW, HEIGHT_WINDOW, (int) asteroids[i].x, (int) asteroids[i].y) || asteroids[i].hasEndedSpawn();
      if (asteroids[i].active){
        asteroids[i].update();
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

}

import java.awt.Graphics2D;

/**
 * AsteroidsHandler
 */
public class AsteroidsHandler {
  final int MAX_ASTEROIDS = 20;
  Asteroid[] asteroids = new Asteroid[MAX_ASTEROIDS];
  int WIDTH_WINDOW;
  int HEIGHT_WINDOW;

  public AsteroidsHandler(int WIDTH, int HEIGHT){
    for (int i = 0; i < MAX_ASTEROIDS; i++) 
      asteroids[i] = new Asteroid(0,0);
  }

  public boolean add_asteroid(float size){ for (int i = 0; i < MAX_ASTEROIDS; i++) {
      if (!asteroids[i].active){
        asteroids[i].make_shape((float)Math.random()*50.0f);
        asteroids[i].active = true;
        return true;
      }
    }
    return false;
  }
  public void splitAsteroid(int index){}
  public void update(){
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      if (!asteroids[i].active){
        asteroids[i].update();
      }
    }
  }

  public void Draw(Graphics2D g2d){
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      if (!asteroids[i].active){
        asteroids[i].Draw(g2d);
      }
    }
  }



}

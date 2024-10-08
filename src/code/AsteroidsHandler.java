import java.awt.Graphics2D;

/**
 * AsteroidsHandler
 */
public class AsteroidsHandler {
  int MAX_ASTEROIDS = 10;
  float max_asteroid_cont = 10;
  float speedAsteroids = 1.0f;
  Asteroid[] asteroids = new Asteroid[100];
  int WIDTH_WINDOW;
  int HEIGHT_WINDOW;
  float peaceTime = -1;

  public AsteroidsHandler(int WIDTH, int HEIGHT){
    this.WIDTH_WINDOW = WIDTH;
    this.HEIGHT_WINDOW = HEIGHT;
    for (int i = 0; i < MAX_ASTEROIDS; i++) 
      asteroids[i] = new Asteroid(0,0);
  }

  public boolean add_asteroid(float size, Point coord, Point dir){
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      if (!asteroids[i].active){
        asteroids[i].make_shape(size);
        asteroids[i].setCoord(coord.x, coord.y);
        asteroids[i].setDirection(0, dir.x, dir.y);
        asteroids[i].active = true;
        return true;
      }
    }
    return false;
  }
  public boolean add_asteroid(float size, Point coord){
    float speed = 15.0f/size;
    return this.add_asteroid(size, coord, new Point(
      speed*speedAsteroids*(float)(1 - Math.random()*2)/2.0f,
      speed*speedAsteroids*(float)(1 - 2*Math.random())));
  }
  public boolean add_asteroid(float size){ return this.add_asteroid(size, new Point(
                        (float)Math.random()*this.WIDTH_WINDOW, 
                        Math.random() > 0.5 ? 0 : this.HEIGHT_WINDOW));}

  public boolean splitAsteroid(Asteroid asteroid){
    boolean ast1 = this.add_asteroid(asteroid.size_shape/2.0f, new Point(asteroid.x - asteroid.size_shape/2, asteroid.y));
    boolean ast2 = this.add_asteroid(asteroid.size_shape/2.0f, new Point(asteroid.x + asteroid.size_shape/2, asteroid.y));
    boolean ast3 = false;
    if (asteroid.size_shape > 50){
      ast3 = this.add_asteroid(asteroid.size_shape/2.0f, new Point(asteroid.x, asteroid.y), new Point(asteroid.dirX*0.97f, asteroid.dirY*0.97f));
    }
    return ast1 || ast2 || ast3;
  }


  public void update(){
    //boolean addAsteroid = Math.random() > 0.1f;
    peaceTime -= 0.01;
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      //asteroids[i].active = Helpers.isInside(WIDTH_WINDOW, HEIGHT_WINDOW, (int) asteroids[i].x, (int) asteroids[i].y) || asteroids[i].hasEndedSpawn();
      if (asteroids[i].active){
        asteroids[i].update(WIDTH_WINDOW, HEIGHT_WINDOW);
      }
    }
    if (peaceTime <= 0){
      speedAsteroids += 0.00001;
      max_asteroid_cont += 0.00025;
    }
    if ((int) max_asteroid_cont != MAX_ASTEROIDS && MAX_ASTEROIDS+1 < 100){
      asteroids[MAX_ASTEROIDS] = new Asteroid(100000, 100000);
      MAX_ASTEROIDS = (int) max_asteroid_cont;
      if (MAX_ASTEROIDS != 10 && MAX_ASTEROIDS%2 == 0){
        peaceTime = 20.0f;
      }
      if (MAX_ASTEROIDS != 10 && MAX_ASTEROIDS%2 == 1){
        peaceTime = 5.0f;
      }
      System.out.println("MAX_ASTEROIDS changed! now: " + MAX_ASTEROIDS);
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
  public boolean canSpawn(){
    if (peaceTime > 0)
      return false;
    return Math.random() < 0.005 && this.active_asteroids() < this.MAX_ASTEROIDS/2.0;
  }
  public int active_asteroids() {
    int active_ast = 0;
    for (Asteroid asteroid : asteroids){
      if (asteroid == null) break;
      if (asteroid.active) active_ast++;
    }
    return active_ast;
  }
  public void reset(){
    for (int i = 0; i < MAX_ASTEROIDS; i++) {
      asteroids[i].active = false;
    }
    peaceTime = 0;
    speedAsteroids = 1;
    MAX_ASTEROIDS = 10;
    max_asteroid_cont = 10;
  }

}

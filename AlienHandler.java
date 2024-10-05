import java.awt.Graphics2D;

/**
 * AlienHandler
 */
public class AlienHandler {
  final int MAX_ALIENS = 2;
  float size;
  Alien[] aliens;
  Shape shape = new Shape(0, 0, new Point[] {
      // UFO Disc
      new Point(-4, 0),    // Left edge of the disc
      new Point(-2, 1),    // Left top of the disc
      new Point(2, 1),     // Right top of the disc
      new Point(4, 0),     // Right edge of the disc
      new Point(2, -1),    // Right bottom of the disc
      new Point(-2, -1),   // Left bottom of the disc
      new Point(-4, 0),    // Back to Left edge of the disc
      
      new Point(-2, 1),    // Left base of the dome
      new Point(0, 3),     // Top of the dome
      new Point(2, 1),     // Right base of the dome
  });
  public AlienHandler(float size){
    aliens = new Alien[MAX_ALIENS];
    this.size = size;
    for (int i = 0; i < MAX_ALIENS; i++) {
      aliens[i] = new Alien();
    }
    shape.scale(size);
  }
  public void add(){
    for (Alien alien : aliens) {
      if (aliens == null) break;
      if (!alien.active){
        alien.activate();
        System.err.println(alien.x + " " + alien.y);
        break;
      }
    }
  }
  public void update(Asteroid[] asteroids, Entity player){
    for (Alien alien : aliens) {
      if (aliens == null) break;
      if (alien.active){
        alien.update(asteroids, player);
      }
    }
  }
  public void Draw(Graphics2D g2d){
    for (Alien alien : aliens) {
      if (alien == null) break;
      if (alien.active){
        //System.out.println(alien.x + " | " + alien.y);
        this.shape.Draw(g2d, (int)alien.x, (int)alien.y, (float)Math.PI);
      }
    }
  }


}

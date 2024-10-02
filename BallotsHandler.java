import java.awt.Graphics2D;


/**
 * BallotsHandler
 */
public class BallotsHandler {
  final int BALLOT_SIZE = 5;
  final float BALLOT_SPEED = 3;
  Ballot[] ballots;
  int n_ballots;
  int WIDTH_WINDOW;
  int HEIGHT_WINDOW;

  int active_bullots = 0;
  Point[] rectangle_vertices = new Point[]{
    new Point(0 , 0),
    new Point(BALLOT_SIZE, 0),
    new Point(BALLOT_SIZE, BALLOT_SIZE*1.5f),
    new Point(0, BALLOT_SIZE*1.5f)
  };
  Shape ballotShape = new Shape(BALLOT_SIZE/2, BALLOT_SIZE/4, rectangle_vertices);

  public BallotsHandler(int n_ballots, int WIDTH, int HEIGHT){
    this.WIDTH_WINDOW = WIDTH;
    this.HEIGHT_WINDOW = HEIGHT;

    this.n_ballots = n_ballots;
    ballots = new Ballot[n_ballots];
    for (int i = 0; i < n_ballots; i++){
      ballots[i] = new Ballot(100000, 100000);
      ballots[i].active = false;
    }
  }

  public boolean add_bullet(int x, int y, float angle){
    float dirX = -(float)Math.cos(angle);
    float dirY = (float)Math.sin(angle);
    for (int i = 0; i < n_ballots; i++) {
      if (!ballots[i].active){
        ballots[i].setCoord(x, y);
        ballots[i].setDirection(angle, BALLOT_SPEED*dirY, BALLOT_SPEED*dirX);
        ballots[i].active = true;
        return true;
      }
    }
    return false;
  }
  public void update(){
    for (int i = 0; i < n_ballots; i++) {
      ballots[i].active = Helpers.isInside(WIDTH_WINDOW, HEIGHT_WINDOW, (int) ballots[i].x, (int) ballots[i].y);
      //System.out.println(ballots[i].active + " | " + ballots[i].x + " | " + ballots[i].y + " | " + ballots[i].dirX + " " +  ballots[i].dirY);
      if (ballots[i].active){
        ballots[i].update();
      }else ballots[i].setCoord(100000, 100000);
    }
    //System.out.println("#############");
  }
  public boolean isColliding(Asteroid asteroid){
    float collider;
    for (Ballot ballot : ballots) {
      collider = CollisionHandler.collisionSAT(asteroid.shape, ballotShape, asteroid, ballot);
      if (collider != 10000.0){
        ballot.kill();
        return true;
      }
    }
    return false;
  }
  public void Draw(Graphics2D g2d){
    for (int i = 0; i < n_ballots; i++) {
      if (this.ballots[i].active)
        this.ballotShape.Draw(g2d, (int) ballots[i].x, (int) ballots[i].y, (float) Math.PI + ballots[i].angle); 
    }
  }

}

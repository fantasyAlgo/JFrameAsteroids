import java.awt.Graphics2D;

/**
 * BallotsHandler
 */
public class BallotsHandler {
  Ballot[] ballots;
  int n_ballots;
  int WIDTH_WINDOW;
  int HEIGHT_WINDOW;

  int active_bullots = 0;
  PlayerHandler player;
  public BallotsHandler(int n_ballots, PlayerHandler player){
    this.n_ballots = n_ballots;
    this.player = player;
    ballots = new Ballot[n_ballots];
    for (int i = 0; i < n_ballots; i++) 
      ballots[i].setCoord(100000, 100000);
  }

  public boolean add_bullet(int x, int y, int dirX, int dirY){
    for (int i = 0; i < n_ballots; i++) {
      if (!ballots[i].active){
        ballots[i].setCoord(x, y);
        ballots[i].setDirection(dirX, dirY);
        return true;
      }
    }
    return false;
  }
  public void update(){
    for (int i = 0; i < n_ballots; i++) {
      if (ballots[i].active){
        ballots[i].active = Helpers.isInside(WIDTH_WINDOW, HEIGHT_WINDOW, ballots[i].x, ballots[i].y);
        ballots[i].update();
      }
    }
  }
  public void Draw(Graphics2D g2d){
    for (int i = 0; i < n_ballots; i++) {
    }
  }

}

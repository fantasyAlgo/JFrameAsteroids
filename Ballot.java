/**
 * Ballot
 */
public class Ballot extends Entity {
  final float MAX_DISTANCE = 500.0f;
  boolean active;
  Point base_player;
  public Ballot(int x, int y){
    super(x, y);
  }
  public void kill(){
    this.active = false;
    this.setCoord(100000, 100000);
  }
  public void setPlayerPos(Point player_pos){
    this.base_player = player_pos;
  }
  public void checkPlayerPos(){
    float base_x = (float)x- base_player.x;
    float base_y = (float)y- base_player.y;
    boolean gone = Math.sqrt(base_x*base_x + base_y*base_y) > MAX_DISTANCE;
    if (gone) this.kill();
  }
}

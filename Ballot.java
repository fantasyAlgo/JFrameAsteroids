/**
 * Ballot
 */
public class Ballot extends Entity {
  boolean active;
  public Ballot(int x, int y){
    super(x, y);
  }
  public void kill(){
    this.active = false;
    this.setCoord(100000, 100000);
  }
}

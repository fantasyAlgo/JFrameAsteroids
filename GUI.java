import java.awt.Graphics2D;

public class GUI {
  public int points = 0;
  public int windowWidth;
  public int windowHeight;
  public GUI(int windowWidth, int windowHeight){
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;
    points = 0;
  }
  public void addPoints(float size){
    points += size/10.0f;
  }

  public void Draw(Graphics2D g2d, boolean playerAlive){
    g2d.drawString("Points: " + this.points, 10, 32);
    if (!playerAlive)
      g2d.drawString("You loser", this.windowWidth/2-44, 32);
    return;
  }
}

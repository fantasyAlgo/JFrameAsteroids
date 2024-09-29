import java.awt.Graphics2D;

/**
 * Asteroid
 */
public class Asteroid extends Entity {
  Shape shape;
  boolean active;
  public Asteroid(int x, int y){
    super(x, y);
    this.active = false;
  }
  public void make_shape(float size){
    // To do: Make the shape
    int n_edges = (int) Math.random()*20 + 1;
    float distorsion = 5;

    float[] edgesX = new float[n_edges];
    float[] edgesY = new float[n_edges];

    double step = (Math.PI*2.0f/((double) n_edges));
    int i = 0;
    for (double angle = 0; angle < Math.PI; angle+=step) {
      if (i >= n_edges) continue;
      edgesX[i] = (float) Math.cos(angle) * size;
      edgesY[i] = (float) Math.sin(angle) * size;
      edgesX[i] += distorsion - Math.random() * distorsion *2;
      edgesY[i] += distorsion - Math.random() * distorsion *2;
      i++;
    }
    this.shape = new Shape(0, 0, edgesX, edgesY);
    active = true;
  }
  public void Draw(Graphics2D g2d){
    if (this.shape != null)
      this.shape.Draw(g2d, (int) super.x, (int) super.y, super.angle);
  }

}


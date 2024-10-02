import java.awt.Graphics2D;
import java.util.Arrays;


/**
 * Asteroid
 */
public class Asteroid extends Entity {
  Shape shape;
  boolean active;
  int time_spawn = 1000000;
  public Asteroid(int x, int y){
    super(x, y);
    this.time_spawn = 1000000;
    this.active = false;
  }
  public void make_shape(float size){
    // To do: Make the shape
    this.time_spawn = 0;
    int n_edges = Math.max((int) (Math.random()*20 + 1), 3);
    float distorsion = 5*(size/20.0f);

    /*float[] edgesX = new float[n_edges];
    float[] edgesY = new float[n_edges];*/
    Point[] edges = new Point[n_edges];

    double step = (Math.PI*2.0f/((double) n_edges));
    int i = 0;
    for (double angle = 0; angle < Math.PI*2.0; angle+=step) {
      if (i >= n_edges) continue;
      if (edges[i] == null) edges[i] = new Point(0, 0);
      edges[i].x = (float) (Math.cos(angle) * size);
      edges[i].y = (float) Math.sin(angle) * size;

      edges[i].x += distorsion - Math.random() * distorsion * 2;
      edges[i].y += distorsion - Math.random() * distorsion * 2;
      i++;
    }
    edges = Helpers.convexhull(edges);
    System.out.println(Arrays.toString(edges));
    System.out.println("@@@@@@@@@@@@@ " + n_edges);
    this.shape = new Shape(0, 0, edges);
    active = true;
  }
  public void Draw(Graphics2D g2d){
    if (this.shape != null){
      this.shape.Draw(g2d, (int) super.x, (int) super.y, super.angle);
    }
  }
  public boolean hasEndedSpawn(){
    return time_spawn++ < 100;
  }

}


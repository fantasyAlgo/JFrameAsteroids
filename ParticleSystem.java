import java.awt.Graphics2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * ParticleSystem
 */
public class ParticleSystem {
  ArrayList<Entity> particles = new ArrayList<>();
  final float PARTICLE_SIZE = 30.0f;
  final float PARTICLE_SPEED = 0.4f;
  public ParticleSystem(){
    // Don't worry child
  }
  public void add_boom_particles(Entity entity, float entity_size){
    int size = Math.max((int) (Math.random()*entity_size/2.0f), 3);
    Entity particle;
    double angle;
    for (int i = 0; i < size; i++) {
      angle = Math.random()*10.0;
      particle = new Entity(entity.x+(float)(1.0f-Math.random()*2.0f)*10.0f, entity.y + (float)(1.0f-Math.random()*2.0f)*10.0f);
      Point p = new Point(
        (float)(Math.cos(angle)*entity_size/20.0f + (1.0f-Math.random()*2.0f))/2.0f,
        (float)(Math.sin(angle)*entity_size/20.0f + (1.0f - Math.random()*2.0f)*2.0f)/2.0f
      );

      particle.setDirection((float)angle, p.x, p.y);
      this.particles.add(particle);
    }
  }
  public void update(){
    float magn;
    Iterator<Entity> iterator = this.particles.iterator();
    Entity entity;
    while (iterator.hasNext()){
      entity = iterator.next();
      entity.setDirection(entity.angle, entity.dirX*0.99f, entity.dirY*0.99f);
      magn = (float)Math.sqrt(entity.dirX*entity.dirX + entity.dirY*entity.dirY)*2.0f;
      if (magn < 0.05f)
        iterator.remove();
      entity.update();
    }
  }
  public void Draw(Graphics2D g2d){
    int magn;
    for (Entity entity : particles) {
      magn = (int)(Math.sqrt(PARTICLE_SIZE*entity.dirX*entity.dirX + PARTICLE_SIZE*entity.dirY*entity.dirY)*2.0f);
      g2d.drawRect((int)entity.x, (int)entity.y, magn, magn);
    }
  }
}

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;

import javax.swing.JPanel;


/**
 * GamePanel
 */
public class GamePanel extends JPanel implements Runnable{
  // You won't win over my mind sun!
  final int originalTileSize = 16;
  final int scale = 3;

  final int tileSize = originalTileSize * scale;
  final int maxScreenCol = 16;
  final int maxScreenRow = 12;
  final int screenWidth = tileSize*maxScreenCol;
  final int screenHeight = tileSize*maxScreenRow;

  int time = 0;
  int FPS = 144;
  boolean pageSettings = false;

  Thread gameThread;
  KeyHandler keyHandler = new KeyHandler();

  PlayerHandler playerH = new PlayerHandler(screenWidth, screenHeight, 0.2f, 10);
  AsteroidsHandler asteroidsHandler = new AsteroidsHandler(screenWidth, screenHeight);
  ParticleSystem particleSystem = new ParticleSystem();
  GUI gui = new GUI(screenWidth, screenHeight);

  public GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
  }
  public void startGameThread(){
    gameThread = new Thread(this);
    gameThread.start();
  }
  @Override
  public void run(){
    double drawInterval = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;
    time++;

    while (gameThread != null) {
      currentTime = System.nanoTime();
      time++;

      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;
      if ( delta >= 1){
        update();
        repaint();
        delta--;
        drawCount++;
      }
      if ( timer >= 1000000000){
        //System.out.println("FPS: " + drawCount);
        drawCount = 0;
        timer = 0;
      }

    }
  }
  public void update(){
    playerH.React(keyHandler.leftPressed, keyHandler.rightPressed, keyHandler.upPressed, keyHandler.spacePressed);
    playerH.ballotsHandler.update();
    asteroidsHandler.update();
    particleSystem.update();
    if (Math.random() < 0.005 && asteroidsHandler.active_asteroids() < asteroidsHandler.MAX_ASTEROIDS/2.0){
      time++;
      asteroidsHandler.add_asteroid(Math.max((float)Math.random()*60, 30.0f));
    }
    // Collision detection
    float collision;
    for (Asteroid asteroid: asteroidsHandler.asteroids) {
      if (asteroid.active){
        collision = CollisionHandler.collisionSAT(asteroid.shape, playerH.shape, asteroid, playerH);
        if (collision != 10000f){
          particleSystem.add_boom_particles(playerH, 100f);
          playerH.kill();
        }
      }
    }
    for (Asteroid asteroid: asteroidsHandler.asteroids) {
      if (asteroid.active && playerH.ballotsHandler.isColliding(asteroid)){
        if (asteroid.size_shape > 20.0f){
          System.out.println(asteroidsHandler.splitAsteroid(asteroid));
        }
        gui.addPoints(asteroid.size_shape);
        particleSystem.add_boom_particles(asteroid, asteroid.size_shape);
        asteroid.kill();
      }
    }

  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    g2.setFont(new Font("serif", Font.PLAIN, 32));
    g2.setColor(Color.white);


    playerH.Draw(g2);
    playerH.DrawBullets(g2);
    asteroidsHandler.Draw(g2);
    particleSystem.Draw(g2);
    gui.Draw(g2, playerH.isAlive);

    
    g2.dispose();

  }
}

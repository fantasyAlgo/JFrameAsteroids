import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


/**
 * GamePanel
 */

enum GameState {
  TitleScreen,
  GameScreen,
  DeathScreen,
  ExitScreen,
  PrepareToRun
};

public class GamePanel extends JPanel implements Runnable{
  // You won't win over my mind sun!
  final static int originalTileSize = 16;
  final static int scale = 3;

  final static int tileSize = originalTileSize * scale;
  final static int maxScreenCol = 20;
  final static int maxScreenRow = 12;
  final static int screenWidth = tileSize*maxScreenCol;
  final static int screenHeight = tileSize*maxScreenRow;

  int time = 0;
  int FPS = 144;
  GameState gameState;

  // System
  Thread gameThread;
  KeyHandler keyHandler = new KeyHandler();
  Sound sound = new Sound();
  GUI gui = new GUI(screenWidth, screenHeight);

  // Objects
  PlayerHandler playerH = new PlayerHandler(screenWidth, screenHeight, 0.15f, 10);
  AsteroidsHandler asteroidsHandler = new AsteroidsHandler(screenWidth, screenHeight);
  ParticleSystem particleSystem = new ParticleSystem();
  AlienHandler alienHandler = new AlienHandler(5);


  public GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);

    alienHandler.add();

    gameState = GameState.TitleScreen;
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
    sound.setFile(0);

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
        System.out.println("FPS: " + drawCount);
        drawCount = 0;
        timer = 0;
      }
      if (gameState == GameState.ExitScreen) System.exit(0);
    }
  }
  public void update(){
    if (gameState == GameState.TitleScreen )return;
    if (gameState == GameState.PrepareToRun){
      gameState = GameState.GameScreen;
      playerH.reset();
      asteroidsHandler.reset();
      playerH.ballotsHandler.reset();
      gui.points = 0;
      return;
    }
    playerH.React(keyHandler.leftPressed, keyHandler.rightPressed, keyHandler.upPressed, keyHandler.spacePressed);
    playerH.ballotsHandler.update();
    asteroidsHandler.update();
    particleSystem.update();
    alienHandler.update(asteroidsHandler.asteroids, playerH);

    if (Math.random() < 0.005 && asteroidsHandler.active_asteroids() < asteroidsHandler.MAX_ASTEROIDS/2.0){
      time++;
      asteroidsHandler.add_asteroid(Math.max((float)Math.random()*60, 30.0f));
    }
    // Collision detection
    float collision;
    for (Asteroid asteroid: asteroidsHandler.asteroids) {
      if (asteroid == null) break;
      if (asteroid.active){
        collision = CollisionHandler.collisionSAT(asteroid.shape, playerH.shape, asteroid, playerH);
        if (collision != 10000f){
          particleSystem.add_boom_particles(playerH, 100f);
          playerH.kill();
          gameState = GameState.DeathScreen;
          //sound.setFile(0);
          sound.play();
          //playSE(0);
        }
      }
    }
    for (Asteroid asteroid: asteroidsHandler.asteroids) {
      if (asteroid == null) break;
      if (asteroid.active && playerH.ballotsHandler.isColliding(asteroid)){
        if (asteroid.size_shape > 20.0f)
          asteroidsHandler.splitAsteroid(asteroid);
        sound.play();
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
    if (gameState == GameState.TitleScreen){
      gameState = gui.DrawTitleScreen(g2, gameState, keyHandler);
    }
    if (gameState == GameState.GameScreen || gameState == GameState.DeathScreen){
      playerH.Draw(g2);
      playerH.DrawBullets(g2);
      asteroidsHandler.Draw(g2);
      particleSystem.Draw(g2);
      alienHandler.Draw(g2);
      gui.DrawGameUI(g2, playerH.isAlive);
      if (gameState == GameState.DeathScreen){
        gameState = gui.DrawDeathUI(g2, keyHandler);
      }
    }
    
    g2.dispose();

  }

  public void playMusic(int i){
    sound.setFile(i);
    sound.play();
    sound.loop();
  }
  public void stopMusic(int i){sound.stop();}
  public void playSE(int i){
    sound.setFile(i);
    sound.play();
  }
}

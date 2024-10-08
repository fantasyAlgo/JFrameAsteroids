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
  PrepareToRun,
  OptionsScreen
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
  final float init_player_speed = 0.15f;

  int time = 0;
  int FPS = 144;
  GameState gameState;

  // System
  Thread gameThread;
  KeyHandler keyHandler = new KeyHandler();
  Sound sound = new Sound();
  Sound bgTitleScreenSound = new Sound();
  GUI gui = new GUI(screenWidth, screenHeight);

  // Objects
  PlayerHandler playerH = new PlayerHandler(screenWidth, screenHeight, init_player_speed, 1);
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
    gui.options[0] = init_player_speed;
    gui.options[1] = this.playerH.angle_speed;
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
    sound.setVolume(0);
    bgTitleScreenSound.setFile(1);
    bgTitleScreenSound.setVolume(-10f);
    bgTitleScreenSound.loop();

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
    if (gameState == GameState.TitleScreen || gameState == GameState.OptionsScreen)return;
    if (gameState == GameState.PrepareToRun){
      bgTitleScreenSound.stop();
      gameState = GameState.GameScreen;
      playerH.reset();
      asteroidsHandler.reset();
      playerH.ballotsHandler.reset();
      alienHandler.reset();
      gui.points = 0;
      return;
    }
    playerH.React(keyHandler.leftPressed, keyHandler.rightPressed, keyHandler.upPressed, keyHandler.spacePressed);
    playerH.ballotsHandler.update();
    asteroidsHandler.update();
    particleSystem.update();
    alienHandler.update(asteroidsHandler.asteroids, playerH);
    if (asteroidsHandler.canSpawn()){
      time++;
      asteroidsHandler.add_asteroid(Math.max((float)Math.random()*60, 30.0f));
    }
    if (Math.random() < 0.0005 && asteroidsHandler.MAX_ASTEROIDS > 10 && alienHandler.getActiveNumber() == 0 ){
      alienHandler.MAX_ASTEROIDS = asteroidsHandler.MAX_ASTEROIDS;
      alienHandler.add();
    }
    // Collision detection
    for (Asteroid asteroid: asteroidsHandler.asteroids) {
      if (asteroid == null) break;
      if (asteroid.active){
        if (playerH.isColliding(asteroid.shape, asteroid)){
          particleSystem.add_boom_particles(playerH, 100f);
          playerH.kill();
          gameState = GameState.DeathScreen;
          //sound.setFile(0);
          sound.play();

          //playSE(0);
        }
        alienHandler.isColliding(particleSystem, asteroid);
      }
      if (asteroid.active && (playerH.ballotsHandler.isColliding(asteroid.shape, asteroid) || alienHandler.isBallotColliding(asteroid.shape, asteroid))){
        if (asteroid.size_shape > 20.0f)
          asteroidsHandler.splitAsteroid(asteroid);

        sound.setVolume( (asteroid.size_shape - 40)/5);
        sound.play();
        gui.addPoints(asteroid.size_shape);
        particleSystem.add_boom_particles(asteroid, asteroid.size_shape);
        asteroid.kill();
      }
    }
    for (Alien alien : alienHandler.aliens) {
      if (alien == null) break;
      if (alien.active && playerH.isColliding(alienHandler.shape, alien)){
        particleSystem.add_boom_particles(playerH, 100f);
        playerH.kill();
        gameState = GameState.DeathScreen;
        //sound.setFile(0);
        sound.play();
      }
      if (alien.active && (playerH.ballotsHandler.isColliding(alienHandler.shape, alien))){
        particleSystem.add_boom_particles(alien, 40f);
        alien.kill();
        gui.addPoints(40);
        sound.play();
      }
      if (alien.active && alien.ballotHandler.isColliding(playerH.shape, playerH)){
          particleSystem.add_boom_particles(playerH, 100f);
          playerH.kill();
          gameState = GameState.DeathScreen;
          sound.play();
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
    if (gameState == GameState.OptionsScreen){
      gameState = gui.DrawOptionScreen(g2, gameState, keyHandler);
      this.playerH.speed = gui.options[0];
      this.playerH.angle_speed = gui.options[1];
      
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
        if (gameState == GameState.TitleScreen) bgTitleScreenSound.loop();
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.InputStream;



enum TitleOptions {
  NEW_GAME,
  OPTIONS,
  EXIT
};

public class GUI {
  public int points = 0;
  public int windowWidth;
  public int windowHeight;

  public final int n_options = 2;
  public float[] options = new float[n_options];

  // For the title screen;
  Asteroid asteroid1 = new Asteroid(GamePanel.screenWidth-90, GamePanel.screenHeight/2);
  Asteroid asteroid2 = new Asteroid(90, GamePanel.screenHeight/2);
  PlayerHandler fake_player = new PlayerHandler(GamePanel.screenWidth, GamePanel.screenHeight, 0.2f, 1.5f);

  int commandNum = 0;
  boolean isUpPressed = false;
  boolean isDownPressed = false;
  float angle = 0;
  Font hyperspace;



  public GUI(int windowWidth, int windowHeight){
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;
    points = 0;
    asteroid1.make_shape(60f);
    asteroid2.make_shape(60f);
    fake_player.setCoord(windowWidth/2-10, windowHeight/2 - windowHeight/(4.5f));

    try {
      InputStream is = getClass().getResourceAsStream("Hyperspace.ttf");
      hyperspace = Font.createFont(Font.TRUETYPE_FONT, is);
    } catch (Exception e) {
      e.printStackTrace();
    }
    for (int i = 0; i < n_options; i++) {
      options[i] = 0.0f;
    }
    
  }
  public void addPoints(float size){
    points += size/10.0f;
  }

  public void DrawGameUI(Graphics2D g2d, boolean playerAlive){
    g2d.drawString(""+this.points, 10, 32);
    return;
  }

  public GameState DrawDeathUI(Graphics2D g2, KeyHandler keyHandler){
    g2.setFont(hyperspace);
    updateCommandNum(keyHandler);
    commandNum = Math.min(Math.max(0, commandNum), 1);

    int width = 360;
    int height = 410;
    int stroke = 5;
    g2.setColor(Color.WHITE);
    g2.fillRoundRect(GamePanel.screenWidth/2-width/2, GamePanel.screenHeight/2 - height/2, width, height, 35, 35);
    g2.setColor(Color.black);
    g2.fillRoundRect(GamePanel.screenWidth/2-(width/2)+stroke, GamePanel.screenHeight/2 - (height/2)+stroke, width-stroke*2, height-stroke*2, 35, 35);


    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
    String text = "You died!";
    int x = getXForCenteredText(g2, text);
    int y = GamePanel.screenHeight/2 - height/2 + GamePanel.tileSize*2;

    int grayAmount = 30;
    g2.setColor(new Color(grayAmount, grayAmount, grayAmount));
    g2.drawString(text, x-3, y+3);
    g2.setColor(Color.white);
    g2.drawString(text, x, y);
    
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
    y += GamePanel.tileSize*1f;
    this.drawSelection(g2, "New game", y, commandNum == 0);
    y += GamePanel.tileSize*1.5f;
    this.drawSelection(g2, "Title screen", y, commandNum == 1);

    if (keyHandler.enterPressed && commandNum == 0){
      return GameState.PrepareToRun;
    }else if (keyHandler.enterPressed && commandNum == 1){
      keyHandler.enterPressed = false;
      return GameState.TitleScreen;
    }
    return GameState.DeathScreen;
  }

  public GameState DrawOptionScreen(Graphics2D g2, GameState page, KeyHandler keyHandler){
    g2.setFont(hyperspace);
    this.updateCommandNum(keyHandler);

    if (keyHandler.enterPressed && commandNum == 3){
      keyHandler.enterPressed = false;
      commandNum = -1;
      keyHandler.enterPressed = false;
      return GameState.TitleScreen;
    }
    asteroid1.angle -= 0.001;
    asteroid2.angle += 0.001;

    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 76F));
    String text = "Options!";
    int x = getXForCenteredText(g2, text);
    int y = GamePanel.tileSize*2;

    int grayAmount = 30;
    g2.setColor(new Color(grayAmount, grayAmount, grayAmount));
    g2.drawString(text, x-3, y+3);
    g2.setColor(Color.white);
    g2.drawString(text, x, y);

    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
    y += GamePanel.tileSize*2.5f;
    this.drawBarSelection(g2, "speed", y, 0, commandNum, keyHandler, 0.01f, 0.5f);
    y += GamePanel.tileSize*1.5f;
    this.drawBarSelection(g2, "Angle speed", y, 1, commandNum, keyHandler, 0.01f, 0.07f);
    y += GamePanel.tileSize*1.5f;
    this.drawSelection(g2, "WASD+K " + (KeyHandler.allow_wasd ? "X" : " "), y, commandNum == 2, keyHandler, () -> {KeyHandler.allow_wasd = !KeyHandler.allow_wasd;});
    y += GamePanel.tileSize*1.5f;
    this.drawSelection(g2, "Back", y, commandNum == 3);



    asteroid1.Draw(g2);
    asteroid2.Draw(g2);
    fake_player.setDirection(angle + (float)Math.PI/2, (float)Math.cos(angle), (float)Math.sin(angle));
    angle += 0.006f;

    fake_player.update();
    fake_player.Draw(g2);

    return GameState.OptionsScreen;
  }

  public GameState DrawTitleScreen(Graphics2D g2, GameState page, KeyHandler keyHandler){
    g2.setFont(hyperspace);
    this.updateCommandNum(keyHandler);
    commandNum = Math.min(Math.max(0, commandNum), 2);

    if (keyHandler.enterPressed){
      keyHandler.enterPressed = false;
      switch (commandNum) {
        case 0:
           return GameState.PrepareToRun;
        case 1:
          return GameState.OptionsScreen;
        case 2:
          return GameState.ExitScreen;
      }
    }

    asteroid1.angle -= 0.001;
    asteroid2.angle += 0.001;

    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 76F));
    String text = "Asteroids!";
    int x = getXForCenteredText(g2, text);
    int y = GamePanel.tileSize*2;

    int grayAmount = 30;
    g2.setColor(new Color(grayAmount, grayAmount, grayAmount));
    g2.drawString(text, x-3, y+3);
    g2.setColor(Color.white);
    g2.drawString(text, x, y);

    
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
    y += GamePanel.tileSize*2.5f;
    this.drawSelection(g2, "New game", y, commandNum == 0);
    y += GamePanel.tileSize*1.5f;
    this.drawSelection(g2, "Options",  y, commandNum == 1);
    y += GamePanel.tileSize*1.5f;
    this.drawSelection(g2, "Exit", y, commandNum == 2);

    asteroid1.Draw(g2);
    asteroid2.Draw(g2);
    fake_player.setDirection(angle + (float)Math.PI/2, (float)Math.cos(angle), (float)Math.sin(angle));
    angle += 0.006f;

    fake_player.update();
    fake_player.Draw(g2);

    return GameState.TitleScreen;
  }
  private void updateCommandNum(KeyHandler keyHandler){
    if (!isUpPressed && keyHandler.upPressed){
      commandNum--;
      isUpPressed = true;
    }else if (!keyHandler.upPressed) isUpPressed = false;
    if (!isDownPressed && keyHandler.downPressed){
      commandNum++;
      isDownPressed = true;
    }else if (!keyHandler.downPressed) isDownPressed = false;
    commandNum = Math.min(Math.max(0, commandNum), 3);

  }
  private void drawBarSelection(Graphics2D g2, String text, float y, int indx, int commandNum, KeyHandler keyHandler, float minV, float maxV){
    float ratio = (maxV-minV)/100.0f;
    int x = getXForCenteredText(g2, text);
    y += GamePanel.tileSize*1.5f;
    g2.drawString(text + " " + String.format("%.3f", options[indx]) , x-x/6, y);
    if (commandNum == indx){
      g2.drawString(">", x - x/6 - GamePanel.tileSize, y);
      if (keyHandler.leftPressed){
        options[indx] -= ratio;
      }else if (keyHandler.rightPressed) options[indx] += ratio;
      options[indx] = Math.max(minV, Math.min(maxV, options[indx]));
    }
  }
  private void drawSelection(Graphics2D g2, String text, float y, boolean isSelected, KeyHandler keyHandler, EventButton eventButton){
    int x = getXForCenteredText(g2, text);
    y += GamePanel.tileSize*1.5f;
    g2.drawString(text, x, y);
    if (isSelected) g2.drawString(">", x - GamePanel.tileSize, y);
    if (keyHandler.enterPressed){
      keyHandler.enterPressed = false;
      eventButton.event();
    }
  }

  private void drawSelection(Graphics2D g2, String text, float y, boolean isSelected){
    int x = getXForCenteredText(g2, text);
    y += GamePanel.tileSize*1.5f;
    g2.drawString(text, x, y);
    if (isSelected) g2.drawString(">", x - GamePanel.tileSize, y);
  }
  private int getXForCenteredText(Graphics2D g2, String text){
    int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    int x = GamePanel.screenWidth/2 - length/2;
    return x;
  }
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.lang.Runtime.Version;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



enum TitleOptions {
  NEW_GAME,
  OPTIONS,
  EXIT,
  RANKINGS,
};

public class GUI {
  public int points = 0;
  public int user_id;
  public String token;
  public String username;

  public int windowWidth;
  public int windowHeight;

  public final int n_options = 2;
  public float[] options = new float[n_options];
  GameState current_state;

  // For the title screen;
  Asteroid asteroid1 = new Asteroid(GamePanel.screenWidth-90, GamePanel.screenHeight/2);
  Asteroid asteroid2 = new Asteroid(90, GamePanel.screenHeight/2);
  PlayerHandler fake_player = new PlayerHandler(GamePanel.screenWidth, GamePanel.screenHeight, 0.2f, 1.5f);

  boolean areRankingsInitialized = false;
  int commandNum = 0;
  //int 

  boolean isUpPressed = false;
  boolean isDownPressed = false;
  boolean isLetterKeyDown = false;
  float angle = 0;
  Font hyperspace;

  String[] rankings;
  int n_ranks = 0;


  public GUI(int windowWidth, int windowHeight){
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;
    rankings = new String[12];
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
    this.areRankingsInitialized = false;
  }
  public void addPoints(float size){
    points += size/10.0f;
  }

  public void DrawGameUI(Graphics2D g2d, boolean playerAlive){
    g2d.drawString(""+this.points, 10, 32); // lol a dumb trick to convert an integer into a string
    return;
  }

  public GameState DrawDeathUI(Graphics2D g2, KeyHandler keyHandler){
    this.current_state = GameState.DeathScreen;
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

  public void SendDeathData(){
    String json = String.format(
      """
      {
        "id": %d,
        "token": "%s",
        "points": %d
      }
      """, this.user_id, this.token, this.points);    
    try {
      HttpResponse<String> response = Networking.sendPostRequest("add_game", json);
      System.out.println("Status: " + response.statusCode());
      System.out.println("Response body: " + response.body());
    }catch (Exception e){
      System.out.println("Wasnt able to send the data");
    }
  }
  public void changeName(){
    String json = String.format(
      """
      {
        "id": %d,
        "token": "%s",
        "new_name": "%s"
      }
      """, this.user_id, this.token, this.username);    

    try {
      HttpResponse<String> response = Networking.sendPostRequest("change_name", json);
      // If sendPostRequest does not return any expection, it writes the new name in the folder
      Path userHome = Paths.get(System.getProperty("user.home"));
      Path storage_file = userHome.resolve("." + GamePanel.name_game_folder + "/data.txt"); 
      List<String> lines = List.of("" + this.user_id , this.token, this.username);
      Files.write(storage_file, lines);

    }catch (Exception e){
      System.out.println("Wasnt able to send the data");
    }
  }




  public GameState DrawOptionScreen(Graphics2D g2, GameState page, KeyHandler keyHandler){
    this.current_state = GameState.OptionsScreen;

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



  public void initializeRankings(){
    String url = "http://fantasyendpoint.duckdns.org:5000/topRankings?id=16";
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("id", Integer.toString(this.user_id)).GET().build();
    HttpClient client = HttpClient.newBuilder().version(java.net.http.HttpClient.Version.HTTP_1_1).build();
    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println("Status: " + response.statusCode());
      System.out.println("Body: " + response.body());
      String cleaned = response.body().replaceAll("[:{}\\[\\]]", " ").trim();
      System.out.println("cleaned: " + cleaned);
      String[] body = cleaned.split(",");
      String[] singleId;
      this.n_ranks = body.length;
      for (int i = 0; i < body.length; i++) {
        singleId = body[i].trim().split(" ");
        System.out.println(singleId[0] + " | " + singleId[1]);
        this.rankings[i] = singleId[1];

      }
    }catch (Exception e){}
    this.areRankingsInitialized = true;
     

  }

  public GameState DrawRankingsScreen(Graphics2D g2, GameState page, KeyHandler keyHandler){
    this.current_state = GameState.RankingScreen;
    if (!this.areRankingsInitialized)
      this.initializeRankings();

    g2.setFont(hyperspace);
    this.updateCommandNum(keyHandler);
    this.updateName(keyHandler);

    if (keyHandler.enterPressed && commandNum == (this.n_ranks/2 + 2)){
      keyHandler.enterPressed = false;
      commandNum = -1;
      keyHandler.enterPressed = false;
      return GameState.TitleScreen;
    }
    asteroid1.angle -= 0.001;
    asteroid2.angle += 0.001;

    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 76F));
    String text = "Rankings!";
    int x = getXForCenteredText(g2, text);
    int y = GamePanel.tileSize*2;

    int grayAmount = 30;
    g2.setColor(new Color(grayAmount, grayAmount, grayAmount));
    g2.drawString(text, x-3, y+3);
    g2.setColor(Color.white);
    g2.drawString(text, x, y);



    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
    y += GamePanel.tileSize*1f;
    this.drawSelection(g2, "Username |  Score", y, commandNum == 0);
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
    for (int i = 0; i < this.n_ranks/2; i++) {
      y += GamePanel.tileSize*1f;
      this.drawSelection(g2, this.rankings[i*2] + "  |  " + this.rankings[i*2+1], y, commandNum == (i+1));
    }
    y += GamePanel.tileSize*1.5f;
    this.drawSelection(g2, "You: " + this.username, y, commandNum == (this.n_ranks/2 + 1));
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
    y += GamePanel.tileSize*1.5f;
    this.drawSelection(g2, "Back", y, commandNum == (this.n_ranks/2 + 2));

    asteroid1.Draw(g2);
    asteroid2.Draw(g2);
    angle += 0.006f;


    return GameState.RankingScreen;
  }

  public GameState DrawTitleScreen(Graphics2D g2, GameState page, KeyHandler keyHandler){
    g2.setFont(hyperspace);
    this.updateCommandNum(keyHandler);
    commandNum = Math.min(Math.max(0, commandNum), 3);

    if (keyHandler.enterPressed){
      keyHandler.enterPressed = false;
      switch (commandNum) {
        case 0:
           return GameState.PrepareToRun;
        case 1:
          return GameState.OptionsScreen;
        case 2: 
          return GameState.RankingScreen;
        case 3:
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
    this.drawSelection(g2, "Rankings", y, commandNum == 2);
    y += GamePanel.tileSize*1.5f;
    this.drawSelection(g2, "Exit", y, commandNum == 3);


    asteroid1.Draw(g2);
    asteroid2.Draw(g2);
    fake_player.setDirection(angle + (float)Math.PI/2, (float)Math.cos(angle), (float)Math.sin(angle));
    angle += 0.006f;

    fake_player.update();
    fake_player.Draw(g2);

    return GameState.TitleScreen;
  }
  private void updateName(KeyHandler keyHandler){
    if (commandNum == (this.n_ranks/2 + 1) && (keyHandler.letter_pressed != -1 || keyHandler.deleteKey)){
      if (!isLetterKeyDown) {
        if (this.username.length() > 0 && keyHandler.deleteKey) {
          this.username = this.username.substring(0, this.username.length()-1);
        }
        else{
          this.username += (char) (keyHandler.letter_pressed + 65);
        }
        isLetterKeyDown = true;
      }
    }else isLetterKeyDown = false;
    if (commandNum == (this.n_ranks/2 + 1) && keyHandler.enterPressed){
      changeName();
    }

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
    if (this.current_state == GameState.RankingScreen) commandNum = Math.min(Math.max(0, commandNum), (this.n_ranks/2 + 2));
    else commandNum = Math.min(Math.max(0, commandNum), 4);

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

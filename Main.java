import javax.swing.JFrame;

public class Main {
  public static void main(String[] args) {
    System.setProperty("sun.java2d.opengl", "true");
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("Asteroids");

    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);

    gamePanel.startGameThread();
    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }
}

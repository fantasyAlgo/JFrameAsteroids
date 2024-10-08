import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyHandler
 */
public class KeyHandler implements KeyListener{

  public boolean leftPressed, rightPressed, upPressed, downPressed, spacePressed, tabPressed, enterPressed = false;
  public static boolean allow_wasd = false;

  @Override
  public void keyTyped(KeyEvent e){

  }
  @Override
  public void keyPressed(KeyEvent e){
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT || (allow_wasd && code == KeyEvent.VK_A)) leftPressed = true;
    if (code == KeyEvent.VK_RIGHT || (allow_wasd && code == KeyEvent.VK_D)) rightPressed = true;
    if (code == KeyEvent.VK_UP || (allow_wasd && code == KeyEvent.VK_W)) upPressed = true;
    if (code == KeyEvent.VK_DOWN || (allow_wasd && code == KeyEvent.VK_S)) downPressed = true;
    if (code == KeyEvent.VK_SPACE || (allow_wasd && code == KeyEvent.VK_K) ) spacePressed = true;
    if (code == KeyEvent.VK_TAB) tabPressed = true;
    if (code == KeyEvent.VK_ENTER) enterPressed = true;
  } 
  @Override
  public void keyReleased(KeyEvent e){
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT || (allow_wasd && code == KeyEvent.VK_A)) leftPressed = false;
    if (code == KeyEvent.VK_RIGHT || (allow_wasd && code == KeyEvent.VK_D)) rightPressed = false;
    if (code == KeyEvent.VK_UP || (allow_wasd && code == KeyEvent.VK_W)) upPressed = false;
    if (code == KeyEvent.VK_DOWN || (allow_wasd && code == KeyEvent.VK_S))downPressed = false;
    if (code == KeyEvent.VK_SPACE || (allow_wasd && code == KeyEvent.VK_K)) spacePressed = false;
    if (code == KeyEvent.VK_TAB) tabPressed = false;
    if (code == KeyEvent.VK_ENTER) enterPressed = false;
  }
}

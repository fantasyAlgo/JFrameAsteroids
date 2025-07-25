import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyHandler
 */
public class KeyHandler implements KeyListener{

  public boolean leftPressed, rightPressed, upPressed, downPressed, spacePressed, tabPressed, enterPressed, deleteKey, key_pressed = false;
  public int letter_pressed = -1;
  public static boolean allow_wasd = false;

  @Override
  public void keyTyped(KeyEvent e){

  }
  @Override
  public void keyPressed(KeyEvent e){
    int code = e.getKeyCode();
    if (key_pressed) letter_pressed = -1;
    if (!key_pressed && code >= KeyEvent.VK_A && code < KeyEvent.VK_Z){
      letter_pressed = (code - KeyEvent.VK_A);
      key_pressed = true;
    }
    if (code == KeyEvent.VK_LEFT || (allow_wasd && code == KeyEvent.VK_A)) leftPressed = true;
    else if (code == KeyEvent.VK_RIGHT || (allow_wasd && code == KeyEvent.VK_D)) rightPressed = true;
    else if (code == KeyEvent.VK_UP || (allow_wasd && code == KeyEvent.VK_W)) upPressed = true;
    else if (code == KeyEvent.VK_DOWN || (allow_wasd && code == KeyEvent.VK_S)) downPressed = true;
    else if (code == KeyEvent.VK_SPACE || (allow_wasd && code == KeyEvent.VK_K) ) spacePressed = true;
    else if (code == KeyEvent.VK_TAB) tabPressed = true;
    else if (code == KeyEvent.VK_ENTER) enterPressed = true;
    else if (code == 8) deleteKey = true;
  } 

  @Override
  public void keyReleased(KeyEvent e){
    int code = e.getKeyCode();
    key_pressed = false;
    letter_pressed = -1;

    if (code == KeyEvent.VK_LEFT || (allow_wasd && code == KeyEvent.VK_A)) leftPressed = false;
    else if (code == KeyEvent.VK_RIGHT || (allow_wasd && code == KeyEvent.VK_D)) rightPressed = false;
    else if (code == KeyEvent.VK_UP || (allow_wasd && code == KeyEvent.VK_W)) upPressed = false;
    else if (code == KeyEvent.VK_DOWN || (allow_wasd && code == KeyEvent.VK_S))downPressed = false;
    else if (code == KeyEvent.VK_SPACE || (allow_wasd && code == KeyEvent.VK_K)) spacePressed = false;
    else if (code == KeyEvent.VK_TAB) tabPressed = false;
    else if (code == KeyEvent.VK_ENTER) enterPressed = false;
    else if (code == 8) deleteKey = false;
  }
}

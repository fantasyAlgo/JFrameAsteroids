import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyHandler
 */
public class KeyHandler implements KeyListener{

  public boolean leftPressed, rightPressed, upPressed, downPressed, spacePressed, tabPressed, enterPressed = false;

  @Override
  public void keyTyped(KeyEvent e){

  }
  @Override
  public void keyPressed(KeyEvent e){
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT) leftPressed = true;
    if (code == KeyEvent.VK_RIGHT) rightPressed = true;
    if (code == KeyEvent.VK_UP) upPressed = true;
    if (code == KeyEvent.VK_DOWN) downPressed = true;
    if (code == KeyEvent.VK_SPACE) spacePressed = true;
    if (code == KeyEvent.VK_TAB) tabPressed = true;
    if (code == KeyEvent.VK_ENTER) enterPressed = true;
  } 
  @Override
  public void keyReleased(KeyEvent e){
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT) leftPressed = false;
    if (code == KeyEvent.VK_RIGHT) rightPressed = false;
    if (code == KeyEvent.VK_UP) upPressed = false;
    if (code == KeyEvent.VK_DOWN)downPressed = false;
    if (code == KeyEvent.VK_SPACE) spacePressed = false;
    if (code == KeyEvent.VK_TAB) tabPressed = false;
    if (code == KeyEvent.VK_ENTER) enterPressed = false;
  }
}

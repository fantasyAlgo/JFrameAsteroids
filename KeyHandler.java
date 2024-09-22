import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyHandler
 */
public class KeyHandler implements KeyListener{

  public boolean leftPressed, rightPressed, upPressed, spacePressed = false;

  @Override
  public void keyTyped(KeyEvent e){

  }
  @Override
  public void keyPressed(KeyEvent e){
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT) leftPressed = true;
    if (code == KeyEvent.VK_RIGHT) rightPressed = true;
    if (code == KeyEvent.VK_UP) upPressed = true;
    if (code == KeyEvent.VK_SPACE) spacePressed = true;
  } 
  @Override
  public void keyReleased(KeyEvent e){
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT) leftPressed = false;
    if (code == KeyEvent.VK_RIGHT) rightPressed = false;
    if (code == KeyEvent.VK_UP) upPressed = false;
    if (code == KeyEvent.VK_SPACE) spacePressed = false;
  }
}

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class Archer {
    private int x;
    final private int y;
    private Image imgStand;
    private Image imgWalk;
    private Image currentImage;
    final private Timer walkTimer;
    private boolean isWalking = false;
    private Image imgWalkleft;
    private Image imgWalkleft2;
    private boolean isWalkingLeft = false;

    public Archer() {
        this.x = 250;
        this.y = 350;

        URL char1 = getClass().getResource("/character/IMG_1002.png");
        imgStand = new ImageIcon(char1).getImage();
        
        URL charWalk = getClass().getResource("/character/IMG_1001.png");
        imgWalk = new ImageIcon(charWalk).getImage();

        URL charWalkleft = getClass().getResource("/character/left2.png");
        imgWalkleft = new ImageIcon(charWalkleft).getImage();

        URL charWalkleft2 = getClass().getResource("/character/leftwalk.png");
        imgWalkleft2 = new ImageIcon(charWalkleft2).getImage();

        currentImage = imgStand;

        walkTimer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWalking) {
                    currentImage = (currentImage == imgStand) ? imgWalk : imgStand;
                }
                if (isWalkingLeft) {
                    currentImage = (currentImage == imgWalkleft) ? imgWalkleft2 : imgWalkleft;
                }
            }
        });
    }

    public void startWalking() {
        isWalking = true;
        walkTimer.start();
    }

    public void startWalkingLeft() {
        isWalkingLeft = true;
        walkTimer.start();
    }

    public void stopWalking() {
        isWalking = false;
        walkTimer.stop();
        currentImage = imgStand;
    }

    public void stopWalkingLeft() {
        isWalkingLeft = false;
        walkTimer.stop();
        currentImage = imgWalkleft;
    }

    public void moveRight() {
        x += 10;
        startWalking();
    }

    public void moveLeft() {
        x -= 10;
        startWalkingLeft();
    }

    public boolean hasReachedEdge(int windowWidth) {
        return x + 200 >= windowWidth;
    }

    public void resetPosition() {
        x = 250; 
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getCurrentImage() {
        return currentImage;
    }
}

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public abstract class  Zombie {
    protected  int x = 900;
    protected final int y = 350;
    protected boolean zombieisWalking = false;
    protected Timer zombieTimer;
    protected boolean isImageZombie = true;
    protected Image currentImageZombie;
    private final App app;

    URL zombie1 = getClass().getResource("/zombie/IMG_1053.png");
    Image imgZombie = new ImageIcon(zombie1).getImage();

    URL zombieWalk = getClass().getResource("/zombie/IMG_1055.png");
    Image imgZombieWalk = new ImageIcon(zombieWalk).getImage();

    public Zombie(App app) {
        this.app = app;
        zombieTimer = new Timer(150, e -> {
            zombieisWalking = true;
            currentImageZombie = imgZombieWalk;
            moveLeft();
            app.repaint();
        });
    }

    public void startWalking() {
        zombieTimer.start();
        zombieisWalking = true;
        currentImageZombie = imgZombieWalk;
        System.out.println("Zombie is walking");
    }
    
    public void stopWalking() {
        zombieisWalking = false;
        zombieTimer.stop();
    }

    public boolean isWalking() {
            return zombieisWalking;
    }

    public void moveLeft() {
        x -= 2;
        isImageZombie = !isImageZombie;
    }

    public boolean reachArcher(int width) {
            return x <= 0;
    }

    public void resetPosition() {
        x = 900;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public Image getCurrentImage() {
        return isImageZombie ? imgZombie : imgZombieWalk;
    }

    public abstract void eat();

    public abstract void Hurt();
}

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

    URL zombieHurt = getClass().getResource("/zombie/zombiehurt.png");
    Image imgZombieHurt = new ImageIcon(zombieHurt).getImage();

    URL zombieEat = getClass().getResource("/zombie/zombieeat.png");
    Image imgZombieEat = new ImageIcon(zombieEat).getImage();

    URL bossDead = getClass().getResource("/boss/boss1.png");
    Image imgBossDead = new ImageIcon(bossDead).getImage();

    URL bossHurt = getClass().getResource("/boss/boss2.png");
    Image imgBossHurt = new ImageIcon(bossHurt).getImage();

    URL bossDamaged = getClass().getResource("/boss/boss3.png");
    Image imgBossDamaged = new ImageIcon(bossDamaged).getImage();

    URL boss = getClass().getResource("/boss/boss4.png");
    Image imgBoss = new ImageIcon(boss).getImage();

    URL bossWalk = getClass().getResource("/boss/bosswalk.png");
    Image imgBossWalk = new ImageIcon(bossWalk).getImage();

    URL bossHurtWalk = getClass().getResource("/boss/bosshurtwalk.png");
    Image imgBossHurtWalk = new ImageIcon(bossHurtWalk).getImage();

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

    public Image getImgZombieHurt() {
        return imgZombieHurt;
    }

    public void takeDamage() {
        currentImageZombie = imgZombieHurt;
        app.repaint(); // Update the screen to show the hurt image
    
        // Wait 150ms to show the hurt image before removing the zombie
        Timer delayTimer = new Timer(100, e -> {
            Timer removeZombieTimer = new Timer(100, ev -> {
                stopWalking();
                x = -1000;
                app.repaint();
                ((Timer) ev.getSource()).stop();
            });
            removeZombieTimer.setRepeats(false);
            removeZombieTimer.start();
            
            ((Timer) e.getSource()).stop();
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }
    

    public abstract void eat();

    public abstract void Hurt();
}
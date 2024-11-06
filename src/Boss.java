import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Boss extends Zombie{
    public Image imgBossDead;
    public Image imgBossHurt;
    public Image imgBossDamaged;
    public Image imgBossNormal;
    public Image imgBossWalk;
    public Image imgBossHurtWalk;
    private boolean bossiswalking = false;
    private Timer BossTimer;
    //private Image currentImageBoss = imgBossNormal;

    public Boss(App app) {
        super(app);

        URL bossDead = getClass().getResource("/boss/boss1.png");
        imgZombie = new ImageIcon(bossDead).getImage();

        URL bossHurt = getClass().getResource("/boss/boss2.png");
        imgZombieHurt = new ImageIcon(bossHurt).getImage();

        URL bossDamaged = getClass().getResource("/boss/boss3.png");
        imgZombieEat = new ImageIcon(bossDamaged).getImage();

        URL bossNormal = getClass().getResource("/boss/boss4.png");
        imgBossNormal = new ImageIcon(bossNormal).getImage();

        URL bossWalk = getClass().getResource("/boss/bosswalk.png");
        imgBossWalk = new ImageIcon(bossWalk).getImage();

        URL bossHurtWalk = getClass().getResource("/boss/bosshurtwalk.png");
        imgBossHurtWalk = new ImageIcon(bossHurtWalk).getImage();

        currentImageZombie = imgBossNormal;

        BossTimer = new Timer(1000, e -> {
            moveLeft();
            currentImageZombie = (currentImageZombie == imgBossWalk) ? imgBossNormal : imgBossWalk;
            app.repaint();
        });
    }

    public void startBossTimer() {
        BossTimer.start();
    }

    public void stopBossTimer() {
        BossTimer.stop();
    }

    // public Image getCurrentBoss(){
    //     return currentImageBoss;
    // }

    @Override
    public void moveLeft(){
        x -= 2;
        isImageZombie = !isImageZombie;
    }

    public Image getImgBossDead() {
        return imgBossDead;
    }

    public Image getImgBossHurt() {
        return imgBossHurt;
    }

    public Image getImgBossDamaged() {
        return imgBossDamaged;
    }

    public Image getImgBoss() {
        return currentImageZombie;
    }

    public Image getImgBossWalk() {
        return imgBossWalk;
    }

    public Image getImgBossHurtWalk() {
        return imgBossHurtWalk;
    }
}
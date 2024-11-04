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
    private App app;
    public Timer ZombieHurtTimer;
    private boolean hurt = false;

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
        zombieTimer = new Timer(200, e -> {
            zombieisWalking = true;
            startWalking();
            currentImageZombie = imgZombieWalk;
            moveLeft();
            app.repaint();
        });

        ZombieHurtTimer = new Timer(100, e -> {
            currentImageZombie = imgZombieHurt;
            takeDamage();
            app.repaint();
        });
            
    }

    public void startWalking() {
        zombieTimer.start();
        zombieisWalking = true;
        currentImageZombie = imgZombieWalk;
        //System.out.println("Zombie is walking");
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
        return hurt ? imgZombieHurt : imgZombieWalk;
    }

    public void hurt(){
        hurt = true;
        currentImageZombie = getImgZombieHurt();
        app.repaint();
    }

    public void takeDamage() {
        hurt = true;
        currentImageZombie = getImgZombieHurt(); // เปลี่ยนเป็นภาพ "hurt"
        app.repaint(); // อัพเดตหน้าจอ
    
        Timer hurtTimer = new Timer(100, e -> { 
            currentImageZombie = imgZombieWalk; // กลับไปเป็นภาพการเดิน
            app.repaint(); 
            ((Timer) e.getSource()).stop();
        });
        hurtTimer.setRepeats(false);
        hurtTimer.start();
    
        // ตั้งเวลาให้ซอมบี้หายไปหลังจากที่แสดงภาพ hurt
        Timer removeZombieTimer = new Timer(300, ev -> { // ตั้งเวลาเพิ่มเป็น 1000 มิลลิวินาที
            stopWalking();
            x = -1000; // ย้ายซอมบี้ออกจากหน้าจอ
            app.repaint();
            ((Timer) ev.getSource()).stop();
        });
        removeZombieTimer.setRepeats(false);
        removeZombieTimer.start();
    }

    public abstract void eat();

}
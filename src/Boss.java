import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Boss {
    private int x = 850;
    private final int y = 350;
    public Image imgBossDead;
    public Image imgBossHurt;
    public Image imgBossDamaged;
    public Image imgBossNormal;
    public Image imgBossWalk;
    public Image imgBossHurtWalk;
    private boolean bossiswalking = false;
    public Timer BossTimer;
    public Image currentimgBoss;
    public Timer BossDamagedTimer;
    private App app;
    private boolean isEating = false;
    private boolean bossDamageiswalking = false;

    public Boss(App app) {
        this.app = app;

        URL bossDead = getClass().getResource("/boss/bossdead.png");
        imgBossDead = new ImageIcon(bossDead).getImage();

        URL bossDamaged = getClass().getResource("/boss/bossdamage.png");
        imgBossDamaged = new ImageIcon(bossDamaged).getImage();

        URL bossNormal = getClass().getResource("/boss/bossnormal.png");
        imgBossNormal = new ImageIcon(bossNormal).getImage();

        URL bossWalk = getClass().getResource("/boss/bosswalk.png");
        imgBossWalk = new ImageIcon(bossWalk).getImage();

        URL bossHurtWalk = getClass().getResource("/boss/bosshurtwalk.png");
        imgBossHurtWalk = new ImageIcon(bossHurtWalk).getImage();

        if(bossiswalking){
            currentimgBoss = imgBossWalk;
        }
        BossTimer = new Timer(500, e -> {
            if (bossiswalking) {
                moveLeft();
                currentimgBoss = (currentimgBoss == imgBossWalk) ? imgBossNormal : imgBossWalk;
            }
            app.repaint();
        });

        if(bossDamageiswalking){
            currentimgBoss = imgBossHurtWalk;
        }
        BossDamagedTimer = new Timer(500, e -> {
            if(bossDamageiswalking){
                moveLeft();
                currentimgBoss = (currentimgBoss == imgBossHurtWalk) ? imgBossDamaged : imgBossHurtWalk;
            }
        });
    }

    public void startBossTimer() {
        BossTimer.start();
    }

    public void stopBossTimer() {
        BossTimer.stop();
    }

    public void startWalking() {
        bossiswalking = true;
        BossTimer.start();
    }

    public void stopWalking() {
        bossiswalking = false;
        BossTimer.stop();
    }

    public void startHurtwalkingTimer(){
        BossDamagedTimer.start();
    }

    public void stopHurtwalkingTimer(){
        BossDamagedTimer.stop();
    }

    public void startHurtWalking() {
        bossDamageiswalking = true;
        BossDamagedTimer.start();
    }

    public void stopHurtWalking() {
        bossiswalking = false;
        BossDamagedTimer.stop();
    }

    public void moveLeft() {
        x -= 12;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getcurrentimgboss() {
        return currentimgBoss;
    }

    public Image getimgbosshurt() {
        return imgBossHurt;
    }

    public Image getimgbossdead() {
        return imgBossDead;
    }

    public Image getimgbosswalk() {
        return imgBossWalk;
    }

    public Image getimgbosshurtwalk() {
        return imgBossHurtWalk;
    }

    public void setBossDead() {
        currentimgBoss = imgBossDead; // เปลี่ยนภาพเป็นภาพตาย
    }

    public boolean checkCollisionWithArcher(Archer archer) {
        int collisionBuffer = 120; // ระยะห่างที่ต้องการให้บอสเข้ามาใกล้ archer มากขึ้น
    
        Image archerImage = archer.getCurrentImage();
        if (archerImage == null) {
            return false; // ถ้า archerImage เป็น null ให้ return false
        }
    
        return this.x < (archer.getX() - collisionBuffer) + archerImage.getWidth(null)
                && this.x + this.currentimgBoss.getWidth(null) > (archer.getX() - collisionBuffer)
                && this.y < archer.getY() + archerImage.getHeight(null)
                && this.y + this.currentimgBoss.getHeight(null) > archer.getY();
    }

    public void bossEat() {
        if (isEating) {
            return; // ถ้ากำลังกินอยู่ ให้ return ออกไป
        }
        isEating = true; // ตั้งสถานะว่าบอสกำลังกิน
    
        System.out.println("Boss is eating");

        Timer eatTimer = new Timer(1000, e -> {
            stopWalking();
            app.repaint();
            isEating = false; // รีเซ็ตสถานะการกิน
            ((Timer) e.getSource()).stop();
        });
        eatTimer.setRepeats(false);
        eatTimer.start();
    }

    public void bossdead(boolean win){
        System.out.println("Boss is dead");
        stopHurtWalking();
        setBossDead(); //show boss dead pic
        app.repaint();
    }
}


import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public abstract class Zombie {

    protected int x = 900;
    protected int y = 350;
    protected boolean zombieisWalking = false;
    protected Timer zombieTimer;
    protected boolean isImageZombie = true;
    protected Image currentImageZombie;
    protected App app;
    public Timer ZombieHurtTimer;
    private boolean hurt = false;
    private boolean isEating = false;

    URL zombie1 = getClass().getResource("/zombie/IMG_1053.png");
    Image imgZombie = new ImageIcon(zombie1).getImage();

    URL zombieWalk = getClass().getResource("/zombie/IMG_1055.png");
    Image imgZombieWalk = new ImageIcon(zombieWalk).getImage();

    URL zombieHurt = getClass().getResource("/zombie/zombiehurt.png");
    Image imgZombieHurt = new ImageIcon(zombieHurt).getImage();

    URL zombieEat = getClass().getResource("/zombie/zombieeat.png");
    Image imgZombieEat = new ImageIcon(zombieEat).getImage();

    public Zombie(App app) {
        this.app = app;
        zombieTimer = new Timer(200, e -> {
            zombieisWalking = true;
            startWalking();
            currentImageZombie = imgZombieWalk;
            moveLeft();
            app.repaint();
        });

        ZombieHurtTimer = new Timer(300, e -> {
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
        x -= 4;
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

    public Image getImgZombieEat() {
        return imgZombieEat;
    }

    public void takeDamage() {
        if (hurt) {
            return;
        }

        hurt = true;
        currentImageZombie = getImgZombieHurt(); // เปลี่ยนเป็นภาพ "hurt"
        app.repaint(); // อัพเดตหน้าจอทันทีเพื่อแสดงภาพ hurt

        // ตั้งเวลาสำหรับการแสดงภาพ hurt
        Timer hurtTimer = new Timer(200, e -> {
            currentImageZombie = imgZombieWalk; // กลับไปเป็นภาพการเดินหลังจาก 1 วินาที
            hurt = false; // ตั้งค่า hurt กลับเป็น false
            app.repaint();
            ((Timer) e.getSource()).stop();
            System.out.println("Zombie is hurt");
        });
        hurtTimer.setRepeats(false);
        hurtTimer.start();

        // ตั้งเวลาให้ซอมบี้หายไปหลังจากแสดงภาพ hurt เสร็จสิ้น
        Timer removeZombieTimer = new Timer(500, ev -> {
            stopWalking();
            x = -1000; // ย้ายซอมบี้ออกจากหน้าจอ
            app.repaint();
            ((Timer) ev.getSource()).stop();
        });
        removeZombieTimer.setRepeats(false);
        removeZombieTimer.start();
    }

    public boolean checkCollisionWithArcher(Archer archer) {
        int collisionBuffer = 120; // ระยะห่างที่ต้องการให้ซอมบี้เข้ามาใกล้ archer มากขึ้น

        Image archerImage = archer.getCurrentImage();
        if (archerImage == null) {
            return false; // ถ้า archerImage เป็น null ให้ return false
        }

        return this.x < (archer.getX() - collisionBuffer) + archerImage.getWidth(null)
                && this.x + this.currentImageZombie.getWidth(null) > (archer.getX() - collisionBuffer)
                && this.y < archer.getY() + archerImage.getHeight(null)
                && this.y + this.currentImageZombie.getHeight(null) > archer.getY();
    }

    public void eatArcher() {
        if (isEating) {
            return; // ถ้ากำลังกินอยู่ ให้ return ออกไป
        }
        isEating = true; // ตั้งสถานะว่าซอมบี้กำลังกิน
    
        System.out.println("Zombie is eating");
        currentImageZombie = imgZombieEat; // เปลี่ยนภาพเป็นการกิน
        app.getArcher().getArcherHurtImg(); // เรียกใช้ฟังก์ชันให้ archer แสดงภาพเจ็บ
        app.incrementZombieBiteCount(); // เพิ่มจำนวนครั้งที่โดนกัด
        app.repaint();
    
        Timer eatTimer = new Timer(1000, e -> {
            stopWalking();
            x = -1000; // ย้ายซอมบี้ออกจากหน้าจอ
            app.spawnNewZombie(); // สร้างซอมบี้ตัวใหม่
            app.repaint();
            isEating = false; // รีเซ็ตสถานะการกิน
            ((Timer) e.getSource()).stop();
        });
        eatTimer.setRepeats(false);
        eatTimer.start();
    }
}

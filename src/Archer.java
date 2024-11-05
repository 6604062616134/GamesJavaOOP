import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import java.util.ArrayList;

public class Archer {
    private int x;
    private int y;
    private Image imgStand;
    private Image imgWalk;
    public Image currentImage;
    private Timer walkTimer;
    private boolean isWalking = false;
    private Image imgWalkleft;
    private Image imgWalkleft2;
    private boolean isWalkingLeft = false;
    private Image imgAttacking;
    public Image imgHurt;
    public boolean isAttacking = false;
    private ArrayList<Arrow> arrows; // รายการลูกศรที่ยิงออกไป
    private Image imgArrow;
    private Timer hurtTimer;
    private App app;
    public boolean isHurt = false;

    public Archer(App app) {
        this.x = 250;
        this.y = 350;
        this.app = app;

        // โหลดภาพต่างๆ
        URL char1 = getClass().getResource("/character/IMG_1002.png");
        imgStand = new ImageIcon(char1).getImage();
        
        URL charWalk = getClass().getResource("/character/IMG_1001.png");
        imgWalk = new ImageIcon(charWalk).getImage();

        URL charWalkleft = getClass().getResource("/character/left2.png");
        imgWalkleft = new ImageIcon(charWalkleft).getImage();

        URL charWalkleft2 = getClass().getResource("/character/leftwalk.png");
        imgWalkleft2 = new ImageIcon(charWalkleft2).getImage();

        URL charHurt = getClass().getResource("/character/hurt.png");
        Image imgHurt = new ImageIcon(charHurt).getImage();

        // โหลดภาพลูกศร
        URL arrow = getClass().getResource("/character/arrow.png");
        imgArrow = new ImageIcon(arrow).getImage();

        URL attack = getClass().getResource("/character/attack.png");
        imgAttacking = new ImageIcon(attack).getImage();

        currentImage = imgStand;

        arrows = new ArrayList<>();

        // ตั้งค่า Timer สำหรับการเดิน
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

        hurtTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImage = imgStand; // กลับเป็นภาพปกติ
                isHurt = false;
                app.repaint();
                ((Timer) e.getSource()).stop();
            }
        });
        hurtTimer.setRepeats(false);
    }

    public void getArcherHurtImg() {
        isHurt = true;
        currentImage = imgHurt;
        hurtTimer.start(); // เริ่ม hurtTimer ก่อน
        app.repaint(); // เรียก repaint หลังเริ่ม timer
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
        return x + 10 >= windowWidth;
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

    public void shoot() {
        if (isAttacking) {
            return; // ถ้ากำลังโจมตีอยู่แล้ว ไม่ให้ยิงอีก
        }
    
        System.out.println("Shooting arrow");
        isAttacking = true;
        currentImage = imgAttacking;
    
        // สร้างลูกศรใหม่และเพิ่มลงในอาร์เรย์
        arrows.add(new Arrow(300, 435, imgArrow));
    
        Timer attackTimer = new Timer(200, new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImage = imgStand;
                isAttacking = false; // เปลี่ยนสถานะกลับเป็นไม่โจมตี
                ((Timer)e.getSource()).stop();
            }
        });
        attackTimer.setRepeats(false);
        attackTimer.start();
    }
    

    public ArrayList<Archer.Arrow> getArrows() {
        return arrows;
    }
    
    //inner class
    public class Arrow {
        private int x = 250;
        private int y = 350;
        private Image img;
        // private boolean hitZombie = false;

        public Arrow(int x, int y, Image img) {
            this.x = x;
            this.y = y;
            this.img = img;
        }

        public void move() {
            x += 500;
        }

        public void draw(Graphics g) {
            g.drawImage(img, x, y, 50, 20, null); // วาดลูกศร
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Image getImg() {
            return img;
        }

        public Image getImgArrow() {
            return imgArrow;
        }

        public void remove(){
            arrows.remove(this);
        }
    }

}

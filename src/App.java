
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class App extends JFrame {

    URL bg1 = getClass().getResource("/background/IMG_0984.png");
    Image imgBG = new ImageIcon(bg1).getImage();

    URL bg2 = getClass().getResource("/background/IMG_0985.png");
    Image imgBG2 = new ImageIcon(bg2).getImage();

    URL bg3 = getClass().getResource("/background/IMG_0986.png");
    Image imgBG3 = new ImageIcon(bg3).getImage();

    URL title = getClass().getResource("/text/title.png");
    Image imgTitle = new ImageIcon(title).getImage();

    URL startBtnImage = getClass().getResource("/buttons/start.png");
    URL exitBtnImage = getClass().getResource("/buttons/quit.png");

    URL heartfull = getClass().getResource("/heart/heart.png");
    Image imgHeartFull = new ImageIcon(heartfull).getImage();

    URL heart2 = getClass().getResource("/heart/heart2.png");
    Image imgHeart2 = new ImageIcon(heart2).getImage();

    URL heart3 = getClass().getResource("/heart/heart3.png");
    Image imgHeart3 = new ImageIcon(heart3).getImage();

    URL bar = getClass().getResource("/progressbar/bar.png");
    Image imgBar = new ImageIcon(bar).getImage();

    URL zombieIcon = getClass().getResource("/progressbar/zombieIcon.png");
    Image imgZombieIcon = new ImageIcon(zombieIcon).getImage();

    URL tryAgain = getClass().getResource("/text/tryagain.png");
    Image imgTryAgain = new ImageIcon(tryAgain).getImage();

    URL next = getClass().getResource("/buttons/nextbtn.png");
    Image imgNext = new ImageIcon(next).getImage();

    private int bgX = 0;
    private boolean isTransitioning = false;
    private float fadeAlpha = 0;
    private Image currentBackground;
    private int currentBgIndex = 0;
    private boolean showTitle = true;
    private boolean gameStarted = false;

    //easy
    private Timer dinner1Timer;
    private Timer house1Timer;
    private Timer morning1Timer;
    private Timer music1Timer;
    private Timer water1Timer;

    //medium
    private Timer goal1Timer;
    private Timer history1Timer;
    private Timer industry1Timer;
    private Timer method1Timer;
    private Timer relation1Timer;
    private Timer task1Timer;

    //hard
    private Timer an1Timer;
    private Timer be1Timer;
    private Timer ce1Timer;
    private Timer de1Timer;
    private Timer dr1Timer;
    private Timer e1Timer;
    private Timer et1Timer;
    private Timer obs1Timer;
    private Timer se1Timer;
    private Timer syn1Timer;

    private Timer changeModeTimer;

    private StringBuilder inputBuffer = new StringBuilder();
    private boolean showTryAgain = false;
    private boolean showNextBtn = false;
    private Timer tryAgainTimer;
    private boolean finishTyped = false;
    public static int state = 0;
    public static boolean isHurt = false;

    final private Image[] backgrounds;

    private Archer archer;
    private Zombie zombie;
    private Zombie boss;
    private Text text;

    private JButton startBtn, exitBtn;

    App() {
        backgrounds = new Image[]{imgBG, imgBG2, imgBG3};
        currentBackground = backgrounds[currentBgIndex];

        DrawArea p = new DrawArea();
        p.setLayout(null);
        archer = new Archer();

        zombie = new BasicZombie(this); //polymorphism
        Zombie zombie2 = new MediumZombie(this);
        //boss = new Boss();

        text = new Text();

        add(p);

        startBtn = new JButton(new ImageIcon(startBtnImage));
        startBtn.setBounds(1200 / 2 - 100, 400, 250, 50);
        startBtn.setBorderPainted(false);
        startBtn.setContentAreaFilled(false);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBtn.setVisible(false);
                exitBtn.setVisible(false);
                gameStarted = true;
                showTitle = false;
                p.repaint();
                zombie.startWalking();
            }
        });
        p.add(startBtn);

        exitBtn = new JButton(new ImageIcon(exitBtnImage));
        exitBtn.setBounds(1200 / 2 - 100, 500, 200, 50);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        p.add(exitBtn);

        //easy
        dinner1Timer = new Timer(300, e -> {
            finishTyped = false;
            state = 1;
            p.repaint();
            dinner1Timer.stop();
        });

        house1Timer = new Timer(300, e -> {
            finishTyped = false; // รีเซ็ต finishTyped เพื่ออนุญาตให้พิมพ์ได้
            state = 2; // เปลี่ยนไปแสดง house1
            p.repaint(); // รีเฟรชเพื่อแสดง house1
            house1Timer.stop(); // หยุด timer หลังจากแสดง house1
        });

        morning1Timer = new Timer(300, e -> {
            finishTyped = false;
            state = 4;
            p.repaint();
            morning1Timer.stop();
        });

        music1Timer = new Timer(300, e -> {
            finishTyped = false;
            state = 6;
            p.repaint();
            music1Timer.stop();
        });

        water1Timer = new Timer(300, e -> {
            finishTyped = false;
            state = 8;
            p.repaint();
            water1Timer.stop();
        });

        tryAgainTimer = new Timer(500, e -> {
            showTryAgain = false;
            inputBuffer.setLength(0);
            repaint();
            tryAgainTimer.stop();
        });

        changeModeTimer = new Timer(300, e -> {
            showNextBtn = true;
            if (zombie != null) {
                zombie.stopWalking();
            }
            //ลบคำออก
            //state = 10;
            repaint();
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!showTitle) {
                    if (!isTransitioning) {
                        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                            if (archer.hasReachedEdge(getWidth())) {
                                initiateSceneTransition();
                            } else {
                                archer.moveRight();
                            }
                        }
                        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                            if (archer.hasReachedEdge(getWidth())) {
                                //ให้หยุดที่ขอบของหน้าจอ
                                archer.stopWalking();
                            } else {
                                archer.moveLeft();
                            }
                        }
                        p.repaint();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!showTitle) {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        archer.stopWalking();
                        p.repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        archer.stopWalkingLeft();
                        archer.stopWalking();
                        p.repaint();
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (!showTitle) {
                    char keyChar = e.getKeyChar();
                    inputBuffer.append(keyChar); // เก็บตัวอักษรที่พิมพ์ลงใน inputBuffer

                    if (gameStarted) {
                        switch (state) {
                            //easy
                            case 0:
                                if (inputBuffer.toString().equalsIgnoreCase("dinner")) {
                                    archer.shoot(); // ยิงเมื่อพิมพ์ถูก
                                    isHurt = true;
                                    inputBuffer.setLength(0); // ล้าง inputBuffer
                                    finishTyped = true;
                                    state = 1; // เปลี่ยน state ไปที่ 1
                                    zombie.stopWalking(); // หยุดซอมบี้ตัวปัจจุบัน
                                    spawnNewZombie(); // สร้างซอมบี้ตัวใหม่
                                    house1Timer.start(); // แสดงคำต่อไป
                                    repaint();
                                } else if (inputBuffer.length() > 6) {
                                    showTryAgain = true;
                                    tryAgainTimer.start();
                                    inputBuffer.setLength(0);
                                    repaint();
                                }
                                break;
                            case 2:
                                if (inputBuffer.toString().equalsIgnoreCase("house")) {
                                    archer.shoot();
                                    isHurt = true;
                                    inputBuffer.setLength(0);
                                    finishTyped = true;
                                    state = 3; // เปลี่ยนไปคำถัดไป
                                    zombie.stopWalking();
                                    spawnNewZombie(); // ซอมบี้ตัวใหม่เริ่มเดิน
                                    morning1Timer.start();
                                    repaint();
                                } else if (inputBuffer.length() > 5) {
                                    showTryAgain = true;
                                    tryAgainTimer.start();
                                    inputBuffer.setLength(0);
                                    repaint();
                                }
                                break;
                            case 4:
                                if (inputBuffer.toString().equalsIgnoreCase("morning")) {
                                    archer.shoot();
                                    isHurt = true;
                                    inputBuffer.setLength(0);
                                    finishTyped = true;
                                    state = 5; // เปลี่ยนไปคำถัดไป
                                    zombie.stopWalking();
                                    spawnNewZombie(); // ซอมบี้ตัวใหม่เริ่มเดิน
                                    music1Timer.start();
                                    repaint();
                                } else if (inputBuffer.length() > 7) {
                                    showTryAgain = true;
                                    tryAgainTimer.start();
                                    inputBuffer.setLength(0);
                                    repaint();
                                }
                                break;
                            case 6:
                                if (inputBuffer.toString().equalsIgnoreCase("music")) {
                                    archer.shoot();
                                    isHurt = true;
                                    inputBuffer.setLength(0);
                                    finishTyped = true;
                                    state = 7; // เปลี่ยนไปคำถัดไป
                                    zombie.stopWalking();
                                    spawnNewZombie(); // ซอมบี้ตัวใหม่เริ่มเดิน
                                    water1Timer.start(); // แสดงคำต่อไป
                                    repaint();
                                } else if (inputBuffer.length() > 5) {
                                    showTryAgain = true;
                                    tryAgainTimer.start();
                                    inputBuffer.setLength(0);
                                    repaint();
                                }
                                break;
                            case 8:
                                if (inputBuffer.toString().equalsIgnoreCase("water")) {
                                    archer.shoot();
                                    isHurt = true;
                                    inputBuffer.setLength(0);
                                    finishTyped = true;
                                    state = 9; // เปลี่ยนไปคำถัดไป
                                    showNextBtn = true;
                                    zombie.stopWalking(); // หยุดการเดินของซอมบี้
                                    changeModeTimer.start(); // เริ่ม timer สำหรับเปลี่ยนโหมด
                                    repaint();
                                } else if (inputBuffer.length() > 5) {
                                    showTryAgain = true;
                                    tryAgainTimer.start();
                                    inputBuffer.setLength(0);
                                    repaint();
                                }
                                break;
                            //medium
                            //case 10    
                        }
                    }
                }
            }

        });

        setFocusable(true);
    }

    private void spawnNewZombie() {
        if (zombie != null) {
            zombie.stopWalking(); // หยุดการเดินของซอมบี้เก่า
        }
        zombie = new BasicZombie(this); // ใช้ polymorphism เพื่อสร้างซอมบี้ใหม่
        zombie.startWalking();
        repaint();
    }

    private void initiateSceneTransition() {
        isTransitioning = true;
        fadeAlpha = 0; // Reset fadeAlpha
        Timer transitionTimer = new Timer(40, new ActionListener() { // Update every 40 ms
            @Override
            public void actionPerformed(ActionEvent e) {
                fadeAlpha += 0.05f; // Increase alpha value for fade-out effect
                if (fadeAlpha >= 1) {
                    fadeAlpha = 1; // Clamp alpha value
                    currentBgIndex = (currentBgIndex + 1) % backgrounds.length; // Move to next background
                    currentBackground = backgrounds[currentBgIndex]; // Switch to next background

                    // Reset archer position
                    archer.resetPosition(); // Reset archer position

                    bgX = 0; // Reset background position
                }
                if (fadeAlpha >= 1) {
                    ((Timer) e.getSource()).stop(); // Stop timer after fade-out completes
                    isTransitioning = false; // End transition
                    fadeAlpha = 0; // Reset fadeAlpha for next transition
                }
                repaint(); // Redraw to reflect fade effect and character reposition
            }
        });
        transitionTimer.start();
    }

    class DrawArea extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(currentBackground, bgX, 0, getWidth(), getHeight(), this);
            g.drawImage(currentBackground, bgX + getWidth(), 0, getWidth(), getHeight(), this);

            if (gameStarted) {
                // if (finishTyped) {
                //     spawnNewZombie();
                //     repaint();
                // }

                int textX = 1200 / 2 - 150;
                int textY = 150;
                int heartX = 20;
                int heartY = 20;
                int barX = 820;
                int barY = 25;
                int zombieIconX = 1090;
                int zombieIconY = 17;

                if (showNextBtn) {
                    g.drawImage(imgNext, 1150, 400, 25, 25, this);
                    g.drawImage(imgHeartFull, heartX, heartY, 200, 50, this);
                    g.drawImage(imgBar, barX, barY, 320, 30, this);
                    g.drawImage(imgZombieIcon, zombieIconX, zombieIconY, 50, 50, this);
                } else {
                    g.drawImage(imgHeartFull, heartX, heartY, 200, 50, this);
                    g.drawImage(imgBar, barX, barY, 320, 30, this);
                    g.drawImage(imgZombieIcon, zombieIconX, zombieIconY, 50, 50, this);

                    int zombiex = zombie.getX();
                    int zombiey = zombie.getY();

                    if (zombie.getX() >= 0) {
                        if (isHurt) {
                            g.drawImage(zombie.getImgZombieHurt(), zombiex, zombiey, 120, 150, this);
                            isHurt = false;
                        } else {
                            g.drawImage(zombie.getCurrentImage(), zombiex, zombiey, 120, 150, this);
                        }
                    }

                    switch (state) {
                        //easy
                        case 0:
                            g.drawImage(text.getImgdinner1(), textX, textY, 300, 50, this);
                            break;
                        case 1:
                            g.drawImage(text.getImgdinner2(), textX, textY, 300, 50, this);
                            break;
                        case 2:
                            g.drawImage(text.getImghouse1(), textX, textY, 300, 50, this);
                            break;
                        case 3:
                            g.drawImage(text.getImghouse2(), textX, textY, 300, 50, this);
                            break;
                        case 4:
                            g.drawImage(text.getImgmorning1(), textX - 10, textY, 350, 50, this);
                            break;
                        case 5:
                            g.drawImage(text.getImgmorning2(), textX - 10, textY, 350, 50, this);
                            break;
                        case 6:
                            g.drawImage(text.getImgmusic1(), textX + 20, textY, 270, 50, this);
                            break;
                        case 7:
                            g.drawImage(text.getImgmusic2(), textX + 20, textY, 270, 50, this);
                            break;
                        case 8:
                            g.drawImage(text.getImgwater1(), textX + 20, textY, 270, 50, this);
                            break;
                        case 9:
                            g.drawImage(text.getImgwater2(), textX + 20, textY, 270, 50, this);
                            break;

                        //medium
                        case 10:
                            g.drawImage(text.getImggoal1(), textX, textY, 300, 50, this);
                            break;
                        case 11:
                            g.drawImage(text.getImggoal2(), textX, textY, 300, 50, this);
                            break;
                        case 12:
                            g.drawImage(text.getImghistory1(), textX, textY, 300, 50, this);
                            break;
                        case 13:
                            g.drawImage(text.getImghistory2(), textX, textY, 300, 50, this);
                            break;
                        case 14:
                            g.drawImage(text.getImgindustry1(), textX, textY, 300, 50, this);
                            break;
                        case 15:
                            g.drawImage(text.getImgindustry2(), textX, textY, 300, 50, this);
                            break;
                        case 16:
                            g.drawImage(text.getImgmethod1(), textX, textY, 300, 50, this);
                            break;
                        case 17:
                            g.drawImage(text.getImgmethod2(), textX, textY, 300, 50, this);
                            break;
                        case 18:
                            g.drawImage(text.getImgrelation1(), textX, textY, 300, 50, this);
                            break;
                        case 19:
                            g.drawImage(text.getImgrelation2(), textX, textY, 300, 50, this);
                            break;
                        case 20:
                            g.drawImage(text.getImgtask1(), textX, textY, 300, 50, this);
                            break;
                        case 21:
                            g.drawImage(text.getImgtask2(), textX, textY, 300, 50, this);
                            break;
                        //hard
                        case 22:
                            g.drawImage(text.getImgan1(), textX, textY, 300, 50, this);
                            break;
                        case 23:
                            g.drawImage(text.getImgan2(), textX, textY, 300, 50, this);
                            break;
                        case 24:
                            g.drawImage(text.getImgbe1(), textX, textY, 300, 50, this);
                            break;
                        case 25:
                            g.drawImage(text.getImgbe2(), textX, textY, 300, 50, this);
                            break;
                        case 26:
                            g.drawImage(text.getImgce1(), textX, textY, 300, 50, this);
                            break;
                        case 27:
                            g.drawImage(text.getImgce2(), textX, textY, 300, 50, this);
                            break;
                        case 28:
                            g.drawImage(text.getImgde1(), textX, textY, 300, 50, this);
                            break;
                        case 29:
                            g.drawImage(text.getImgde2(), textX, textY, 300, 50, this);
                            break;
                        case 30:
                            g.drawImage(text.getImgdr1(), textX, textY, 300, 50, this);
                            break;
                        case 31:
                            g.drawImage(text.getImgdr2(), textX, textY, 300, 50, this);
                            break;
                        case 32:
                            g.drawImage(text.getImge1(), textX, textY, 300, 50, this);
                            break;
                        case 33:
                            g.drawImage(text.getImge2(), textX, textY, 300, 50, this);
                            break;
                        case 34:
                            g.drawImage(text.getImget1(), textX, textY, 300, 50, this);
                            break;
                        case 35:
                            g.drawImage(text.getImget2(), textX, textY, 300, 50, this);
                            break;
                        case 36:
                            g.drawImage(text.getImgobs1(), textX, textY, 300, 50, this);
                            break;
                        case 37:
                            g.drawImage(text.getImgobs2(), textX, textY, 300, 50, this);
                            break;
                        case 38:
                            g.drawImage(text.getImgse1(), textX, textY, 300, 50, this);
                            break;
                        case 39:
                            g.drawImage(text.getImgse2(), textX, textY, 300, 50, this);
                            break;
                        case 40:
                            g.drawImage(text.getImgsyn1(), textX, textY, 300, 50, this);
                            break;
                        case 41:
                            g.drawImage(text.getImgsyn2(), textX, textY, 300, 50, this);
                            break;

                        default:
                            break;
                    }
                }

                if (showTryAgain) {
                    g.drawImage(imgTryAgain, 475, 600, 200, 30, this);
                }

                g.drawImage(archer.getCurrentImage(), archer.getX(), archer.getY(), 150, 150, this);

                for (Archer.Arrow arrow : archer.getArrows()) {
                    arrow.move();
                    g.drawImage(arrow.getImg(), arrow.getX(), arrow.getY(), 80, 30, null);

                    if (arrow.getX() + 80 > zombie.getX() && arrow.getX() < zombie.getX() + 120
                            && arrow.getY() + 30 > zombie.getY() && arrow.getY() < zombie.getY() + 150) {

                        archer.getArrows().remove(arrow);
                        zombie.takeDamage();
                        break;
                    }
                }

            }

            if (showTitle) {
                int newWidth = 500;
                int newHeight = 130;
                int x = 1200 / 2 - newWidth / 2;
                int y = 150;
                g.drawImage(imgTitle, x, y, newWidth, newHeight, this);
            }

            // If transitioning, draw a black rectangle with transparency
            if (isTransitioning) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha)); // Set transparency
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }

    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new App();

        frame.setTitle("Word archer : Zombie hunt");
        frame.setSize(1200, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //
        frame.setVisible(true);
    }
}

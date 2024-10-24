import java.awt.*;
import java.net.URL;
import javax.swing.*;
import java.awt.event.*;

public class App extends JFrame {

    URL bg1 = getClass().getResource("/background/IMG_0984.png");
    Image imgBG = new ImageIcon(bg1).getImage();
    
    URL bg2 = getClass().getResource("/background/IMG_0985.png");
    Image imgBG2 = new ImageIcon(bg2).getImage();
    
    URL bg3 = getClass().getResource("/background/IMG_0986.png");
    Image imgBG3 = new ImageIcon(bg3).getImage();

    URL zombie1 = getClass().getResource("/zombie/IMG_1053.png");
    Image imgZombie = new ImageIcon(zombie1).getImage();

    URL zombieWalk = getClass().getResource("/zombie/IMG_1055.png");
    Image imgZombieWalk = new ImageIcon(zombieWalk).getImage();

    URL title = getClass().getResource("/text/title.png");
    Image imgTitle = new ImageIcon(title).getImage();

    URL startBtnImage = getClass().getResource("/buttons/start.png");
    URL exitBtnImage = getClass().getResource("/buttons/quit.png");
    
    private int bgX = 0;
    private boolean isTransitioning = false;
    private float fadeAlpha = 0;
    private Image currentBackground;
    private int currentBgIndex = 0;
    private Image currentImageZombie;
    private boolean isWalking = false;
    private boolean showTitle = true;

    private Image[] backgrounds;

    private Archer archer;

    final private Zombie zombie;

    private JButton startBtn, exitBtn;

    App(){
        backgrounds = new Image[]{imgBG, imgBG2, imgBG3};
        currentBackground = backgrounds[currentBgIndex];

        archer = new Archer();

        zombie = new Zombie(){
            private int x = 0;
            final private int y = 300;
            @Override
            public void startWalking() {
                isWalking = true;
            }

            @Override
            public void stopWalking() {
                isWalking = false;
            }

            @Override
            public void moveRight() {
                x += 5;
            }

            @Override
            public boolean hasReachedEdge(int windowWidth) {
                return x >= windowWidth;
            }

            @Override
            public void resetPosition() {
                x = 0;
            }

            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }

            @Override
            public Image getCurrentImage() {
                return imgZombie;
            }
        };

        DrawArea p = new DrawArea();
        p.setLayout(null);

        currentImageZombie = imgZombie;

        add(p);

       // Add Start Button with Image
       startBtn = new JButton(new ImageIcon(startBtnImage));
       startBtn.setBounds(1200 / 2 - 100, 400, 250, 50);  // ตั้งตำแหน่งปุ่ม Start
       startBtn.setBorderPainted(false);  // ลบเส้นขอบ
       startBtn.setContentAreaFilled(false);  // ลบพื้นหลัง
       startBtn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               startBtn.setVisible(false);
               exitBtn.setVisible(false);
               p.repaint();  // เริ่มเกมใหม่
           }
       });
       p.add(startBtn);

        // Add Exit Button with Image
        exitBtn = new JButton(new ImageIcon(exitBtnImage));
        exitBtn.setBounds(1200 / 2 - 100, 500, 200, 50);  // ตั้งตำแหน่งปุ่ม Quit
        exitBtn.setBorderPainted(false);  // ลบเส้นขอบ
        exitBtn.setContentAreaFilled(false);  // ลบพื้นหลัง
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // ออกจากเกม
            }
        });
        p.add(exitBtn);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (showTitle) {
                    // Turn off the title screen on any key press
                    showTitle = false;
                    p.repaint();
                }
                if (!isTransitioning) {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (archer.hasReachedEdge(getWidth())) {
                            initiateSceneTransition();
                        } else {
                            archer.moveRight(); 
                        }
                    }
                    if(e.getKeyCode() == KeyEvent.VK_LEFT){
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

            @Override
            public void keyReleased(KeyEvent e) {
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
        });

        setFocusable(true);

        Timer zombieTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWalking) {
                    currentImageZombie = (currentImageZombie == imgZombie) ? imgZombie : imgZombieWalk;
                }
                repaint();
            }
        });
        zombieTimer.start();
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
                    ((Timer)e.getSource()).stop(); // Stop timer after fade-out completes
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
            
            // Draw the current background image
            g.drawImage(currentBackground, bgX, 0, getWidth(), getHeight(), this);
            g.drawImage(currentBackground, bgX + getWidth(), 0, getWidth(), getHeight(), this);

            // Draw the archer character
            g.drawImage(archer.getCurrentImage(), archer.getX(), archer.getY(), 150, 150, this); // Adjust the size

            // Draw the zombie character
            g.drawImage(zombie.getCurrentImage(), zombie.getX(), zombie.getY(), 120, 150, this); // Adjust the size

            if (showTitle) {
                int newWidth = 500; 
                int newHeight = 130;
                int x = 1200/2 - newWidth/2;
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
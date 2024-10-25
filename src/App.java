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

    URL title = getClass().getResource("/text/title.png");
    Image imgTitle = new ImageIcon(title).getImage();

    URL startBtnImage = getClass().getResource("/buttons/start.png");
    URL exitBtnImage = getClass().getResource("/buttons/quit.png");

    URL heartfull = getClass().getResource("/heart/heart.png");
    Image imgHeartFull = new ImageIcon(heartfull).getImage();

    URL heart2= getClass().getResource("/heart/heart2.png");
    Image imgHeart2 = new ImageIcon(heart2).getImage();

    URL heart3 = getClass().getResource("/heart/heart3.png");
    Image imgHeart3 = new ImageIcon(heart3).getImage();

    URL bar = getClass().getResource("/progressbar/bar.png");
    Image imgBar = new ImageIcon(bar).getImage();

    URL zombieIcon = getClass().getResource("/progressbar/zombieIcon.png");
    Image imgZombieIcon = new ImageIcon(zombieIcon).getImage();
    
    private int bgX = 0;
    private boolean isTransitioning = false;
    private float fadeAlpha = 0;
    private Image currentBackground;
    private int currentBgIndex = 0;
    private boolean showTitle = true;
    private boolean gameStarted = false;

    final private Image[] backgrounds;

    private Archer archer;

    private JButton startBtn, exitBtn;

    private Zombie zombie;

    private Zombie boss;

    App(){
        backgrounds = new Image[]{imgBG, imgBG2, imgBG3};
        currentBackground = backgrounds[currentBgIndex];

        archer = new Archer();
        
        DrawArea p = new DrawArea();
        p.setLayout(null);
        
        zombie = new BasicZombie(this);
        //boss = new Boss();

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
               zombie.startWalking();
               p.repaint();
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

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!showTitle){
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
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!showTitle){
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
        });

        setFocusable(true);
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
            g.drawImage(archer.getCurrentImage(), archer.getX(), archer.getY(), 150, 150, this);

            // Draw the zombie character
            if(gameStarted){
                int zombiex = zombie.getX();
                int zombiey = zombie.getY();
                g.drawImage(zombie.getCurrentImage(), zombiex, zombiey, 120, 150, this);

                int heartX = 20;
                int heartY = 20;
                g.drawImage(imgHeartFull, heartX, heartY, 200, 50, this);
                // g.drawImage(imgHeart2, heartX + 50, heartY, 50, 50, this);
                // g.drawImage(imgHeart3, heartX + 100, heartY, 50, 50, this);

                int barX = 820;
                int barY = 25;
                g.drawImage(imgBar, barX, barY, 320, 30, this);

                int zombieIconX = 1090;
                int zombieIconY = 17;
                g.drawImage(imgZombieIcon, zombieIconX, zombieIconY, 50, 50, this);

            }

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
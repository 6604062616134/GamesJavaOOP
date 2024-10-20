import java.awt.*;
import java.net.URL;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class App extends JFrame implements Zombie {
    private int level;
    private int score;
    private int lives;

    URL bg1 = getClass().getResource("IMG_0984.png");
    Image imgBG = new ImageIcon(bg1).getImage();
    
    URL bg2 = getClass().getResource("IMG_0985.png");
    Image imgBG2 = new ImageIcon(bg2).getImage();
    
    URL bg3 = getClass().getResource("IMG_0986.png");
    Image imgBG3 = new ImageIcon(bg3).getImage();

    
    private int bgX = 0; // x-coordinate of the background
    private boolean isTransitioning = false; // flag for scene transition
    private float fadeAlpha = 0; // alpha value for fade effect
    private Image currentBackground; // Current background being displayed
    private int currentBgIndex = 0; // To track the current background

    //Backgrounds array for easy access
    private Image[] backgrounds;

    private Archer archer; // Archer character object

    App(){
        //Initialize the array of backgrounds
        backgrounds = new Image[]{imgBG, imgBG2, imgBG3};
        currentBackground = backgrounds[currentBgIndex]; // Set the initial background

        archer = new Archer(); // Create an Archer object

        DrawArea p = new DrawArea();
        add(p);

        // Add key listener to detect movement and transition
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!isTransitioning) {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (archer.hasReachedEdge(getWidth())) {
                            initiateSceneTransition(); // Trigger scene transition
                        } else {
                            archer.moveRight(); // Move archer when right key is pressed
                        }
                    }
                    p.repaint(); // Repaint the frame
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    archer.stopWalking(); // Stop archer when key is released
                    p.repaint();
                }
            }
        });

        setFocusable(true); // Ensure key events are captured
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

    // Inner class for drawing area
    class DrawArea extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Draw the current background image
            g.drawImage(currentBackground, bgX, 0, getWidth(), getHeight(), this);
            g.drawImage(currentBackground, bgX + getWidth(), 0, getWidth(), getHeight(), this);

            // Draw the archer character
            g.drawImage(archer.getCurrentImage(), archer.getX(), archer.getY(), 150, 150, this); // Adjust the size

            // If transitioning, draw a black rectangle with transparency
            if (isTransitioning) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha)); // Set transparency
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    @Override
    public void startWalking() {
        
    }

    @Override
    public void stopWalking() {
        
    }

    @Override
    public void moveRight() {
        
    }

    @Override
    public boolean hasReachedEdge(int windowWidth) {
        return false;
    }

    @Override
    public void resetPosition() {
        
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public Image getCurrentImage() {
        return null;
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new App();
        
        frame.setTitle("Word archer : Zombie hunt");
        frame.setSize(1200, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close frame and return memory to OS
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

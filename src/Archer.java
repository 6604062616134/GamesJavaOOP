import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class Archer {
    private int x; // x-coordinate of the character
    private int y; // y-coordinate of the character
    private Image imgStand; // Image when standing
    private Image imgWalk; // Image when walking
    private Image currentImage; // Currently displayed image
    private Timer walkTimer; // Timer to switch images for walking animation
    private boolean isWalking = false;

    public Archer() {
        this.x = 250; // Initial x-coordinate
        this.y = 350; // Initial y-coordinate

        // Load images for standing and walking
        URL char1 = getClass().getResource("IMG_1002.png");
        imgStand = new ImageIcon(char1).getImage();
        
        URL charWalk = getClass().getResource("IMG_1001.png");
        imgWalk = new ImageIcon(charWalk).getImage();

        currentImage = imgStand; // Initially standing

        // Timer for switching between standing and walking images
        walkTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch between standing and walking images
                if (isWalking) {
                    currentImage = (currentImage == imgStand) ? imgWalk : imgStand;
                }
            }
        });
    }

    // Start the walking animation
    public void startWalking() {
        isWalking = true;
        walkTimer.start(); // Start switching between walking and standing
    }

    // Stop the walking animation and set the character back to standing
    public void stopWalking() {
        isWalking = false;
        walkTimer.stop(); // Stop switching images
        currentImage = imgStand; // Set image back to standing
    }

    // Move the character to the right
    public void moveRight() {
        x += 10;
        startWalking(); // Start walking animation when moving
    }

    // Check if character has reached the right edge of the window
    public boolean hasReachedEdge(int windowWidth) {
        return x + 200 >= windowWidth; // Assuming character width is 200px
    }

    // Reset the character's position
    public void resetPosition() {
        x = 250; // Reset to the initial position
    }

    // Getter for x-coordinate
    public int getX() {
        return x;
    }

    // Getter for y-coordinate
    public int getY() {
        return y;
    }

    // Getter for current image to display
    public Image getCurrentImage() {
        return currentImage;
    }
}

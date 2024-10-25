import java.awt.Image;

public interface Zombie {
    void startWalking();
    void stopWalking();
    boolean isWalking();
    void moveLeft();
    boolean reachArcher(int width);
    void resetPosition();
    int getX();
    int getY();
    Image getCurrentImage();
    void eat();
}

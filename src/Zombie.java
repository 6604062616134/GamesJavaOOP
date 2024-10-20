import java.awt.Image;

public interface Zombie {
    public void startWalking();
    public void stopWalking();
    public void moveRight();
    public boolean hasReachedEdge(int windowWidth);
    public void resetPosition();
    public int getX();
    public int getY();
    public Image getCurrentImage();
}

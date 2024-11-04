public class MediumZombie extends Zombie{
    public MediumZombie(App app) {
        super(app);
    }

    @Override
    public void moveLeft() {
        x -= 5;
        isImageZombie = !isImageZombie;
    }

    @Override
    public void eat() {
        currentImageZombie = imgZombieEat;
    }
    
}

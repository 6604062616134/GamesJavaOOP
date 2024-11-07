public class MediumZombie extends Zombie{
    public MediumZombie(App app) {
        super(app);
    }

    @Override
    public void moveLeft() {
        x -= 15;
        isImageZombie = !isImageZombie;
    }
}

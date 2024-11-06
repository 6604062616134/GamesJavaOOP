public class MediumZombie extends Zombie{
    public MediumZombie(App app) {
        super(app);
    }

    @Override
    public void moveLeft() {
        x -= 7;
        isImageZombie = !isImageZombie;
    }
}

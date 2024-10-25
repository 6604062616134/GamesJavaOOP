public class BasicZombie extends Zombie{
    public BasicZombie(App app) {
        super(app);
    }

    @Override
    public void Hurt() {
        currentImageZombie = imgZombieHurt;
    }

    @Override
    public void eat() {
        currentImageZombie = imgZombieEat;
    }
}
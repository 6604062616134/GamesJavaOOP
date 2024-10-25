public class BasicZombie extends Zombie{
    public BasicZombie(App app) {
        super(app);
    }

    @Override
    public void Hurt() {
        System.out.println("BasicZombie is hurt");
    }

    @Override
    public void eat() {
        System.out.println("BasicZombie is eating");
    }
}
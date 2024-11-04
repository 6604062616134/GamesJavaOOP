public class Boss extends Zombie{
    public Boss(App app) {
        super(app);
    }

    @Override
    public void eat() {
        System.out.println("Boss is eating");
    }
}
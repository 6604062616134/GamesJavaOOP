import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class Boss extends Zombie{
    URL Boss = getClass().getResource("/boss/boss4.png");
    Image imgBoss = new ImageIcon(Boss).getImage();

    // URL BossWalk = getClass().getResource("/boss/boss5.png");
    // Image imgBossWalk = new ImageIcon(BossWalk).getImage();

    URL BossHurt = getClass().getResource("/boss/boss2.png");
    Image imgBossHurt = new ImageIcon(BossHurt).getImage();

    URL BossDead = getClass().getResource("/boss/boss1.png");
    Image imgBossDead = new ImageIcon(BossDead).getImage();

    URL BossDamaged = getClass().getResource("/boss/boss3.png");
    Image imgBossDamaged = new ImageIcon(BossDamaged).getImage();

    public Boss(App app) {
        super(app);
    }

    @Override
    public void Hurt() {
        System.out.println("Boss is hurt");
    }

    @Override
    public void eat() {
        System.out.println("Boss is eating");
    }
}

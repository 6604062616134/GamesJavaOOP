import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class component {

    //start screen
    URL title = getClass().getResource("/text/title.png");
    Image imgTitle = new ImageIcon(title).getImage();

    URL startBtnImage = getClass().getResource("/buttons/start.png");
    Image imgStartBtn = new ImageIcon(startBtnImage).getImage();
    URL exitBtnImage = getClass().getResource("/buttons/quit.png");
    Image imgExitBtn = new ImageIcon(exitBtnImage).getImage();

    //heart
    URL heartfull = getClass().getResource("/heart/heart.png");
    Image imgHeartFull = new ImageIcon(heartfull).getImage();

    URL heart2 = getClass().getResource("/heart/heart2.png");
    Image imgHeart2 = new ImageIcon(heart2).getImage();

    URL heart3 = getClass().getResource("/heart/heart3.png");
    Image imgHeart3 = new ImageIcon(heart3).getImage();

    URL HeartEmpty = getClass().getResource("/heart/heartempty.png");
    Image imgHeartEmpty = new ImageIcon(HeartEmpty).getImage();

    //progressbar
    URL bar = getClass().getResource("/progressbar/bar.png");
    Image imgBar = new ImageIcon(bar).getImage();

    URL zombieIcon = getClass().getResource("/progressbar/zombieIcon.png");
    Image imgZombieIcon = new ImageIcon(zombieIcon).getImage();

    URL tryAgain = getClass().getResource("/text/tryagain.png");
    Image imgTryAgain = new ImageIcon(tryAgain).getImage();

    //ending
    URL menu = getClass().getResource("/buttons/menubtn.png");
    Image imgMenu = new ImageIcon(menu).getImage();

    URL win = getClass().getResource("/text/win.png");
    Image imgWin = new ImageIcon(win).getImage();

    URL lose = getClass().getResource("/buttons/lose.png");
    Image imgLose = new ImageIcon(lose).getImage();

    public Image getImgTitle(){
        return imgTitle;
    }

    public Image getStartBtnImage(){
        return imgStartBtn;
    }

    public Image getExitBtnImage(){
        return imgExitBtn;
    }

    public Image getImgHeartFull(){
        return imgHeartFull;
    }

    public Image getImgHeart2(){
        return imgHeart2;
    }

    public Image getImgHeart3(){
        return imgHeart3;
    }

    public Image getImgHeartEmpty(){
        return imgHeartEmpty;
    }

    public Image getImgBar(){
        return imgBar;
    }

    public Image getImgZombieIcon(){
        return imgZombieIcon;
    }

    public Image getImgTryAgain(){
        return imgTryAgain;
    }

    public Image getImgMenu(){
        return imgMenu;
    }

    public Image getImgWin(){
        return imgWin;
    }

    public Image getImgLose(){
        return imgLose;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EnemyShip extends JLabel{
    int x;
    int y;
    Game mainGame;
    private ImageIcon icon;
    public EnemyShip(int x, int y,Game parent) {
        icon = new ImageIcon("enemyship.png");
        this.setIcon(icon);
        this.x = x;
        this.y = y;
        this.mainGame = parent;
        this.setBounds(x,y,this.getWidth(),this.getHeight());
    }
    public void moveenemyshipxright(){
        this.x+=32;
        this.setBounds(x,y,this.getWidth(),this.getHeight());
    }
    public void moveenemyshipxleft(){
        this.x-=32;
        this.setBounds(x,y,this.getWidth(),this.getHeight());
    }

    public void moveenemyshipy(){
//        System.out.println(level);
        if (mainGame.getLevel()==1) {
            this.y += 32;
            this.setBounds(x, y, this.getWidth(), this.getHeight());
        }
        if (mainGame.getLevel()==2) {
            this.y += 64;
            this.setBounds(x, y, this.getWidth(), this.getHeight());
        }
    }
    public int getWidth(){
        return icon.getIconWidth();
    }
    public int getHeight(){
        return icon.getIconHeight();
    }
}

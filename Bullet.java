import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bullet extends JLabel{ //implements ActionListener {
    int x;
    int y;
    private ImageIcon icon;

    public Bullet(int x,int y) {
        icon = new ImageIcon("bullet.png");
        this.setIcon(icon);
        this.x = x;
        this.y = y;
        this.setBounds(x,y,this.getWidth(),this.getHeight());
    }
    public void movebullets(){
        this.y-=10;
        this.setBounds(x,y,this.getWidth(),this.getHeight());
    }
    public int getWidth(){
        return icon.getIconWidth();
    }
    public int getHeight(){
        return icon.getIconHeight();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Asteroid extends JLabel{
    int x;
    int y;
    private ImageIcon icon;
    public Asteroid(int x,int y) {
        icon = new ImageIcon("asteroid.png");
        this.setIcon(icon);
        this.x = x;
        this.y = y;
        this.setBounds(x, y, this.getWidth(), this.getHeight());
    }
    public void moveasteroidy(){
        this.y+=getHeight()/5;
        this.setBounds(x,y,this.getWidth(),this.getHeight());
    }
    public int getWidth(){
        return icon.getIconWidth();
    }
    public int getHeight(){
        return icon.getIconHeight();
    }
}



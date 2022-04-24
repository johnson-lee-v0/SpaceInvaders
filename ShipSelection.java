import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShipSelection extends ImageIcon implements ActionListener {
    JButton ship1, ship2;
    JPanel middle;
    JLabel select;
    JDialog frame;

    ImageIcon imgship1, imgship2;

    public ShipSelection() {
        frame = new JDialog((JFrame)null, "Select Ship:", true);
        frame.setBounds(100,100,500,500);
        imgship1 = new ImageIcon("spaceship.png");
        imgship2 = new ImageIcon("spaceship2.png");
        frame.setLayout(new BorderLayout());
        // Title of space ship selection
        select = new JLabel("Select a space ship");
        frame.add(select, BorderLayout.NORTH);
        //Option of ship
        middle = new JPanel(new GridLayout(1, 2));
        frame.add(middle, BorderLayout.CENTER);
        ship1 = new JButton(imgship1);
        ship2 = new JButton(imgship2);
        ship1.addActionListener(this);
        ship2.addActionListener(this);
        middle.add(ship1);
        middle.add(ship2);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ship1) {
            this.setImage(imgship1.getImage());
            frame.dispose();

        }
        if (e.getSource() == ship2) {
            this.setImage(imgship2.getImage());
            frame.dispose();
        }

    }

}
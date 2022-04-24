import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen extends JFrame implements ActionListener {
    JLabel GameTitle;
    JButton Start, Help, Exit;
    JFrame Title;

    public TitleScreen() {
        //https://giphy.com/gifs/dark-galaxy-matter-VbZAQbvmSSbf2 Background image
        ImageIcon image = new ImageIcon("background.gif");
        Title = new JFrame("Space Invaders");
        Title.setContentPane(new JLabel(image));
        Title.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        // https://stackoverflow.com/a/29813618 for how to add space between buttons
        gbc.insets = new Insets(10, 0, 10, 0);

        // Adding Space to Separate Game Name and Start Button
        Title.add(Box.createVerticalStrut(420));

        // Game Name on Title Screen
        GameTitle = new JLabel("Space Invasion");
        Title.add(GameTitle, gbc);
        GameTitle.setFont(new Font("Papyrus", Font.PLAIN, 40));
        GameTitle.setForeground(Color.white);

        // Start Button
        Start = new JButton("Start");
        Title.add(Start, gbc);
        Start.addActionListener(this);
        Start.setFont(new Font("Snap ITC", Font.PLAIN, 40));
        //https://stackoverflow.com/a/30258191 For Border
        Start.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));

        //Help Button
        Help = new JButton("Help");
        Title.add(Help, gbc);
        Help.setFont(new Font("Snap ITC", Font.PLAIN, 40));
        Help.addActionListener(this);
        //https://stackoverflow.com/a/30258191 For Border
        Help.setBorder(BorderFactory.createLineBorder(Color.RED, 5));

        //Exit
        Exit = new JButton("Exit");
        Title.add(Exit, gbc);
        Exit.addActionListener(this);
        Exit.setFont(new Font("Snap ITC", Font.PLAIN, 40));
        //https://stackoverflow.com/a/30258191 For Border
        Exit.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));


        Title.setExtendedState(MAXIMIZED_BOTH);
        Title.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Title.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Start) {
            Title.dispose();
//            new ShipSelection();
            new Game();

        }
        if (e.getSource() == Help) {
            JOptionPane.showMessageDialog(null,"Hold down the right side of the mosue and move the mouse in order to steer the spaceship through outerspace");
        }
        if (e.getSource() == Exit) {
            this.dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new TitleScreen();
    }
}

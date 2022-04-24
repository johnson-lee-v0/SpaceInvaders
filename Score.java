import javax.swing.*;
import java.awt.*;
import java.text.*;
import java.io.*;

public class Score extends JDialog {
    JLabel ScoreLabel = new JLabel();
    JLabel TitleLabel = new JLabel();
    int[] highScores = new int[10];
    String[] names = new String[10];
    String[] listItems = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
    JList<String> scoreList = new JList<String>(listItems);

    public Score(int score, Frame owner) {
        //Window
        super(owner, "HIGH SCORE LIST", true);
        setBounds(400, 400, 800, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel outer = new JPanel(new BorderLayout());
        //Layout
        outer.add(panel, BorderLayout.NORTH);
        outer.add(scoreList, BorderLayout.CENTER);
        JPanel Title = new JPanel();
        Title.add(TitleLabel);
        JPanel Display = new JPanel();
        Display.add(ScoreLabel);
        panel.add(Title);
        panel.add(Display);
        //Widen
        scoreList.setFixedCellWidth(400);
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) scoreList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        //Creating Object
        JList<String> scoreList = new JList<String>(listItems);
        TitleLabel.setText("HIGH SCORE LIST");
        //Adding Content
        setContentPane(outer);
        //Displaying
        addScore(score, null);
    }

    public void addScore(int newScore, String name) {
        if (name == null) {
            name = JOptionPane.showInputDialog(null, "What is your name?");
        }
        ScoreLabel.setText(name + " has just socred " + newScore);
        loadScores();
        for (int i = 0; i < 10; i++) {
            if (newScore > highScores[i]) {
                for (int j = 9; j > i; j--) {
                    highScores[j] = highScores[j - 1];
                    names[j] = names[j - 1];
                    listItems[j] = listItems[j - 1];
                }
                highScores[i] = newScore;
                names[i] = name;
                listItems[i] = name + "         " + Integer.toString(highScores[i]);
                saveScores();
                break;
            }
        }
        repaint();
        setVisible(true);
    }

    public void saveScores() {
        int num = 0;

        try {
            PrintWriter out = new PrintWriter("highscore.txt");
            for (int i = 0; i < 10; i++) {
                out.println(highScores[i]);
                out.println(names[i]);
                System.out.println(highScores[i] + "    " + names[i]);
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Cannot open file.");
        }

    }

    public void loadScores() {
        int i = 0;
        String line;
        try {
            BufferedReader in = new BufferedReader(new FileReader("highscore.txt"));
            while ((line = in.readLine()) != null) {    // Read a line
                highScores[i] = Integer.parseInt(line);   // Convert it to integer
                names[i] = in.readLine();    // Read a line
                listItems[i] = names[i] + "    " + Integer.toString(highScores[i]);
                i++;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Cannot open file.");
        }
    }

}



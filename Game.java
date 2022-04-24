import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class Game extends JFrame implements ActionListener, MouseListener, MouseMotionListener, KeyListener {//Runnable {
    private JLabel spaceship, inner, scorebar, healthbar;
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    ArrayList<EnemyShip> enemyships = new ArrayList<EnemyShip>();
    ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private Timer BulletTimer, xmovementTimer, ymovementTimer, SpawnTimer, asteroidTimer, asteroidyTimer;
    private Random ran = new Random();
    int direction = 0, score = 0, lives = 3;
    public int level = 1, wave = 1;
    JPanel top;

    public Game() {
        this.setLayout(new BorderLayout());
        inner = new JLabel((new ImageIcon("background.gif")));//new JPanel();
        inner.setLayout(null);
        top = new JPanel(new GridLayout(1, 2));
        top.setBackground(Color.black);
        this.add(inner, BorderLayout.CENTER);
        this.add(top, BorderLayout.NORTH);

        //Spaceship
        ImageIcon icon = new ShipSelection();
        spaceship = new JLabel(icon);
        inner.add(spaceship);
        spaceship.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        spaceship.addMouseListener(this);

        //Score Bar
        scorebar = new JLabel("Score: " + score);
        scorebar.setForeground(Color.white);
        top.add(scorebar);

        //Health Bar
        healthbar = new JLabel("Lives: " + lives);
        healthbar.setForeground(Color.white);
        top.add(healthbar);

        //Game Frame
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Spawn enemy ship
        while (enemyships.size() <= 0) {
            double radius = ran.nextDouble() * getWidth() / 5;
            for (int z = 0; z < 15; z++) {
                int originX = getWidth() / 2;
                int originY = 64;
                double angle = ran.nextDouble() * 360;
                //https://gamedev.stackexchange.com/a/9610
                EnemyShip e = new EnemyShip((int) (originX + cos(angle) * radius), (int) (originY + sin(angle) * radius), this);
                inner.add(e);
                enemyships.add(e);
                e.setVisible(true);
            }
        }
        //Timers
        BulletTimer = new Timer(50, this);
        BulletTimer.start();
        xmovementTimer = new Timer(1000, this);
        xmovementTimer.start();
        ymovementTimer = new Timer(1500, this);
        ymovementTimer.start();
        SpawnTimer = new Timer(100, this);
        SpawnTimer.start();
        asteroidTimer = new Timer(500, this);
        asteroidTimer.start();
        asteroidyTimer = new Timer(50, this);
        asteroidyTimer.start();


    }

    public void actionPerformed(ActionEvent e) {
        //Bullet Movement
        if (e.getSource() == BulletTimer) {
            for (int a = 0; a < bullets.size(); a++) {
                bullets.get(a).movebullets();
                if (bullets.get(a).getY() < 0 || !bullets.get(a).isVisible()) {
                    bullets.get(a).setVisible(false);
                    bullets.remove(a);
                    shipscored();
                } else {
                    BulletTimer.restart();
                    for (int f = 0; f < enemyships.size(); f++) {
                        if (enemyships.get(f).getY() > getHeight() || !enemyships.get(f).isVisible()) {
                            enemyships.get(f).setVisible(false);
                            enemyships.remove(f);
                        } else {
                            //BULLET DETECTION
                            if (Math.abs(bullets.get(a).getX() - enemyships.get(f).getX()) < 32) {
                                if (Math.abs(bullets.get(a).getY() - enemyships.get(f).getY()) < 32) {
                                    enemyships.get(f).setVisible(false);
                                    bullets.get(a).setVisible(false);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (e.getSource() == SpawnTimer) {
            for (int f = 0; f < enemyships.size(); f++) {
                if (enemyships.get(f).getY() > getHeight()) {
                    enemyships.remove(f);
                    loselive();
                }
                if (!enemyships.get(f).isVisible()) {
                    enemyships.remove(f);
                }
            }


            while (enemyships.size() <= 0) {
                wave += 1;
                if (wave == 3) {
                    level += 1;
                }
                int spawn = ran.nextInt(3);
                if (spawn == 0) {
                    for (int z = 0; z < 15; z++) {
                        spawnrandom();

                    }
                }
                if (spawn == 1) {
                    for (int z = 0; z < 15; z++) {
                        spawnrandom1();

                    }
                }
                if (spawn == 2) {
                    double radius = ran.nextDouble() * getWidth() / 5;
                    for (int z = 0; z < 15; z++) {
                        spawnrandom2((int) radius);
                    }
                }
            }
        }
        //Move Enemy Ship Left
        if (e.getSource() == xmovementTimer && direction == 0) {

            direction = 1;
            xmovementTimer.restart();
            for (int c = 0; c < enemyships.size(); c++) {
                enemyships.get(c).moveenemyshipxleft();
                if (isHitting(enemyships.get(c), spaceship)) {
                    enemyships.get(c).setVisible(false);
                    shipscored();
                    loselive();
                    break;
                }
            }

        }


        //Move Enemy ship to the Right
        else if (e.getSource() == xmovementTimer && direction == 1) {
            direction = 0;
            xmovementTimer.restart();
            for (int c = 0; c < enemyships.size(); c++) {
                enemyships.get(c).moveenemyshipxright();
                if (isHitting(enemyships.get(c), spaceship)) {
                    enemyships.get(c).setVisible(false);
                    shipscored();
                    loselive();
                    break;
                }
            }

        }
        if (e.getSource() == ymovementTimer) {
            ymovementTimer.restart();
            for (int c = 0; c < enemyships.size(); c++) {
                enemyships.get(c).moveenemyshipy();
                if (isHitting(enemyships.get(c), spaceship)) {
                    enemyships.get(c).setVisible(false);
                    shipscored();
                    loselive();
                    break;
                }
            }
        }
        if (e.getSource() == asteroidTimer) {
            double num = ran.nextDouble() * 10;
            if (num < 2) {
                double x = ran.nextDouble() * getWidth();
                double y = 0;
                Asteroid as = new Asteroid((int) x, (int) y);
                inner.add(as);
                asteroids.add(as);

            }
            else {}
        }
        if (e.getSource() == asteroidyTimer) {
            for (int a = 0; a < asteroids.size(); a++) {
                asteroids.get(a).moveasteroidy();
                asteroidyTimer.restart();
                if (isHitting(asteroids.get(a), spaceship)) {
                        end();
                    }
                }
            }
        }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        //https://alvinalexander.com/java/java-mouse-current-position-location-coordinates //FOR CONCEPT
        Point p = MouseInfo.getPointerInfo().getLocation();
        int x = p.x - (spaceship.getWidth() / 2);
        int y = p.y - (spaceship.getHeight() / 2);

        //Right Boundaries
        if (x >= getWidth()) {
            x = getWidth()-spaceship.getWidth() / 2;
        }
        //Left Boundaries
        if (x <= 0) {
            x+= spaceship.getWidth() / 2;
        }
        //Top Boundaries
        if (y <= 0) {
            y+= spaceship.getHeight() / 2;
        }
        //Bottom Boundaries
        if (y >= getHeight()) {
            y = getHeight()-spaceship.getHeight() / 2;
        }
        spaceship.setLocation(x, y);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (bullets.size() <= 5) {
                Bullet b = new Bullet(spaceship.getX(), spaceship.getY());
                inner.add(b);
                bullets.add(b);
                b.setVisible(true);
            } else {
            }
        }
    }

    public boolean isHitting(JLabel one, JLabel two) {
        if (Math.abs(one.getX() - two.getX()) < 50) {
            if (Math.abs(one.getY() - two.getY()) < 50) {
                return true;
            }
        }
        return false;
    }

    public void spawnrandom() {
        double radius = ran.nextDouble() * getWidth() / 5;
        int originX = getWidth() / 2;
        int originY = 64;
        double angle = ran.nextDouble() * 360;
        //https://gamedev.stackexchange.com/a/9610
        EnemyShip en = new EnemyShip((int) (originX + cos(angle) * radius), (int) (originY + sin(angle) * radius), this);
        inner.add(en);
        enemyships.add(en);
        en.setVisible(true);
    }

    public void spawnrandom1() {
        int x = ran.nextInt(getWidth() - 100);
        int y = ran.nextInt(getHeight() - 500);
        EnemyShip en = new EnemyShip(x + 10, y + 10, this);
        inner.add(en);
        enemyships.add(en);
        en.setVisible(true);
    }

    public void spawnrandom2(int radius) {
        int originX = getWidth() / 2;
        int originY = 64;
        double angle = ran.nextDouble() * 360;
        //https://gamedev.stackexchange.com/a/9610
        EnemyShip en = new EnemyShip((int) (originX + cos(angle) * radius), (int) (originY + sin(angle) * radius), this);
        inner.add(en);
        enemyships.add(en);
        en.setVisible(true);
    }

    public int getLevel() {
        return level;
    }

    public void shipscored() {
        score += 70;
        scorebar.setText("Score: " + score);
    }

    public void loselive() {
        if (lives <= 1) {
            healthbar.setText("Lives: " + (lives - 1));
            end();
        } else {
            lives -= 1;
            healthbar.setText("Lives: " + lives);
        }
    }

    public void end() {
        xmovementTimer.stop();
        ymovementTimer.stop();
        BulletTimer.stop();
        SpawnTimer.stop();
        asteroidTimer.stop();
        asteroidyTimer.stop();

        new Score(score, this);
        JRadioButton radio1 = new JRadioButton("Yes");
        JRadioButton radio2 = new JRadioButton("No", true);
        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        Object[] newOptions = {radio1, radio2, "Done"};
        JOptionPane.showOptionDialog(null, "Do you want to play again", "Input", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, newOptions, newOptions[2]);
        if (radio1.isSelected()) {
            for (int c = 0; c < enemyships.size(); c++) {
                enemyships.get(c).setVisible(false);
            }
            enemyships=new ArrayList<EnemyShip>();
            for (int a = 0; a < asteroids.size(); a++) {
                asteroids.get(a).setVisible(false);
            }
            asteroids=new ArrayList<Asteroid>();
            for (int b = 0; b < bullets.size(); b++) {
                bullets.get(b).setVisible(false);
            }
            bullets=new ArrayList<Bullet>();
            BulletTimer.restart();
            xmovementTimer.restart();
            ymovementTimer.restart();
            asteroidTimer.restart();
            asteroidyTimer.restart();
            SpawnTimer.restart();
            score = 0;
            lives = 3;
            healthbar.setText("Lives: "+ lives);
            scorebar.setText("Score: "+ score);
        }
        if (radio2.isSelected()) {
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        new Game();
    }
}


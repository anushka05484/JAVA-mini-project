import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {

    private int[] snakeX = new int[750];
    private int[] snakeY = new int[750];
    private int length = 3, score = 0;

    private boolean left = false, right = true, up = false, down = false;

    private Timer timer;
    private int delay = 100;

    private int foodX, foodY;
    private Random random = new Random();

    private boolean gameOver = false;

    public SnakeGame() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();

        snakeX[0] = 100;
        snakeY[0] = 100;

        newFood();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Game area
        g.setColor(Color.BLACK);
        g.fillRect(10, 10, 670, 670);

        // Food
        g.setColor(Color.RED);
        g.fillOval(foodX, foodY, 10, 10);

        // Snake
        for (int i = 0; i < length; i++) {
            if (i == 0)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.YELLOW);

            g.fillRect(snakeX[i], snakeY[i], 10, 10);
        }

        // Game Over
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!", 230, 300);
            g.drawString("Score: " + score, 260, 340);
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameOver) {
            // Move body
            for (int i = length - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }

            // Move head
            if (left) snakeX[0] -= 10;
            if (right) snakeX[0] += 10;
            if (up) snakeY[0] -= 10;
            if (down) snakeY[0] += 10;

            // Border collision
            if (snakeX[0] < 10 || snakeX[0] > 670 ||
                snakeY[0] < 10 || snakeY[0] > 670) {
                gameOver = true;
            }

            // Self collision
            for (int i = 1; i < length; i++) {
                if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]) {
                    gameOver = true;
                }
            }

            // Food eaten
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                length++;
                score += 10;
                newFood();
            }

            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT && !right) {
            left = true; up = down = false;
        }
        if (code == KeyEvent.VK_RIGHT && !left) {
            right = true; up = down = false;
        }
        if (code == KeyEvent.VK_UP && !down) {
            up = true; left = right = false;
        }
        if (code == KeyEvent.VK_DOWN && !up) {
            down = true; left = right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    void newFood() {
        foodX = 10 + random.nextInt(65) * 10;
        foodY = 10 + random.nextInt(65) * 10;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();

        frame.add(game);
        frame.setSize(700, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

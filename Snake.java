
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class Snake extends JFrame implements KeyListener, Runnable {
// initializing values
    JPanel gameScreen, scoreScreen;
    JButton[] snakeBody = new JButton[350000];
    JTextArea t;
    int x = 780, y = 470, size = 2, directionx = 1, directiony = 0, speed = 50, difference = 0, oldx, oldy, score = 0;
    int[] xPosition = new int[300];
    int[] yPosition = new int[300];
    Point[] snakePoint = new Point[300];
    Point bfp = new Point();
    Thread myt;
    boolean food = false, runLeft = false, runRight = true, runUp = true, runDown = true;
    Random r = new Random();

//initializing values
    public void initializeValues() {
        size = 3;
        xPosition[0] = 100;
        yPosition[0] = 150;
        directionx = 10;
        directiony = 0;
        difference = 0;
        score = 0;
        food = false;
        runLeft = false;
        runRight = true;
        runUp = true;
        runDown = true;

    }
// run all necessary programs
    Snake() {
        super("Snake");
        setSize(800, 550);
        //initialize all variables
        initializeValues();
        gameScreen = new JPanel();
        scoreScreen = new JPanel();
        // t will view the score
        t = new JTextArea("Begin!!     ");
        t.setFont(new Font("Serif", Font.BOLD, 13));
        t.setEnabled(false);
        t.setBackground(Color.BLUE);
        // will make first snake
        createFirstSnake();

        gameScreen.setLayout(null);
        scoreScreen.setLayout(new GridLayout(2, 1));
        gameScreen.setBounds(0, 0, x, y);
        gameScreen.setBackground(Color.MAGENTA);
        scoreScreen.setBounds(0, y, x, 30);
        scoreScreen.setBackground(Color.RED);

        scoreScreen.add(t);
        getContentPane().setLayout(null);
        getContentPane().add(gameScreen);
        getContentPane().add(scoreScreen);

        show();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addKeyListener(this);
        // start thread
        myt = new Thread(this);
        myt.start(); // go to run() method
    }

    public void createFirstSnake() {
        // Initially the snake has small length 3
        for (int i = 0; i < 3; i++) {
            snakeBody[i] = new JButton("snakeBody" + i);
            snakeBody[i].setEnabled(false);
            gameScreen.add(snakeBody[i]);
            snakeBody[i].setBounds(xPosition[i], yPosition[i], 10, 10);
            xPosition[i + 1] = xPosition[i] - 10;
            yPosition[i + 1] = yPosition[i];
        }
    }
// resets the game with a score of 0
    void reset() {
        initializeValues();
        gameScreen.removeAll();

        myt.stop();

        createFirstSnake();
        t.setText("Score: " + score);

        myt = new Thread(this);
        myt.start();
    }

// snake grows longer
    void growup() {
        snakeBody[size] = new JButton();
        snakeBody[size].setEnabled(false);
        gameScreen.add(snakeBody[size]);

        int a = 10 + (10 * r.nextInt(48));
        int b = 10 + (10 * r.nextInt(23));

        xPosition[size] = a;
        yPosition[size] = b;
        snakeBody[size].setBounds(a, b, 10, 10);

        size++;
    }

// main gameplay
    void moveForward() {
        for (int i = 0; i < size; i++) {
            snakePoint[i] = snakeBody[i].getLocation();
        }

        xPosition[0] += directionx;
        yPosition[0] += directiony;
        snakeBody[0].setBounds(xPosition[0], yPosition[0], 10, 10);

        for (int i = 1; i < size; i++) {
            snakeBody[i].setLocation(snakePoint[i - 1]);
        }

        if (xPosition[0] == x) {
            xPosition[0] = 10;
        } else if (xPosition[0] == 0) {
            xPosition[0] = x - 10;
        } else if (yPosition[0] == y) {
            yPosition[0] = 10;
        } else if (yPosition[0] == 0) {
            yPosition[0] = y - 10;
        }

        if (xPosition[0] == xPosition[size - 1] && yPosition[0] == yPosition[size - 1]) {
            food = false;
            score += 1;
            t.setText("Score:     " + score);
        }

        if (food == false) {
            growup();
            food = true;
        } else {
            snakeBody[size - 1].setBounds(xPosition[size - 1], yPosition[size - 1], 10, 10);
        }

        for (int i = 1; i < score; i++) {
            if (snakePoint[0] == snakePoint[i]) {
                t.setText("GAME OVER!! High Score is " + score);
                try {
                    myt.join();
                } catch (InterruptedException ie) {
                }
                break;
            }
        }
        
        if(xPosition[0] == 770 || xPosition[0] == -10 || yPosition[0] == 460 || yPosition[0] ==-10){
            t.setText("GAME OVER!! High Score is " + score);
            try {
                myt.join();
            } catch (InterruptedException ie) {
            }
        }


        if(score%2 != 0 || score == 0){
            gameScreen.setBackground(Color.MAGENTA);
        }else{
            gameScreen.setBackground(Color.PINK);
        }        

        gameScreen.repaint();
        show();
    }


// initialize keyboard to movements
    public void keyPressed(KeyEvent e) {
        if(score == 0 || score%2 != 0){
            // snake move to left when player pressed left arrow
            if (runLeft == true && e.getKeyCode() == 37) {
                directionx = -10; // means snake move right to left by 10pixels
                directiony = 0;
                runRight = false;     // run right(runRight) means snake cant move from left to right
                runUp = true;      // run up   (runUp) means snake can move from down to up
                runDown = true;      // run down (run down) means snake can move from up to down
            }
            // snake move to up when player pressed up arrow
            if (runUp == true && e.getKeyCode() == 38) {
                directionx = 0;
                directiony = -10; // means snake move from down to up by 10 pixel
                runDown = false;     // run down (run down) means snake can move from up to down
                runRight = true;      // run right(runRight) means snake can move from left to right
                runLeft = true;      // run left (runLeft) means snake can move from right to left
            }
            // snake move to right when player pressed right arrow
            if (runRight == true && e.getKeyCode() == 39) {
                directionx = +10; // means snake move from left to right by 10 pixel
                directiony = 0;
                runLeft = false;
                runUp = true;
                runDown = true;
            }
            // snake move to down when player pressed down arrow
            if (runDown == true && e.getKeyCode() == 40) {
                directionx = 0;
                directiony = +10; // means snake move from left to right by 10 pixel
                runUp = false;
                runRight = true;
                runLeft = true;
            }
        }else{
            // snake moves in opposite direction for multiples of 3
            if (runLeft == true && e.getKeyCode() == 37) {
                directionx = +10; 
                directiony = 0;
                runRight = false;     
                runUp = true;      
                runDown = true;     
            }
            if (runUp == true && e.getKeyCode() == 38) {
                directionx = 0;
                directiony = +10; 
                runDown = false;  
                runRight = true;  
                runLeft = true;   
            }
            if (runRight == true && e.getKeyCode() == 39) {
                directionx = -10; 
                directiony = 0;
                runLeft = false;
                runUp = true;
                runDown = true;
            }
            if (runDown == true && e.getKeyCode() == 40) {
                directionx = 0;
                directiony = -10; 
                runUp = false;
                runRight = true;
                runLeft = true;
            }            
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void run() {
        for (;;) {
            // Move the snake move forword. In the start of the game snake move left to right, 
            // if player press up, down, right or left arrow snake change its direction according to
            // pressed arrow
            moveForward();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ie) {
            }
        }
    }
}

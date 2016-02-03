import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random; // For initialize balls with random velocities

/**
 * A representation of a world containing a set of moving balls and a pad.
 * NB! The worlds y-axis points downward.
 * 
 * @author Anders Lindström, anderslm@kth.se 2015-08-20
 * Expended by Zhengyu Wang, zhengyuw@kth.se 2015-09-10
 * Lab 1B, Java, HT 2015
 */
public class World {

    private final double width, height; // this worlds width and height
    private final int noOfBalls = 3;
    public int noOfBricks = 180;   // The number of lines of bricks

    private Ball[] balls; // an array of refernces to the balls
    private final Rectangle pad; // the pad used for bouncing the balls
    // Arralylist of bricks
    ArrayList<Brick> bricks;

    /**
     * Creates a new world, containing a pad and a set of balls. 
     * NB! The worlds y-axis points downward.
     *
     * @param width the width of this world
     * @param height the height of this worl
     */
    public World(double width, double height) {
        this.width = width;
        this.height = height;

        // create one (later many) ball(s)
        initBalls();
//        balls = new Ball[1]; // an array of references
//        balls[0] = new Ball(10, 10); // the actual ball object(s)
//        balls[0].setVelocity(75.0, 100.0);
        // Create bricks
        initBricks();

        // create the pad
        pad = new Rectangle(width / 2, 0.95 * height, 
                width / 8, height / 64);
    }

    

    /**
     * Move the world one step, based on the time elapsed since last move.
     *
     * @param elapsedTimeNs the elpsed time in nanoseconds
     */
    public void move(long elapsedTimeNs) {
        for(int i = 0; i < noOfBalls; i ++){
        balls[i].move(elapsedTimeNs);
        constrainBall(balls[i]);
        checkForCollisionWithPad(balls[i]);
        checkForCollisionWithBrick(balls[i]);
        //checkForCollisionWithBall(balls[0],balls[i]);
        }
    }
    
    // Getters **********************************************************
    /**
     * Returns a copy of the list of ball references.
     *
     * @return a copy of the list of balls
     */
    public Ball[] getBalls() {
        return (Ball[]) balls.clone();
    }
    public ArrayList<Brick> getBricks() {
        return (ArrayList<Brick>) bricks.clone();
    }

    /**
     * Returns a reference to the pad.
     *
     * @return a reference to the pad
     */
    public Rectangle getPad() {
        return pad;
    }
    
    public int getNoOfBricks() {
        return noOfBricks;
    }
    

    // Setters **********************************************************
    /**
     * Moves the pad to a new position. Left-click.
     * Keep the pad inside the "World"
     * No y-coordinate change, so that the pad moves only horizonally.
     * @param x the new x coordinate of the upper-left corner
     */
    public void setPadX(double x) {
        if (x > width) {
            x = width;
        }
        if (x < 0) {
            x = 0;
        }

        pad.setX(x);
    }
    
    /**
     * Task 5: add control if player misses some balls
     * Condition: ball's y-coordinate > pad.getY()
     * private method isGameOver()
     * @return 
     */
    public boolean isGameOver(){
        boolean test = false;
        for(int i = 0; i < noOfBalls; i ++){
           if((balls[i].getY() > pad.getY()) ){
               test = true;
           }
       }
        
        return test;
    }

    // Methods Private **************************************************
    /**
     * Task 3: add method to initialize at least 4 balls of different
     * colors, start-positions and velocities
     * The balls can move inside the world and bounces with the pad
     */
    private void initBalls(){
        
        balls = new Ball[noOfBalls]; // an array of references
        /**
         * Task 4: use java.util.Random to creat a random object for velocity
         * and apply the random velocitis to the balls
         */
        Random randomVelocity = new Random();
        double[] velocity;
        velocity = new double[noOfBalls];
        for(int i = 0; i < noOfBalls; i ++){
            velocity[i] = randomVelocity.nextDouble() * 200.0;
        }
        // First ball
        balls[0] = new Ball(100, 100, 30, Color.NAVY); // the actual ball object(s)
        balls[0].setVelocity(velocity[0], velocity[0]);
        // Second ball
        balls[1] = new Ball(200, 300, 38, Color.LIME);
        balls[1].setVelocity(velocity[1], velocity[1]);
        // Third ball
        balls[2] = new Ball(80, 200, 40, Color.MEDIUMVIOLETRED);
        balls[2].setVelocity(velocity[2], velocity[2]);
//        // Fourth Ball
//        balls[3] = new Ball(300, 160, 36, Color.ORANGE);
//        balls[3].setVelocity(velocity[3], velocity[3]);
//        // Fifth ball
//        balls[4] = new Ball(30, 100, 28, Color.FLORALWHITE);
//        balls[4].setVelocity(velocity[4], velocity[4]);
        // More balls ...
    }
    /**
     * Add method to initailize bricks with colors
     */
    
    private void initBricks() {
        
        bricks = new ArrayList<>(noOfBricks);
        Random random = new Random();
        double initX = 0, initY = 0;
        double initWidth = 40;  // Divide the width of panel by 10: 400 / 10 = 40
        double initHeight = 10;
        double red, green, blue;
        //int cnt = 0;
        
        for(int i = 0; i < noOfBricks ; i ++) {
                red = random.nextDouble();
                green = random.nextDouble();
                blue = random.nextDouble();
                Color randColor = new Color(red, green, blue, 1);
            bricks.add(new Brick(initX, initY, initWidth, initHeight, randColor));
            initX += 40;
            // Change to next line under
            if(initX == 400) {
                initX = 0;
                initY += 10;
                //cnt ++; // Need this counter, whenever line changes it loses a brick
                
            }
        }
    }
    /**
     * Keep the ball inside the world (i.e. inside the canvas).
     *
     * @param ball the ball to constrain
     */
    private void constrainBall(Ball ball) {
        double x = ball.getX(), y = ball.getY();
        double dx = ball.getDx(), dy = ball.getDy();
        double radius = ball.getRadius();

        // If outside the box - calculate new dx and dy
        if (x < radius) {
            dx = Math.abs(dx);  // Moves rightwards
        } else if (x > width - radius) {
            dx = -Math.abs(dx); // Moves leftwards
        }
        if (y < radius) {
            dy = Math.abs(dy);  // Moves downwards
        } else if (y > height - radius) {
            dy = -Math.abs(dy); // Moves upwards
        }

        ball.setVelocity(dx, dy);
    }

    /**
     * Check if the ball collides with the pads upper edge. If so, bounce 
     * the ball vertically.
     * 
     * @param ball the ball to check
     */
    private void checkForCollisionWithPad(Ball ball) {
        if (ball.intersectsArea(
                pad.getX(), pad.getY(), pad.getWidth(), pad.getHeight())) {
            double dx = ball.getDx();
            // set dy negative, i.e. moving "up"
            double newDy = -Math.abs(ball.getDy()); 
            ball.setVelocity(dx, newDy);
        }
    }
    
    // Check if a brick collides with a ball. If yes, the brick disappear.
    // Reduce the number of bricks as a counter
    private void checkForCollisionWithBrick(Ball ball) {
        for(int i=0; i < bricks.size(); i++) {
            if (ball.intersectsArea(
                    bricks.get(i).getX(), bricks.get(i).getY(), bricks.get(i).getWidth(), bricks.get(i).getHeight())) {
                double dx = ball.getDx();
                // set dy positive, i.e. moving "down"
                double newDy = Math.abs(ball.getDy()); 
                ball.setVelocity(dx, newDy);
                bricks.remove(i);
                noOfBricks --;
            }

        }
        
    }
    // Check if a ball collides with another ball
//    private void checkForCollisionWithBall(Ball ball1, Ball ball2) {
//        if (ball1.intersectsArea(
//                ball2.getX(), ball2.getY(), ball2.getRadius(), ball2.getRadius())) {
//            double dx = ball1.getDx();
//            // set dy negative, i.e. moving "up"
//            double newDy = -Math.abs(ball1.getDy()); 
//            ball1.setVelocity(dx, newDy);
//        }
//    }
    
}

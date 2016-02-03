import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A representation of a moving ball. The method paint(GraphicsContext) 
 * draws the ball using a graphics context.
 *
 * @author Anders Lindström, anderslm@kth.se 2015-08-20
 * Expended by Zhengyu Wang, zhengyuw@kth.se 2015-09-10
 * Lab 1B, Java, HT 2015
 */
public class Ball {

    public static final double BILLION = 1_000_000_000.0;
    
    /** Private fields */
    private double x, y; // position of the balls center
    private double dx, dy; // velocity measured in pixels/second
    private double radius;
    private Color color;
    private double diameter;    // Add diameter => radius = diameter / 2.0

    /**
     * Creates a new Ball with center at (x0,y0), radius 10 pixels, blue 
     * color and zero velocity.
     *
     * @param x0 center x
     * @param y0 center y
     */
    public Ball(double x0, double y0) {
        x = x0;
        y = y0;
        radius = 10;    // This number is pixels
        color = Color.BLUE; // Set color here
    }

    /** Task 1: add another constructor with params position, diameter and color */
    public Ball(double x0, double y0, double diameter, Color color) {
        x = x0;
        y = y0;
        this.radius = diameter / 2.0;    // This number is pixels
        this.color = color; // Set color here
    }
    // Getters/Accessors!************************************************
    /**
     * @return x-coordinate of center: the center of ball, not origo
     */
    public double getX() {
        return x;
    }

    /**
     * @return y-coordinate of center
     */
    public double getY() {
        return y;
    }
    
    /**
     * @return the radius
     */
    double getRadius() {
        return radius;
    }

    /**
     * @return the velocity in the x-direction, pixels/second
     */
    double getDx() {
        return dx;
    }

    /**
     * @return the velocity in the y-direction, pixels/second
     */
    double getDy() {
        return dy;
    }
    
    // Setters/Mutators! ************************************************
    /**
     * Sets the new x coordinate of the center
     *
     * @param newX
     */
    public void setX(double newX) {
        x = newX;
    }

    /**
     * Sets the new y coordinate of the center
     *
     * @param newY
     */
    public void setY(double newY) {
        y = newY;
    }

    /**
     * Task 2: Set the color which can change the color of an existed ball
     * @param newColor
     */
    public void setColor(Color newColor){
        color = newColor;
    }
    
    /**
     * Sets the velocity, pixels/second, to (newDx, newDy).
     *
     * @param newDx
     * @param newDy
     */
    public void setVelocity(double newDx, double newDy) {
        dx = newDx;
        dy = newDy;
    }
    
    // Methods Public ****************************************************
    /**
     * Moves the center of the ball to (newX, newY).
     *
     * @param newX
     * @param newY
     */
    public void moveTo(double newX, double newY) {
        x = newX;
        y = newY;
    }

    /**
     * Move the ball a distance depending on the elapsed time in nanoseconds. 
     * NB - the velocitey is measured in pixels/second.
     *
     * @param elapsedTimeNs the elapsed time in nanoseconds.
     */
    public void move(long elapsedTimeNs) {
        x += dx * elapsedTimeNs / BILLION;
        y += dy * elapsedTimeNs / BILLION;
    }

    /**
     * Paint the ball on the screen using the grapchics context.
     *
     * @param gc
     */
    public void paint(GraphicsContext gc) {
        gc.setFill(color);
        // arguments to fillOval: see the javadoc for GraphicsContext
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    /**
     * Collision detection - check if this circle overlaps/intersects a
     * rectangular area with position (x,y) and dimensions (width,height)
     *
     * @param rectX upper-left corner
     * @param rectY upper-left corner
     * @param rectWidth
     * @param rectHeight
     * @return true if an overlap is detected, otherwise false.
     */
    public boolean intersectsArea(
            double rectX, double rectY,
            double rectWidth, double rectHeight) {

        // Find the closest point to the circle's center within 
        // the rectangle
        double closestX = clamp(x, rectX, rectX + rectWidth);
        double closestY = clamp(y, rectY, rectY + rectHeight);

        // Calculate the distance between the circle's center and the
        // rectangles closest point
        double distanceX = x - closestX;
        double distanceY = y - closestY;

        // If the distance is less than the circle's radius, an 
        // intersection occurs (Pythagoras theorem)
        return (distanceX * distanceX) + (distanceY * distanceY)
                < (radius * radius);
    }

    // Methods Private ************************************************
    // Limits value to the range [lower..upper]
    private double clamp(double value, double lower, double upper) {
        if (value < lower) {
            return lower;
        }
        if (value > upper) {
            return upper;
        }
        return value;
    }
}

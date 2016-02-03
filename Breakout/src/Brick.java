
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 * Del B, Lab 2. Java 2015.09.17
 */

/**
 *
 * @author Zhengyu Wang <zhengyuw@kth.se>
 */
public class Brick {
    
    // Datafield
    private double x, y;
    private double width, height;
    private Color color;
    
    // Constructor
    public Brick(double x0, double y0, double w0, double h0, Color c0) {
        this.x = x0;
        this.y = y0;
        this.width = w0;
        this.height = h0;
        this.color = c0;
    }
    
    // Getters/Incapsulation
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public Color getColor() {
        return color;
    }
    
    // Muttors
    public void setX(double newX) {
        this.x = newX;
    }
    public void setY(double newY) {
        this.y = newY;
    }
    public void setWidth(double newWidth) {
        this.width = newWidth;
    }
    public void setHeight(double newHeight) {
        this.height = newHeight;
    }
    public void setColor(Color newColor) {
        this.color = newColor;
    }
    // The main function to paint bricks
    public void paint(GraphicsContext gc) {
        // Fill the bricks with color
        gc.setFill(color);
        // arguments to fillOval: see the javadoc for GraphicsContext
        gc.fillRect(x, y, width, height);
    }
}

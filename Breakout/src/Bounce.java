import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This class holds the canvas where the components in the world are  
 * displayed. It also holds the timer managing the animation.
 *
 * @author Anders Lindström, anderslm@kth.se 2015-08-20
 * Expended by Zhengyu Wang, zhengyuw@kth.se 2015-09-10
 * Lab 1B, Java, HT 2015
 */
public class Bounce extends Application {

    private World world;

    private Canvas canvas; // the surface whera pad and balls are drawn
    private AnimationTimer timer;

    protected class BounceTimer extends AnimationTimer {

        private long previousNs = 0; //System.currentTimeMillis(); // set it to system  time and try real time views on dialog frame....

        /**
         * This method deals with drawing the world. You do not have to 
         * change this code, except for handling "game over" 
         * (see comment below).
         *
         * @param nowNs
         */
        @Override
        public void handle(long nowNs) {
            // odd initialization...
            if (previousNs == 0) {
                previousNs = nowNs;
            }

            // move the objects in the world
            world.move(nowNs - previousNs); // elapsed time
            // save the new timestamp, for the next cycle
            previousNs = nowNs;

            
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // paint the background
            gc.setFill(Color.TEAL);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // paint the pad
            Rectangle pad = world.getPad();
            gc.setFill(Color.BLACK);
            double x = pad.getX(), y = pad.getY(),
                    w = pad.getWidth(), h = pad.getHeight();
            gc.fillRoundRect(x, y, w, h, h, h);

            // paint the balls
            for (Ball b : world.getBalls()) {
                b.paint(gc);
            }
            // Paint the bricks
            for (Brick br: world.getBricks()) {
                br.paint(gc);
            }

            // TODO: Check if game is over by calling world.isGameOver
            // And by checking if noOfBricks equals 0.
            // If so, stop the animation and show a message by calling 
            // timer.stop() and showAlert("Some message")
            
            
            if(world.getNoOfBricks() == 0) {
                    timer.stop();
                    showAlert("Game Over! You won!");
            }
            if(world.isGameOver()){
                timer.stop();
                showAlert("Game Over! You lost and "+ world.getNoOfBricks() + " bricks remain.");
                
            }
            
            
        }
    }

    // This code initializes the graphics. You do not have to (should not)
    // change this code.
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 400, 500, Color.LIGHTSKYBLUE);

        canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);

        stage.setTitle("Jag hatar att hålla många bollar i luften!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        world = new World(canvas.getWidth(), canvas.getHeight());

        timer = new BounceTimer();
        timer.start();

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent me) {
                        world.setPadX(me.getX());
                    }
                });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
        
    }

    private void showAlert(String message) {
        alert.setHeaderText("Result of this round: ");
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.show();
    }

    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
}

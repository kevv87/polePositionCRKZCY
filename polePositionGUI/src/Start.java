import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello, World!");

        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene( theScene );

        GameScreen pantalla = new GameScreen(600, new Canvas(600,600));

        root.getChildren().add(pantalla.getCanvas());

        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 10000000.0;
                double ddy = 2.3;
                double dy = ((t*ddy)%300);

                // background image clears canvas
                pantalla.drawStreet();
                pantalla.drawLines(dy);
                pantalla.drawBackground();

            }
        }.start();

        primaryStage.show();
    }
}

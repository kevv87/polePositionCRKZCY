import GameObjects.Car;
import GameObjects.Pista;
import Screens.GameScreen;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import logic.MyAnimationTimer;

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

        final Pista pista = new Pista(new Car(0));

        MyAnimationTimer anim = new MyAnimationTimer(pantalla, startNanoTime, pista);
        /*{
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
        };*/
        anim.start();
        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if(code == "UP"){
                            anim.addDdy();
                        }else if(code == "DOWN"){
                            anim.subDdy();
                        }
                    }
                });

        primaryStage.show();
    }
}

import GameObjects.Camera;
import GameObjects.Car;
import GameObjects.Pista;
import GameObjects.Static;
import Screens.GameScreen;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import logic.Client;
import logic.MyAnimationTimer;

import java.io.IOException;

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
        Client cliente = new Client("25.28.229.147", 8888);

        GameScreen pantalla = new GameScreen(600, new Canvas(600,600));

        root.getChildren().add(pantalla.getCanvas());

        pantalla.gc.drawImage(new Image("./images/car1.png"), 300, 300);

        final long startNanoTime = System.nanoTime();

        final Pista pista = new Pista(new Car(0));

        pista.camera = new Camera();
        pista.camera.attachTo(pista.j1);
        pista.addObjeto(new Static("boost", 2, 160));

        MyAnimationTimer anim = new MyAnimationTimer(pantalla, startNanoTime, pista);

        anim.start();
        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        System.out.println(code);
                        try {
                            cliente.writeI(code);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        /*
                        if(code == "UP"){
                            anim.addDdy();
                        }else if(code == "DOWN"){
                            anim.subDdy();
                        }else if(code == "RIGHT"){
                            anim.pista.j1.right();
                        }else if(code == "LEFT"){
                            anim.pista.j1.left();
                        }*/
                    }
                });
        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        System.out.println(code);
                        try {
                            cliente.writeR(code);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        /*
                        if(code == "UP"){
                            anim.addDdy();
                        }else if(code == "DOWN"){
                            anim.subDdy();
                        }else if(code == "RIGHT"){
                            anim.pista.j1.right();
                        }else if(code == "LEFT"){
                            anim.pista.j1.left();
                        }*/
                    }
                });

        primaryStage.show();
    }
}

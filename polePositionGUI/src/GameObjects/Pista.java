package GameObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

// Esta es la lista que contiene toda la informacion de la pista, se piensa enviar al
// servidor.
public class Pista {
    public Car j1; // Carro del jugador 1
    public Car j2; // Carro del jugador 2

    public List<Static> objetos;

    public Camera camera;

    public void drawAll(GraphicsContext gc){
        if(camera != null && camera.getAttached() != null){
            camera.getAttached().resizeImage(200, 200);
            gc.drawImage(camera.getAttached().imagen, camera.getAttached().getPosX(), 520);  // Dibujando el attached de la camara, siempre tiene y constante
        }
        //gc.drawImage(j1.imagen, 300, 300);
    }

    public Pista(Car j1) {
        this.j1 = j1;
    }
}

package GameObjects;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

// Esta es la lista que contiene toda la informacion de la pista, se piensa enviar al
// servidor.
public class Pista {
    public Car j1; // Carro del jugador 1
    public Car j2; // Carro del jugador 2

    public List<Static> objetos;

    public void drawAll(GraphicsContext gc){
    }

    public Pista(Car j1) {
        this.j1 = j1;
    }
}

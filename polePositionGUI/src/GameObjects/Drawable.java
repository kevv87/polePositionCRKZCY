package GameObjects;

import javafx.scene.image.Image;

// Clase abstracta de la que heredaran todos los objeto que se quieran
// dibujar en pantalla
public abstract class Drawable {
    protected double posM; // Position in meters from the start
    protected double posX; // Position in meters in the street.
    protected Image imagen; // Imagen a dibujar

    public double distanceFromCamera(Camera camera){
        return posM - camera.getPosM();
    };
}

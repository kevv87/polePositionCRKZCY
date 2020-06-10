package GameObjects;

import javafx.scene.image.Image;

// Clase abstracta de la que heredaran todos los objeto que se quieran
// dibujar en pantalla
public abstract class Drawable {
    protected double posM; // Position in meters from the start
    protected double posX; // Posicion en x en la calle.
    public Image imagen; // Imagen a dibujar

    public double distanceFromCamera(Camera camera){
        return posM - camera.getPosM();
    };

    public void resizeImage(double height, double width){
        imagen = new Image(this.imagen.getUrl(), width, height, true, true);
    }

    public double getPosM() {
        return posM;
    }

    public double getPosX() {
        return posX;
    }
}

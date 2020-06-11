package GameObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Clase designada a objetos estaticos: vidas, huecos, boosts.
public class Static extends Drawable{

    String type; // El tipo de objeto estatico: vida, hueco o boost.

    public Static(String type, double posM, double posX) {
        this.type = type;
        this.posM = posM;
        this.posX = posX;
        if(type == "vida"){
            imagen = new Image("./images/vida.png", 50, 50, true, true);
        }
    }

    // Metodo que dibuja al objeto estatico en funcion de la distancia en kilometros de la camara
    public void draw(GraphicsContext gc, double distanciaCamara){
        double size = 600;
        double distanciapx = size*distanciaCamara/2;
        double y = size-distanciapx;
        double x2 = (2*size -4*posX)*2*distanciapx/(5*size) + posX;
        //System.out.println(x2);
        gc.drawImage(imagen, x2, y);
    }
}

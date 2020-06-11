package GameObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Clase designada a objetos estaticos: vidas, huecos, boosts.
public class Static extends Drawable{

    String type; // El tipo de objeto estatico: vida, hueco o boost.
    double v; // Velocidad de movimiento de la imagen

    public Static(String type, double posM, double posX) {
        this.type = type;
        this.posM = posM;
        this.posX = posX;
        v = 0;
        if(type == "vida"){
            imagen = new Image("./images/vida.png", 50, 50, true, true);
        }
    }

    // Metodo que dibuja al objeto estatico en funcion de la distancia en kilometros de la camara
    public void draw(GraphicsContext gc, double distanciaCamara){
        double size = 600;
        double distanciapx = size*distanciaCamara/2;
        double x2 = (2*size -4*posX)*2*distanciapx/(5*size) + posX;
        //System.out.println(x2);
        double y = (size-distanciapx-v);
        if((size/2)/y >= 0.9){
            modifySize(15);
            v-=0;
        }else if((size/2)/y >= 0.7){
            modifySize(30);
            v-=1;
        }else{
            modifySize(50);
            v-=2;
        }
        System.out.println((size/2)/y);
        gc.drawImage(imagen, x2-imagen.getWidth()/2, y);
    }
}

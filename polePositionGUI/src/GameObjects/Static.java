package GameObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

// Clase designada a objetos estaticos: vidas, huecos, boosts.
public class Static extends Drawable{

    public String type; // El tipo de objeto estatico: vida, hueco o boost.
    public double v; // Velocidad de movimiento de la imagen
    public double camX;
    public double camZ;
    public int camY;
    public Graphics g;
    public int x,y,w;


    public Static(String type, double posM, double posX) {
        this.type = type;
        this.posM = posM;
        this.posX = posX;
        this.type = type;
        v = 0;

        if(type == "vida"){
            imagen = "/resources/images/vida.png";
        }else if(type == "hueco"){
            imagen = "/resources/images/obs.png";
        }else if(type=="boost"){
            imagen = "/resources/images/boost.png";
        }else if(type=="palm"){
            imagen = "/resources/images/palmera.png";
        }
    }

    public Static(Graphics g, String type, int x, int y, int w, double camX, double camZ, double posM, double posX){
        this.g = g;
        this.posM = posM;
        this.posX = posX;
        this.x = x;
        this.y = y;
        this.w = w;
        this.camX = camX;
        this.camZ = camZ;
        if(type == "vida"){
            imagen = "/resources/images/vida.png";
        }else if(type == "hueco"){
            imagen = "/resources/images/obs.png";
        }else if(type=="boost"){
            imagen = "/resources/images/boost.png";
        }else if(type=="palm"){
            imagen = "/resources/images/palmera.png";
        }
    }

    public void modifySize(double newSize){
        if(type=="vida"){
            newSize *=1;
        }else if(type == "hueco"){
            newSize *=3;
        }else if(type=="boost"){
            newSize*=4;
        }
        super.modifySize(newSize);
    }

    // Metodo que dibuja al objeto estatico en funcion de la distancia en kilometros de la camara
    public void draw(GraphicsContext gc, double distanciaCamara){
        double size = 600;
        double wanted = 1;
        double distanciapx = size*distanciaCamara/(2*wanted);
        double x2 = (2*size -4*posX)*2*distanciapx/(5*size) + posX;
        //System.out.println(x2);
        double y = (size-distanciapx-v);
        if((size/2)/y >= 0.97){
            modifySize(15);
            v-=0;
        }else if((size/2)/y >= 0.8){
            modifySize(30);
            v-=1;
        }else{
            modifySize(50);
            v-=2;
        }
        System.out.println((size/2)/y);
        //gc.drawImage(imagen, x2-imagen.getWidth()/2, y);
    }
}

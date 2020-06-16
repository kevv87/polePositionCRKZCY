package GameObjects;

import javafx.scene.image.Image;

public class Car extends Drawable{

    private double velocidad;
    private double aceleracion;

    public Car(int pos){
        this.posM = pos;
        velocidad = 0;
        aceleracion = 0;
        this.posX = 200;
        imagen = new Image("resources/images/car1.png");
    }

    // Funcion que hace que avance el carro, segun la cantidad de nani
    //  segundos que lleva en la misma velocidad
    public void avanzar(){
        posM+=velocidad/3600;
    }

    public void right(){
        this.posX += 20;
    }

    public void left(){
        this.posX -= 20;
    }

    public void acelerar(double dv){
        if(velocidad+dv < 120 && velocidad+dv > 0){
            velocidad += dv;
        }
    }

    public double getPos(){
        return posM;
    }

    public double getVelocidad() {
        return velocidad;
    }
}

package GameObjects;

import javafx.scene.image.Image;

public class Car extends Drawable{

    private double velocidad;
    private double aceleracion;

    public Car(int pos){
        this.posM = pos;
        velocidad = 60;
        aceleracion = 0;
        this.posX = 200;
        imagen = "/resources/images/car1.png";
    }

    // Funcion que hace que avance el carro, segun la cantidad de nani
    //  segundos que lleva en la misma velocidad
    public void avanzar(){
        posM+=velocidad/3600;
    }

    public void normal(){
        imagen = "/resources/images/car1.png";
    }

    public void right(){
        imagen= "/resources/images/carL.png";
    }

    public void left(){
        imagen = "/resources/images/carR.png";
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

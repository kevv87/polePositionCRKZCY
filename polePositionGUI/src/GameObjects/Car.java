package GameObjects;

public class Car extends Drawable{

    private double velocidad;
    private double aceleracion;

    public Car(int pos){
        this.posM = pos;
        velocidad = 0;
        aceleracion = 0;
    }

    public void avanzar(){
        posM+=velocidad;
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

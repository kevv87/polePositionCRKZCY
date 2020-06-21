package GameObjects;

public class Car extends Drawable{

    private double velocidad;
    private double aceleracion;
    private boolean p1; // Positivo si es del primer tipo de carro, negativo si no

    public void setP1(boolean p1) {
        this.p1 = p1;
    }

    public boolean isP1() {
        return p1;
    }

    public Car(int pos, boolean p1){
        this.posM = pos;
        this.p1 = p1;
        velocidad = 60;
        aceleracion = 0;
        this.posX = 200;
        if(p1){
            imagen = "/resources/images/car1.png";
        }else{
            imagen = "/resources/images/car2.png";
        }
    }

    public void normal(){
        if(p1){
            imagen = "/resources/images/car1.png";
        }else{
            imagen = "/resources/images/car2.png";
        }
    }

    public void right(){
        if(p1){
            imagen = "/resources/images/carL1.png";
        }else{
            imagen = "/resources/images/carL2.png";
        }
    }

    public void left(){
        if(p1){
            imagen = "/resources/images/car1R.png";
        }else{
            imagen = "/resources/images/car2R.png";
        }
    }

    public double getVelocidad() {
        return velocidad;
    }
}

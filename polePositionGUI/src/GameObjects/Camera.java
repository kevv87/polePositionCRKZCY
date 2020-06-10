package GameObjects;

// Esta clase se utiliza para tener un punto de referencia a la hora de renderizar
// las imagenes.
public class Camera {
    private Drawable attached = null; // Objeto al que la camara se mantiene unido
    private double posX;  // Posicion en X que toma del objeto al que esta unida
    private double posM; // Posicion en la pista que toma del objeto al que esta unida

    public Drawable getAttached() {
        return attached;
    }

    public void attachTo(Drawable newAttachment){
        attached = newAttachment;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosM() {
        return posM;
    }

    // Funcion que actualiza la posicion en funcion al objeto al que esta agarrada la camara.
    public void updatePos(){
        posM = attached.posM;
        posX = attached.posX;
    }
}

package jsonObjects;

/**
 * Clase necesaria para parsear el json que envia el servidor en medio juego
 *
 * @author Kevin
 * @version 1.0
 * @date 20/6/2020
 */
public class midGameMsg {
    int poscarro1;
    String tipoCarro1;
    int velocidadCarro1;
    int posCarro2;
    String tipoCarro2;
    int velocidadCarro2;
    int posBalas[];
    String accion;

    // Dummy for jackson
    public midGameMsg(){};

    // Getters & Setters

    public int getPoscarro1() {
        return poscarro1;
    }

    public void setPoscarro1(int poscarro1) {
        this.poscarro1 = poscarro1;
    }

    public String getTipoCarro1() {
        return tipoCarro1;
    }

    public void setTipoCarro1(String tipoCarro1) {
        this.tipoCarro1 = tipoCarro1;
    }

    public int getVelocidadCarro1() {
        return velocidadCarro1;
    }

    public void setVelocidadCarro1(int velocidadCarro1) {
        this.velocidadCarro1 = velocidadCarro1;
    }

    public int getPosCarro2() {
        return posCarro2;
    }

    public void setPosCarro2(int posCarro2) {
        this.posCarro2 = posCarro2;
    }

    public String getTipoCarro2() {
        return tipoCarro2;
    }

    public void setTipoCarro2(String tipoCarro2) {
        this.tipoCarro2 = tipoCarro2;
    }

    public int getVelocidadCarro2() {
        return velocidadCarro2;
    }

    public void setVelocidadCarro2(int velocidadCarro2) {
        this.velocidadCarro2 = velocidadCarro2;
    }

    public int[] getPosBalas() {
        return posBalas;
    }

    public void setPosBalas(int[] posBalas) {
        this.posBalas = posBalas;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}

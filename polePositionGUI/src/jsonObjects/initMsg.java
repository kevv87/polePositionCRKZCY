package jsonObjects;

/**
 * Clase necesaria para parsear el json del servidor al inicio del juego.
 *
 *  @author Kevin Zeledon
 *   @version 1.0
 *    @since  06/20/2020
 *
 */
public class initMsg {
    int curvas[];
    int boosts[];
    int huecos[];
    int vidas[];

    // Dummy constructor for jackson
    public initMsg(){};

    // Getters & Setters
    public int[] getCurvas() {
        return curvas;
    }

    public void setCurvas(int[] curvas) {
        this.curvas = curvas;
    }

    public int[] getBoosts() {
        return boosts;
    }

    public void setBoosts(int[] boosts) {
        this.boosts = boosts;
    }

    public int[] getHuecos() {
        return huecos;
    }

    public void setHuecos(int[] huecos) {
        this.huecos = huecos;
    }

    public int[] getVidas() {
        return vidas;
    }

    public void setVidas(int[] vidas) {
        this.vidas = vidas;
    }
}

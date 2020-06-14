package Screens;

public class Punto {
    double z;
    int camera;
    int screen;

    public Punto(int n, double segmentLength) {
        this.z = n*segmentLength;
    }
}

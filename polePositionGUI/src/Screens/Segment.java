package Screens;

import javafx.scene.paint.Color;

public class Segment {
    private int index;
    private Punto p1;
    private Punto p2;
    private Color color;

    public Segment(int n, double segmentLength, Color color) {
        this.index = n;
        this.p1 = new Punto(n, segmentLength);
        this.p2 = new Punto(n+1, segmentLength);
        this.color = color;
    }
}

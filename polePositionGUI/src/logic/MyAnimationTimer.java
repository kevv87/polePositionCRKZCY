package logic;

import GameObjects.Pista;
import Screens.GameScreen;
import javafx.animation.AnimationTimer;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;

public class MyAnimationTimer extends AnimationTimer{

    public GameScreen pantalla;
    public Pista pista;
    final double startNanoTime;
    private double secondCounter = -1;
    double ddy;

    public MyAnimationTimer(GameScreen pantalla, double startNanoTime, final Pista pista) {
        this.pantalla = pantalla;
        this.startNanoTime = startNanoTime;
        this.pista = pista;
    }

    @Override
    public void handle(long now) {
        double t = (now - startNanoTime) / 10000000.0;
        double dy = ((t*ddy)%300);

        // background image clears canvas
        pantalla.drawStreet();
        pantalla.drawLines(dy);
        pantalla.drawBackground();

        // Objects
        pista.drawAll(pantalla.gc);
        if(secondCounter == -1){
            secondCounter = now;
        }else if (now-secondCounter > 1){
            pista.j1.avanzar();
            secondCounter = now;
        }
        //System.out.println(pista.j1.getPosM());
        //System.out.println(pista.j1.getVelocidad());
    }

    public void addDdy(){
        if(ddy < 12){
            ddy+=0.05;
        }
        pista.j1.acelerar(0.5);
    }

    public void subDdy(){
        if(ddy > 0){
            ddy-=0.05;
        }
        pista.j1.acelerar(-0.5);


    }
}

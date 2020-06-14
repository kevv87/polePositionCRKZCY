package logic;

import GameObjects.Pista;
import Screens.GameScreen;
import javafx.animation.AnimationTimer;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;
import pseudo3dRacing.RoadAppMain;

public class MyAnimationTimer extends AnimationTimer{

    public GameScreen pantalla;
    public Pista pista;
    final double startNanoTime;
    private double secondCounter = -1;
    //private RoadAppMain.DrawPanel drawPanel;
    double ddy;

    public MyAnimationTimer(GameScreen pantalla, double startNanoTime, final Pista pista) {
        this.pantalla = pantalla;
        this.startNanoTime = startNanoTime;
        this.pista = pista;
    }

    @Override
    public void handle(long now) {
        //drawPanel.repaint();
        System.out.println('a');
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

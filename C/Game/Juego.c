//
// Created by cvaz on 6/11/20.
//
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>
#include "Juego.h"

void setJugador(Jugador_t jugador){
    jugador.client = 0;
    jugador.movimiento_avance = '_';
    jugador.movimiento_lateral = '_';
    jugador.colision = 0;
    jugador.color = 0;
    jugador.puntaje = 0;
    jugador.aceleracion = 38.62;   // 38.62km/s basado en 60 millas en 2.5s (promedio)
    jugador.pos_X = 0;
    jugador.pos_Y = 0;
    jugador.rapidez = 0;
    jugador.t_acumulado = 0;
}

void asignarColor(Jugador_t jugador, int color){
    jugador.color = color;
}

void avanzar(Jugador_t jugador, float tiempo){
    if(jugador.movimiento_avance == 'w'){
        jugador.t_acumulado += tiempo;
        if(abs(jugador.pos_X) <= 5){
            avanzar_carretera(jugador, tiempo);
        }
        else{
            avanzar_cesped(jugador, tiempo);
        }
    }
    else{
        frenar(jugador);
    }
}

void avanzar_carretera(Jugador_t jugador, float tiempo){
    jugador.pos_Y += tiempo*jugador.rapidez;
    jugador.rapidez = 210*(-exp((jugador.t_acumulado)*k) + 1);
}

void avanzar_cesped(Jugador_t jugador, float tiempo){
    jugador.pos_Y += tiempo*jugador.rapidez;
    jugador.rapidez = 80*(-exp((-jugador.t_acumulado)*k) + 1);
}

void moverBalas(){

}

int colision(Jugador_t jugador){
    if(jugador.colision == 1){
        jugador.t_acumulado = 0;
        jugador.rapidez = 0;
    }
}

void frenar(Jugador_t jugador){
    if(jugador.movimiento_avance == '_'){
        if(abs(jugador.pos_X) <= 5){        //Si el jugador esta en la carretera
            jugador.t_acumulado = (log(1-(jugador.rapidez/210)))/k;
        }
        else{       //Si el jugador esta en el cesped
            jugador.t_acumulado = (log(1-(jugador.rapidez/80)))/k;
        }
        jugador.t_acumulado -= 5;
    }
}

int meta(){
    if((jugador1.pos_Y >= pista_tamano) || (jugador2.pos_Y >= pista_tamano)){
        partida = 0;
        printf("La carrera ha terminado!");
    }
}

void *juego(){
    continuar = 1;
    k = -0.2463;
    setJugador(jugador1);
    setJugador(jugador2);
    srand(time(0));
    while(continuar!=0){
        partida = 1;
        pista_tamano = (rand() %(35 -15 +1)) +15;
        t_referencia = clock();
        while(partida!=0){
            sleep(1);
            meta();
            printf("El tamano de la pista es: %d\n", pista_tamano);
            t_actual = clock();
            printf("El tiempo actual es: %ld\n", t_actual);
            t_transcurrido = t_actual - t_referencia;
            avanzar(jugador1, t_transcurrido);
            t_actual = clock();
            t_transcurrido = t_actual - t_referencia;
            avanzar(jugador2, t_transcurrido);
            t_referencia = t_actual;
            colision(jugador1);
            colision(jugador2);
        }
    }
}

void actualizarJugador(Jugador_t jugador, char input[5]){
    //Permite el uso de WASD o las flechas del teclado
    //Formato: I+tecla --> Usuario presiona dicha tecla
    if((input == "IUP") || (input == "IW")){
        jugador.movimiento_avance = 'w';
    }
    else if((input == "ILEFT") || (input == "IA")){
        jugador.movimiento_lateral = 'a';
    }
    else if((input == "IRIGHT") || (input == "ID")){
        jugador.movimiento_lateral = 'd';
    }
    //Formato: R+Tecla --> Usuario suelta (release) dicha tecla
    if((input == "RUP") || (input == "RW")){
        jugador.movimiento_avance = '_';
    }
    else if((input == "RLEFT") || (input == "RA")){
        jugador.movimiento_lateral = '_';
    }
    else if((input == "RRIGHT") || (input == "RD")){
        jugador.movimiento_lateral = '_';
    }
}
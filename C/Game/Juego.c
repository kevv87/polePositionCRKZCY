//
// Created by cvaz on 6/11/20.
//
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include "Juego.h"

void setJugador(Jugador_t jugador){
    jugador.vidas = 3;
    jugador.client = 0;
    jugador.movimiento_avance = '_';
    jugador.movimiento_lateral = '_';
    jugador.colision = 0;
    jugador.color = 0;
    jugador.puntaje = 0;
    jugador.disparo_activo = 0;
    jugador.aceleracion = 38.62;   // 38.62km/s basado en 60 millas en 2.5s (promedio)
    jugador.pos_X = 0;
    jugador.pos_Y = 0;
    jugador.rapidez = 0;
    jugador.t_acumulado = 0;

    jugador.disparo.pos_x = 0;
    jugador.disparo.pos_y = 0;
}

void delantera(){
    if(jugador1.pos_Y >= jugador2.pos_Y){
        lider = &jugador1;
    }
    else{
        lider = &jugador2;
    }
}

void asignarColor(Jugador_t jugador, int color){
    jugador.color = color;
}

void avanzar(Jugador_t jugador, float tiempo){
    if(jugador.movimiento_avance == 'w'){
        jugador.t_acumulado += tiempo;
        //Si el jugador esta en los margenes de la carretera
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

//*******************************************************************CONSIDERAR CAMBIAR A MRUA
//*******************************************************************
void avanzar_carretera(Jugador_t jugador, float tiempo){
    jugador.pos_Y += tiempo*jugador.rapidez;
    jugador.rapidez = 170*(-exp((jugador.t_acumulado)*k) + 1);
}

void avanzar_cesped(Jugador_t jugador, float tiempo){
    jugador.pos_Y += tiempo*jugador.rapidez;
    jugador.rapidez = 80*(-exp((-jugador.t_acumulado)*k) + 1);
}

void moverBalas(float tiempo){
    moverBalas_aux(jugador1, tiempo);
    moverBalas_aux(jugador2, tiempo);
}

void moverBalas_aux(Jugador_t jugador, float tiempo){
    if(jugador.disparo_activo == 1){
        //Si la bala ha avanzado mas de 100m respecto al lider, desaparece
        if((jugador.disparo.pos_y - lider->pos_Y) > 100.0){
            jugador.disparo_activo = 0;
            jugador.disparo.pos_x = 0;
            jugador.disparo.pos_y = 0;
        }
        else{
            jugador.disparo.pos_y += 330*tiempo;        //La bala avanza 330 metros por segundo
        }
    }

}

int colision(Jugador_t jugador){
    if(jugador.colision == 1){
        jugador.t_acumulado = 0;
        jugador.rapidez = 0;
    }
}

void frenar(Jugador_t jugador){
    if(abs(jugador.pos_X) <= 5){        //Si el jugador esta en la carretera
        jugador.t_acumulado = (log(1-(jugador.rapidez/170)))/k;
    }
    else{       //Si el jugador esta en el cesped
        jugador.t_acumulado = (log(1-(jugador.rapidez/80)))/k;
    }
    jugador.t_acumulado -= 5;
}

void disparar(Jugador_t jugador){
    if(jugador.disparo_activo == 0){
        jugador.disparo_activo = 1;
        jugador.disparo.pos_x = jugador.pos_X;
        jugador.disparo.pos_y = jugador.pos_Y;
    }
}

int meta(){
    if((jugador1.pos_Y >= (float)(pista_tamano*1000)) || (jugador2.pos_Y >= (float)(pista_tamano*1000))){
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

void inputJugador(Jugador_t jugador, char input[64]){
    //Permite el uso de WASD o las flechas del teclado
    //Formato: I+tecla --> Usuario presiona dicha tecla
    //         R+Tecla --> Usuario suelta (release) dicha tecla
    if((strcmp(input, "IUP") == 0) || (strcmp(input, "IW") == 0)){
        jugador.movimiento_avance = 'w';
    }
    else if((strcmp(input, "ILEFT") == 0) || (strcmp(input, "IA") == 0)){
        jugador.movimiento_lateral = 'a';
    }
    else if((strcmp(input, "IRIGHT") == 0) || (strcmp(input, "ID") == 0)){
        jugador.movimiento_lateral = 'd';
    }
    //F
    else if((strcmp(input, "RUP") == 0) || (strcmp(input, "RW") == 0)){
        jugador.movimiento_avance = 'w';
    }
    else if((strcmp(input, "RLEFT") == 0) || (strcmp(input, "RA") == 0)){
        jugador.movimiento_lateral = 'a';
    }
    else if((strcmp(input, "RRIGHT") == 0) || (strcmp(input, "RD") == 0)){
        jugador.movimiento_lateral = 'd';
    }
    //Disparo del jugador
    else if((strcmp(input, "ISPACE") == 0)){
        disparar(jugador);
    }
}
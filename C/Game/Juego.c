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

void setJugador(Jugador_t *jugador){
    jugador->vidas = 3;
    jugador->client = 0;
    jugador->movimiento_avance = '_';
    jugador->movimiento_lateral = '_';
    jugador->colision = 0;
    jugador->color = 0;
    jugador->puntaje = 0;
    jugador->disparo_activo = 0;
    jugador->aceleracion = 38.62;   // 38.62km/s basado en 60 millas en 2.5s (promedio)
    jugador->pos_X_anterior = 0;
    jugador->pos_Y_anterior = 0;
    jugador->pos_X = 0;
    jugador->pos_Y = 0;
    jugador->rapidez = 0;
    jugador->t_acumulado = 0;

    jugador->disparo.pos_x = 0;
    jugador->disparo.pos_y = 0;
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

void avanzar(Jugador_t *jugador, double tiempo){
    if(jugador->movimiento_avance == 'w'){
        jugador->t_acumulado += tiempo;
        //Si el jugador esta en los margenes de la carretera
        if(abs(jugador->pos_X) <= 5){
            avanzar_carretera(jugador, tiempo);
        }
        else{
            avanzar_cesped(jugador, tiempo);
        }
    }
    else{
        frenar(jugador);
    }
    if(jugador->movimiento_lateral == 'a'){
        if((jugador->pos_X - 0.5) >= -10){
            jugador->pos_X -= 0.5;
        }
    }
    else if(jugador->movimiento_lateral == 'd'){
        if((jugador->pos_X + 0.5) <= 10){
            jugador->pos_X += 0.5;
        }
    }
}

//*******************************************************************CONSIDERAR CAMBIAR A MRUA
//*******************************************************************
void avanzar_carretera(Jugador_t *jugador, double tiempo){
    jugador->pos_Y_anterior = jugador->pos_Y;
    jugador->pos_Y += tiempo*((jugador->rapidez)*(10.00/36.00));
    printf("El jugador esta en la posicion %f\n", jugador->pos_Y);
    jugador->rapidez = 170*(-exp((jugador->t_acumulado)*k) + 1);
}

void avanzar_cesped(Jugador_t *jugador, double tiempo){
    jugador->pos_Y += tiempo*((jugador->rapidez)*(1/3.6));
    jugador->rapidez = 80*(-exp((-jugador->t_acumulado)*k) + 1);
}

void moverBalas(Jugador_t *jugador1,Jugador_t *jugador2, double tiempo){
    moverBalas_aux(jugador1, tiempo);
    moverBalas_aux(jugador2, tiempo);
}

void moverBalas_aux(Jugador_t *jugador, double tiempo){
    if(jugador->disparo_activo == 1){
        //Si la bala ha avanzado mas de 100m respecto al lider, desaparece
        if((jugador->disparo.pos_y - lider->pos_Y) > 100.0){
            jugador->disparo_activo = 0;
            jugador->disparo.pos_x = 0;
            jugador->disparo.pos_y = 0;
        }
        else{
            if(fabsf(jugador->disparo.pos_x - lider->pos_X) <= 1){
                lider->colision = 1;
                lider->rapidez = 0;
                lider->t_acumulado = 0;
                lider->vidas -= 1;
            }
            jugador->disparo.pos_y += 330*tiempo;        //La bala avanza 330 metros por segundo
        }
    }

}

int colision(Jugador_t *jugador, Node_colisionable_t *head_lista){
    Node_colisionable_t *temp = head_lista;
    while(temp->colisionable.posY <= jugador->pos_Y){
        if((jugador->pos_Y_anterior <= temp->colisionable.posY) && (jugador->pos_Y >= temp->colisionable.posY)){
            if(fabs(jugador->pos_X - head_lista->colisionable.posX) <= 1){
                if(temp->colisionable.tipo == 1){       //Si es un bonus
                    jugador->rapidez = 180;
                }
                else if(temp->colisionable.tipo == 2){  //Si es un obstaculo
                    jugador->colision = 1;
                    jugador->t_acumulado = 0;
                    jugador->rapidez = 0;
                }
                else{       //Si es una vida
                    if(jugador->vidas < 3){
                        jugador->vidas += 1;
                    }
                }
            }
        }
        temp = temp->siguiente;
    }
}

void frenar(Jugador_t *jugador){
    //Si ya no tiene tiempo acumulado a una rapidez,
    if((jugador->t_acumulado -5) <= 0){
        jugador->t_acumulado = 0.0;
        jugador->rapidez = 0.0;
    }
    else{
        if(fabsf(jugador->pos_X) <= 5){        //Si el jugador esta en la carretera
            jugador->t_acumulado = (log(1-(jugador->rapidez/170)))/k;
        }
        else{       //Si el jugador esta en el cesped
            jugador->t_acumulado = (log(1-(jugador->rapidez/80)))/k;
        }
        jugador->t_acumulado -= 5;
    }
}

void disparar(Jugador_t *jugador){
    if(jugador->disparo_activo == 0){
        jugador->disparo_activo = 1;
        jugador->disparo.pos_x = jugador->pos_X;
        jugador->disparo.pos_y = jugador->pos_Y;
    }
}

int meta(Jugador_t *jugador1, Jugador_t *jugador2, int pista_largo){
    if((jugador1->pos_Y >= (float)(pista_largo*1000)) || (jugador2->pos_Y >= (float)(pista_largo*1000))){
        partida = 0;
        printf("La carrera ha terminado!");
    }
}
/*
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
}*/

void inputJugador(Jugador_t *jugador, char input[64]){
    //Permite el uso de WASD o las flechas del teclado
    //Formato: I+tecla --> Usuario presiona dicha tecla
    //         R+Tecla --> Usuario suelta (release) dicha tecla
    if((strcmp(input, "IUP") == 0) || (strcmp(input, "IW") == 0)){
        jugador->movimiento_avance = 'w';
        printf("estoy en el input W :D\n");
    }
    else if((strcmp(input, "ILEFT") == 0) || (strcmp(input, "IA") == 0)){
        jugador->movimiento_lateral = 'a';
    }
    else if((strcmp(input, "IRIGHT") == 0) || (strcmp(input, "ID") == 0)){
        jugador->movimiento_lateral = 'd';
    }
    //F
    else if((strcmp(input, "RUP") == 0) || (strcmp(input, "RW") == 0)){
        jugador->movimiento_avance = '_';
    }
    else if((strcmp(input, "RLEFT") == 0) || (strcmp(input, "RA") == 0)){
        jugador->movimiento_lateral = '_';
    }
    else if((strcmp(input, "RRIGHT") == 0) || (strcmp(input, "RD") == 0)){
        jugador->movimiento_lateral = '_';
    }
    //Disparo del jugador
    else if((strcmp(input, "ISPACE") == 0)){
        disparar(jugador);
    }
}
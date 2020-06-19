//
// Created by cvaz on 6/11/20.
//
#ifndef GAME_JUEGO_H
#define GAME_JUEGO_H
#include "ListaEnlazada.h"

/**********************
Estructuras del juego
**********************/
/*
*Estructura de las balas
*/
typedef struct Disparo{
    float pos_x;        //Posicion de la bala respecto a l centro de la pantalla en metros
    float pos_y;        //Posicion de la bala respecto al punto de partida
} Disparo_t;

/*
 * Estructura de los jugadores
 */
typedef struct Jugador{
    //Identificadores del jugador
    int vidas;                  //Vidas restantes del jugador
    //Socket del jugador
    int client;

    //Indicadores de movimiento del jugador
    char movimiento_avance;     //Indicador del avance de un jugador 'w' o ""
    char movimiento_lateral;    //Indicador del movimiento lateral del jugador 'a', 'd' o ""

    //Indicadores de estado
    int colision;       //Indicador booleano de colision
    int color;          //Indicador del color escogido 0-Azul/1-Rojo/2-Amarillo/3-Verde
    int puntaje;        //Contador del puntaje
    int disparo_activo; //Indicador de si disparo activo

    Disparo_t disparo;  //Disparo asociado al jugador

    //Contadores del progreso del jugador
    float aceleracion;  //Contador de aceleracion
    float pos_X;        //Posicion del jugador respecto al centro de la pantalla en metros
    float pos_Y;        //Posicion del jugador respecto al punto de partida en km
    float rapidez;      //Contador de la rapidez
    float t_acumulado;  //Contador de tiempo acumulado para calcular rapidez
} Jugador_t;


/*
 * Estructura de la pista
 */
typedef struct Pista{
    int tamano;         //Longitud en km de la pista
} Pista_t;

/*
 * Estructura de los colisionables
 */
typedef struct Colisionable{
    char tipo;          //Tipo de colisionable 'v' (vida) u 'o' (obstaculo)
    int pos_X;          //Ubicacion del colisionable respecto al centro de la pantalla en metros
    int pos_Y;          //Ubicacion del colisionable, en la pista, respecto al punto de partida en km
} Colisionable_t;

/****************************
Elementos internos del juego
****************************/
//Booleano Continuar juego / Booleano continuar partida / int tamano pista
int continuar, partida, pista_tamano;

//Constante de aceleracion de los carros
double k;

//Estructura para ambos jugadores y puntero para saber cual va delante
struct Jugador jugador1, jugador2, *lider;
struct Disparo disparo_jugador1, disparo_jugador2;

//Lista de disparos y colisionables
NodePointer disparos, colisionables;

//Tiempos del juego
clock_t t_referencia, t_actual, t_transcurrido;

/****************************
    Funciones del juego
****************************/
/* setJugador()
 * Funcion que inicializa los valores del jugador
 * args: -struct jugador (estructura del jugador)
 */
void setJugador(struct Jugador jugador);

/* delantera()
 * Funcion que determina quien va a la delantera
 */
void delantera();

/* asignarColor()
 * Funcion que asigna el color escogido al jugador
 */
void asignarColor(Jugador_t jugador, int color);

/* avanzar()
 * Funcion que mueve a un jugador segun este en carretera o cesped
 * si esta avanzando, si no, lo frena
 * args: -struct jugador (estructura del jugador)
 *       -float tiempo (tiempo que ha avanzado)
 */
void avanzar(Jugador_t jugador, float tiempo);

/* avanzar_carretera()
 * Funcion que mueve un carro en carretera segun sus valores
 * args: -struct jugador (estructura del jugador)
 *       -float tiempo (tiempo que ha avanzado)
 */
void avanzar_carretera(Jugador_t jugador, float tiempo);

/* avanzar_cesped()
 * Funcion que mueve un carro en cesped segun sus valores
 * args: -struct jugador (estructura del jugador)
 *       -float tiempo (tiempo que ha avanzado)
 */
void avanzar_cesped(Jugador_t jugador, float tiempo);

/* moverBalas()
 * Funcion que mueve las balas segun sus valores
 */
void moverBalas(float tiempo);

/* moverBalas_aux()
 * Funcion que mueve la bala del jugador
 */
void moverBalas_aux(Jugador_t jugador, float tiempo);

/* colision()
 * Funcion que determina si una bala colisiono con algun jugador
 * args: -struct jugador (estructura del jugador)
 */
int colision(Jugador_t jugador);

/* frenar()
 * Funcion que frena al jugador si no esta acelerando
 * args: -struct jugador (estructura del jugador)
 */
void frenar(Jugador_t jugador);

/* frenar()
 * Funcion que inicia el disparo del jugador
 * args: -struct jugador (estructura del jugador)
 */
void disparar(Jugador_t jugador);

/* juego()
 * Funcion que inicia un juego
 */
void *juego();

/* actualizarJugador()
 * Funcion que actualiza valores del jugador segun los comandos del cliente
 * args: -struct jugador (estructura del jugador)
 *       -char input[64] (mensaje recibido del cliente)
 */
void inputJugador(Jugador_t jugador, char input[64]);

/* meta()
 * Funcion que determina si la carrera acabo
 */
int meta();

#endif //GAME_JUEGO_H

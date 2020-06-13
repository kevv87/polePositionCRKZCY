//
// Created by cvaz on 6/11/20.
//

#ifndef GAME_JUEGO_H
#define GAME_JUEGO_H
//Estructura de los jugadores
typedef struct Jugador{
    int avanzando;      //Indicador booleano de si el jugador esta avanzando
    int colision;       //Indicador booleano de colision
    int color;          //Indicador del color escogido 0-Azul/1-Rojo/2-Amarillo/3-Verde
    int puntaje;        //Contador del puntaje

    float aceleracion;  //Contador de aceleracion
    float pos_X;        //Posicion del jugador respecto al centro de la pantalla en metros
    float pos_Y;        //Posicion del jugador respecto al punto de partida en km
    float rapidez;      //Contador de la rapidez
    float t_acumulado;  //Contador de tiempo acumulado para calcular rapidez
} Jugador_t;

//Estructura de las balas
typedef struct Bala{
    int rapidez;        //Rapidez constante de la bala

    float pos_x;        //Posicion de la bala respecto a l centro de la pantalla en metros
    float pos_y;        //Posicion de la bala respecto al punto de partida
} Bala_t;

//Estructura de la pista
typedef struct Pista{
    int tamano;         //Longitud en km de la pista
} Pista_t;

//Estructura de los obstaculos/vidas
typedef struct Colisionable{
    int pos_X;          //Ubicacion del colisionable respecto al centro de la pantalla en metros
    int pos_Y;          //Ubicacion del colisionable, en la pista, respecto al punto de partida en km
} Colisionable_t;

//Booleano para continuar/cerrar el juego
int continuar;
//Booleano para continuar/terminar una carrera
int partida;

//Estructura para ambos jugadores
struct Jugador jugador1;
struct Jugador jugador2;

//Lista de balas

//Largo de la pista en km
int pista_tamano;

//Tiempo referencia
clock_t t_referencia;

//Tiempo actual
clock_t t_actual;

//Tiempo transcurrido
clock_t t_transcurrido;

/* setJugador()
 * Funcion que inicializa los valores del jugador
 */
void setJugador(struct Jugador jugador);

/* asignarColor()
 * Funcion que asigna el color escogido al jugador
 */
void asignarColor(Jugador_t jugador, int color);

/* avanzar()
 * Funcion que mueve a ambos jugadores segun esten en carretera o cesped
 */
void avanzar(Jugador_t jugador, float tiempo);

/* avanzar_carretera()
 * Funcion que mueve un carro en carretera segun sus valores
 */
void avanzar_carretera(Jugador_t jugador, float tiempo);

/* avanzar_cesped()
 * Funcion que mueve un carro en cesped segun sus valores
 */
void avanzar_cesped(Jugador_t jugador, float tiempo);

/* moverBalas()
 * Funcion que mueve las balas segun sus valores
 */
void moverBalas();

/* colision()
 * Funcion que determina si una bala colisiono con algun jugador
 */
int colision(Jugador_t jugador);

/* frenar()
 * Funcion que frena al jugador si no esta acelerando
 */
void frenar(Jugador_t jugador);

/* juego()
 * Funcion que inicia un juego
 */
void juego();

/* carrera()
 * Funcion que inicia una carrera
 */
void carrera();

/* meta()
 * Funcion que determina si la carrera acabo
 */
int meta();

#endif //GAME_JUEGO_H

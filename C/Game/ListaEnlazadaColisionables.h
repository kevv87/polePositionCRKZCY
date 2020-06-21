//
// Created by cvaz on 6/20/20.
//

#ifndef GAME_LISTAENLAZADACOLISIONABLES_H
#define GAME_LISTAENLAZADACOLISIONABLES_H
/*
 * Estructura de los objetos colisionables
 */
typedef struct Colisionable{
    int tipo;
    double posX, posY;
} Colisionable_t;

/*
 * Estructura de los nodos de las balas
 */
typedef struct Node_colisionable{
    Colisionable_t colisionable;
    struct Node_colisionable *siguiente;
} Node_colisionable_t;

/*newColisionable()
 * Funcion que crea un nuevo nodo con una estrcutrura colisionable
 */
Node_colisionable_t * newColisionable(int tamano_pista, int tipo);

/* nuevaListaColisionables()
 * Funcion que crea una lista de W bonus, X obstaculos y Y vidas segun el tamano Z de la pista
 * args: -int cantidad (numero de elementos a generar)
 *       -int tamano_pista (longitud de la pista en m)
 *       Node_colisionable_t *cabeza (nodo que almacena el puntero al primer elemento de la lista)
 */
Node_colisionable_t * nuevaListaColisionables(int cantidad, int tamano_pista, int tipo);

/* eliminarColisionables()
 * Funcion que elimina de la lista todos aquellos elementos que hayan sido sobrepasados por ambos jugadores
 * args: double posX, posY (posicion a partir de donde se debe borrar hacia atras)
 */
Node_colisionable_t * eliminarColisionable(double posY, Node_colisionable_t *cabeza);
#endif //GAME_LISTAENLAZADACOLISIONABLES_H

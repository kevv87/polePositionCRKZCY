//
// Created by cvaz on 6/20/20.
//
#include <stdlib.h>
#include <time.h>

#include "ListaEnlazadaColisionables.h"

Node_colisionable_t * newColisionable(int tamano_pista, int tipo){
    Node_colisionable_t *temp = malloc(sizeof(Colisionable_t));;
    srand(time(0));
    Colisionable_t colisionable;
    int posX = (rand() %(5 +1 + 5)) - 5;        //PosX entre los limites de la calle
    int posY = (rand() %(tamano_pista - 200 +1)) +200;      //PosY entre los primeros 200 metros y el final de la pista
    colisionable.tipo = tipo;
    colisionable.posX = posX;
    colisionable.posY = posY;
    temp->colisionable = colisionable;
    temp->siguiente = NULL;
    return temp;
}

Node_colisionable_t * nuevaListaColisionables(int cantidad, int tamano_pista, int tipo){
    Node_colisionable_t *cabeza = NULL;
    Node_colisionable_t *current = newColisionable(tamano_pista, tipo);
    cantidad -= 1;
    cabeza = current;
    while(cantidad > 0){
        Node_colisionable_t *temp = newColisionable(tamano_pista, tipo);
        current->siguiente = temp;
        current = temp;
        cantidad-=1;
    }
    return cabeza;
}

Node_colisionable_t * eliminarColisionable(double posY, Node_colisionable_t *cabeza){
    if(cabeza != NULL){
        if(cabeza->colisionable.posY < posY){
            return eliminarColisionable(posY, cabeza->siguiente);
        }
        else{
            Node_colisionable_t *current = cabeza;
            Node_colisionable_t *temp = current->siguiente;
            while(temp != NULL){
                if(temp->colisionable.posY > posY){     //Si la posicion del colisionable es menor al ultimo jugador
                    current->siguiente = temp;
                    current = temp;
                    temp = temp->siguiente;
                }
                else{
                    free(temp);
                    temp = temp->siguiente;
                }
            }

        }
    }

}

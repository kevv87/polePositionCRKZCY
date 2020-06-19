//
// Created by cvaz on 6/17/20.
//
#include <arpa/inet.h> //close
#include <errno.h>
#include <netinet/in.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h> //strlen
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h> //FD_SET, FD_ISSET, FD_ZERO macros
#include <time.h>
#include <unistd.h> //close

#ifndef GAME_SERVER_H
#define GAME_SERVER_H
/***********************
 Valores del servidor
***********************/
int opt;
int master_socket, addrlen, new_socket, client_socket[2], max_clients, activity, i , valread , sd, max_sd, chck_clients;
struct sockaddr_in address;
struct timeval timeout;
char buffer[64]; //data buffer of 64

//set of socket descriptors
fd_set readfds;

//Welcome messagge
char *message;

/***********************
 Funciones del servidor
***********************/
/* initializeServer()
 * Funcion que inicializa al servidor y sus valores
 */
int initializeServer();

/* addClients()
 * Funcion que anade a dos clientes
 */
void addClients();

/* checkClients()
 * Funcion que revisa si los clientes tienen
 * mensajes pendientes
 */
void checkClients();

/* checkClients()
 * Funcion que revisa si un cliente en especifico
 * ha enviado un mensaje y opera con el
 */
int readFromClient(int file_descriptor);

#endif //GAME_SERVER_H

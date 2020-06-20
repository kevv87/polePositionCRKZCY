//
// Created by cvaz on 6/15/20.
//
#include <arpa/inet.h> //close
#include <errno.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h> //strlen
#include <sys/socket.h>
#include <sys/time.h> //FD_SET, FD_ISSET, FD_ZERO macros
#include <time.h>
#include <unistd.h> //close

#include "Juego.h"

#define TRUE 1
#define FALSE 0
#define PORT 8888

struct timeval timeout;

int main(int argc , char *argv[]){
    timeout.tv_sec = 5;
    /***********************
       Valores del juego
    ***********************/
    continuar = 1;
    k = -0.2463;
    jugador1_ptr = &jugador1;
    jugador2_ptr = &jugador2;
    setJugador(jugador1_ptr);
    setJugador(jugador2_ptr);
    lider = &jugador1;
    srand(time(0));

    /***********************
     Valores del servidor
    ***********************/
    int opt = TRUE;
    int master_socket, addrlen, new_socket, client_socket[2], max_clients = 2, activity, i , valread , sd;
    int max_sd;
    struct sockaddr_in address;

    char buffer[64]; //data buffer of 1K

    //set of socket descriptors
    fd_set readfds;

    //a message
    char *message = " Welcome \r\n";

    //initialise all client_socket[] to 0 so not checked
    for (i = 0; i < max_clients; i++){
        client_socket[i] = 0;
    }

    //create a master socket
    if( (master_socket = socket(AF_INET , SOCK_STREAM , 0)) == 0){
        perror("socket failed");
        exit(EXIT_FAILURE);
    }

    //set master socket to allow multiple connections ,
    //this is just a good habit, it will work without this
    if(setsockopt(master_socket, SOL_SOCKET, SO_REUSEADDR, (char *)&opt, sizeof(opt)) < 0 ){
        perror("setsockopt");
        exit(EXIT_FAILURE);
    }

    //type of socket created
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons( PORT );

    //bind the socket to localhost port 8888
    if (bind(master_socket, (struct sockaddr *)&address, sizeof(address))<0){
        perror("bind failed");
        exit(EXIT_FAILURE);
    }
    printf("Listener on port %d \n", PORT);

    //try to specify maximum of 2 pending connections for the master socket
    if (listen(master_socket, 2) < 0){
        perror("listen");
        exit(EXIT_FAILURE);
    }

    //accept the incoming connection
    addrlen = sizeof(address);
    puts("Waiting for connections ...");    //****************HASTA AQUI***************\\

    /***********************
     Crea una partida nueva
    ***********************/
    while(continuar != 0) {
        partida = 1;
        pista_tamano = (rand() %(35 -15 +1)) +15;

        /***********************
          Escucha a los clientes
        ***********************/
        int cont_clientes = 0;
        while(cont_clientes < 2){
            //clear the socket set
            FD_ZERO(&readfds);

            //add master socket to set
            FD_SET(master_socket, &readfds);
            max_sd = master_socket;

            //add child sockets to set
            for (i = 0; i < max_clients; i++) {
                //socket descriptor
                sd = client_socket[i];

                //if valid socket descriptor then add to read list
                if (sd > 0)
                    FD_SET(sd, &readfds);

                //highest file descriptor number, need it for the select function
                if (sd > max_sd)
                    max_sd = sd;
            }

            //wait for an activity on one of the sockets , timeout is NULL ,
            //so wait indefinitely
            activity = select(max_sd + 1, &readfds, NULL, NULL, &timeout);
            if ((activity < 0) && (errno != EINTR)) {
                printf("select error");
            }

            //If something happened on the master socket ,
            //then its an incoming connection
            if (FD_ISSET(master_socket, &readfds)) {
                if ((new_socket = accept(master_socket, (struct sockaddr *) &address, (socklen_t *) &addrlen)) < 0) {
                    perror("accept");
                    exit(EXIT_FAILURE);
                }

                //inform user of socket number - used in send and receive commands
                printf("New connection , socket fd is %d , ip is : %s , port : %d\n", new_socket,
                       inet_ntoa(address.sin_addr), ntohs(address.sin_port));

                //send new connection greeting message
                if (send(new_socket, message, strlen(message), 0) != strlen(message)) {
                    perror("send");
                }
                //Vincula un socket a cada jugador
                if(cont_clientes == 0){
                    jugador1_ptr->client = new_socket;
                }
                if(cont_clientes == 1){
                    jugador2_ptr->client = new_socket;
                    printf("El jugador 2 tiene como cliente *********** %d\n", jugador2_ptr->client);
                }

                puts("Welcome message sent successfully");

                //add new socket to array of sockets
                for (i = 0; i < max_clients; i++) {
                    //if position is empty
                    if (client_socket[i] == 0) {
                        client_socket[i] = new_socket;
                        printf("Adding to list of sockets as %d\n", i);
                        cont_clientes+=1;
                        break;
                    }
                }
            }
        }
        while (partida == 1) {
            //Inicia el timer
            t_referencia = clock();
            //clear the socket set
            FD_ZERO(&readfds);

            //add master socket to set
            FD_SET(master_socket, &readfds);
            max_sd = master_socket;

            //add child sockets to set
            for (i = 0; i < max_clients; i++) {
                //socket descriptor
                sd = client_socket[i];

                //if valid socket descriptor then add to read list
                if (sd > 0)
                    FD_SET(sd, &readfds);

                //highest file descriptor number, need it for the select function
                if (sd > max_sd)
                    max_sd = sd;
            }

            //wait for an activity on one of the sockets , timeout is 10 ,
            //so wait indefinitely
            activity = select(max_sd + 1, &readfds, NULL, NULL, &timeout);

            if ((activity < 0) && (errno != EINTR)) {
                printf("select error");
            }

            //else its some IO operation on some other socket
            for (i = 0; i < max_clients; i++) {
                sd = client_socket[i];

                if (FD_ISSET(sd, &readfds)) {
                    //Check if it was for closing , and also read the
                    //incoming message
                    bzero(buffer, 255);
                    valread = read(sd, buffer, 255);
                    if (valread == 0) {
                        //Somebody disconnected , get his details and print
                        getpeername(sd, (struct sockaddr *) &address, \
                            (socklen_t *) &addrlen);
                        printf("Host disconnected , ip %s , port %d \n",
                               inet_ntoa(address.sin_addr), ntohs(address.sin_port));

                        //Close the socket and mark as 0 in list for reuse
                        close(sd);
                        client_socket[i] = 0;
                    }

                        //Echo back the message that came in
                    else {
                        //set the string terminating NULL byte on the end
                        //of the data read
                        printf("Estoy en el echo else\n");
                        printf("Client: %s\n", buffer);
                        //APLICA CAMBIOS EN EL JUEGO SEGUN EL INPUT DEL USUARIO
                        printf("Este es el usuario: %d\n", sd);
                        if(sd == jugador1_ptr->client){
                            inputJugador(jugador1_ptr, buffer);
                        }
                        else{
                            inputJugador(jugador2_ptr, buffer);
                        }
                        bzero(buffer, 255);
                        //valread = write(sd, buffer, strlen(buffer));
                        buffer[valread] = '\0';
                        //Server input
                        bzero(buffer, 255);
                        //write(sd, message, strlen(message));
                        //send(sd, message, strlen(message), 0);
                    }
                }
            }
            /***********************
               Ejecucion del juego
            ***********************/
            meta(jugador1_ptr, jugador2_ptr, pista_tamano);
            //printf("El tamano de la pista es: %d\n", pista_tamano);
            if(jugador1_ptr->pos_Y != 0.0){
                printf("%f\n", jugador1.pos_Y);
            }
            t_transcurrido = (double)(clock() - t_referencia);
            sleep(1);
            printf("%f\n", t_transcurrido);
            moverBalas(jugador1_ptr, jugador2_ptr, t_transcurrido);
            avanzar(jugador1_ptr, t_transcurrido);
            avanzar(jugador2_ptr, t_transcurrido);
            colision(jugador1_ptr);
            colision(jugador2_ptr);
            delantera();
        }
    }
    return 0;
}

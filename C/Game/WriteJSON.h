//
// Created by cvaz on 6/20/20.
//
#ifndef GAME_WRITEJSON_H
#define GAME_WRITEJSON_H
char* jsonWrite(double PosCarro1X, double PosCarro1Y, char* TipoCarro1, double velocidadCarro1, double PosCarro2X, double PosCarro2Y, char* TipoCarro2, double velocidadCarro2, int* posBalas, int balasN, char* accion);

char* jsonInitWrite(int raceSize, int* curvas, int Ncurvas, int* vidas, int Nvidas, int* huecos, int Nhuecos, int* boosts, int Nboosts);
#endif //GAME_WRITEJSON_H

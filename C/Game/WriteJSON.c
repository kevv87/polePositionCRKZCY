//
// Created by cvaz on 6/20/20.
//
#include <stdio.h>
#include "WriteJSON.h"
#include "json-c/json_object.h"

char* jsonWrite(double PosCarro1X, double PosCarro1Y, char* TipoCarro1, double velocidadCarro1, double PosCarro2X, double PosCarro2Y, char* TipoCarro2, double velocidadCarro2, int* posBalas, int balasN, char* accion){

    struct json_object *jobj;
    jobj = json_object_new_object();

    // Creando la json_array para posBalas y populandola
    struct json_object* jarr = json_object_new_array();
    for(int i=0;i<balasN;i++){
        json_object_array_add(jarr,json_object_new_int(posBalas[i]));
    }

    json_object_object_add(jobj,"poscarro1X",json_object_new_double(PosCarro1X));
    json_object_object_add(jobj,"poscarro1Y",json_object_new_double(PosCarro1Y));
    json_object_object_add(jobj, "tipoCarro1", json_object_new_string(TipoCarro1));
    json_object_object_add(jobj, "velocidadCarro1", json_object_new_double(velocidadCarro1));
    json_object_object_add(jobj, "posCarro2X", json_object_new_double(PosCarro2X));
    json_object_object_add(jobj, "posCarro2Y", json_object_new_double(PosCarro2Y));
    json_object_object_add(jobj, "tipoCarro2", json_object_new_string(TipoCarro2));
    json_object_object_add(jobj, "velocidadCarro2", json_object_new_double(velocidadCarro2));
    json_object_object_add(jobj, "posBalas", jarr);
    json_object_object_add(jobj, "accion", json_object_new_string(accion));

    return json_object_to_json_string_ext(jobj, "JSON_C_TO_STRING_PLAIN");
}

char* jsonInitWrite(int raceSize, int* curvas, int Ncurvas, int* vidas, int Nvidas, int* huecos, int Nhuecos, int* boosts, int Nboosts){

    struct json_object *jobj;
    jobj = json_object_new_object();

    struct json_object* jcurvas = json_object_new_array();
    struct json_object* jvidas = json_object_new_array();
    struct json_object* jhuecos = json_object_new_array();
    struct json_object* jboosts = json_object_new_array();

    for(int i=0;i<Nvidas;i++){
        json_object_array_add(jvidas, json_object_new_int(vidas[i]));
    }
    for(int i=0;i<Ncurvas;i++){
        json_object_array_add(jcurvas, json_object_new_int(curvas[i]));
    }
    for(int i=0;i<Nhuecos;i++){
        json_object_array_add(jhuecos, json_object_new_int(huecos[i]));
    }
    for(int i=0;i<Nboosts;i++){
        json_object_array_add(jboosts, json_object_new_int(boosts[i]));
    }

    json_object_object_add(jobj, "Vidas", jvidas);
    json_object_object_add(jobj, "Huecos", jhuecos);
    json_object_object_add(jobj, "Boosts", jboosts);
    json_object_object_add(jobj, "Curvas", jcurvas);

    return json_object_to_json_string_ext(jobj, "JSON_C_TO_STRING_PLAIN");
}
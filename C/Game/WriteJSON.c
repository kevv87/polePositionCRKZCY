//
// Created by cvaz on 6/20/20.
//
#include <stdio.h>
#include "json-c/json_object.h"

char* jsonWrite(int PosCarro1, char* TipoCarro1, int velocidadCarro1, int PosCarro2, char* TipoCarro2, int velocidadCarro2, int* posBalas, int balasN, char* accion){

    struct json_object *jobj;
    jobj = json_object_new_object();

    // Creando la json_array para posBalas y populandola
    struct json_object* jarr = json_object_new_array();
    for(int i=0;i<balasN;i++){
        json_object_array_add(jarr,json_object_new_int(posBalas[i]));
    }

    json_object_object_add(jobj,"poscarro1",json_object_new_int(PosCarro1));
    json_object_object_add(jobj, "tipoCarro1", json_object_new_string(TipoCarro1));
    json_object_object_add(jobj, "velocidadCarro1", json_object_new_int(velocidadCarro1));
    json_object_object_add(jobj, "posCarro2", json_object_new_int(PosCarro2));
    json_object_object_add(jobj, "tipoCarro2", json_object_new_string(TipoCarro2));
    json_object_object_add(jobj, "velocidadCarro2", json_object_new_int(velocidadCarro2));
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
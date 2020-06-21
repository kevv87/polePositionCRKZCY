cmake_minimum_required(VERSION 3.16)
project(Game C)

set(CMAKE_C_STANDARD 99)

add_executable(Game Juego.c "Server-main.c" Server.c Server.h ListaEnlazadaColisionables.c ListaEnlazadaColisionables.h WriteJSON.c WriteJSON.h)

SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -ljson-c")

target_link_libraries(Game m json-c)

cmake_minimum_required(VERSION 3.16)
project(Game C)

set(CMAKE_C_STANDARD 99)

add_executable(Game Juego.c ListaEnlazada.h ListaEnlazada.c "Server-main.c")

target_link_libraries(Game m)
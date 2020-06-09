cmake_minimum_required(VERSION 3.16)
project(polePositionCRKZCY C)

set(CMAKE_C_STANDARD 99)

add_executable(polePositionCRKZCY server.c client.c)
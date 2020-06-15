//
// Created by cvaz on 6/15/20.
//

#ifndef GAME_LISTAENLAZADA_H
#define GAME_LISTAENLAZADA_H

//Creating a new type
typedef void *  pVoid;
// Creating Node
struct Node{
    /*void pointer*/
    pVoid iData;
    /*Node Pointer*/
    struct Node *pNextNode;
};
// Define the new type Node type and Node pointer
typedef  struct Node  NodeType, * NodePointer;

int CreateLinkedList(NodePointer *pHead, void *InputData, int SizeofData);
int InsertNodeAtEnd(NodePointer * pHead, void *InputData, int SizeofData);

#endif //GAME_LISTAENLAZADA_H

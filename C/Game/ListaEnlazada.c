//
// Created by cvaz on 6/15/20.
//
#include "ListaEnlazada.h"
#include<stdio.h>
#include<stdlib.h>
#include <stdint.h>
//Calculate size of buffer element
#define DATA_SIZE(y) sizeof(y[0])
//Calculate number of element in buffer
#define Number_Node(x)  sizeof(x)/DATA_SIZE(x)

int CreateLinkedList(NodePointer *pHead, void *InputData, int SizeofData){
    int iRetValue = -1;
    int iOffSet = 0;
    NodePointer pNewNode = NULL;
    if((*pHead) == NULL){
        // Call malloc to allocate memory in heap for the first node
        pNewNode = malloc(sizeof(NodeType));
        if( pNewNode != NULL){      //Check allocated memory
            pNewNode->iData = malloc(SizeofData); //put the desire Data
            //Copy the bytes of data as per the data types
            for (iOffSet = 0; iOffSet < SizeofData; iOffSet++){
                *((uint8_t *)(pNewNode->iData  + iOffSet)) =   *((uint8_t *)(InputData + iOffSet));
            }
            pNewNode->pNextNode  = NULL; //Give the Address of first Node
            *pHead = pNewNode; /*Assign the address of
                                      the first node to the head pointer*/
            iRetValue = 0; // Update the return value
        }
    }
    else{
        //Add the Node at the End
        iRetValue = InsertNodeAtEnd(pHead,InputData,SizeofData);
    }
    return iRetValue;
}

int InsertNodeAtEnd(NodePointer * pHead, void *InputData, int SizeofData){
    int iRetValue = -1;
    int iOffSet = 0;
    NodePointer pLastNode = NULL;
    NodePointer pNewNode = NULL;
    //Give the Address of first Node
    pLastNode  = *pHead;
    // Call malloc to allocate memory in heap for the new node
    pNewNode = malloc(sizeof(NodeType));
    if( pNewNode != NULL){      //Check allocated memory
        pNewNode->iData = malloc(SizeofData); //put the desire Data
        //Copy the bytes of data as per the data types
        for (iOffSet = 0; iOffSet < SizeofData; iOffSet++){
            *((uint8_t *)(pNewNode->iData  + iOffSet)) =  *((uint8_t *)(InputData + iOffSet));
        }
        pNewNode->pNextNode  = NULL; //Give the Address of first Node
        iRetValue = 0; // Update the return value
    }
    // If there is no node in beginning
    if(pLastNode == NULL){
        *pHead = pNewNode;
    }
    else{
        // Find the address of last node
        while( pLastNode ->pNextNode != NULL){
            pLastNode  = pLastNode ->pNextNode;
        }
        // Assign last node address
        pLastNode ->pNextNode = pNewNode;
    }
    return iRetValue;
}

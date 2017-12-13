// Name: Bingxue Ouyang
// CruzID: bouyang
// Lab 5
#include<stdio.h>
#include<stdlib.h>
#include<assert.h>
#include "queue.h"
typedef struct Node{
    int val;
    struct Node* next;
}Node;

typedef Node* NodeRef;

struct Queue{
    NodeRef top, last;
}Queue;

NodeRef newNode(int x){
    NodeRef newVal = malloc(sizeof(Node));
    assert(newVal!= NULL);
    newVal -> val = x;
    newVal -> next = NULL;
    return(newVal);
}
void freeNode(NodeRef *pN){
    if(pN != NULL && *pN!=NULL){
        free(*pN);
        *pN=NULL;
    }
}

queueRef newQueue(void){
    queueRef Q = malloc(sizeof(Queue));
    assert(Q!=NULL);
    Q->top = NULL;
    Q->last = NULL;
    return Q;
}
void freeQueue(queueRef *pQ){
    if(pQ != NULL && *pQ!=NULL){
        free(*pQ);
        *pQ=NULL;
    }
}
int enqueue(int val, queueRef Q){
    NodeRef newN = newNode(val);
    if(Q->last == NULL){
        Q->top = newN;
        Q->last = newN;
        return val;
    }
    Q->last->next= newN;
    Q->last = newN;
    return val;
}

void dequeue(FILE* out, queueRef Q){
    if(Q->top == NULL){
        fprintf(out, "empty\n");
        return;
    }
    NodeRef temp = Q->top;
    Q->top = Q->top->next;
    if(Q->top == NULL){
        Q->last = NULL;
    }
    fprintf(out, "%d\n",temp->val);
}

void print(FILE* out, queueRef Q){
    if(Q != NULL){
        NodeRef temp = Q->top;
        while(temp!=NULL){
            fprintf(out, "%d ", temp->val);
            temp = temp->next;
        }
        fprintf(out, "\n");
    }
}
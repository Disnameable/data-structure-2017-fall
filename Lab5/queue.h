//-----------------------------------------------------------------------------
// IntegerLinkedList.h
// Header file for the queue ADT
//-----------------------------------------------------------------------------

#ifndef _QUEUE_H_INCLUDE_
#define _QUEUE_H_INCLUDE_

#include<stdio.h>

// QUEUE
typedef struct Queue* queueRef;
// queue constructor
queueRef newQueue(void);

void freeQueue(queueRef *pQ);

// print function, print queue
void print(FILE* out, queueRef Q);

// enqueue function, insert
int enqueue(int val, queueRef Q);

// dequeue function, deletes the oldest
void dequeue(FILE* out, queueRef Q);

#endif

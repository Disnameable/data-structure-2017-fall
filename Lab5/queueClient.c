
// Name: Bingxue Ouyang
// CruzID: bouyang
// Lab 5
// queueClient.c

 #include<stdio.h>
 #include<stdlib.h>
 #include"queue.h"
 #define maximum 100

int main(int argc, char* argv[]){
    FILE* in = fopen(argv[1], "r");
    FILE* out = fopen(argv[2], "w");
    if(argc < 2){
        printf("Usage: %s <input file> <output file>\n", argv[0]);
        exit(EXIT_FAILURE);
    }
    // check if input and output files are able to read and write
    if(in==NULL){
        printf("Unable to read from file %s\n", argv[1]);
        exit(EXIT_FAILURE);
    }
    if(out==NULL){
        printf("Unable to write to file %s\n", argv[2]);
        exit(EXIT_FAILURE);
    }

    queueRef q = newQueue();
    char* line;
    line = malloc(1000);
    char command;
    int value;
    while(!feof(in)){
        fscanf(in, "%c %d",&command, &value);
        if(command=='e'){
            enqueue(value,q);
            fprintf(out,"enqueued %d\n", value);
        } 
        else if(command =='d'){
            dequeue(out,q);
        }
        else if(command=='p'){
            print(out, q);
        }
    }
    freeQueue(&q);
    fclose(in);
    fclose(out);
    
    return (EXIT_SUCCESS);
}
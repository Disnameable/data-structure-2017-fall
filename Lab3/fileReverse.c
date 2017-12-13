/* 
 * Name: Bingxue Ouyang
 * cruzID: bouyang
 * fileReverse.c
 * a c program that reads input file and reverse it to output file
 */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

void stringReverse(char* s){
    // the first char
    int head = 0;
    // the last char
    int end = strlen(s)-1;
    // start replacing from both sides, move towards middle
    while(head<=end){
        char replace = s[end];
        s[end] = s[head];
        s[head] = replace;
        head++;
        end--;
    }
}
int main(int argc, char* argv[]){
    // check if command contains input and output file
    if(argc<2){
        printf("Usage: %s some-string\n", argv[0]);
        exit(EXIT_FAILURE);
    }
    // initialize input and output files
    FILE* in = fopen(argv[1], "r");
    FILE* out = fopen(argv[2], "w");
    char word[256];
    // check if input and output files are able to read and write
    if(in==NULL){
        printf("Unable to read from file %s\n", argv[1]);
        exit(EXIT_FAILURE);
    }
    if(out==NULL){
        printf("Unable to write to file %s\n", argv[2]);
        exit(EXIT_FAILURE);
    }
    // start reading and reverse
    while(fscanf(in, " %s", word) != EOF){
        stringReverse(word);
        fprintf(out,"%s\n", word);
    }
    // close both files
    fclose(in);
    fclose(out);
    
    return (EXIT_SUCCESS);
}
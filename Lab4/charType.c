// Name: Bingxue Ouyang
// CruzID: bouyang
// a program that reads input file, detect and calculate
// alphabetic, numeric, punctuation and whitespace
// in each line
// and report in output file
#include<stdio.h>
#include<ctype.h>
#include<stdlib.h>
#include<string.h>
#include<assert.h>
#define maximum 100 

int main(int argc, char* argv[]){
    void extract_chars(char* s, char* a, char* d, char* p, char* w);

    FILE* in = fopen(argv[1], "r");
    FILE* out = fopen(argv[2], "w");

    if(argc < 2){
        // check command line
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
    char* line;
    char* alph;
    char* num;
    char* pun;
    char* whitespace;

    line = calloc(maximum+1, sizeof(char));
    alph = calloc(maximum+1, sizeof(char));
    num = calloc(maximum+1, sizeof(char));
    pun = calloc(maximum+1, sizeof(char));
    whitespace = calloc(maximum+1, sizeof(char));
    assert(line != NULL && alph != NULL && num != NULL && pun != NULL && whitespace != NULL);

    int i = 1;
    while(fgets(line, maximum, in) != NULL){
        extract_chars(line, alph, num, pun, whitespace);
        int anum = (int)strlen(alph);
        int nnum = (int)strlen(num);
        int pnum = (int)strlen(pun);
        int wnum = (int)strlen(whitespace);
        fprintf(out,"line %d contains:\n", i);

        if(anum == 1) fprintf(out,"%d alphabetic character: %s\n", anum, alph);
        else fprintf(out, "%d alphabetic characters: %s\n", anum, alph);

        if(nnum==1) fprintf(out, "%d numeric character: %s\n", nnum, num);
        else fprintf(out, "%d numeric characters: %s\n", nnum, num);

        if(pnum==1) fprintf(out, "%d punctuation character: %s\n", pnum, pun);
        else fprintf(out, "%d punctuation characters: %s\n", pnum, pun);

        if(wnum==1) fprintf(out, "%d whitespace character: %s\n", wnum, whitespace);
        else fprintf(out, "%d whitespace characters: %s\n", wnum, whitespace);

        i++;
    }

    free(line);
    free(alph);
    free(num);
    free(pun);
    free(whitespace);
    fclose(in);
    fclose(out);

    return EXIT_SUCCESS;
}

void extract_chars(char* s, char* a, char* d, char* p, char* w){
    int i=0;
    int an=0;
    int dn=0;
    int pn=0;
    int wn=0;

    while(s[i] != '\0' && i < maximum){
        if(isupper((int)s[i]) || isalpha((int)s[i]) ){
            a[an] = s[i];
            an++;
            a[an]='\0';
        }
        else if(isdigit((int)s[i])){
            d[dn] = s[i];
            dn++;
            d[dn]='\0';
        }
        else if(ispunct((int)s[i])){
            p[pn] = s[i];
            pn++;
            p[pn]='\0';
        }
        else{
            w[wn] = s[i];
            wn++;
            w[wn]='\0';
        }
        i++;
    }
}
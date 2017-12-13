//-----------------------------------------------------------------------------
// FileReverse.c
// Reverses all tokens in a file and outputs them to another file
// Solution to Lab3 in CMPS 12B/M 
// Code reuse from other lab 3 documents. 
// 
// Usage: this should be compiled into executable FileReverse.
// The command "./FileReverse <in> <out>" will produce a file <out> that, in a separate line, has the reverse of each word of <in>
//
// Matt Bryson, Jan 2016. C. Seshadhri, Sept 2017.
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

//Function definitions 
void stringReverse(char* s);

int main(int argc, char* argv[])
{
	FILE* in;  /* file handle for input */  
	FILE* out; /* file handle for output */
	char word[256]; /* char array to store words from input file */
	
    if(argc < 3){
       printf("Usage: %s <input file> <output file>\n", argv[0]);
       exit(EXIT_FAILURE);
    }
	  
	/* open input file for reading */
	in = fopen(argv[1], "r");
	if( in==NULL )
	{
		printf("Unable to read from file %s\n", argv[1]);
		exit(EXIT_FAILURE);
	}

	/* open output file for writing */
	out = fopen(argv[2], "w");
	if( out==NULL )
	{
		printf("Unable to write to file %s\n", argv[2]);
		exit(EXIT_FAILURE);
	}
	
	/* read words from input file, print on separate lines to output file*/
	while( fscanf(in, " %s", word) != EOF )
	{
		//printf("%s\n", word);
		stringReverse(word);
		fprintf(out, "%s\n", word);
	}

	/* close input and output files */
	fclose(in);
	fclose(out);
	
	return(EXIT_SUCCESS);
	
}

// function to reverse string. 
// Input: string s
// Output: none. The string s is modified into its reverse.

void stringReverse(char* s)
{
	int length, halfway;
	if(s != NULL) // if string is null, do nothing
	{
		length = strlen(s); 
	}
	else
	{
		return;
	}
	
    halfway = (length-1)/2; // index at half the length of s
   
    for(int i = 0; i <= halfway; i++) //loop up to the halfway point
	{
        int to_swap = length-1-i; //index where ith char should end up
		char temp = s[i];  // swap s[i] and s[to_swap]
		s[i] = s[to_swap];
		s[to_swap] = temp;
	}
}

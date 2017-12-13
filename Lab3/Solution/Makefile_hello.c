#Makefile for hello.c with macros

FLAGS   = -std=c99 -Wall
SOURCES = hello.c
OBJECTS = hello.o
EXEBIN  = hello

all: $(EXEBIN)

$(EXEBIN) : $(OBJECTS)
	gcc -o $(EXEBIN) $(OBJECTS)

$(OBJECTS) : $(SOURCES)
	gcc -c $(FLAGS) $(SOURCES)

clean :
	rm -f $(EXEBIN) $(OBJECTS)

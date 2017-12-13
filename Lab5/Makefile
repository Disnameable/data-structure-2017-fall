# Name: Bingxue Ouyang
# cruzID: bouyang
# Makefile for queue.c with macros

FLAGS = -std=c99 -Wall
SOURCES = queue.c queueClient.c
OBJECTS = queue.o queueClient.o
HEADERS = queue.h
EXEBIN = queueClient

all: $(EXEBIN)

$(EXEBIN) : $(OBJECTS) $(HEADERS)
	gcc -o $(EXEBIN) $(OBJECTS)

$(OBJECTS) : $(SOURCES) $(HEADERS)
	gcc -c $(FLAGS) $(SOURCES)

clean :
	rm -f $(EXEBIN) $(OBJECTS)

check:
	bcheck -all $(EXEBIN) 
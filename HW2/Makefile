# Name: Bingxue Ouyang
# CruzID: bouyang
# Makefile for ChessBoard
JAVASRC = ChessBoard.java ChessPiece.java Node.java LinkedList.java King.java Queen.java Knight.java Rook.java Bishop.java Pawn.java
SOURCES = README Makefile $(JAVASRC)
MAINCLASS = ChessBoard
CLASSES = ChessBoard.class ChessPiece.class Node.class LinkedList.class King.class Queen.class Knight.class Rook.class Bishop.class Pawn.class
JARFILE = ChessBoard.jar

all:$(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	chmod +x $(JARFILE)


$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)

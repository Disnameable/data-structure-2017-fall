# Name: Bingxue Ouyang
# CruzID: bouyang
# Makefile for ChessMove
JAVASRC = ChessMoves.java ChessPiece.java Node.java ChessBoard.java King.java Queen.java Knight.java Rook.java Bishop.java Pawn.java
SOURCES = README Makefile $(JAVASRC)
MAINCLASS = ChessMoves
CLASSES = ChessMoves.class ChessPiece.class Node.class ChessBoard.class King.class Queen.class Knight.class Rook.class Bishop.class Pawn.class
JARFILE = ChessMoves.jar

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

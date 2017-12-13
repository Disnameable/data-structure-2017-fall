#-------------------------------
# Name: Bingxue Ouyang
# CruzID: bouyang
# Makefile for ChessBoard.java and Queen.java
#-------------------------------

JAVASRC = ChessBoard.java Queen.java
SOURCES = README Makefile $(JAVASRC)
MAINCLASS = ChessBoard
CLASSES = ChessBoard.class Queen.class
JARFILE = NQueens.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	chmod +x $(JARFILE)

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)
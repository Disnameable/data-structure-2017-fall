#-------------------------------
# Name: Bingxue Ouyang
# CruzID: bouyang
# Makefile for NQueens.jar
#-------------------------------

JAVASRC = Queen.java NQueens.java
SOURCES = README Makefile $(JAVASRC)
MAINCLASS = NQueens
CLASSES = Queen.class NQueens.class
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
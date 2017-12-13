NQueens.jar: NQueens.class Queen.class
	echo Main-class: NQueens > Manifest
	jar cvfm NQueens.jar Manifest NQueens.class Queen.class
	rm Manifest

NQueens.class Queen.class: NQueens.java Queen.java
	javac -Xlint *.java

clean:
	rm *.class
	rm *.jar

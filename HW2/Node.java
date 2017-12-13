/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * Node object
 * contains a ChessPiece object and next pointer
 */
class Node{
    public ChessPiece piece;
    public Node next;
    public Node(ChessPiece newPiece){
        piece = newPiece;
        next = null;
    }
}
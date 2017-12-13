/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * A chessboard class that's made of a linked list and its operation methods
 */
class ChessBoard{
    public int size;
    public Node head;
    // constructor
    public void ChessBoard(){
        head = null;
    }
    public void insert(ChessPiece npiece){
        Node curr = new Node(npiece);
        curr.next = head;
        head = curr;
        size ++;
    }
    public ChessPiece find(int x, int y){
        Node curr = head;
        while(curr != null){
            if(curr.piece.x == x && curr.piece.y == y){
                return curr.piece;
            }
            curr = curr.next;
        }
        return null;
    }
    public ChessPiece findName(String a){
        Node curr = head;
        while(curr != null){
            if(curr.piece.name.equals(a)){
                return curr.piece;
            }
            curr = curr.next;
        }
        return null;
    }
    // check if chesspiece attacks its enemy
    public boolean findAttack(ChessPiece newPiece){
        Node curr = head;
        while(curr !=null){
            if(curr.piece.isWhite != newPiece.isWhite){
                if(newPiece.isAttack(curr.piece) && curr.piece != newPiece) return true;
            }
            curr = curr.next;
        }
        return false;
    }
    public void delete(int x, int y){
        Node prev = null;
        Node curr = head;
        while( !(curr.piece.x == x && curr.piece.y == y) && curr != null ){
            prev = curr;
            curr = curr.next;
        }
        if(curr == null) return;
        if(prev == null) head = head.next;
        else prev.next = curr.next;
        size --;
    }
}
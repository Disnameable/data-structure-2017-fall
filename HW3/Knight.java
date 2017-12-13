/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * Knight class and its attack method
 */
class Knight extends ChessPiece{
    public Knight(String name, int x, int y){
        super(name, x, y);
    }
    public boolean isAttack(ChessPiece piece){
        int absX = Math.abs(piece.x - this.x);
        int absY = Math.abs(piece.y - this.y);
        // "reverse" of Queen attack method
        if( absX * absY == 2 ) return true;
        return false;
    }
    public boolean isValidMove(int toX, int toY, ChessBoard board, int counter){
        if(this.isWhite && counter%2 != 0) return false;
        if(!this.isWhite && counter%2 == 0) return false;
        ChessPiece target = new Pawn("p", toX, toY);
        if(this.isAttack(target)){
            Node curr = board.head;
            // check all blocking pieces
            while(curr != null){
                ChessPiece path = curr.piece;
                if(this.isAttack(path)){
                    // if this piece is the target
                    if(path.x == toX && path.y == toY){
                        if(this.isWhite != path.isWhite){
                            return true;
                        }
                        else return false;
                    }
                }
                curr = curr.next;
            }
            if(board.find(toX,toY) != null){
                if(this.isWhite != board.find(toX,toY).isWhite){
                    return true;
                }
                else return false;
            }
            return true;
        }
        else{
            return false;
        }
    }
    public void update(int toX, int toY, ChessBoard board){
        if(board.find(toX,toY) != null){
            if(this.isWhite != board.find(toX,toY).isWhite){
                board.delete(toX, toY);
            }
        }
        ChessPiece newpiece = new Knight(this.name,toX,toY);
        board.insert(newpiece);
        board.delete(this.x, this.y);
    }
}
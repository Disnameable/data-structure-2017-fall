/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * Pawn class and its attack method
 */
class Pawn extends ChessPiece{
    public Pawn(String name, int x, int y){
        super(name, x, y);
    }
    public boolean isAttack(ChessPiece piece){
        int updown;
        int absX = Math.abs(piece.x - this.x);
        if(this.isWhite) updown = 1;
        else updown = -1;
        if(absX == 0 && piece.y - this.y == updown) return true;
        return false;
    }
    public boolean isValidMove(int toX, int toY, ChessBoard board, int counter){
        int updown;
        if(this.isWhite) updown = 1;
        else updown = -1;
        // first move moves 2 steps
        if((this.isWhite && counter == 2) || (!this.isWhite && counter == 3)){
            // no blocking
            if(board.find(toX,this.y+updown)==null){
                if(toY-this.y==(updown*2))return true;
            }
            else return false;
        }
        ChessPiece target = new Pawn("p", toX, toY);
        if(this.isAttack(target)) return true;
        if(Math.abs(toX-this.x)==1 && toY-this.y == updown){
            //System.out.println("Pawn moves");
            if(board.find(toX,toY) != null){
                if(this.isWhite != board.find(toX,toY).isWhite){
                    return true;
                }
            }
        }
        return false; // movement is not valid
    }
    
    public void update(int toX, int toY, ChessBoard board){
        if(board.find(toX,toY) != null){
            if(this.isWhite != board.find(toX,toY).isWhite){
                board.delete(toX, toY);
            }
        }
        ChessPiece newpiece = new Pawn(this.name,toX,toY);
        board.insert(newpiece);
        board.delete(this.x, this.y);
    }
}
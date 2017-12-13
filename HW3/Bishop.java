/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * Bishop class and its attack method
 */

class Bishop extends ChessPiece{
    public Bishop(String name, int x, int y){
        super(name, x, y);
    }
    public boolean isAttack(ChessPiece piece){
        int absX = Math.abs(piece.x - this.x);
        int absY = Math.abs(piece.y - this.y);
        // diagonal
        if(absY == absX) return true;
        return false;
    }

    public void update(int toX, int toY, ChessBoard board){
        if(board.find(toX,toY) != null){
            if(this.isWhite != board.find(toX,toY).isWhite){
                board.delete(toX, toY);
            }
        }
        ChessPiece newpiece = new Bishop(this.name,toX,toY);
        board.insert(newpiece);
        board.delete(this.x, this.y);
    }
}
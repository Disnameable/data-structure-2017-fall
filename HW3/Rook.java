/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * Rook class and its attack method
 */
class Rook extends ChessPiece{
    public Rook(String name, int x, int y){
        super(name, x, y);
    }
    public boolean isAttack(ChessPiece piece){
        // straight lines
        if(piece.x == this.x || piece.y == this.y) return true;
        return false;
    }
    public void update(int toX, int toY, ChessBoard board){
        if(board.find(toX,toY) != null){
            if(this.isWhite != board.find(toX,toY).isWhite){
                board.delete(toX, toY);
            }
        }
        ChessPiece newpiece = new Rook(this.name,toX,toY);
        board.insert(newpiece);
        board.delete(this.x, this.y);
    }
}
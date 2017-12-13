/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * King class and its attack method
 */
class King extends ChessPiece{
    public King(String name, int x, int y){
        super(name, x, y);
    }
    public boolean isAttack(ChessPiece piece){
        int absX = Math.abs(piece.x - this.x);
        int absY = Math.abs(piece.y - this.y);
        if( (absX == 0 && absY == 0) ||
            (absX == 1 && absY == 1) ||
            (absX == 1 && absY == 0) ||
            (absX == 0 && absY == 1) ){
            return true;
        }
        return false;
    }
    public void update(int toX, int toY, ChessBoard board){
        if(board.find(toX,toY) != null){
            if(this.isWhite != board.find(toX,toY).isWhite){
                board.delete(toX, toY);
            }
        }
        ChessPiece newpiece = new King(this.name,toX,toY);
        board.insert(newpiece);
        board.delete(this.x, this.y);
    }
}
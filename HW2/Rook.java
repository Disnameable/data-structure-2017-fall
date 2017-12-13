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
}
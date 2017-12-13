/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * Queen class and its attack method
 */
class Queen extends ChessPiece{
    public Queen(String name, int x, int y){
        super(name, x, y);
    }
    public boolean isAttack(ChessPiece piece){
        int absX = Math.abs(piece.x - this.x);
        int absY = Math.abs(piece.y - this.y);
        // diagonal and straight
        if(absY == absX || piece.x == this.x || piece.y == this.y){
            return true;
        }
        return false;
    }
}
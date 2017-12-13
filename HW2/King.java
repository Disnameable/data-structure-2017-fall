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
        // surrouding
        if( (absX == 0 && absY == 0) ||
            (absX == 1 && absY == 1) ||
            (absX == 1 && absY == 0) ||
            (absX == 0 && absY == 1) ){
            return true;
        }
        return false;
    }
}
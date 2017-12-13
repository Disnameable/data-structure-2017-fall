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
        if( (absX == 0 && absY == 0) ||
            (absX == 1 && absY == 2) ||
            (absX == 2 && absY == 1) ){
            return true;
        }
        return false;
    }
}
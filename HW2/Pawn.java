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
        if(absX == 1 && piece.y - this.y == updown) return true;
        return false;
    }
}
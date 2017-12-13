/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * ChessPiece abstract super class
 */
abstract class ChessPiece{
    public String name;
    public int x;
    public int y;
    public boolean isWhite;

    public ChessPiece(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
        // assign color
        if(name.equals(name.toUpperCase())){
            isWhite = false;
        }
        else isWhite = true;
    }
    
    public boolean isAttack(ChessPiece piece){
        return false;
    }
}
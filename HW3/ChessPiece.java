/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * ChessPiece abstract super class
 */
import java.lang.*;
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

    public boolean isValidMove(int toX, int toY, ChessBoard board, int counter){
        if(this.isWhite && counter%2 != 0) return false;
        if(!this.isWhite && counter%2 == 0) return false;
        ChessPiece target = new Pawn("p", toX, toY);
        if(this.isAttack(target)){
            Node curr = board.head;
            int goRight = 1;
            int goUp = 1;
            if(toX-this.x<0) goRight = -1; // left
            else if(toX-this.x==0) goRight = 0; // origin
            if(toY-this.y<0) goUp = -1; // down
            else if(toY-this.y==0) goUp = 0; // origin
            // check all blocking pieces
            while(curr != null){
                ChessPiece path = curr.piece;
                if(this.isAttack(path)){
                    if(goUp == 0 && goRight == 1){ // right
                        if(path.x > this.x && path.x < toX && path.y == toY) return false;
                    }
                    else if(goUp == 0 && goRight == -1){ // left
                        if(path.x > toX && path.x < this.x && path.y == toY) return false;
                    }
                    else if(goRight == 0 && goUp == 1){ // up
                        if(path.y > this.y && path.y < toY && path.x == toX) return false;
                    }
                    else if(goRight == 0 && goUp == -1){ // down
                        if(path.y > toY && path.y < this.y && path.x == toX) return false;
                    }
                    else if(goUp == 1 && goRight == 1){ // right up
                        if(path.y > this.y && path.y < toY && path.x > this.x && path.x < toX) return false;
                    }
                    else if(goUp == -1 && goRight == -1){ // left down
                        if(path.y < this.y && path.y > toY && path.x < this.x && path.x > toX) return false;
                    }
                    else if(goUp == -1 && goRight == 1){ //right down
                        if(path.y < this.y && path.y > toY && path.x > this.x && path.x < toX) return false;
                    }
                    if(goUp == 1 && goRight == -1){ //left up
                        if(path.y > this.y && path.y < toY && path.x < this.x && path.x > toX) return false;
                    }
                }
                curr=curr.next;
            }
            if(board.find(toX,toY) != null){
                if(this.isWhite != board.find(toX,toY).isWhite){
                    return true;
                }
                else return false;
            }
            return true; // no blocking
        }
        else return false; // movement is not valid
    }

    public void update(int toX, int toY, ChessBoard board){}

    public boolean canEscape(){
        return false;
    }
}
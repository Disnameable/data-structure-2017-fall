/* Name: Bingxue Ouyang
 * CruzID: bouyang
 * Queen class
 */

class Queen extends ChessBoard{
    
    int qx, qy;
    
    // queen class
    Queen(int qx, int qy){
        this.qx=qx;
        this.qy=qy;
    }
    
    // check if this space is safe
    boolean isSafe(Queen[] solution, int x, int y){
        // check for every placed queen
        for(int i=0; i<solution.length; i++){
            if(solution[i]!=null){
                if(solution[i].qx == x ||
                   solution[i].qy == y ||
                   solution[i].qx-solution[i].qy == x-y ||
                   solution[i].qx+solution[i].qy == x+y) return false;
            }
        }
        return true;
    }
    
    // place queen
    boolean placeQueen(Queen[] solution, int x, int begin){
        // cycle ends
        if(x==begin) return true;
        
        // cycle begins
        if(x==solution.length){
            if(solution[0]==null) x=0;
            else return true;
        }
        
        // check for every space in x column
        for(int y=0; y<solution.length; y++){
            if(isSafe(solution,x,y)){
                solution[x] = new Queen(x,y);
                // start recursion
                if(placeQueen(solution, x+1, begin)) return true;
                // remove wrong queen
                else solution[x]=null;
            }
        }
        return false;
    }
}

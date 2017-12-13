/* Name: Bingxue Ouyang
 * CruzID: bouyang
 * Queen class
 */
import java.util.Stack;

class Queen extends NQueens{
    int qx, qy;
    
    // queen class
    public Queen(int qx, int qy){
        this.qx=qx;
        this.qy=qy;
    }
        // check if this space is safe
    public boolean isSafe(Queen[] solution, int x, int y){
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
    
    public boolean placeQueen(Queen[] solution, Queen[] jump){
        int top;
        Stack<Integer> s = new Stack<>();
        int x = 0;
        int start = 0;
        while(x<=solution.length){
            System.out.println("x is "+x);
            for(int i=0; i<jump.length; i++){
                if(jump[i] != null){
                    if(x==i){
                        x++;
                        System.out.println("x is now "+x);
                    }
                }
            }
            if(x>=solution.length) return true;
            boolean placed = false;
            for(int y=start; y<solution.length; y++){
                if(isSafe(solution, x, y) && isSafe(jump, x, y)){
                    System.out.println("placed with y "+y);
                    solution[x] = new Queen(x,y);
                    placed = true;
                    s.push(y);
                    x++;
                    break;
                }
            }
            if(!placed){
                if(s.isEmpty()){
                    System.out.println("empty");
                    return false;
                }
                System.out.println("pop");
                start = s.pop()+1;
                x--;
                System.out.println("pop x is "+x);
                for(int i=0; i<jump.length; i++){
                    if(jump[i] != null){
                        if(x==i){
                            x--;
                            System.out.println("pop x is now "+x);
                        }
                    }
                }
                solution[x]=null;
            }
            else{
                start = 0;
                placed=false;
            }
        }
        return false;
    }
}
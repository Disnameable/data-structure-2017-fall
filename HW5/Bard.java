/* Bingxue Ouyang
 * bouyang
 * a program that sort words in text file and output them by length and rank
 */
import java.util.*;
import java.io.*;

public class Bard{
    
    public static void main(String[] args) throws IOException{
        if(args.length<2){
            System.out.println("Usage: java -jar NQueens.jar <input file> <output file>");
            System.exit(1);
        }
        // open files
        Scanner text = new Scanner(new File("shakespeare.txt"));
        Scanner in = new Scanner(new File(args[0]));
        PrintWriter out = new PrintWriter(new FileWriter(args[1]));

        Hashtable<String, Integer> counter = new Hashtable<String, Integer>();

        // create hash table for token (key) and frequency (value)
        while(text.hasNextLine()){
            String line = text.nextLine();
            line = line.replace("?", " ").replace(",", " ").replace(".", " ").replace("!", " ").replace(":", " ").replace(";", " ").replace("[", " ").replace("]", " ");
            String[] token = line.trim().split("\\s+");
            for(int i=0; i<token.length; i++){
                if(token[i].length()>1){
                    token[i]=token[i].toLowerCase();
                    if(!counter.containsKey(token[i])){
                        counter.put(token[i], 1);
                    }
                    else{
                        counter.put(token[i], (counter.get(token[i]))+1);
                    }
                }
            }
        }
        text.close();

        // create length hash table with length (key) and words with that length (val/array list)
        Hashtable<Integer, ArrayList<String>> leng = new Hashtable<Integer, ArrayList<String>>();
        // use key set
        Set<String> s = counter.keySet();
        for(String token : s){
            int l = token.length();
            // sort words by size
            ArrayList<String> curr;
            if(leng.containsKey(l)){
                curr = leng.get(l);
            }
            else{
                curr = new ArrayList<String>();
            }
            curr.add(token);
            leng.put(l, curr);
        }
        
        while(in.hasNextLine()){
            String line = in.nextLine().trim()+" ";
            String[] split = line.split("\\s+");
            int length = Integer.parseInt(split[0]);
            int rank = Integer.parseInt(split[1]);
            int most = 0;
            String winner = "";
            ArrayList<String> c = leng.get(length);
            if(c==null){
                out.println("-");
                continue;
            }
            ArrayList<Word> ranker = new ArrayList<Word>();
            for(String word : c){ // reads from the arraylist of length
                int freq = counter.get(word);
                ranker.add(new Word(word, freq));
            }
            // sort the ranker by word
            Collections.sort(ranker);
            Collections.reverse(ranker);
            // sort the ranker by frequency
            Collections.sort(ranker, new Word());
            Collections.reverse(ranker);
            if(rank>=ranker.size()){
                out.println("-");
                continue;
            }
            Iterator<Word> itr = ranker.iterator();
            for(int i = 0; i<=rank; i++){
                winner = itr.next().word;
            }
            
            out.println(winner);
        }
        in.close();
        out.close();

    }
}
// Word class to compare/sort
class Word implements Comparator<Word>, Comparable<Word> {
   String word;
   int frequency;
   Word() {}

   Word(String w, int f) {
      word = w;
      frequency = f;
   }
   public int compareTo(Word w1) {
      return (this.word).compareTo(w1.word);
   }
   // Overriding the compare method to sort the age 
   public int compare(Word w1, Word w2) {
      return w1.frequency - w2.frequency;
   }
}
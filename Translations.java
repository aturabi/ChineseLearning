import java.util.*;
import java.io.*;
public class Translations{

    //Maintain parallel array lists of English and Chinese words
    private ArrayList<String> english, chinese;
    private int numTranslations = 0;

    private Random rand = new Random();

    /* Create object from text file.
     * Assumes text file is a .csv in the following format:
     * 
     * English1,Chinese1
     * English2,Chinese2
     * English3,Chinese3
     */
    public void readFile(String fileName) throws IOException{
        Scanner fileReader = new Scanner(new File(fileName));
        english = new ArrayList<String>();
        chinese = new ArrayList<String>();

        while(fileReader.hasNext()){
            String[] words = fileReader.nextLine().split(",");
            english.add(words[0]);
            chinese.add(words[1]);
            numTranslations++;
        }
        
        fileReader.close();
    }

    //Get num words randomly
    //Returned as [en1, ch1, en2, ch2 ... enN, chN]
    public String[] getRandomWords(int num){
        if(num > numTranslations) return null;
        String[] result = new String[num*2];

        //Shuffle arrays of english and chinese words
        shuffleArrays();

        //Choose ArrayList indices without repeating
        for(int i = 0; i < num;){
            result[2*i] = english.get(i);
            result[2*i+1] = chinese.get(i);
            i++;
        }

        return result;
    }

    public int getNumTranslations(){
        return numTranslations;
    }

    //swaps both english and chinese words at indexes 1 and 2
    public void swap(int index1, int index2){
        String temp = english.get(index1);
        english.set(index1, english.get(index2));
        english.set(index2, temp);

        temp = chinese.get(index1);
        chinese.set(index1, chinese.get(index2));
        chinese.set(index2, temp);
    }

    //shuffle array of chinese and english words
    public void shuffleArrays(){
        for(int i = numTranslations - 1; i > 0; i--){
            int randomNum = rand.nextInt(numTranslations - 1);
            swap(i, randomNum);
        }
    }
}

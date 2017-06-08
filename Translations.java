import java.util.*;
import java.io.*;
public class Translations{

    //Maintain parallel array lists of English and Chinese words
    private ArrayList<String> english, chinese;
    private int numTranslations = 0;

    private Random rand = new Random();

    public final int ENGLISH = 0;
    public final int CHINESE = 1;

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
    }

    //Get num words randomly from the chosen array
    public String[] getRandomWords(int num, int array){
        if(num > numTranslations) return null;

        String[] result = new String[num];
        int[] lines = new int[numTranslations]; //Keep track of indices used

        //Choose ArrayList indices without repeating
        for(int i = 0; i < num;){
            int randomNum = rand.nextInt(numTranslations);
            if(lines[randomNum] == 0){
                lines[randomNum] = 1; //Mark as used
                if(array == ENGLISH){
                    result[i] = english.get(randomNum);
                } else if(array == CHINESE) {
                    result[i] = chinese.get(randomNum);
                }
                i++;
            }
        }

        return result;
    }

    public int getNumTranslations(){
        return numTranslations;
    }
}
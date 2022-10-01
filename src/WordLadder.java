import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class WordLadder {

    public HashMap<Integer, String> starting;
    public HashMap<Integer, String> ending;
    public File dictionary;
    public File inputFile;
    public Queue<Stack> mainQueue;

    public int testLine;// for testLine to trouble shoot

    public WordLadder(String dict, String input, int tempInt) {
        dictionary = new File(dict);
        inputFile = new File(input);
        mainQueue = new LinkedList<Stack>();
        starting = new HashMap<Integer, String>();
        ending = new HashMap<Integer, String>();

        String scanFirst;
        String scanSecond;
        int lineNumber = 1;
        try {
            Scanner scanInput = new Scanner(inputFile);
            while (scanInput.hasNext()) {
                scanFirst = scanInput.next();
                scanSecond = scanInput.next();
                starting.put(lineNumber, scanFirst);
                ending.put(lineNumber, scanSecond);
                lineNumber++;

            }
            System.out.println(starting + "\n" + ending);
            scanInput.close();
        } catch (FileNotFoundException e) {
            System.out.println("error");
            e.printStackTrace();
        }

    }
    //method to check if the word in question is a real word
    public boolean checkIfInDict(String word) {
        word = word.toLowerCase();
        try {
            Scanner scanner = new Scanner(dictionary);

            String scan;
            while (scanner.hasNext()) {
                scan = scanner.next().toLowerCase();

                if (word.equals(scan)) {
                    // System.out.println(scan)
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("error in dict file");
            e.printStackTrace();
        }
        return false;

    }
    //method to check if starting and ending word is same - avoid a shit ton of confusion 
    public boolean checkIfSame(int line) {
        return starting.get(line).toLowerCase().equals(ending.get(line).toLowerCase());
    }

    public static void main(String[] args) {
        WordLadder test = new WordLadder("src/dictionary.txt", "src/test.txt", 1);
        

    }
}
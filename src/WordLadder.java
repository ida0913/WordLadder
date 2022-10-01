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
    public File input;
    public Queue<Stack> mainQueue;

    public WordLadder(String dict, String input) {
        File dictionary = new File(dict);
        File inputFile = new File(input);
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

    

    public static void main(String[] args) {
        WordLadder test = new WordLadder("src/dictionary.txt", "src/input.txt");

    }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import javax.xml.transform.Templates;

public class WordLadder {

    public HashMap<Integer, String> starting;
    public HashMap<Integer, String> ending;
    public File dictionary;
    public File inputFile;
    public Queue<Stack> mainQueue;
    boolean solved;
    public Stack<String> ladder;

    public int testLine;// for testLine to trouble shoot

    public WordLadder(String dict, String input, int tempInt) {
        dictionary = new File(dict);
        inputFile = new File(input);
        mainQueue = new LinkedList<Stack>();
        starting = new HashMap<Integer, String>();
        ending = new HashMap<Integer, String>();
        testLine = tempInt;

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

        mainQueue = initOneLetterDifferent();
        solved = false;

    }

    // method to check if the word in question is a real word
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

    // method to check if starting and ending word is same - avoid a shit ton of
    // confusion
    public boolean checkIfSame(int line) {
        return starting.get(line).toLowerCase().equals(ending.get(line).toLowerCase());
    }

    public boolean checkSameLength(int line) {
        return starting.get(line).length() == ending.get(line).length();
    }

    public boolean checkIfValid(int line) {
        if ((!checkIfSame(line))
                && (checkIfInDict(starting.get(line)) && checkIfInDict(ending.get(line)) && checkSameLength(line))) {
            return true;
        }
        return false;
    }

    public boolean isOneLetterDifferent(String baseWord, String checkWord) {
        baseWord = baseWord.toLowerCase();
        checkWord = checkWord.toLowerCase();
        int diffLetters = 0;
        if (baseWord.length() != checkWord.length())
            return false;

        char[] baseChars = baseWord.toCharArray();
        char[] checkChars = checkWord.toCharArray();

        for (int i = 0; i < baseChars.length; i++) {
            if (baseChars[i] != checkChars[i]) {
                diffLetters++;
                if (diffLetters > 1)
                    return false;
            }
        }

        return diffLetters == 1;

    }

    public Queue<Stack> initOneLetterDifferent() {

        if (checkIfValid(testLine)) {
            Queue<Stack> main = new LinkedList<>();

            try {
                Scanner scanner = new Scanner(dictionary);

                String scan;
                while (scanner.hasNext()) {
                    scan = scanner.next().toLowerCase();

                    if (isOneLetterDifferent(starting.get(testLine).toLowerCase(), scan)) {
                        Stack<String> temp = new Stack<>();
                        temp.push(starting.get(testLine).toLowerCase());
                        temp.push(scan);
                        main.offer(temp);
                    }

                }
            } catch (FileNotFoundException e) {
                System.out.println("error in dict file");
                e.printStackTrace();
            }
            return main;
        }

        return null;

    }

    public boolean alreadyUsed(Stack<String> stack, String a){
        Stack<String> temp = (Stack<String>) stack.clone();
        while(!temp.isEmpty()){
            if(temp.pop().equals(a)) return true;
        }
        return false;
        
    }

    public void stackOneLetterDifferent() {
        Stack<String> stack = mainQueue.poll();
        // Queue<Stack> main = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(dictionary);

            String scan;
            while (scanner.hasNext()) {
                scan = scanner.next().toLowerCase();
                // System.out.println(stack);
                if (isOneLetterDifferent(stack.peek(), scan) && !scan.equals(starting.get(testLine)) && !alreadyUsed(stack, scan)) {

                    Stack<String> tempStack = (Stack<String>) stack.clone();

                    tempStack.push(scan);
                    mainQueue.offer(tempStack);
                }

            }
            // return main;
        } catch (FileNotFoundException e) {
            System.out.println("error in dict file");
            e.printStackTrace();
        }
        // return null;

    }

    public boolean checkIfSolved() {
        Queue<Stack> temp = new LinkedList<>();

        while (!mainQueue.isEmpty()) {
            if (mainQueue.peek().peek().equals(ending.get(testLine))) {
                ladder = mainQueue.peek();
                return true;
            } else {
                temp.offer(mainQueue.poll());
            }
        }
        mainQueue = temp;
        return false;

    }

    public static void main(String[] args) {
        WordLadder test = new WordLadder("src/dictionary.txt", "src/test.txt", 1);
        while(!test.checkIfSolved()){
            System.out.println(test.mainQueue);
            test.stackOneLetterDifferent();
        }
        System.out.println(test.ladder);
        // System.out.println(test.mainQueue);

       
    }
}
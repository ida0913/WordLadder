//Adi Bhattacharya CS3 Lab HHS

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.io.FileWriter;

public class WordLadder3 {

    public HashMap<Integer, String> starting;
    public HashMap<Integer, String> ending;
    public HashSet<String> dictionary;
    public File dictionaryFile;
    public File inputFile;
    public HashSet<String> done;
    public boolean solved;
    public File output;
    public String outputStr = "";
    // public Stack<String> ladder;

    public int testLine;// for testLine to trouble shoot

    public WordLadder3(String dict, String input, int testInt) {
        dictionaryFile = new File(dict);
        testLine = testInt;

        // file input into hashmaps
        inputFile = new File(input);
        starting = new HashMap<>();
        ending = new HashMap<>();
        dictionary = new HashSet<>();
        done = new HashSet<>();
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
        // file dictionary into hashset

        try {
            try (Scanner scanner = new Scanner(dictionaryFile)) {
                while (scanner.hasNext()) {
                    dictionary.add(scanner.next().trim().toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace(); 
            // TODO: handle exception
        }
        try {
            output = new File("filename.txt");
            if (output.createNewFile()) {
                System.out.println("File created: " + output.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    // method to check if the word in question is a real word
    public boolean checkIfInDict(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    // method to check if starting and ending word is same - avoid a shit ton of
    // confusion
    public boolean checkIfSame(int line) {
        return starting.get(line).toLowerCase().equals(ending.get(line).toLowerCase());
    }

    // method to check if words are same length
    public boolean checkSameLength(int line) {
        return starting.get(line).length() == ending.get(line).length();
    }

    // method to check if words are valid
    public boolean checkIfValid(int line) {
        if ((!checkIfSame(line))
                && (checkIfInDict(starting.get(line)) && checkIfInDict(ending.get(line)) && checkSameLength(line))) {
            return true;
        }
        return false;
    }

    // from CS powerpoints
    public char changeLetterByOne(char basechar) {
        return (char) (((basechar + 1) - 97) % 26 + 97);

    }

    public void breadth_first_search() {
        // init shit
        Queue<Stack<String>> init = new LinkedList<>();
        Stack<String> firstStack = new Stack<>();
        firstStack.push(starting.get(testLine));
        init.offer(firstStack);
        done.add(starting.get(testLine));
        dictionary.remove(starting.get(testLine));
        solved = false;
        //
        while (!init.isEmpty()) {

            Stack<String> temp = init.poll();
            String word = temp.peek();
            if (word.equals(ending.get(testLine))) {
                String a = "";

                while (!temp.isEmpty()) {
                    a = temp.pop() + arrUtil(a) + a;

                }
                outputStr += "ladder " + a + "\n";
                // System.out.println("ladder:" + a);

                solved = true;

                break;
            } else {

                char[] charLi = word.toCharArray();

                for (int i = 0; i < charLi.length; ++i) {

                    for (int j = 0; j < 26; ++j) {

                        charLi[i] = changeLetterByOne(charLi[i]);
                        String ladder = String.valueOf(charLi);
                        if (dictionary.contains(ladder)) {

                            dictionary.remove(ladder);
                            done.add(ladder);

                            Stack<String> copy = new Stack<String>();
                            copy.addAll(temp);
                            copy.push(ladder);
                            init.offer(copy);

                        }

                    }
                }
            }
        }
        if (solved == false) {
            outputStr += "no ladder  \n";
            // System.out.println("no ladder");

        }

        dictionary.addAll(done);
        done.clear();
    }

    // helper method to help format str to look half decent
    public String arrUtil(String a) {
        if (a.length() > 1) {
            return ",";
        } else
            return "";
    }

    public void run() {

        if (testLine > starting.size()) {
            outputStr += "end \n";
            // System.out.println("end");
        } else if (!checkIfInDict(ending.get(testLine))) {
            outputStr += "no ladder \n";
            // System.out.println("no ladder");
            testLine++;
            run();
        } else if (!checkSameLength(testLine)) {
            outputStr += "no ladder \n";
            // System.out.println("no ladder");
            testLine++;
            run();
        } else if (starting.get(testLine).equals(ending.get(testLine))) {
            outputStr += "same word \n";
            // System.out.println("same word");
            testLine++;
            run();
        } else {

            breadth_first_search();
            testLine++;
            run();
        }
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write(outputStr);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        // System.out.println(outputStr);

    }
    // if (testLine > starting.size()) {
    // try {
    // myWriter = new FileWriter("filename.txt");
    // myWriter.write("end");
    // myWriter.close();
    // System.out.println("Successfully wrote to the file.");
    // } catch (IOException e) {
    // System.out.println("An error occurred.");
    // e.printStackTrace();
    // }
    // }

    // else if (!checkIfInDict(ending.get(testLine))) {
    // try {
    // myWriter = new FileWriter("filename.txt");
    // myWriter.write("no ladder");
    // myWriter.close();
    // System.out.println("Successfully wrote to the file.");
    // } catch (IOException e) {
    // System.out.println("An error occurred.");
    // e.printStackTrace();
    // }
    // testLine++;
    // run();
    // } else if (!checkSameLength(testLine)) {
    // try {
    // myWriter = new FileWriter("filename.txt");
    // myWriter.write("no ladder");
    // myWriter.close();
    // System.out.println("Successfully wrote to the file.");
    // } catch (IOException e) {
    // System.out.println("An error occurred.");
    // e.printStackTrace();
    // }
    // testLine++;
    // run();
    // } else if (starting.get(testLine).equals(ending.get(testLine))) {
    // try {
    // myWriter = new FileWriter("filename.txt");
    // myWriter.write("same word");
    // myWriter.close();
    // System.out.println("Successfully wrote to the file.");
    // } catch (IOException e) {
    // System.out.println("An error occurred.");
    // e.printStackTrace();
    // }
    // testLine++;
    // run();
    // } else {
    // System.out.println(starting.size());
    // breadth_first_search();
    // testLine++;
    // run();

    // }

    public static void main(String[] args) {
        WordLadder3 test = new WordLadder3("src/dictionary.txt", "src/input.txt", 1);
        test.run();
        
        // System.out.println(test.changeLetterByOne('z'));

    }
}
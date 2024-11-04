import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {

    public int processText(StringBuffer s, String stopword) throws InvalidStopwordException, TooSmallText{
        String text = s.toString();
        int numWords = 0;
        boolean foundStopword = false;

      
        Pattern regex = Pattern.compile("[a-zA-Z0-9']");
        Matcher regexMatcher = regex.matcher(text);
        while (regexMatcher.find()) {
            String word = regexMatcher.group();
            System.out.println("I just found the word: " + word);
            numWords++;
            if (word == stopword){
                foundStopword = true;
                break;
            }
           
       } 

        if(foundStopword == false) {
            throw new InvalidStopwordException(stopword + "not found");
        }
        if (numWords < 5) {
            throw new TooSmallText("TooSmallText: " + "Only found " + numWords +  ".");

        }

        return numWords;
    }

    public StringBuffer processFile(String path){
        StringBuffer s = new StringBuffer();

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
         while(true){
             String line = reader.readLine();
             boolean empty = true;
             while(line != null){
                s.append(line);
                empty = false;
                break;
             }
             if (empty){
                throw new EmptyFileException("error");
             }
            
            break;
        }

        }
        catch (EmptyFileException e) {
            System.out.println("error");
        }

        return s;

    }

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        StringBuffer details = new StringBuffer();
        String stopword = null;

        
        if (args.length < 1) {
            System.out.println("Please provide an integer (1 for file, 2 for text)");
            return;
        }

        int option = 0;
    
        try {
            option = Integer.parseInt(args[0]);
            if (option != 1 && option != 2) {
                throw new InvalidStopwordException("Invalid.  Enter 1 to process a file or 2 to process text.");
            }
        } catch (InvalidStopwordException e) {
            System.out.println("Invalid. Enter 1 to process a file or 2 to process text.");
            return;
        }


        if (args.length > 1) {
            stopword = args[1];
        }

        if (option == 1) {
            System.out.println("Enter the file path:");
            String path = scanner.nextLine();

      
            try {
                details = processFile(path);
            } catch (EmptyFileException e) {
                System.out.println(e.getMessage());
                details = new StringBuffer();  
            }
        } else {
            System.out.println("Enter the text to be processed:");
            details.append(scanner.nextLine());
        }

      
        try {
            int wordCount = processText(details, stopword);
            System.out.println("Word count: " + wordCount);
        } catch (InvalidStopwordException e) {
            System.out.println(e.getMessage());

            System.out.println("Please enter a new stopword:");
            stopword = scanner.nextLine();

            try {
                int wordCount = processText(details, stopword);
                System.out.println("Word count: " + wordCount);
            } catch (InvalidStopwordException ep) {
                System.out.println("\"This file has enough words to not trigger and exception and the stopword we're going to use \" +\n" + //
                                        "        \"is yellow so we shouldn't have scanned into this point -- it just isn't necessary...unless \" +\n" + //
                                        "        \"the stopword we wanted was green in which case we stopped above. Or, perhaps no stopword was provided, \" + \n" + //
                                        "        \"so then we will read in the whole file. \" " );
            } catch (TooSmallText ep) {
                System.out.println(ep.getMessage());
            }
        } catch (TooSmallText e) {
            System.out.println( e.getMessage());
        }
    
        


    }


    
}
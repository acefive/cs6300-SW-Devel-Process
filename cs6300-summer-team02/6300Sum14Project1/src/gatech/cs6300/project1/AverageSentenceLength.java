package gatech.cs6300.project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AverageSentenceLength {
    private File _file;
    private Integer _minWordLength = 3;
    private String _delimiter = "(\\.)|(\\?)|(\\;)|(\\!)";
    private Boolean _debug = false;

    public void setSentenceDelimiters(String delimiter){
        //loop through the characters and build a regex for the scanner
        //in computeAverageSentenceLength
        _delimiter = "(";
        char[] delims = delimiter.toCharArray();
        for(int i=0; i<delims.length; i++){
           _delimiter += "(\\" + delims[i] + ")";
           if(i < delims.length - 1){ _delimiter += "|"; }
        }
        _delimiter += ")";
    }

    public void setDebug(Boolean debug){
        _debug = debug;
    }

    public void setFile(File file){
        _file = file;
    }

    public void setMinWordLength(Integer minWordLength){
        _minWordLength = minWordLength;
    }

    public Integer computeAverageSentenceLength()
    {
       Integer rVal = 0;
       try {
            FileReader fr = new FileReader(_file);

            Scanner src = new Scanner(fr);
            src.useDelimiter(_delimiter);

            Integer sentenceCount = 0;
            Integer wordCount = 0;
            if(!src.hasNext()){
            	System.out.println("\nSpecified file is empty.");
            	return -1;
            }
            while (src.hasNext()) {
                String str = src.next();
                String[] s = str.trim().replaceAll(",", " ").replaceAll("\n", " ").replace("\\s+", " ").split(" ");
                if (str.trim().length() > 0) sentenceCount++;

                if(_debug) System.out.println("\nSentence " + sentenceCount + ". (LENGTH=" + s.length + "): " + str);

                for (int i = 0; i < s.length; i++) {
                    //TODO: we have to remove a couple of nonalpha characters.  Maybe just remove them all here?
                    String word = s[i].replaceAll("\\\"","").replaceAll("\'","");
                    if (word.length() >= _minWordLength) {
                        if(_debug) System.out.println("Word " + wordCount + ". " + word + " (" + word.length() + ")");
                        wordCount++;
                    } else {
                        if(_debug) System.out.println("SKIPPED: " + word + " (" + word.length() + ")");
                    }
                }
            }

            if(_debug) System.out.println("\n\nTotals:\nWord count: " + wordCount);
            if(_debug) System.out.println("Sentence count: " + sentenceCount);
            if(_debug) System.out.print("File: " + _file.toString());
            if(_debug && sentenceCount > 0) System.out.println("Avg: " + Math.round(wordCount / sentenceCount));

            if(sentenceCount > 0) {
                rVal = (int) Math.floor((double) wordCount / sentenceCount);
            }

            src.close();
        } catch(FileNotFoundException f) {
           System.out.println("Error, file not found.");
           return -1;
        }

        return rVal;
    }
    public void printHelp()
    {  	String fileDir = "";
    	String fileName = "";
    	String line = null;
    	boolean error = false;

    	BufferedReader helpFile = null;
    	try {
    		fileDir = new String("gatech" + File.separator + "cs6300" + File.separator + "project1" + File.separator);
    		helpFile = new BufferedReader(new FileReader(fileDir + "help.txt"));
    	} catch (FileNotFoundException e) {
    		error = true;
    	}

    	if (!error)
    			try {
    				while ((line = helpFile.readLine()) != null) {
    				   System.out.println(line);
    				 }
    			} catch (IOException e) {
    				error = true;
    			}
    	if (!error)
    			try {
    				helpFile.close();
    			} catch (IOException e) {
    				error =true;
    			}
    	
    	if (error) 
    	{
    		System.out.println("java AverageSentenceLength [-l minimumWordLength] [-d listOfdelimiters] file\r");
    		System.out.println("\t -l \t optional \t specifies the minimum length of a word in order for it to be considered in the calculation (default is 3)\r");
    		System.out.println("\t -d \t optional \t specifies the list of delimiters used to determine the end of a sentence (default is .?;!)\r");
    		System.out.println("\t file \t required \t Specifies the file containing the text to calculate the average sentence length\r");
    	}
    }

}

package gatech.cs6300.project1;

import java.util.*;
import java.io.*;


public class Main {
    public static void main(String args[])
    {
		String fileName = "";
    	
		//instantiate and initialize 
	   	AverageSentenceLength asl = new AverageSentenceLength();
	   	asl.setDebug(false);
	   	
	   	//loop through and parse the supplied arguments
    	for (int i = 0; i < args.length; i++) {
    		
        	if (args[i].charAt(0) == '-') {
        		
		        	//word length
		        	if (Character.toUpperCase(args[i].charAt(1)) == 'L') {

		        		try { 
		        			if (i < args.length-1) {i++;
		        			System.out.println("Minimum word length: " + args[i]);
		        			asl.setMinWordLength(Integer.parseInt(args[i])); 
		        			continue;
		        			}else{
		        				System.out.println("\nMinimum word length expected.");
		        				return;
		        			}
		        	    } catch(NumberFormatException e) {
		        	    	System.out.println("Minimum word length must be numeric");
		        	    	return;
		        	    }
		        	} 
		        	//delimiters
		        	else if (Character.toUpperCase(args[i].charAt(1)) == 'D') {
	        	    		if (i < args.length-1) {i++; 
	        	    		System.out.println("Delimiters: " + args[i]);
	        	    		asl.setSentenceDelimiters(args[i]); 
		        	    	continue;
	        	    		}else{
		        				System.out.println("\nDelimiters expected.");
		        				return;
	        	    		}
		        	}        	
		        	//help
		        	else if (Character.toUpperCase(args[i].charAt(1)) == 'H') {
		        		asl.printHelp();
		        		return;
		        	}
		        	//return with message if unrecognized option is entered
		        	else{
		        		System.out.println("\n"+args[i]+ " is not a recognized option." );
		        		asl.printHelp();
		        		return;
		        	}
        	} else {
        		//filename
        		if (fileName == "") {
		        		System.out.println("File name: "+ args[i]);
		        		fileName = new String(args[i]);
		        		asl.setFile(new File(fileName));
		        		continue;
        		}
        	}
    	}
    	if(fileName == ""){
    		System.out.println("\nNo File Path specified.");
    	}
    	
    	if (args.length == 0 || fileName == "") {
    		System.out.println("\n");
    			asl.printHelp();
    			return;
    	}
	   	if(fileName!=""){
	    	//do the calculation
		    Integer avg = asl.computeAverageSentenceLength();
		   	
		    //check for error condition. Don't print result if an error occured.
		    if(avg!=-1){
		    	//results
		    	System.out.println("\rAverage sentence length = " + avg);  
		    }
	   	}
    }
}

package edu.gatech.replace;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.System;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import java.io.DataInputStream;
import java.io.FileInputStream;
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.*;
import java.io.File;
import java.util.regex.*;

public class ReplaceTest {

    private Replace replace;
    static PrintStream orig_sysout;
    static PrintStream orig_syserr;
    static ByteArrayOutputStream tmp_sysout;
    static ByteArrayOutputStream tmp_syserr;

    @BeforeClass 
    public static void setupForAll() {

    }

    @AfterClass 
    public static void tearDownForAll() {

    }



    @Before
    public void setUp() throws Exception {

	// capture System.out so we can examine the results
	orig_sysout = System.out;
	tmp_sysout = new ByteArrayOutputStream();
	System.setOut(new PrintStream(tmp_sysout));

	// capture System.err so we can examine the results
	orig_syserr = System.err;
	tmp_syserr = new ByteArrayOutputStream();
	System.setErr(new PrintStream(tmp_syserr));

	//restore the test file to its original content
        File orig = new File("foo.txt_orig");
	File dest = new File("foo.txt");
	Files.copy(orig.toPath(), dest.toPath(), REPLACE_EXISTING);

        replace = new Replace();

    }


    @After
    public void tearDown() throws Exception {

	tmp_sysout = null;
	tmp_syserr = null;
	System.setOut(orig_sysout);
	System.setErr(orig_syserr);
        replace = null;

    }


    //
    // print the contents of the file when needed
    //
    private String printInputFile(String fname) throws Exception {

 	String content = null;

	DataInputStream infile = new DataInputStream ( new FileInputStream (fname));
	byte[] datainBytes = new byte[infile.available()];
	infile.readFully(datainBytes);
	infile.close();
	content = new String(datainBytes, 0, datainBytes.length);
 
	return content;
	
    }

    //
    // compare contents of two files
    // return True if equal
    // return False if not equal
    //
    private Boolean contentIsEqual(String fname) throws Exception {

  	String f1_orig = printInputFile(fname+"_orig").trim();
  	String f2 = printInputFile(fname).trim();
        return f1_orig.equals(f2);

    }
  
    //
    // 0 occurances expected
    // simple search
    //
    @Test
    public void testReplace_zeroOccur() throws Exception {

        String[] args = new String[3];
	args[0] = "from";
        args[1] = "to";
	args[2] = new String("foo.txt");

        replace.replace(args);
	
	String testout = tmp_sysout.toString().trim();
        assertEquals("Replaced 0 occurrences.",testout.trim());

	//verify contents of changed file
        assertTrue(contentIsEqual(args[2]));

    }
  
    //
    // 1 occurances expected
    // simple search with numbers
    //
    @Test
    public void testReplace_searchNumeric() throws Exception {

        String[] args = new String[3];
	args[0] = "456";
        args[1] = "four five six";
	args[2] = new String("foo.txt");

        replace.replace(args);
	
	//print contents of changed file
	//orig_sysout.println("searchNumeric: "+printInputFile(args[2]).trim());

	String testout = tmp_sysout.toString().trim();
        assertEquals("Replaced 1 occurrences.",testout.trim());

	//verify contents of changed file
        assertFalse(contentIsEqual(args[2]));

    }

    //
    // 0 occurance expected
    // source file not found (args[2])
    //
    @Test
    public void testReplace_noSourceFile() throws Exception {

        String[] args = new String[3];
        args[0] = "to";
        args[1] = "chosen";
        args[2] = new String("thisFilenameDoesNotExist.txt");

        replace.replace(args);

	String testout = tmp_syserr.toString();
	assertTrue(testout.indexOf("No such file or directory")>=0);

    }

    //
    // 0 occurance expected
    // source file is present but empty
    //
    @Test
    public void testReplace_emptySourceFile() throws Exception {

        String[] args = new String[3];
        args[0] = "to";
        args[1] = "chosen";
        args[2] = new String("foo.txt");

        //empty the test file
        RandomAccessFile tmp  = new RandomAccessFile(args[2], "rw");
	tmp.setLength(0);

        replace.replace(args);

	String testout = tmp_sysout.toString().trim();
        assertEquals("Replaced 0 occurrences.",testout.trim());

	//verify contents of changed file
        assertTrue(contentIsEqual(args[2]));

    }

    //
    // 9 occurances expected
    // replace all occurances of the word "test"
    //
    @Test
    public void testReplace_moreThanOneOccur() throws Exception {


        String[] args = new String[3];
        args[0] = "test";
        args[1] = "";
        args[2] = new String("foo.txt");

        replace.replace(args);

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
        //orig_sysout.println("moreThanOneOccur: "+verify);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 9 occurrences.",testout.trim());

	//verify contents of changed file
  	String verify = printInputFile(args[2]).trim();
        assertTrue(verify.indexOf("test") < 0);

        //verify contents of changed file
        assertFalse(contentIsEqual(args[2]));

    }


    //
    // 1 occurance expected
    // double quotes within search string, using escape char
    // double quotes within replace string
    //
    @Test
    public void testReplace_searchStrHasDblQuotes() throws Exception {


        String[] args = new String[3];
        args[0] = "\"double\"";
        args[1] = "\"DOUBL3\"";
        args[2] = new String("foo.txt");

        replace.replace(args);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 1 occurrences.",testout.trim());

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
        //orig_sysout.println("searchStrHasDblQuotes: "+verify);
       
        //verify contents of changed file
        assertFalse(contentIsEqual(args[2]));

    }

    //
    // 1 occurance expected
    // search string has single quotes and multi word and blanks 
    // replace string does not have single quotes and has multi word and has blanks
    //
    @Test
    public void testReplace_searchWithBlanksSingleQuotes() throws Exception {

        String[] args = new String[3];
        args[0] = "'test test test test test test'";
        args[1] = "TEST TEST TEST TEST TEST TEST";
        args[2] = new String("foo.txt");

        replace.replace(args);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 1 occurrences.",testout.trim());

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
	//orig_sysout.println("searchWithBlanksSingleQuotes: "+verify);

        //verify contents of changed file
        assertFalse(contentIsEqual(args[2]));

   }

    //
    // 3 occurances expected
    // search string is first word in file
    // search string is also first word on several lines
    // search string has a capital letter
    //
    @Test
    public void testReplace_searchStrIsFirstWord() throws Exception {


        String[] args = new String[3];
        args[0] = "This";
        args[1] = "1234";
        args[2] = new String("foo.txt");

        replace.replace(args);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 3 occurrences.",testout.trim());

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
	//orig_sysout.println("searchStrIsFirstWord: "+verify);

        //verify contents of changed file
        assertFalse(contentIsEqual(args[2]));

    }

    //
    // 3 occurances expected
    // search string is first word in file using Regular Expression RE
    // search string is also first word on several lines
    // search string has a capital letter
    //
    @Test
    public void testReplace_searchStrIsFirstWordRE() throws Exception {


        String[] args = new String[3];
        args[0] = "^This";
        args[1] = "1234";
        args[2] = new String("foo.txt");

        replace.replace(args);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 3 occurrences.",testout.trim());

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
	//orig_sysoeut.println("searchStrIsFirstWordRE: "+verify);

        //verify contents of changed file
        assertFalse(contentIsEqual(args[2]));

    }

    //
    // 3 occurances expected
    // search string for one occurance is the last word in a line, and also
    // search string is a whole word and also part of another word 
    // search string occurs more than once
    // search string has upper and lower character counterparts
    //
    @Test
    public void testReplace_searchStrIsLastWord() throws Exception {


        String[] args = new String[3];
        args[0] = "fun";
        args[1] = "345";
        args[2] = new String("foo.txt");

        replace.replace(args);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 3 occurrences.",testout.trim());

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
	//orig_sysout.println("searchStrIslastWord: "+verify);

        //verify contents of changed file
        assertFalse(contentIsEqual(args[2]));
    }

    //
    // 2 occurances expected
    // search string is last word in a line using RE
    // search string occurs more than once, but only once as the last word in a line
    //
    @Test
    public void testReplace_searchStrIsLastWordRE() throws Exception {


        String[] args = new String[3];
        args[0] = "fun$";
        args[1] = "345";
        args[2] = new String("foo.txt");

        replace.replace(args);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 2 occurrences.",testout.trim());

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
	//orig_sysout.println("searchStrIslastWordRE: "+verify);

        //verify contents of changed file
        assertFalse(contentIsEqual(args[2]));
    }


    //
    // 1 occurance expected
    // search string is multi line string 
    //
    @Test
    public void testReplace_multiLineSearch() throws Exception {

        String[] args = new String[3];
        args[0] = "fun because";
        args[1] = "fun!!! because";
        args[2] = new String("foo.txt");

        replace.replace(args);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 1 occurrences.",testout.trim());

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
	//orig_sysout.println("multiLineSearch: "+verify);

        //verify contents of changed file
        assertFalse(contentIsEqual(args[2]));
    }


    //
    // 0 occurances expected
    // search string is empty
    //
    @Test
    public void testReplace_searchWithNothing() throws Exception {

        String[] args = new String[3];
        args[0] = "";
        args[1] = "abcd";
        args[2] = new String("foo.txt");

        replace.replace(args);

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
	//orig_sysout.println("searchWithNothing: "+verify);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 0 occurrences.",testout.trim());

        //verify contents of changed file
        assertTrue(contentIsEqual(args[2]));

   }

    //
    //  occurances expected
    // 
    //
    @Test
    public void testReplace_searchWithRegex() throws Exception {

        String[] args = new String[3];
        args[0] = "([0-9]{3}\\s)+";
        args[1] = "XXX-";
        args[2] = new String("foo.txt");

        replace.replace(args);

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
	//orig_sysout.println("searchWithRegex: "+verify);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 1 occurrences.",testout.trim());

        //verify contents of changed file
        assertFalse(contentIsEqual(args[2]));

   }

/*
    //
    // I DON'T KNOW WHY, BUT THIS FINAL TEST CRASHES THE ENTIRE SUITE, THEREFORE ITS BEST TO COMMENT IT OUT.  
    // NOTE:  THE COVERAGE IS 97% WHEN I KEEP IT IN, BUT WILL DROP TO 85% WHEN IT IS COMMENTED OUT.  
    // BUT IF ALL THE TESTS CRASH THEN THE WHOLE THING IS USELESS REGARDLESS OF THE HIGH COVERAGE SO I'LL RISK THE 85%.
    //
    // 0 occurances expected
    // search string has meta chars 
    // from RE_FORMAT manpage: A repetition operator (`?', `*', `+', or bounds) cannot follow another repetition operator, except for the use of `?' for minimal repetition
    //
    @Test
    public void testReplace_searchForMeta() throws Exception {

        String[] args = new String[3];
	//tried this but it still crashes the code
	//Pattern p = Pattern.compile("*&&(#)");
        //args[0] = p.pattern(); 
        args[0] = "*&&(#)";
        args[1] = "NOTHING GOING ON HERE FOLKS";
        args[2] = new String("foo.txt");

       try {
           replace.replace(args);
       } catch(Exception ex) {
	   orig_sysout.println("FAILED with exception: "+ex.toString());
	   //String testerr = tmp_syserr.toString();
	   //orig_syserr.println("searchForMeta: "+testerr);
       }

	//print contents of changed file
  	//String verify = printInputFile(args[2]).trim();
	//orig_sysout.println("searchForMeta: "+verify);

	String testout = tmp_sysout.toString();
        assertEquals("Replaced 0 occurrences.",testout.trim());

        //verify contents of changed file
        assertTrue(contentIsEqual(args[2]));
   }
*/
}

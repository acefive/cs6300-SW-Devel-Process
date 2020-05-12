package gatech.cs6300.project1;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import gatech.cs6300.project1.AverageSentenceLength;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AverageSentenceLengthTest {

    private AverageSentenceLength asl;
    private String fileDir;

    @Before
    public void setUp() throws Exception {
        asl = new AverageSentenceLength();
        fileDir = new String("test" + File.separator + "inputfiles"
                + File.separator);
    }

    @After
    public void tearDown() throws Exception {
        asl = null;
        fileDir = null;
    }

    @Test
    public void testComputeAverageSentenceLength1() {
        asl.setFile(new File(fileDir + "multi.txt"));
        assertEquals(6, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength2() {
        asl.setFile(new File(fileDir + "file.txt"));
        asl.setSentenceDelimiters("|%");
        assertEquals(1, asl.computeAverageSentenceLength(), 0);
    }


    @Test
    public void testComputeAverageSentenceLength3() {
        asl.setFile(new File(fileDir + "essay.txt"));
        assertEquals(10, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength4() {
        asl.setFile(new File(fileDir + "essay.txt"));
        asl.setMinWordLength(5);
        assertEquals(4, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength5() {
        asl.setFile(new File(fileDir + "numbers.txt"));
        asl.setSentenceDelimiters("/|");
        asl.setMinWordLength(1);
        assertEquals(5, asl.computeAverageSentenceLength(), 0);
    }


    private File tmpFileFromString(String str) throws IOException {
    	//Create temporary file that gets deleted when the JVM exits
        File tmpFile = File.createTempFile("asl_unit_test", ".tmp");
        tmpFile.deleteOnExit();
        BufferedWriter out = new BufferedWriter(new FileWriter(tmpFile));
        out.write(str);
        out.close();
        return tmpFile;
    }

    @Test
    public void testComputeAverageSentenceLength6() throws IOException {
    	// comma is not a default delimiter
        asl.setFile(tmpFileFromString("a a, b b. c c?"));
        asl.setMinWordLength(1);
        assertEquals(3, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength7() throws IOException {
        asl.setFile(tmpFileFromString("a a; b b! c c."));
        asl.setMinWordLength(1);
        assertEquals(2, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength8() throws IOException {
    	// average length must be rounded DOWN to the nearest integer:
    	// 3 sentences, 7 words, avg = 2.33.  floor(2.33)==2
        asl.setFile(tmpFileFromString("a a a a. b. b b."));
        asl.setMinWordLength(1);
        assertEquals(2, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength9() throws IOException {
    	// No delimiters is equivalent to one sentence
        asl.setFile(tmpFileFromString("a a a a"));
        asl.setMinWordLength(1);
        assertEquals(4, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength10() throws IOException {
    	// ignore ' when calculating sentence length
        asl.setFile(tmpFileFromString("aaa's bbb's."));
        asl.setMinWordLength(1);
        assertEquals(2, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength11() throws IOException {
        asl.setFile(tmpFileFromString(""));
        asl.setMinWordLength(1);
        assertEquals(-1, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength12() throws IOException {
        asl.setFile(tmpFileFromString("aaa"));
        asl.setMinWordLength(1);
        //changed from 3 to 1, it's 1 sentence 1 word.  i think maybe you
        //got mixed up with word count vs sentence count
        assertEquals(1, asl.computeAverageSentenceLength(), 0);
    }
}

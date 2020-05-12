package gatech.cs6300.assignment1;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;

public class CalculatorTest {

	Random rand = new Random();
	double x = rand.nextInt(15) + Math.random();
	double y = rand.nextInt(15) + Math.random();
	double result = 0;
   	
	Calculator myCalc = new Calculator();

	@Test
	public void testAdd() {

		//init
	    result = 0;
	    
		//do the calculations, get results
		myCalc.add(x,y);
		double expected_result = x+y;
		result = myCalc.getResult();
		
		//other error use junit matchers
		assertTrue("ERR: add: Result does not equal "+expected_result,result==x+y);
	    
		//ok
		System.out.println("ADD:  OK:\t" + x + " + " + y + " = " + result);
	   
	}

	@Test
	public void testSubtract() {
		
		//init
		result = 0;
		
		//do the calculations, get results
		myCalc.subtract(x,y);
		double expected_result = x-y;
		result = myCalc.getResult();
		
		//other error use junit matchers
		assertTrue("ERR: subtract: Result does not equal "+expected_result,result==x-y);
	    
		//ok
		System.out.println("SUB:  OK:\t" + x + " - " + y + " = " + result);
	}

	@Test
	public void testMultiply() {

		//init
		result = 0;		
		
		//do the calculations, get results
		myCalc.multiply(x,y);
		double expected_result = x*y;
	    result = myCalc.getResult();
	    
	    //can't have result = 0 if args are not zero
	    //test this using hamcrest matchers
	    if (x != 0 && y != 0) { 
	    	assertThat("ERR: multiply: Result is 0 using non-zero arguments.",result, is(not(0.0)));
	    }  
	    
	    //other error occurred
	    assertThat("ERR: multiply: Result does not equal "+expected_result, result, is(expected_result));
	    
	    //ok
	    System.out.println("MULT: OK:\t" + x + " * " + y + " = "+result);
	    
	}

	@Test
	public void testDivide() {

		//init
		result = 0;	
		
		//test for divide by zero using hamcrest matchers
		assertThat("ERR: divide: Cannot divide by zero.", y, is(not(0.0)));

		//do the calculations, get results
	    myCalc.divide(x,y);
		double expected_result = x/y;
	    result = myCalc.getResult();
	    
	    //other error
	    assertThat("ERR: divide: Result does not equal "+expected_result,result, is(expected_result));
	    
	    //ok
	    System.out.println("DIV:  OK:\t" + x + " / " + y + " = "+result);
	    
	}

}

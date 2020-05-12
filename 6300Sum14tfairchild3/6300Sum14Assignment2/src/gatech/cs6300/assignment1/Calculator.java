package gatech.cs6300.assignment1;


public class Calculator implements CalculatorInterface {
	
	// instance variable to store the calculated result
	private double z;

	public Calculator() {
		
		//init
		this.z = 0;
	}

	//@Override
	public void add(double x, double y) {
		
		try {
			this.z = x+y;
		}
		catch (ArithmeticException e) {
			System.out.println("ADD ERR! Setting result to zero.");
			this.z = 0;
		}
	}

	//@Override
	public void subtract(double x, double y) {
		
		try {
			this.z = x-y;
		}
		catch (ArithmeticException e) {
			System.out.println("SUBTRACT ERR! Setting result to zero.");
			this.z = 0;
		}
	}

	//@Override
	public void multiply(double x, double y) {

		try {
			this.z = x * y;
		}
		catch (ArithmeticException e) {
			System.out.println("MULTIPLY ERR! Setting result to zero.");
			this.z = 0;
		}		
	}

	//@Override
	public void divide(double x, double y) {

			try {
				this.z = x/y;
			}
			catch (ArithmeticException e) {
				System.out.println("DIVISION ERROR! Setting result to zero.");
				this.z = 0;
			}
	}

	//@Override
	public double getResult() {
		
		// return the instance variable
		return this.z;
	}

}

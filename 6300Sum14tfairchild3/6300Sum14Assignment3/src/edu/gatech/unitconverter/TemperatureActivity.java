package edu.gatech.unitconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import edu.gatech.unitconverter.R;

public class TemperatureActivity extends Activity {

    final double CELSIUS_RISE_RATE = 9;
    final double FAHRENHEIT_RISE_RATE = 5;
    final int TEMP_SCALE_DIFFERENCE = 32;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		//create the layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temperature);
		
		//identify and setup for the return button
		Button myButton = (Button) findViewById(R.id.return_to_main);
		myButton.setOnClickListener(goToMainListener);
		
		//identify and setup for the reset button
		Button myReset = (Button) findViewById(R.id.buttonreset);
		myReset.setOnClickListener(goToResetListener);
	}

	//what to do if return button is pressed
	OnClickListener goToMainListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			doBackToMain();
		}
	};
	
	//send the user back
	private void doBackToMain() {
		startActivity(new Intent(this, MainActivity.class));
	}
	
	//what to do if the reset button is pressed
	OnClickListener goToResetListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			doResetUnit();
		}
	};
	
	//reset the input unit back to 1
	private void doResetUnit() {
		EditText txt = (EditText) findViewById(R.id.text1);
		txt.setText("1");
	}
	
	//identify which radio button has been clicked
	public void handleClick(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		EditText txt = (EditText) findViewById(R.id.text1);
		
		//error if empty
		if(txt.getText().length()==0) {
        	txt.setError("You must specify a value");
    	} else {
    		//otherwise get the input
    		double temp;
    		
    		try {
    			temp = Double.parseDouble(txt.getText().toString());		
    		}
    	    catch (NumberFormatException err) {
    	           txt.setError("Invalid number format");
    	           return;
    	    }
			switch(view.getId()) {
			    //do the calculation and set the results depending on the radio button selected
				case R.id.radioButton1:
					if(checked)
						txt.setText(fahrenheitToCelsius(temp));
					break;
				case R.id.radioButton2:
					if (checked)
						txt.setText(celsiusToFahrenheit(temp));
					break;
			}
    	}
	}
	
	//calc
	public String celsiusToFahrenheit(double temp) {
		double fahrenheit =  ( temp * CELSIUS_RISE_RATE / FAHRENHEIT_RISE_RATE ) + TEMP_SCALE_DIFFERENCE;
		return String.valueOf(fahrenheit);
	}

	//calc
	public String fahrenheitToCelsius(double temp) {
		double celsius = ( temp - TEMP_SCALE_DIFFERENCE ) * FAHRENHEIT_RISE_RATE / CELSIUS_RISE_RATE;		
		return String.valueOf(celsius);
	}
	
}


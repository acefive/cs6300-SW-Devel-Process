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

public class DistanceActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//setup the layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distance);
		
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
		
		//error if empty, or get the input value
		if(txt.getText().length()==0) {
        	txt.setError("You must specify a value");
    	} else {
    		
    		double distance;
    		try {
    			distance = Double.parseDouble(txt.getText().toString());	
    		} catch (NumberFormatException err) {
  	           txt.setError("Invalid number format");
  	           return;
     		}
    		
			switch(view.getId()) {
		    	//do the calculation and set the results depending on the radio button selected
				case R.id.radioButton1:
					if(checked)
						txt.setText(kmToMiles(distance));
					break;
				case R.id.radioButton2:
					if (checked)
						txt.setText(milesToKm(distance));
					break;
			}
    	}
	}
	
	//calc
	public String milesToKm(double miles) {
		double km = miles * 1.609;
		return String.valueOf(km);
	}

	//calc
	public String kmToMiles(double km) {
		double miles = km / 1.609;
		return String.valueOf(miles);
	}
	
}

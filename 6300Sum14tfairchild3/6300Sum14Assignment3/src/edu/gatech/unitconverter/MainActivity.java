package edu.gatech.unitconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
    	//main activity layout
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        
        //setup for weight converter
        Button wtButton = (Button) findViewById(R.id.gotoWeightActivity);
        wtButton.setOnClickListener(goToWeightListener);

        //setup for temperature converter
        Button tempButton = (Button) findViewById(R.id.gotoTemperatureActivity);
        tempButton.setOnClickListener(goToTempListener);
        
        //setup for Distance Activity
        Button distanceButton = (Button) findViewById(R.id.gotoDistanceActivity);
        distanceButton.setOnClickListener(goToDistanceListener);
        
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // A placeholder fragment containing a simple view.
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
   
    //add a listener for the weight activity
    private OnClickListener goToWeightListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			doWeight();
		}
		
	};
	
	//start the weight activity
	private void doWeight() {
		startActivity(new Intent(this, WeightActivity.class));
	}
	
	
	
	//add a listener for the temperature activity
	private OnClickListener goToTempListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			doTemp();
		}
	};
	
	//start the temperature activity
	private void doTemp() {
		startActivity(new Intent(this, TemperatureActivity.class));
	}
	
	//add a listener for the Distance activity
	private OnClickListener goToDistanceListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			doDistance();
		}
	};
	
	//start the distance activity
	private void doDistance() {
		startActivity(new Intent(this, DistanceActivity.class));
	}
	
}

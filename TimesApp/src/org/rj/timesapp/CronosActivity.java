package org.rj.timesapp;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CronosActivity extends Activity {

	private TextView date, time;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				// Oculta el título del layout
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				setContentView(R.layout.activity_cronos);
				
				// Fecha actual del sistema
			    Calendar rightNow = Calendar.getInstance();
			    date = (TextView) findViewById(R.id.date);
			    date.setText(rightNow.get(Calendar.YEAR) + "-" + rightNow.get(Calendar.MONTH)+ "-" + rightNow.get(Calendar.DAY_OF_MONTH));


	}

}

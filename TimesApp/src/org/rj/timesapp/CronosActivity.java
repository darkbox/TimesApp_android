package org.rj.timesapp;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class CronosActivity extends Activity {

	private boolean flag = false;
	private TextView date;
	private Button btn_playStop;
	private Chronometer chrono;
	private long lastTime = 0;

	
	
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
			    
			    // Chronometer
			    chrono = (Chronometer)findViewById(R.id.chronometer);
			    chrono.setBase(SystemClock.elapsedRealtime());
			    chrono.setText("00:00:00");
			    chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			        @Override
			        public void onChronometerTick(Chronometer chronometer) {
			            CharSequence text = chronometer.getText();
			            if (text.length()  == 5) {
			                chronometer.setText("00:"+text);
			            } else if (text.length() == 7) {
			                chronometer.setText("0"+text);
			            }
			        }
			    });

			    // Button play/Stop
			    btn_playStop = (Button) findViewById(R.id.playStop);

	}

	
	/**
	 * actions
	 * @param view
	 */
	public void actions (View view){
		// Recoge la acción
		int action = Integer.parseInt(view.getTag().toString());
		
		switch (action) {
		case 1: // ListActivity
			Intent listActivity = new Intent(this, ListActivity.class);
			startActivity(listActivity);
			break;
		case 2: // Play/Stop
			if(!flag){
				chrono.setBase(SystemClock.elapsedRealtime() + lastTime);
				chrono.start();
				flag = true;
				btn_playStop.setBackgroundResource(R.drawable.btn_stop);
			}else{
				lastTime = chrono.getBase() - SystemClock.elapsedRealtime();
				chrono.stop();
				flag = false;
				btn_playStop.setBackgroundResource(R.drawable.btn_play);
			}
			
			break;
		case 3: // Reset
			chrono.setBase(SystemClock.elapsedRealtime());
			lastTime = 0;
			chrono.setText("00:00:00");
			break;
		case 4: // Add
			
			break;
		default:
			break;
		}
	}
	
	
}

package org.rj.timesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Oculta el título del layout
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.activity_list);
			
			
	}

	/**
	 * actions
	 * @param view
	 */
	public void actions (View view){
		// Recoge la acción
		int action = Integer.parseInt(view.getTag().toString());
		
		switch (action) {
		case 0: // TimerActivity
			finish();
			break;

		default:
			break;
		}
	}
	
}

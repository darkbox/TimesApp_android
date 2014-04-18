package org.rj.timesapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class SettingsActivity extends Activity {
	
	// Campos
	private EditText remoteServerInput;
	
    // Preferencias
    private static final String SETTINGS = "TimesAppSettings";
    private String remoteServer = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Animación de transición
				overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
				
				// Oculta el título del layout
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setTheme(R.style.AppTheme);
				setContentView(R.layout.activity_settings);
				
				// Campos de texto
				remoteServerInput = (EditText) findViewById(R.id.remoteServerEditText);
				
				// Preferencias de la aplicación
				SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
				remoteServer = settings.getString("remoteServer", "");
				
				remoteServerInput.setText(remoteServer);
	}
	
	/**
	 * save app's settings
	 * @param view
	 */
	public void save(View view){
		if(remoteServerInput.getText().toString().length() > 3){
			Log.i("Settings","Saving settings");
			 SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
		      SharedPreferences.Editor editor = settings.edit();
		      editor.putString("remoteServer", remoteServerInput.getText().toString());
		
		      // Commit the edits!
		      editor.commit();
		      
		      Intent i = new Intent();
		      setResult(RESULT_OK, i);        
		      finish();
		}else{
			
		}
	}

}

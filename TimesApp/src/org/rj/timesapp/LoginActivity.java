package org.rj.timesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	//Views
	private EditText usernameInput, passwordInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Oculta el título del layout
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		
		// Campos de texto
		usernameInput = (EditText) findViewById(R.id.usernameEditText);
		passwordInput = (EditText) findViewById(R.id.passwordEditText);
		
	}

	/**
	 * action
	 * @param view
	 */
	public void action(View view){
		// Recupera los valores	
		String[] credentials = new String[2];
		credentials[0] = usernameInput.getText().toString();
		credentials[1] = passwordInput.getText().toString();
		credentials[0] = credentials[0].trim();
		credentials[1] = credentials[1].trim();
		
		// Valida los campos
		if(credentials[0].length() < 3 || credentials[1].length() < 3){
			
		}else{
			// Comprueba credenciales
			
			// Inicia actividad
			Intent cronos = new Intent(this, CronosActivity.class);
			startActivity(cronos);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}

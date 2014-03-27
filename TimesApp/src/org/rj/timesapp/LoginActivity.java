package org.rj.timesapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.rj.lib.JSONParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	//Views
	private EditText usernameInput, passwordInput;
	
	//JSONParser
	JSONParser jParser = new JSONParser();
    private static String url_login = "http://192.168.1.37/login.php";	
    private static final String TAG_SUCCESS = "success"; // Nodo JSON

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
			
		}else{  // Comprueba credenciales
			
			// Creando los parametros
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", credentials[0]));
            params.add(new BasicNameValuePair("password", credentials[1]));
        	
            // Envia la petición al servidor
            JSONObject json = null;
            json = jParser.makeHttpRequest(url_login, "POST", params);
            Log.d("Esperando respuesta", json.toString());
           
            
            // Comprobar respuesta del servidor
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
	                	// Inicia actividad
	        			Intent cronos = new Intent(this, CronosActivity.class);
	        			startActivity(cronos);
	        			
	        			// Finaliza actividad login
	        			finish();
                } else {
                    	// Fallo
                		Toast.makeText(this, json.getString("message"), Toast.LENGTH_LONG).show();
                		
                }
            } catch (JSONException e) {
                	e.printStackTrace();
            }       
			
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	
}

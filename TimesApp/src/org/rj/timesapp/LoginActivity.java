package org.rj.timesapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.rj.lib.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	//Views
	private EditText usernameInput, passwordInput;
	
	//ProgressDialog
	private ProgressDialog pDialog;
	
	//JSONParser
	JSONParser jParser = new JSONParser();
    private static String url_login = "http://192.168.1.37/login.php";	
    private static final String TAG_SUCCESS = "success"; // Nodo JSON

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Animación de transición
		overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
		
		// Oculta el título del layout
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setTheme(R.style.AppTheme);
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
			if(isNetworkConnected()){
				new CheckLogin ().execute();
			}else{
				Toast.makeText(this, "No Internet connection was found", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Comprueba si hay conexión a internet
	 * @return
	 */
	public boolean isNetworkConnected() {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED;
   }
	
	/**
     * Background Async Task para comprobar el login
     * */
    class CheckLogin extends AsyncTask<String, String, String> {
    	
    	private int success = 0;
 
        /**
         * Antes de crear el hilo crea y muestra el pDialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Realiza el login
         * */
        protected String doInBackground(String... args) {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            // Creando los parametros
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
 
            // Comprueba la etiqueta de exito
            try {
            	// Obteniendo el objeto  JSON Object
                JSONObject json = jParser.makeHttpRequest(url_login,  "POST", params);
                Log.d("Create Response", json.toString());
                success = json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
 
        /**
         * Después de terminar elimina el pDialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            
            if (success == 1) {
	           	// Inicia actividad
	   			Intent cronos = new Intent(getApplicationContext(), CronosActivity.class);
	   			startActivity(cronos);
	   			
	   			// Finaliza actividad login
	   			finish();
           } else {
               // fallo
        	   Log.i("access","Wrong username or password");
           }
        }
 
    }
	
}

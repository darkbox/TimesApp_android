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
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	// Views
	private EditText usernameInput, passwordInput;
	
	// ProgressDialog
	private ProgressDialog pDialog;
	
	// JSONParser
	JSONParser jParser = new JSONParser();
    private static String url_login = "";
    private static final String LOGIN_SCRIPT = "login.php";
    private static final String TAG_SUCCESS = "success"; // Nodo JSON
    
    // Preferencias
    private static final String SETTINGS = "TimesAppSettings";
    private SharedPreferences settings;
    private String remoteServer = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Animación de transición
		overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
		
		// Oculta el título del layout
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AppTheme);
		setContentView(R.layout.activity_login);
		
		// Campos de texto
		usernameInput = (EditText) findViewById(R.id.usernameEditText);
		passwordInput = (EditText) findViewById(R.id.passwordEditText);
		
		// Preferencias de la aplicación
		settings = getSharedPreferences(SETTINGS, 0);
		remoteServer = settings.getString("remoteServer", "");
		url_login = "http://" + remoteServer + "/" + LOGIN_SCRIPT;
		
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
			Toast.makeText(this, "Please, fill out text fields.", Toast.LENGTH_LONG).show();
		}else{  // Comprueba credenciales
			if(isNetworkConnected()){
				// Comprueba si hay un servidor
				if(remoteServer != ""){
					try{
						new CheckLogin ().execute();
					}catch(Exception e){
						Log.e("Connection","No server");
						Toast.makeText(this, "Server not found.", Toast.LENGTH_LONG).show();
					}
				}else{
					Log.e("Connection","Missing server address");
					Toast.makeText(this, "Please, set up a server.", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(this, "No Internet connection was found.", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 200 && resultCode == RESULT_OK){
			// Refresca los datos de configuración
			remoteServer = settings.getString("remoteServer", "");
			url_login = "http://" + remoteServer + "/" + LOGIN_SCRIPT;
			Log.i("Settings", "Getting preferences");
		}
	}

	@Override
	    public boolean onOptionsItemSelected(MenuItem item)   {
	         
	        switch (item.getItemId())   {
	        case R.id.action_settings:
	            Log.i("Settings", "Launching setting's activity");
	            Intent i = new Intent(this, SettingsActivity.class);
	            startActivityForResult(i, 200);
	            return true;
 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }    

	/**
	 * Comprueba si hay conexión a internet
	 * @return
	 */
	private boolean isNetworkConnected() {
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

package org.rj.timesapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rj.lib.JSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class ListActivitiesActivity extends ListActivity {
	
	// Progress Dialog
    private ProgressDialog pDialog;
	
	// JSONParser
	JSONParser jParser = new JSONParser();
	ArrayList<HashMap<String, String>> activityList;
	private static String url_list = "http://192.168.1.37/list.php";	
	
	// activities JSONArray
    JSONArray activities = null;
	
	// Nodos JSON
	private static final String TAG_SUCCESS = "success"; 
	private static final String TAG_ACTIVITIES = "activities";
	private static final String TAG_PROJECT = "project"; 
	private static final String TAG_DATE = "date"; 
	private static final String TAG_HOURS = "hours"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Animación de transición
			overridePendingTransition( R.anim.right_in, R.anim.left_out);
			
			// Oculta el título del layout
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setTheme(R.style.AppTheme);
			setContentView(R.layout.activity_list);
			
			// Hashmap para el ListView
	        activityList = new ArrayList<HashMap<String, String>>();
	 
	        // Carga las actividades en segundo plano
	        new LoadAllActivities().execute();
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
	
	/**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllActivities extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListActivitiesActivity.this);
            pDialog.setMessage("Loading activities. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_list, "GET", params);
 
            // Check your log cat for JSON reponse
            Log.d("All Activities: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                	activities = json.getJSONArray(TAG_ACTIVITIES);
 
                    // looping through All Products
                    for (int i = 0; i < activities.length(); i++) {
                        JSONObject c = activities.getJSONObject(i);
 
                        // Storing each json item in variable
                        String id = c.getString(TAG_DATE);
                        String name = c.getString(TAG_HOURS);
                        String project = c.getString(TAG_PROJECT);
 
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_DATE, id);
                        map.put(TAG_HOURS, name);
                        map.put(TAG_PROJECT, project);
 
                        // adding HashList to ArrayList
                        activityList.add(map);
                    }
                } else {
                    // no activities found

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
        	// dismiss the dialog after getting all products
        	pDialog.dismiss();
        	// updating UI from Background Thread
        	runOnUiThread(new Runnable() {
        		public void run() {
        			/**
        			 * Updating parsed JSON data into ListView
        			 * */
        			ListAdapter adapter = new SimpleAdapter(ListActivitiesActivity.this, activityList, R.layout.list_item, new String[] { TAG_DATE,  TAG_HOURS, TAG_PROJECT},   new int[] { R.id.pid, R.id.name, R.id.project_name });
        			// updating listview
        			setListAdapter(adapter);
        		}
        	});

        }
 
    }
	
}

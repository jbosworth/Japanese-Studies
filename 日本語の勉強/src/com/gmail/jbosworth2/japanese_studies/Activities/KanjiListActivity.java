package com.gmail.jbosworth2.japanese_studies.Activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.jbosworth2.japanese_studies.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class KanjiListActivity extends AppCompatActivity  {
	private String TAG = KanjiListActivity.class.getSimpleName();
    
    private ProgressDialog pDialog;
    private ListView lv;
 
    private ArrayList<HashMap<String,String>> kanjiList = null;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.lang:
	            Intent i = new Intent(this, ChangeLocaleActivity.class);
	            startActivity(i);
	            return true;
	        case R.id.return_home:
	        	Intent j = new Intent(this, MainActivity.class);
	        	startActivity(j);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kanji_list);
		
		if(kanjiList == null){
        	kanjiList = new ArrayList<HashMap<String, String>>();
		}
        lv = (ListView) findViewById(R.id.list);
        
     
        /**
         * Async task class to get json by making HTTP call
         */
        class GetKanji extends AsyncTask<Void, Void, Void> {
     
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog
                pDialog = new ProgressDialog(KanjiListActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
     
            }
     
            @Override
            protected Void doInBackground(Void... arg0) {
            	Log.i(null, "entered");
                URL url;
                BufferedReader reader = null;
                String jsons = "";
                try {
                    url = new URL("https://www.wanikani.com/api/user/b40596b8cfe2cfe30368b8e8180a826d/kanji/");
                    URLConnection con = url.openConnection();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine().toString()) != null) {
                        jsons = jsons + line;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }  catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.i(null, "response: " + jsons);
                
                if(!jsons.isEmpty()){
                try {
				    JSONObject jsonObj = new JSONObject(jsons);
    
				    // Getting JSON Array node
				    final JSONArray kanji = jsonObj.getJSONArray("requested_information");
    
				    // looping through All Kanji
				    runOnUiThread(new Runnable() {
				        @Override
				        public void run() {
				            Toast.makeText(getApplicationContext(),
				                    "Number of Kanji: " + kanji.length(),
				                    Toast.LENGTH_LONG)
				                    .show();
				        }
				    });
				    for (int i = 0; i < kanji.length(); i++) {
				        JSONObject c = kanji.getJSONObject(i);
    
				        String character = c.getString("character");
				        String meaning = c.getString("meaning");
				        String onyomi = c.getString("onyomi");
				        String kunyomi = c.getString("kunyomi");
				        String level = c.getString("level");
				        
				        //String synonyms = "";
				        //String srs = "";
				        // user_specific node is JSON Object
				        //JSONObject user = null;
				        //if(c.getJSONObject("user_specific") != null){
				        //	user = c.getJSONObject("user_specific");
				        //}
				        //if(user != null){
				        //	synonyms = user.getString("user_synonyms");
				        //	srs = user.getString("srs");
				        //}
    
				        // tmp hash map for single kanji
				        HashMap<String, String> kanjim = new HashMap<>();
    
				        // adding each child node to HashMap key => value
				        kanjim.put("character", character);
				        kanjim.put("meaning", meaning);
				        kanjim.put("onyomi", onyomi);
				        kanjim.put("kunyomi", kunyomi);
				        kanjim.put("level", level);
				        //kanjim.put("synonyms", synonyms);
				        //kanjim.put("srs", srs);
    
				        // adding kanji to kanji list
				        kanjiList.add(kanjim);
				    }
				} catch (final JSONException e) {
				    Log.e(TAG, "Json parsing error: " + e.getMessage());
				    runOnUiThread(new Runnable() {
				        @Override
				        public void run() {
				            Toast.makeText(getApplicationContext(),
				                    "Json parsing error: " + e.getMessage(),
				                    Toast.LENGTH_LONG)
				                    .show();
				        }
				    });
    
				}
                }else{
                	//jsons failed to read
                }
     
                return null;
            }
     
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();
                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        KanjiListActivity.this, kanjiList,
                        R.layout.list_item, new String[]{"character", "meaning",
                        "onyomi", "kunyomi", "level"}, new int[]{R.id.character,
                        R.id.meaning, R.id.onyomi, R.id.kunyomi, R.id.level});
     
                lv.setAdapter(adapter);
            }
     
        }
        if(kanjiList == null){
        	new GetKanji().execute();
		}
	}
	
	public ArrayList<HashMap<String, String>> getKanji(){
		return kanjiList;
	}
}

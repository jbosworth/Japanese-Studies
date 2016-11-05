package com.gmail.jbosworth2.japanese_studies.Activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.sqlite.KanjiDbHelper;
import com.gmail.jbosworth2.japanese_studies.sqlite.WKVocabDbHelper;
import com.gmail.jbosworth2.japanese_studies.xml.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class InstallationActivity extends Activity {
	private static boolean k_installed = false;
	private static boolean v_installed = false;
	private static boolean d_installed = false;
	private static boolean g_installed = false;
	
	private InputStream in;
	private XMLReader reader = XMLReader.getInstance();
	
	
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
	        case R.id.install:
	        	Intent k = new Intent(this, InstallationActivity.class);
	        	startActivity(k);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_installation);
		
	}
	
	public void installKanji(View view){
		if(KanjiDbHelper.getLastUpdate() == null){
			//Read kanji from XML file and put them into a list
			String fn = "kanji.xml";
			try {
				in = getAssets().open(fn);
				BufferedReader inputReader = new BufferedReader(new InputStreamReader(in));
		        StringBuilder sb = new StringBuilder();
		        String inline = "";
		        while ((inline = inputReader.readLine()) != null) {
		          sb.append(inline);
		        }
				reader.readFile(sb, fn);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Insert kanji from list into a table
			String level = "";
			String meanings = "";
			String kanas = "";
			KanjiDbHelper kdb = new KanjiDbHelper(getBaseContext());
			for(Item i : reader.getKanji()){
				level += i.getLevel();
				for(String m : i.getMeaning()){
					meanings += m;
					meanings += ",";
				}
				for(String k : i.getKana()){
					meanings += k;
					meanings += ",";
				}
				kdb.insertKanji(level, i.getCharacter(), meanings, kanas);
				level = "";
				meanings = "";
				kanas = "";
			}
		}
		k_installed = true;
	}
	
	public void installVocab(View view){
		String fn = "vocab.xml";
		if(WKVocabDbHelper.getLastUpdate() == null){
			try {
				in = getAssets().open(fn);
				BufferedReader inputReader = new BufferedReader(new InputStreamReader(in));
		        StringBuilder sb = new StringBuilder();
		        String inline = "";
		        while ((inline = inputReader.readLine()) != null) {
		          sb.append(inline);
		        }
				reader.readFile(sb, fn);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Insert vocab from list into a table
			String level = "";
			String meanings = "";
			String kanas = "";
			WKVocabDbHelper vdb = new WKVocabDbHelper(getBaseContext());
			for(Item i : reader.getVocab()){
				level += i.getLevel();
				for(String m : i.getMeaning()){
					meanings += m;
					meanings += ",";
				}
				for(String k : i.getKana()){
					meanings += k;
					meanings += ",";
				}
				vdb.insertVocab(level, i.getCharacter(), meanings, kanas);
				level = "";
				meanings = "";
				kanas = "";
			}
		}
		v_installed = true;
	}

	public void installDictionary(View view){
		
	}
	
	public void installGrammar(View view){
		
	}
	
	public static boolean isK_installed() {
		return k_installed;
	}

	public static boolean isV_installed() {
		return v_installed;
	}

	public static boolean isD_installed() {
		return d_installed;
	}

	public static boolean isG_installed() {
		return g_installed;
	}
}

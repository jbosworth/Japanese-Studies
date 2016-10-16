package com.gmail.jbosworth2.japanese_studies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class KanjiActivity extends Activity {
	private InputStream in;
	private String fn = "kanji.xml";
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kanji);
		
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
	}
	
	public void startKanjiReading(View view){
		Intent intent = new Intent(this, KanjiReadingActivity.class);
		startActivity(intent);
	}
	
	public void startKanjiWriting(View view){
		Intent intent = new Intent(this, KanjiWritingActivity.class);
		startActivity(intent);
	}
}

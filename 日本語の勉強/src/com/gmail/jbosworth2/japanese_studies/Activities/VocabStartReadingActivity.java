package com.gmail.jbosworth2.japanese_studies.Activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.MyNumberPicker;
import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.sqlite.WKVocabDbHelper;
import com.gmail.jbosworth2.japanese_studies.xml.XMLReader;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VocabStartReadingActivity extends Activity {
	private XMLReader reader = XMLReader.getInstance();
	private InputStream in;
	private String fn = "context_sentences.xml";
	private ArrayList<Item> vocab = reader.getVocab();
	private MyNumberPicker np1;
	private MyNumberPicker np2;
	private EditText et;
	private TextView tv;

	//Settings
	private static int startLevel;
	private static int endLevel;
	private static int amount;
	
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
		setContentView(R.layout.activity_vocab_start_reading);
		
		np1 = (MyNumberPicker) findViewById(R.id.vrrnp1);
		np2 = (MyNumberPicker) findViewById(R.id.vrrnp2);
		et = (EditText) findViewById(R.id.vrret);
		tv = (TextView) findViewById(R.id.vrrtv2);
		
		if(!MainActivity.isContextSentencesLoaded()){
			try {
				in = getAssets().open(fn);
				BufferedReader inputReader = new BufferedReader(new InputStreamReader(in));
		        StringBuilder sb = new StringBuilder();
		        String inline = "";
		        while ((inline = inputReader.readLine()) != null) {
		          sb.append(inline);
		        }
				reader.readFile(sb, fn);
				MainActivity.setContextSentencesLoaded(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startVRA(View view){
		//Check if an amount has been entered
		if(et.getText().toString().length() == 0){
			inputWarning();
		}else{
			//Set settings
			startLevel = np1.getValue();
			endLevel = np2.getValue();
			amount = Integer.parseInt(et.getText().toString());
			//Commented until I figure out how to fix error
			//WKVocabDbHelper vdb = new WKVocabDbHelper(getBaseContext());
			//vdb.selectFromRange(startLevel, endLevel, amount);
			
			//Choose from range
			ArrayList<Item> temp = new ArrayList<Item>();
			for(Item i : vocab){
				if(startLevel <= i.getLevel() && i.getLevel() <= endLevel){
					temp.add(i);
				}
			}
			//Clear vocab list
			vocab.clear();
			
			//Choose a specific amount of items at random from list to be stored in another list
			Random r = new Random();
			if(amount > temp.size()){
				amount = temp.size();
			}
			for(int j=0; j<amount; j++){
				vocab.add(temp.remove(r.nextInt(temp.size())));
			}
			reader.setWKVocab(vocab);
			//Start Review Activity
			Intent intent = new Intent(this, VocabReadingActivity.class);
			startActivity(intent);
		}
	}
	
	private void inputWarning(){
		//Set text warning
		String warning = "***Please enter an amount in the text box.***";
		tv.setText(warning);
	}
	
	public static int getAmount(){
		return amount;
	}
}

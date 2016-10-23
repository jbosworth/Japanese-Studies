package com.gmail.jbosworth2.japanese_studies.Activities;

import java.util.ArrayList;
import java.util.Random;

import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.MyNumberPicker;
import com.gmail.jbosworth2.japanese_studies.R;
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
	private MyNumberPicker np1;
	private MyNumberPicker np2;
	private EditText et;
	private TextView tv;

	//Settings
	private static int startLevel;
	private static int endLevel;
	private static int amount;
	
	//Pool of items
	private static ArrayList<Item> in = new ArrayList<Item>();
	
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocab_start_reading);
		
		np1 = (MyNumberPicker) findViewById(R.id.vrrnp1);
		np2 = (MyNumberPicker) findViewById(R.id.vrrnp2);
		et = (EditText) findViewById(R.id.vrret);
		tv = (TextView) findViewById(R.id.vrrtv2);
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
			
			//Pools of items
			ArrayList<Item> total = reader.getVocab();
			ArrayList<Item> pool = new ArrayList<Item>();
			
			
			//Create review pool based on chosen level range
			for(Item i : total){
				if((startLevel <= i.getLevel()) && (i.getLevel() <= endLevel)){
					pool.add(i);
				}
			}
			total.clear();
			
			//Randomly select items to be reviewed using @param amount
			Random r = new Random();
			if(amount > pool.size()){
				amount = pool.size();
			}
			for(int i=0; i<amount; i++){
				in.add(pool.remove(r.nextInt(pool.size())));
			}
			
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
	
	public static ArrayList<Item> getIn(){
		return in;
	}
	
	public static int getAmount(){
		return amount;
	}
}

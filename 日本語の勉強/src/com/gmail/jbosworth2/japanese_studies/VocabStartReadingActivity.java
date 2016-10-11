package com.gmail.jbosworth2.japanese_studies;

import java.util.ArrayList;
import java.util.Random;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VocabStartReadingActivity extends Activity {
	private XMLReader reader = XMLReader.getInstance();
	private LinearLayout ll;
	private LinearLayout ll2;
	private MyNumberPicker np1;
	private MyNumberPicker np2;
	private EditText et;

	//Settings
	private int startLevel;
	private int endLevel;
	private int amount;
	
	//Pool of items
	private static ArrayList<Item> in = new ArrayList<Item>();
	//Current Item
	private Item i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocab_start_reading);
		
		ll = (LinearLayout) findViewById(R.id.vrll);
		ll2 = (LinearLayout) findViewById(R.id.vrll2);
		np1 = (MyNumberPicker) findViewById(R.id.vrrnp1);
		np2 = (MyNumberPicker) findViewById(R.id.vrrnp2);
		et = (EditText) findViewById(R.id.vrret);
	}
	
	public void startVRA(View view){
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
	
	public static ArrayList<Item> getIn(){
		return in;
	}
}

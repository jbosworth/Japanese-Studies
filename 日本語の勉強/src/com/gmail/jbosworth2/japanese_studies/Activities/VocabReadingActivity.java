package com.gmail.jbosworth2.japanese_studies.Activities;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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
public class VocabReadingActivity extends Activity {
	//Pools of items
	private ArrayList<Item> in = new ArrayList<Item>();
	private ArrayList<Item> out = new ArrayList<Item>();
	private ArrayList<Item> incorrect = new ArrayList<Item>();
	//Current Item
	private Item i;
	//Random number generator
	private Random r = new Random();
	//Booleans to track correct/incorrect responses
	private boolean m_correct = false;
	private boolean r_correct = false;
	//Strings to present to user
	private String result;
	private String incorrect_items;
	private String final_result;
	//Scorekeeping
	private int msc;
	private int rsc;
	//Views
	private TextView tv;
	private EditText et1;
	private EditText et2;
	private Button b1;
	private Button b2;
	private Button b3;
	
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
		setContentView(R.layout.activity_vocab_reading);
		
		result = "";
		incorrect_items = "";
		final_result = "";
		
		tv = (TextView) findViewById(R.id.vrtv);
		et1 = (EditText) findViewById(R.id.vret1);
		et2 = (EditText) findViewById(R.id.vret2);
		b1 = (Button) findViewById(R.id.vrb1);
		b2 = (Button) findViewById(R.id.vrb2);
		b3 = (Button) findViewById(R.id.vrb3);
		
		b2.setEnabled(false);
		b3.setEnabled(false);
		
		in = VocabStartReadingActivity.getIn();
	}
	
	public void review(View v){
		b1.setEnabled(false);
		if(in.size()>0){
			i = in.remove(r.nextInt(in.size()));
			tv.setText(i.getCharacter());
			b2.setEnabled(true);
		}else{
			finish();
		}
	}
	
	public void checkAnswer(){
		String meaning = et1.getText().toString().toLowerCase(Locale.ENGLISH);
		String reading = et2.getText().toString();
		m_correct = false;
		r_correct = false;
		for(String m : i.getMeaning()){
			if(meaning.equals(m.toLowerCase())){
				m_correct = true;
			}
		}
		if(reading.equals(i.getKana())){
			r_correct = true;
		}
	}
	
	public void enteredAnswer(View v){
		b2.setEnabled(false);
		result = "";
		Resources res = getResources();
		checkAnswer();
		et1.setText(null);
		et2.setText(null);
		if(m_correct){
			result += res.getString(R.string.correct_meaning) + "\n";
			msc++;
		}else{
			result += res.getString(R.string.incorrect_meaning) +": " + i.getMeaning() + "\n";
			incorrect.add(i);
		}
		if(r_correct){
			result += res.getString(R.string.correct_reading);
			rsc++;
		}else{
			result += res.getString(R.string.incorrect_reading) + ": " + i.getKana();
			if(!incorrect.contains(i)){
				incorrect.add(i);
			}
		}
		tv.setText(result);
		out.add(i);
		b3.setEnabled(true);
	}
	
	public void proceed(View v){
		b3.setEnabled(false);
		review(v);
	}
	
	public void finish(){
		Resources res = getResources();
		float m_score = (msc/VocabStartReadingActivity.getAmount())*100;
		float r_score = (rsc/VocabStartReadingActivity.getAmount())*100;
		for(Item j : incorrect){
			incorrect_items += j.getCharacter() + "-";
			incorrect_items += j.getMeaning() + "-";
			incorrect_items += j.getKana() + ", ";
		}
		final_result += res.getString(R.string.end_of_review) + "\n" + m_score + "%"
		+ " " + res.getString(R.string.correct_meanings) + "\n" + r_score + "%"
		+ " " + res.getString(R.string.correct_readings) + "\n\n" + incorrect_items;
		tv.setText(final_result);
	}
	
	public void returnHome(View v){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}

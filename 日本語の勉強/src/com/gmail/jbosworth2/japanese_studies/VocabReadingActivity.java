package com.gmail.jbosworth2.japanese_studies;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
	//Current Item
	private Item i;
	//Random number generator
	private Random r = new Random();
	//Booleans to track correct/incorrect responses
	private boolean m_correct = false;
	private boolean r_correct = false;
	//Strings to present to user
	private String result;
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
	private Button b4;
	
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
		final_result = "";
		
		tv = (TextView) findViewById(R.id.vrtv);
		et1 = (EditText) findViewById(R.id.vret1);
		et2 = (EditText) findViewById(R.id.vret2);
		b1 = (Button) findViewById(R.id.vrb1);
		b2 = (Button) findViewById(R.id.vrb2);
		b3 = (Button) findViewById(R.id.vrb3);
		b4 = (Button) findViewById(R.id.vrb4);
		
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
		String meaning = et1.getText().toString();
		String reading = et2.getText().toString();
		for(String m : i.getMeaning()){
			if(meaning.equals(m)){
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
		if(m_correct){
			result += res.getString(R.string.correct_meaning) + "\n";
			msc++;
		}else{
			result += res.getString(R.string.incorrect_meaning) +": " + i.getMeaning() + "\n";
		}
		if(r_correct){
			result += res.getString(R.string.correct_reading);
			rsc++;
		}else{
			result += res.getString(R.string.incorrect_reading) + ": " + i.getKana();
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
		final_result += res.getString(R.string.end_of_review) + "\n" + msc + "/" + VocabStartReadingActivity.getAmount()
		+ " " + res.getString(R.string.correct_meaning) + "\n" + rsc + "/" + VocabStartReadingActivity.getAmount()
		+ " " + res.getString(R.string.correct_reading);
		tv.setText(final_result);
	}
	
	public void returnHome(View v){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}

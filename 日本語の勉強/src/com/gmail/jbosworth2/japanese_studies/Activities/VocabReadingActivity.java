package com.gmail.jbosworth2.japanese_studies.Activities;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import com.gmail.jbosworth2.japanese_studies.ContextSentence;
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
	//Current Item's context sentence
	private ContextSentence context;
	//Random number generator
	private Random r = new Random();
	//Booleans 
	//to track correct/incorrect responses
	private boolean m_correct = false;
	private boolean r_correct = false;
	//to check if context sentence(s) need to be updated
	private boolean cs_up = false;
	//to check if context sentence(s) are shown
	private boolean show_active = false;
	//to check if it's safe to show english sentence
	private boolean show_e = false;
	//Strings to present to user
	private String result;
	private String incorrect_items;
	private String final_result;
	//Scorekeeping
	private float msc;
	private float rsc;
	private int numItems;
	//Views
	private TextView tv;
	private TextView tv2;
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
		incorrect_items = "";
		final_result = "";
		
		numItems = VocabStartReadingActivity.getAmount();
		
		tv = (TextView) findViewById(R.id.vrtv);
		tv2 = (TextView) findViewById(R.id.vrtv2);
		et1 = (EditText) findViewById(R.id.vret1);
		et2 = (EditText) findViewById(R.id.vret2);
		b1 = (Button) findViewById(R.id.vrb1);
		b2 = (Button) findViewById(R.id.vrb2);
		b3 = (Button) findViewById(R.id.vrb3);
		
		tv2.setVisibility(View.INVISIBLE);
		b2.setEnabled(false);
		b3.setEnabled(false);
		
		review();
	}
	
	public void review(){
		if(in.size()>0){
			i = in.remove(r.nextInt(in.size()));
			tv.setText(i.getCharacter());
			b2.setEnabled(true);
			show_e = false;
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
			m.trim();
			m.toLowerCase();
			if(meaning.equals(m)){
				m_correct = true;
			}
		}
		for(String k : i.getKana()){
			if(reading.equals(k)){
				r_correct = true;
			}
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
		show_e = true;
		out.add(i);
		b3.setEnabled(true);
	}
	
	public void proceed(View v){
		b3.setEnabled(false);
		cs_up = false;
		review();
	}
	
	public void finish(){
		Resources res = getResources();
		int m_score = Math.round((msc/numItems) * 100);
		int r_score = Math.round((rsc/numItems) * 100);
		for(Item j : incorrect){
			incorrect_items += j.getCharacter() + "-";
			incorrect_items += j.getMeaning() + "-";
			incorrect_items += j.getKana() + ", ";
		}
		final_result += res.getString(R.string.end_of_review) + "\n" + m_score + "%"
		+ " " + res.getString(R.string.correct_meanings) + "\n" + r_score + "%"
		+ " " + res.getString(R.string.correct_readings) + "\n\n" + incorrect_items;
		show_e = false;
		tv.setText(final_result);
		b1.setEnabled(false);
	}
	
	public void returnHome(View v){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
	
	public void showContext(View v){
		searchContext();
		//Show sentence(s)
		if(!show_active){
			tv2.setVisibility(View.VISIBLE);
			if(!show_e){//show japanese sentence only
				tv2.setText(context.getJapanese_Sentence());
			}else{//show both
				tv2.setText(context.getJapanese_Sentence() + "\n" + context.getEnglish_Sentence());
			}
			show_active = true;
		}else{//hide sentence(s)
			tv2.setVisibility(View.INVISIBLE);
			show_active = false;
		}
	}
	
	public void searchContext(){
		if(!cs_up){
			//for(ContextSentence cs : reader.getContextSentences()){
			//	if(cs.getVocab().equals(i.getCharacter())){
			//		context = cs;
			//	}
			//}
			//cs_up = true;
		}
	}
}

package com.gmail.jbosworth2.japanese_studies;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
		checkAnswer();
		if(m_correct){
			result+="Correct meaning\n";
			//tv.setText("Correct meaning");
			msc++;
		}else{
			result += "Incorrect meaning: " + i.getMeaning() + "\n";
			//tv.setText("Incorrect meaning: " + i.getMeaning());
		}
		if(r_correct){
			result += "Correct reading";
			//tv.setText("Correct reading");
			rsc++;
		}else{
			result += "Incorrect reading: " + i.getKana();
			//tv.setText("Incorrect reading: " + i.getKana());
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
		final_result += "End of Review.\n" + msc + "/" + VocabStartReadingActivity.getAmount()
		+ " meanings correct.\n" + rsc + "/" + VocabStartReadingActivity.getAmount()
		+ " readings correct.";
		//tv.setText("End of Review. " + msc + "/" + VocabStartReadingActivity.getAmount()
		//+ " meanings correct.");
		tv.setText(final_result);
	}
	
	public void returnHome(View v){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}

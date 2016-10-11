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
	private Random r = new Random();
	private boolean correct = false;
	//Scorekeeping
	private int sc;
	//Views
	private TextView tv;
	private EditText et;
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocab_reading);
		
		tv = (TextView) findViewById(R.id.vrtv);
		et = (EditText) findViewById(R.id.vret);
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
		String answer = et.getText().toString();
		if(answer.equals(i.getKana())){
			correct = true;
		}
	}
	
	public void enteredAnswer(View v){
		b2.setEnabled(false);
		checkAnswer();
		if(correct){
			tv.setText("Correct");
			sc++;
		}else{
			tv.setText("Incorrect: " + i.getKana());
		}
		out.add(i);
		b3.setEnabled(true);
	}
	
	public void proceed(View v){
		b3.setEnabled(false);
		review(v);
	}
	
	public void finish(){
		tv.setText("End of Review. " + sc + "/" + VocabStartReadingActivity.getAmount()
		+ " correct.");
	}
	
	public void returnHome(View v){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}

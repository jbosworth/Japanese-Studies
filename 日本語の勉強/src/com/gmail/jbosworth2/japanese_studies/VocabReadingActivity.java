package com.gmail.jbosworth2.japanese_studies;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VocabReadingActivity extends Activity {
	//Pools of items
	private ArrayList<Item> in = new ArrayList<Item>();
	private ArrayList<Item> out = new ArrayList<Item>();
	//Current Item
	private Item i;
	Random r = new Random();
	private boolean correct = false;
	
	private TextView tv;
	private EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocab_reading);
		
		tv = (TextView) findViewById(R.id.vrtv);
		et = (EditText) findViewById(R.id.vret);
		
		in = VocabStartReadingActivity.getIn();
	}
	
	public void review(View v){
		if(in.size()>0){
			i = in.remove(r.nextInt(in.size()));
			tv.setText(i.getCharacter());
		}
	}
	
	public void checkAnswer(){
		String answer = et.getText().toString();
		if(answer.equals(i.getKana())){
			correct = true;
		}
	}
	
	public void enteredAnswer(View v){
		checkAnswer();
		if(correct){
			tv.setText("Correct");
		}else{
			tv.setText("Incorrect: " + i.getKana());
		}
		out.add(i);
	}
	
	public void proceed(View v){
		review(v);
	}
}

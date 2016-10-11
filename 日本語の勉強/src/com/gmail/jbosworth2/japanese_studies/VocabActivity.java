package com.gmail.jbosworth2.japanese_studies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VocabActivity extends Activity {
	private InputStream in;
	private String fn = "vocab.xml";
	private XMLReader reader = XMLReader.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocab);
		
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
	
	public void startVocabReading(View view){
		Intent intent = new Intent(this, VocabStartReadingActivity.class);
		startActivity(intent);
	}
	
	public void startVocabListening(View view){
		Intent intent = new Intent(this, VocabListeningActivity.class);
		startActivity(intent);
	}
}

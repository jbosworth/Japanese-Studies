package com.gmail.jbosworth2.japanese_studies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void startKanji (View view){
		Intent intent = new Intent(this, KanjiActivity.class);
		startActivity(intent);
	}
	
	public void startVocab (View view){
		Intent intent = new Intent(this, VocabActivity.class);
		startActivity(intent);
	}
}

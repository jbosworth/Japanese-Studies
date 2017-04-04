package com.gmail.jbosworth2.japanese_studies.Activities;

import com.gmail.jbosworth2.japanese_studies.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	private static boolean kanjiLoaded;
	private static boolean vocabLoaded;
	private static boolean contextSentencesLoaded;
	
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
		setContentView(R.layout.activity_main);

		kanjiLoaded = false;
	    vocabLoaded = false;
	    contextSentencesLoaded = false;
	}
	
	public static boolean isKanjiLoaded() {
		return kanjiLoaded;
	}

	public static void setKanjiLoaded(boolean kanjiLoaded) {
		MainActivity.kanjiLoaded = kanjiLoaded;
	}

	public static boolean isVocabLoaded() {
		return vocabLoaded;
	}

	public static void setVocabLoaded(boolean vocabLoaded) {
		MainActivity.vocabLoaded = vocabLoaded;
	}
	
	public static boolean isContextSentencesLoaded() {
		return contextSentencesLoaded;
	}

	public static void setContextSentencesLoaded(boolean contextSentencesLoaded) {
		MainActivity.contextSentencesLoaded = contextSentencesLoaded;
	}

	public void startKanji (View view){
		Intent intent = new Intent(this, KanjiActivity.class);
		startActivity(intent);
	}
	
	public void startVocab (View view){
		Intent intent = new Intent(this, VocabActivity.class);
		startActivity(intent);
	}
	
	public void startGrammar (View view){
		Intent intent = new Intent(this, GrammarActivity.class);
		startActivity(intent);
	}
	
	public void startSearch (View view){
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
}

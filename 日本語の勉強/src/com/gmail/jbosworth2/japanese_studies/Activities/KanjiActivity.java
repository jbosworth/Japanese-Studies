package com.gmail.jbosworth2.japanese_studies.Activities;

import com.gmail.jbosworth2.japanese_studies.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class KanjiActivity extends Activity {
	private TextView tv;
	
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
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kanji);
		
		tv = (TextView) findViewById(R.id.ktv);
	}
	
	public void startKanjiReading(View view){
		Resources res = getResources();
		Intent intent = new Intent(this, KanjiListActivity.class);
		startActivity(intent);
	}
	
	public void startKanjiWriting(View view){
		Intent intent = new Intent(this, KanjiWritingActivity.class);
		startActivity(intent);
	}
}

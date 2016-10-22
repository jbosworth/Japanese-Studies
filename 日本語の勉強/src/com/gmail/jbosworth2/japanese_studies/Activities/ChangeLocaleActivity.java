package com.gmail.jbosworth2.japanese_studies.Activities;

import java.util.Locale;

import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.R.id;
import com.gmail.jbosworth2.japanese_studies.R.layout;
import com.gmail.jbosworth2.japanese_studies.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ChangeLocaleActivity extends Activity {
	
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
		setContentView(R.layout.activity_change_locale);
	}
	
	@SuppressWarnings("deprecation")
	public void changeLocale(String lang) { 
		Resources res = getResources();
		Configuration conf = res.getConfiguration();
		Locale myLocale = new Locale(lang); 
		conf.locale = myLocale;
	    DisplayMetrics dm = res.getDisplayMetrics();
	    res.updateConfiguration(conf, dm); 
	    finish();
	} 
	
	public void en(View v){
		String s = "en";
		changeLocale(s);
	}
	
	public void jp(View v){
		String s = "jp";
		changeLocale(s);
	}
}

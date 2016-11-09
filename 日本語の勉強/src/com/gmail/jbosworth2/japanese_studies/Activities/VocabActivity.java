package com.gmail.jbosworth2.japanese_studies.Activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.sqlite.WKVocabDbHelper;
import com.gmail.jbosworth2.japanese_studies.xml.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VocabActivity extends Activity {
	private XMLReader reader = XMLReader.getInstance();
	private InputStream in;
	private TextView tv;
	private Button b2;
	
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
	        case R.id.install:
	        	Intent k = new Intent(this, InstallationActivity.class);
	        	startActivity(k);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocab);
		
		tv = (TextView) findViewById(R.id.vtv);
		b2 = (Button) findViewById(R.id.vb2);
		
		b2.setEnabled(false);
		
		String fn = "vocab.xml";
		if(WKVocabDbHelper.getLastUpdate() == null){
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
	}
	
	public void startVocabReading(View view){
	Resources res = getResources();
		//if(InstallationActivity.isV_installed()){
			Intent intent = new Intent(this, VocabStartReadingActivity.class);
			startActivity(intent);
		//}else{
		//	tv.setText(res.getString(R.string.vinstall));
		//}
	}
	
	public void startVocabListening(View view){
		Intent intent = new Intent(this, VocabListeningActivity.class);
		startActivity(intent);
	}
}

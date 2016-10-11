package com.gmail.jbosworth2.japanese_studies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VocabListeningActivity extends Activity {
	private TextView readList = null;
	private XMLReader reader = XMLReader.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocab_listening);
		
		String s = "";
	    readList = (TextView)findViewById(R.id.vltvid);
	    readList.setText(s);
	}
	
}

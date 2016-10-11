package com.gmail.jbosworth2.japanese_studies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class KanjiReadingActivity extends Activity {
	private TextView readList = null;
	private XMLReader reader = XMLReader.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kanji_reading);
		
		String s = reader.listKanji();
	    readList = (TextView)findViewById(R.id.krtvid);
	    readList.setText(s);
	}
}

package com.gmail.jbosworth2.japanese_studies.Activities;

import java.util.ArrayList;

import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.Review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class KanjiWritingActivity extends Activity {
	private TextView readList = null;
	//Activity Data
	private static final int maxLevel = 12;//Max level of kanji to study
	private static final int numLessons = 10;
	private ArrayList<Item> total;
	private ArrayList<Item> pool;
	private ArrayList<Item> completed;
	private ArrayList<Item> lessons;
	private ArrayList<Review> reviews;
	//Time before next review
	private static final long first = 1000*60*60*4;//4 hours
	private static final long second = 1000*60*60*24;//1 day
	private static final long third = 1000*60*60*24*4;//4 days
	private static final long fourth = 1000*60*60*24*7*2;//2 weeks
	private static final long fifth = 1000*60*60*24*7*4;//4 weeks
	private static final long sixth = 1000*60*60*24*7*8;//8 weeks
	private static final long seventh = 1000*60*60*24*7*16;//16 weeks
	
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
		setContentView(R.layout.activity_kanji_writing);
		
		//Get all kanji, then get all kanji up to level 12 to be studied

		for(Item i : total){
			if(i.getLevel() <= maxLevel){
				pool.add(i);
			}
		}
		
		
		lessons = new ArrayList<Item>();
		reviews = new ArrayList<Review>();
		completed = new ArrayList<Item>();
		//Read in reviews and completed items from file
		//Remove review and completed items from pool
		//Get numLessons random kanji from pool and add to lessons
		//Display size of lessons plus button to start lessons
		
		//Display number of reviews plus button to start reviews
		
		//Display or test completed items (optional activities)
	}
	
	//Start Lesson Activity
	
	//Start Review Activity
	
	//Start Completed Item Display/Test Activity
}

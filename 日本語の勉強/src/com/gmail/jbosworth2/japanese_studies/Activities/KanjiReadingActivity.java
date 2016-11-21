package com.gmail.jbosworth2.japanese_studies.Activities;

import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.xml.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

public class KanjiReadingActivity extends Activity implements OnGestureListener, OnDoubleTapListener {
	private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private static final String DEBUG_TAG2 = "Velocity";
    private VelocityTracker mVelocityTracker = null;

	private TextView readList = null;
	private XMLReader reader = XMLReader.getInstance();
	
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
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kanji_reading);
		
		// Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);

		
		String s = reader.listKanji(getBaseContext());
	    readList = (TextView)findViewById(R.id.krtvid);
	    readList.setText(s);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    int index = event.getActionIndex();
	    int action = event.getActionMasked();
	    int pointerId = event.getPointerId(index);
	
	    switch(action) {
	        case MotionEvent.ACTION_DOWN:
	            if(mVelocityTracker == null) {
	                // Retrieve a new VelocityTracker object to watch the velocity of a motion.
	                mVelocityTracker = VelocityTracker.obtain();
	            }
	            else {
	                // Reset the velocity tracker back to its initial state.
	                mVelocityTracker.clear();
	            }
	            // Add a user's movement to the tracker.
	            mVelocityTracker.addMovement(event);
	            break;
	        case MotionEvent.ACTION_MOVE:
	            mVelocityTracker.addMovement(event);
	            // When you want to determine the velocity, call
	            // computeCurrentVelocity(). Then call getXVelocity()
	            // and getYVelocity() to retrieve the velocity for each pointer ID.
	            mVelocityTracker.computeCurrentVelocity(1000);
	            // Log velocity of pixels per second
	            // Best practice to use VelocityTrackerCompat where possible.
	            Log.d("", "X velocity: " +
	                    VelocityTrackerCompat.getXVelocity(mVelocityTracker,
	                    pointerId));
	            Log.d("", "Y velocity: " +
	                    VelocityTrackerCompat.getYVelocity(mVelocityTracker,
	                    pointerId));
	            break;
	        case MotionEvent.ACTION_UP:
	        case MotionEvent.ACTION_CANCEL:
	            // Return a VelocityTracker object back to be re-used by others.
	            mVelocityTracker.recycle();
	            break;
	    }
	    return true;
	}

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
            float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }
    
    
	

}

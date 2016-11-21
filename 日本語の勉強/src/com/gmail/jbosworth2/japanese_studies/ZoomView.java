package com.gmail.jbosworth2.japanese_studies;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ZoomView extends View {
	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;

	public ZoomView(Context mContext, AttributeSet attrs){
	    // View code goes here
		super(mContext, attrs);
	    mScaleDetector = new ScaleGestureDetector(mContext, new ScaleListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
	    // Let the ScaleGestureDetector inspect all events.
	    mScaleDetector.onTouchEvent(ev);
	    return true;
	}

	@Override
	public void onDraw(Canvas canvas) {
	    super.onDraw(canvas);

	    canvas.save();
	    canvas.scale(mScaleFactor, mScaleFactor);

	    // onDraw() code goes here

	    canvas.restore();
	}

	private class ScaleListener
	        extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        mScaleFactor *= detector.getScaleFactor();

	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

	        invalidate();
	        return true;
	    }
	}

}

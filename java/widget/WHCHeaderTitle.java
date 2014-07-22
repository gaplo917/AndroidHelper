package com.kanhan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

public class WHCHeaderTitle extends WaHongCircleTextView {
	private static boolean resized = false;
	
	public WHCHeaderTitle(Context context) {
		super(context);
	}
	public WHCHeaderTitle(Context context, AttributeSet attrs) {
		super(context, attrs);

	}
	public WHCHeaderTitle(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public void adjustTextSizeWithTextLength(){
			int length = this.length();
			float size = this.getTextSize();
			float resizePortion = length/20;
			if(resizePortion >0 && !resized){
				float targetSize = (size/ (1+ resizePortion));
				this.setTextSize(targetSize);
				resized = true;
			}

	}
	
	@Override
	public void onDraw(Canvas canvas) {

	    setTextColor(Color.argb(255, 48, 22, 28)); // your stroke's color
	    getPaint().setStrokeWidth(7);
	    getPaint().setStyle(Paint.Style.STROKE);
	    super.onDraw(canvas);
	    super.onDraw(canvas);
	    setTextColor(Color.argb(255, 252, 183, 26));
	    getPaint().setStrokeWidth(0);
	    getPaint().setStyle(Paint.Style.FILL);
	    super.onDraw(canvas);
	}
	@Override
	public boolean onPreDraw() {

		return super.onPreDraw();
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		adjustTextSizeWithTextLength();
		return super.onCreateDrawableState(extraSpace);
	}
	

}

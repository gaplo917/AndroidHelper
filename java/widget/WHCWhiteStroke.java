package com.kanhan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class WHCWhiteStroke extends WaHongCircleTextView {

	public WHCWhiteStroke(Context context) {
		super(context);

	}
	public WHCWhiteStroke(Context context, AttributeSet attrs) {
		super(context, attrs);

	}
	public WHCWhiteStroke(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

	}

	@Override
	public void onDraw(Canvas canvas) {
	    setTextColor(Color.argb(255, 255,255, 255)); // your stroke's color
	    getPaint().setStrokeWidth(7);
	    getPaint().setStyle(Paint.Style.STROKE);
	    super.onDraw(canvas);
	    setTextColor(Color.argb(255, 48, 22, 28));
	    getPaint().setStrokeWidth(0);
	    getPaint().setStyle(Paint.Style.FILL);
	    super.onDraw(canvas);
	}
}
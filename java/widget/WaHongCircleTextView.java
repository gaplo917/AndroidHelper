package com.kanhan.widget;

import com.kanhan.had.unit.constant.LanguageConstant;
import com.kanhan.had.utils.LangUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class WaHongCircleTextView extends TextView{
	public static Typeface cacheTypeFace;
	public WaHongCircleTextView(Context context) {
		super(context);
		init(null);
	}
	public WaHongCircleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);

	}
	public WaHongCircleTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);

	}



	   
	private void init(AttributeSet attrs)  {
		if (attrs!=null) {
			try{

					 if(cacheTypeFace == null)
						 	cacheTypeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/fz_cu_yuan_m03.ttf");
					 setTypeface(cacheTypeFace);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	

	//and finally draw the text, mAscent refers to a member attribute which had
	//a value assigned to it in the measureHeight and Width methods
	   @Override
	   public void onDraw(Canvas canvas) {
		    super.onDraw(canvas);
	   }

	
	
	
	
	

}

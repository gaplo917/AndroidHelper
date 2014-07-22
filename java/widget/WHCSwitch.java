package com.kanhan.widget;

import com.kanhan.had.unit.constant.LanguageConstant;
import com.kanhan.had.utils.LangUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Switch;

public class WHCSwitch extends Switch {
	public static Typeface cacheTypeFace;
	public WHCSwitch(Context context) {
		super(context);
		init(null);
	}
	public WHCSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	public WHCSwitch(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}
	
	private void init(AttributeSet attrs) {
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
}

package com.kanhan.widget;

/*
 * Copyright (c) 2012 Wireless Designs, LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

 
import java.util.ArrayList;

import com.kanhan.had.R;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings.RenderPriority;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
 
/**
 * PagerContainer: A layout that displays a ViewPager with its children that are outside
 * the typical pager bounds.
 */
public class PagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {
 
    private static final int NUM_PAGE = 5;
	private ViewPager mPager;
    private 	ArrayList<ImageView> imageViews = new ArrayList<ImageView>() ;
    private TextView labelText;
    private ArrayMap<Integer,Integer> TextMap = new ArrayMap<Integer,Integer>();
    private LinearLayout indicatorContainer;
    boolean mNeedsRedraw = false;
    private Context context;
 
    public PagerContainer(Context context) {
        super(context);
        this.context= context;
        init();
    }
 
    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
        init();
    }
 
    public PagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context= context;
        init();
    }
 
    private void init() {
        //Disable clipping of children so non-selected pages are visible
        setClipChildren(false);
 
        //Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
        //You need to set this value here if using hardware acceleration in an
        // application targeted at these releases.
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }
 
    @Override
    protected void onFinishInflate() {
        try {
            mPager = (ViewPager) this.getChildAt(0);
            mPager.setOnPageChangeListener(this);
            
            //The label Text
            labelText = (TextView)  this.getChildAt(1);
            TextMap.put(0, R.string.welcome_setting);
            TextMap.put(1, R.string.footer_search);
            TextMap.put(2, R.string.footer_news);
            TextMap.put(3, R.string.footer_report);
            TextMap.put(4, R.string.footer_abtus);
            
            
            // The indicator container
            indicatorContainer = (LinearLayout)  this.getChildAt(2);
            
            for(int i =0 ; i < NUM_PAGE ; i ++){
            	imageViews.add((ImageView) indicatorContainer.getChildAt(i));
            }
            
            
            // init
            labelText.setText(TextMap.get(0));
            imageViews.get(0).setImageResource(R.drawable.page_indicator_focused);
            
            System.out.println("onFinishInflate");
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }
 
    public ViewPager getViewPager() {
        return mPager;
    }
 
    private Point mCenter = new Point();
    private Point mInitialTouch = new Point();
 
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenter.x = w / 2;
        mCenter.y = h / 2;
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //We capture any touches not already handled by the ViewPager
        // to implement scrolling from a touch outside the pager bounds.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int)ev.getX();
                mInitialTouch.y = (int)ev.getY();
            default:
                ev.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
                break;
        }
        return mPager.dispatchTouchEvent(ev);
    }
 
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Force the container to redraw on scrolling.
        //Without this the outer pages render initially and then stay static
        if (mNeedsRedraw) invalidate();
    }
 
    @Override
    public void onPageSelected(int position) { 
        labelText.setText(TextMap.get(position));
    	imageViews.get(position).setImageResource(R.drawable.page_indicator_focused);
		for (int i = 0; i < imageViews.size(); i++) {
			if (position != i) {
				imageViews.get(i)
						.setImageResource(R.drawable.page_indicator);
			}
		}
    	
    }
 
    @Override
    public void onPageScrollStateChanged(int position) {
        mNeedsRedraw = (position != ViewPager.SCROLL_STATE_IDLE);
		
    }
}
package com.kanhan.had.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.util.LruCache;

public class MemoryCache {
	public static MemoryCache staticMemoryCache = null;
	private  LruCache<String, Bitmap> mMemoryCache = null;
	int cachedSize =  0;
	// Get max available VM memory, exceeding this amount will throw an
	// OutOfMemory exception. Stored in kilobytes as LruCache takes an
	// int in its constructor.
	 final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	// Use 1/8th of the available memory for this memory cache.
	 final int cacheSize = maxMemory / 8;
	
	private MemoryCache() {

	    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            // The cache size will be measured in kilobytes rather than
	            // number of items.
	            return bitmap.getByteCount() / 1024;
	        }
	    };
	}
	private static MemoryCache createInstance(){
		if(staticMemoryCache == null)
			staticMemoryCache = new MemoryCache();
		return staticMemoryCache;
	}
	public static MemoryCache getInstance(){
		if(staticMemoryCache == null)
				return createInstance();
		else
				return staticMemoryCache;
	}
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {

		if (getBitmapFromMemCache(key) == null) {
		    			mMemoryCache.put(key, bitmap);
		}

	}

	public Bitmap getBitmapFromMemCache(String key) {
			return mMemoryCache.get(key);
	}
	public void setDisplayed(final String key){
		Runnable removeBitmap = new Runnable(){
			@Override
			public void run() {
				try{
					if(mMemoryCache.get(key) != null){
						System.out.println("recycle start");
						mMemoryCache.get(key).recycle();
						mMemoryCache.remove(key);
						System.out.println("recycle "+ key+" finished");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
	    	
	    };
	    new Handler().post(removeBitmap);
	}


}

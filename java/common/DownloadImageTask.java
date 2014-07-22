package com.kanhan.had.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.kanhan.had.utils.MemoryCache;
import com.kanhan.had.utils.URLUtils;

import java.io.InputStream;

/**
 * All image will be downloaded to memory , and use the url as a key
 * 
 * if the image is cached in memory, it will not trigger download action and directly get from memory
 * 
 * @author gary.lo
 *
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	  ImageView bmImage;
	  public DownloadImageTask(ImageView bmImage) {
	      this.bmImage = bmImage;
	  }

	  protected Bitmap doInBackground(String... urls) {
		  if(MemoryCache.getInstance().getBitmapFromMemCache(urls[0]) != null)
			  return MemoryCache.getInstance().getBitmapFromMemCache(urls[0]) ;
	      String urldisplay = urls[0];
	      Bitmap mIcon11 = null;
	      try {
	    	  URLUtils.trustEveryone();
	        InputStream in = new java.net.URL(urldisplay).openStream();
	        mIcon11 = BitmapFactory.decodeStream(in);
	        MemoryCache.getInstance().addBitmapToMemoryCache(urls[0], mIcon11);
	      } catch (Exception e) {
	          Log.e("Error", e.getMessage());
	          e.printStackTrace();
	      }
	      return mIcon11;
	  }

	  protected void onPostExecute(Bitmap result) {
	      bmImage.setImageBitmap(result);
	  }
	}
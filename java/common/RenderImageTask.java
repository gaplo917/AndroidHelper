package com.kanhan.had.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class RenderImageTask extends AsyncTask<String,String,Bitmap>{

	private ImageView imageView;
	
	public RenderImageTask(ImageView imageView){
		this.imageView = imageView;
	}
	
	@Override
	protected Bitmap doInBackground(String... path) {
		return BitmapFactory.decodeFile(path[0]);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		this.imageView.setImageBitmap(result);
		super.onPostExecute(result);
	}

}

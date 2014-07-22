package com.kanhan.had.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class ImageUtils {
	/**
	 * 
	 * Translate the  imageView
	 * 
	 * @param v							Target to translate position
	 * @param touchX				From Coordinate X
	 * @param touchY				From Coordinate Y
	 * @param posX					To Coordinate X
	 * @param posY					To Coordinate Y
	 * @param frame					Frame number
	 * @param fps						Frame per second
	 * @param acceleration		0 = No, 1 = Acceleration , 2 = Deceleration
	 */
	public  static  synchronized void translateImageViewTo(final ImageView v, double touchX, double touchY, double posX , double posY , int frame ,final int fps, final int acc ){



		final double CURRENT_X = touchX;
		final double CURRENT_Y = touchY;
		final Handler handler = new Handler();
		final int STEPS = frame;
		int i  =1;
		final double DISTANCE_X = (CURRENT_X - posX) ;
		final double DISTANCE_Y = (CURRENT_Y - posY) ;

		class translateView implements Runnable{
			double step;
			public translateView(int step){
				this.step = step;
			}
			@Override
			public void run() {
				if(this.step <= STEPS){
					handler.postDelayed(new translateView((int)this.step+1), (long) (1000.0/fps));
					double distanceToMoveX = 0;
					double distanceToMoveY = 0;
					switch(acc){
					case 0:
						distanceToMoveX = DISTANCE_X * this.step / STEPS;
						distanceToMoveY = DISTANCE_Y *this.step / STEPS ;
						break;
					case 1:
						distanceToMoveX = DISTANCE_X *  (this.step / STEPS) *  (this.step / STEPS);
						distanceToMoveY = DISTANCE_Y *  (this.step / STEPS) *  (this.step / STEPS);
						break;
					case 2:
						distanceToMoveX = DISTANCE_X - (DISTANCE_X * ( (STEPS - this.step) / STEPS) *  ( (STEPS - this.step) / STEPS) );
						distanceToMoveY = DISTANCE_Y - (DISTANCE_Y * ( (STEPS - this.step) / STEPS) *  ( (STEPS - this.step) / STEPS) );
						break;
					default:

					}

					Matrix matrix = new Matrix();
					matrix.reset();
					matrix.postTranslate((float)(CURRENT_X  - distanceToMoveX),(float)( CURRENT_Y  - distanceToMoveY));
					((ImageView)v).setImageMatrix(matrix);
				}
				return;
			}
		}

		handler.post(new translateView(i));
		if(frame == 0){
			new translateView(0);
		}
	}
	/**
	 * 
	 * ie.  If the resize parameter  = 2; 
	 * bitmap width  = width/2 
	 * bitmap height = height /2
	 * The bitmap size will be scaled to 1/4 size
	 * 
	 * @param bitmap							The target bitmap need to be resized
	 * @param resizePortion					The resize parameter
	 * @return
	 */
	public static Bitmap resizeBitmp(Bitmap bitmap, double resizePortion,boolean sizeLimit){
		final int height = bitmap.getHeight();
		final int width = bitmap.getWidth();
		if(resizePortion > 1){
			if(sizeLimit)
					resizePortion = (resizePortion>1.4)? 1.4 : resizePortion;
			Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,(int) Math.floor(width/resizePortion),(int) Math.floor(height/resizePortion), true);
			return resizedBitmap;
		}
		return bitmap;
	}

	
	
	public static  BitmapFactory.Options reszieOption(File photoFile,long MAX_SIZE) {
        BitmapFactory.Options option = new BitmapFactory.Options();
   
       option.inScaled = true;
       option.inPreferQualityOverSpeed = true;
       option.inPurgeable = true;
       File img = new  File(photoFile.getAbsolutePath());
       if(img.length() > MAX_SIZE){
       	option.inSampleSize = (int) Math.floor((img.length()/MAX_SIZE));
       }
       return option;
       
	}
	/**
	 * ImageView Matrix operation must use px to perform. So this you can you 1080px as default in matrix 
	 * 
	 * @param context							The context from any activity will work
	 * @param width								The width of the px
	 * @return
	 */
	public static int correctWidthPx(Context context,int width){
		return (int)((width / 1080.0) * context.getResources().getDisplayMetrics().widthPixels);
	}
	/**
	 * ImageView Matrix operation must use px to perform. So this you can you 1920px as default in matrix 
	 * 
	 * @param context
	 * @param height
	 * @return
	 */
	public static int correctHeightPx(Context context,int height){
		return (int)((height / 1920.0) * context.getResources().getDisplayMetrics().heightPixels);
	}

	public static Drawable LoadImageFromWeb(String url) {
		try {
			URLUtils.trustEveryone();
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

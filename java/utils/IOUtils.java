package com.kanhan.had.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gary.lo
 *
 */
public class IOUtils {
	/**
	 * Read Raw file in res/raw
	 * 
	 * @param context
	 * @param resId
	 * @return String
	 */
	public static String readRawTextFile(Context context, int resId)
	{
	    InputStream inputStream = context.getResources().openRawResource(resId);

	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	    int i;
	    try {
	        i = inputStream.read();
	        while (i != -1)
	        {
	            byteArrayOutputStream.write(i);
	            i = inputStream.read();
	        }
	        inputStream.close();
	    } catch (IOException e) {
	        return null;
	    }
	    return byteArrayOutputStream.toString();
	}
	
	
	/**
	 * Read Text file in filesystem
	 * 
	 * @param context
	 * @param resId
	 * @return String
	 */
	public static String readTextFileInFileSystem(String path)
	{
	   File file = new File(path);
		 //Read text from file
		   StringBuilder text = new StringBuilder();
	
		   try {
		       BufferedReader br = new BufferedReader(new FileReader(file));
		       String line;
	
		       while ((line = br.readLine()) != null) {
		           text.append(line);
		           text.append('\n');
		       }
		   }
		   catch (IOException e) {
		       //You'll need to add proper error handling here
		   }
	    return text.toString();
	}
}

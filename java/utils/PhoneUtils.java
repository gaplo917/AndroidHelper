package com.kanhan.had.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtils {
	public static void call (Context context, String phoneNumber){
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		try{
			phoneNumber = phoneNumber.replace(" ", "");
			callIntent.setData(Uri.parse("tel:"+phoneNumber));
			context.startActivity(callIntent);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
}

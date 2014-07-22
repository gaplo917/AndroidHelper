package com.kanhan.had.utils;

import android.content.Context;
import android.content.Intent;

public class EmailUtils {
	public static void SendEmail( Context context, String[] sendToAddress, String subject , String content){
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,sendToAddress);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        context.startActivity(emailIntent);
	}
}

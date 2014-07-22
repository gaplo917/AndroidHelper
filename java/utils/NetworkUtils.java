package com.kanhan.had.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import com.kanhan.had.R;

public class NetworkUtils {

	private final static String classTag = "NetworkUtils";

	public static boolean isOnline(Context context){

		ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conMgr.getActiveNetworkInfo();

		boolean connected = (network != null) && network.isAvailable() && network.isConnected();

		if (!connected){
			createNoNetworkDialog(context).show();
			Log.d(classTag, classTag + ": Active network = " + (network == null ? "DISCONNECTED" : "TRUE , avilable = " + network.isAvailable() + ", connected = " + network.isConnected()));
		}
		return connected;
	}
	
	private static Dialog createNoNetworkDialog(final Context context){
	    // Use the Builder class for convenient dialog construction
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setTitle(R.string.msg_error)
	    .setMessage(R.string.msg_no_internet)
                .setPositiveButton(R.string.dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                context.startActivity(new Intent(
                                        Settings.ACTION_WIFI_SETTINGS));
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton(R.string.dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                dialog.dismiss();
                            }
                        }
                );
	    // Create the AlertDialog object and return it
	    return builder.create();
	}

}
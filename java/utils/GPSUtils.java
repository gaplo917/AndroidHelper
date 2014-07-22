package com.kanhan.had.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Handler;

import com.kanhan.had.R;

public class GPSUtils {
	public final static String PENDDING_GPS = "PENDDING_GPS";
	
	
	public static boolean isGPSEnabled(final Context context){
		LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (!mgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) 
				return false;
		return true;
	}
	public static void showAlert(final Context context){
		LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (!mgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(R.string.msg_no_gps)
					.setCancelable(false)
					.setPositiveButton(R.string.dialog_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									context.startActivity(new Intent(
											android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
											dialog.dismiss();
								}
							})
					.setNegativeButton(R.string.dialog_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});

			Dialog dialog = builder.create();
			dialog.show();
		} 
	}
	
	public static void showAlertAndExitIfCacnel(final Context context){

		LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (!mgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(R.string.msg_no_gps)
					.setCancelable(false)
					.setPositiveButton(R.string.dialog_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									context.startActivity(new Intent(
											android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
											dialog.dismiss();
								}
							})
					.setNegativeButton(R.string.dialog_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									
									alertAndExit(context);
								}
							});

			final Dialog dialog = builder.create();
			dialog.show();
			BroadcastReceiver   penddingGps = new BroadcastReceiver(){

				@Override
				public void onReceive(Context context, Intent intent) {
					dialog.dismiss();
					context.unregisterReceiver(this);
				}
				
			};
			context.registerReceiver(penddingGps, new IntentFilter(GPSUtils.PENDDING_GPS));
		} 
	}
	
	public static void alertAndExit(final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.msg_no_gps_not_work)
				.setCancelable(false)
				.setPositiveButton(R.string.dialog_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
										((Activity)context).finish();
										dialog.dismiss();
							}
						});

		Dialog dialog = builder.create();
		dialog.show();
	}
	
	public static void keepMoniteringGPSStatus(final Context context){
		final Handler handler = new Handler();
		Runnable checkGPSStatus = new Runnable(){
			@Override
			public void run() {
				if(GPSUtils.isGPSEnabled(context))
						context.sendOrderedBroadcast(new Intent(GPSUtils.PENDDING_GPS),null);
				else
					handler.postDelayed(this, 500);
			}
			
		};
		handler.postDelayed(checkGPSStatus,500);
	}
}

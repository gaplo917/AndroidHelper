package com.kanhan.had.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.kanhan.had.R;
import com.kanhan.had.config.Config;
import com.kanhan.had.utils.URLUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class DownloadFileAsyncTask extends AsyncTask<String, String, String> {
	private final String classTag = this.getClass().getSimpleName();
	private ProgressDialog _dialog;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private String _url ;
	private String _downloadLocation = Environment.getExternalStorageDirectory() + "/HAD";
	private String fileName = "";
	private AsyncTaskReceiver<String> parent = null;
	public DownloadFileAsyncTask(AsyncTaskReceiver<String> parent,String url,String downloadLocation,String fileName,ProgressDialog progress_dialog){
		_url = url;
		_downloadLocation = downloadLocation;
		this.fileName = fileName;
		_dialog = progress_dialog;
		this.parent = parent;
		Log.v(classTag,"DownloadFileAsyncTask");
	}
	public DownloadFileAsyncTask(AsyncTaskReceiver<String> parent,String url,String downloadLocation,String fileName){
		_url = url;
		_downloadLocation = downloadLocation;
		this.fileName = fileName;
		this.parent = parent;
		Log.v(classTag,"DownloadFileAsyncTask");
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(_dialog != null){
				_dialog.setMessage(((Context)parent).getResources().getString(R.string.download_hotel_info));
				_dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				_dialog.setCancelable(false);
				_dialog.show();
		}
	}

	@Override
	protected String doInBackground(String... aurl) {
		int count;
		

		try {
			
			   URL url = new URL(_url);
			   URLUtils.trustEveryone();
			   URLConnection conexion = (URLConnection) url.openConnection();
			   conexion.connect();

			int lenghtOfFile = conexion.getContentLength();
			Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
			if(_dialog != null) publishProgress("",""+lenghtOfFile/1024);
			File folder = new File(_downloadLocation);
			if (!folder.exists()) {
				folder.mkdir();
			}
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(_downloadLocation + "/" + fileName);

			byte data[] = new byte[4*1024 ];

			long total = 0;

			while ((count = input.read(data)) != -1) {
				Log.v(classTag,"count "+count);
				total += count;
				if(_dialog != null) publishProgress(""+(int)((total*100)/lenghtOfFile));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	protected void onProgressUpdate(String... progress) {
		//		 Log.d("ANDRO_ASYNC",progress[0]);
		if(progress[0]==""){
			_dialog.setMessage("Downloading  Hotel information..("+progress[1]+"MB)");
		}
		else
			_dialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
	protected void onPostExecute(String unused) {
		((CommonActivity)(parent)).getPreference().edit().putBoolean(Config.JSON_DOWNLOAD_COMPLETE, true).commit();
		if(_dialog !=null)	_dialog.dismiss();
		if(parent !=null) 	parent.onTaskDone(unused);

	}

}

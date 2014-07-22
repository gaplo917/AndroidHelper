package com.kanhan.had.common;

import android.os.AsyncTask;
import android.util.Log;

import com.kanhan.had.unit.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
	
public class HttpGetJSONAsyncTask<T> extends AsyncTask<String, Void, T> {
	private final String classTag = this.getClass().getSimpleName();
	T result_json;
	JSONObject dataRecieved = new JSONObject();
	ArrayList<JSONObject> result_json_arr = new ArrayList<JSONObject>();
	Captcha<T> parent;
	String request;

	public HttpGetJSONAsyncTask(Captcha<T> parent){
		this.parent = parent;
		
	};
	
	public HttpGetJSONAsyncTask(){
		
	}
        @Override
        protected T doInBackground(String... urls) {
        	Thread.currentThread().setPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(T result) {
        	super.onPostExecute(result);
        	if(parent!=null ) parent.onCaptchaTaskDone(result);
			Log.v(classTag,"Finished post!!");
   
       }
   
		
	public T GET(String url){
		InputStream inputStream = null;
		String result = "";
		try {
			Log.v(classTag,"starting to post");
			// 1. create HttpClient
			HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();
			// 2. make POST request to the given URL
			 HttpGet httpGet = new HttpGet(url);
			 Log.v(classTag,"url:"+url);
		    
		    // 7. Set some headers to inform server about the type of the content   
		    httpGet.setHeader("Accept", "application/json");
		    httpGet.setHeader("Content-type", "application/json");
		    
		    //Create Cookie
		    if(User.getInstance().getCookies() == null)
		    	User.getInstance().setCookieStore(new BasicCookieStore());
            // Create local HTTP context
            HttpClientContext localContext = HttpClientContext.create();
            // Bind custom cookie store to the local context
            localContext.setCookieStore(User.getInstance().getCookies() );
		    
			System.out.println("cookieStore before" + User.getInstance().getCookies() );
			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpGet,localContext);
			System.out.println("cookieStore" + User.getInstance().getCookies() );
			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			
//			Log.v(classTag,"inputStream:"+inputStream);
			// 10. convert inputstream to string
			if(inputStream != null){
				result = convertInputStreamToString(inputStream);
				Log.v(classTag,result);
				try{
					result_json = (T) new JSONArray(result);
				}catch(JSONException e){
					result_json = (T) new JSONObject(result);
				}

									
				
			}else
				result = "Did not work!";
			    
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		// 11. return result
		return result_json;
	}
	  
    private String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        
        inputStream.close();
        return result;
        
    }


}

package com.kanhan.had.common;

import android.os.AsyncTask;
import android.util.Log;

import com.kanhan.had.unit.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class HttpPostFormTask extends AsyncTask<String, String, String> {
	private final String classTag = this.getClass().getSimpleName();
	private PostFormTaskListener<String> parent;
	private List<NameValuePair> dataList ;
	public  HttpPostFormTask(PostFormTaskListener<String> parent,List<NameValuePair> dataList){
		this.parent = parent;
		this.dataList = dataList;
	}
	public  HttpPostFormTask(List<NameValuePair> dataList){
		this.dataList = dataList;
	}
	@Override
	protected String doInBackground(String... params) {
		
		return doMultiPost(params[0], dataList);
	}

	private String doMultiPost(String url, List<NameValuePair> dataList) {

		HttpClient client =  SSLSocketFactoryEx.getNewHttpClient();

		HttpPost post = new HttpPost(url);
		try {

			// setup multipart entity
			MultipartEntityBuilder builder =  MultipartEntityBuilder.create();        

			/* example for setting a HttpMultipartMode */
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);


			int count  = 1;
			for (int i = 0; i < dataList.size(); i++) {

				// identify param type by Key

				if (dataList.get(i).getName().equals(HttpPostConstant.REPORT_IMAGE)) {

							if( !dataList.get(i).getValue().isEmpty() && dataList.get(i).getValue() !=null ){
									File f = new File(dataList.get(i).getValue());
				
									/* example for adding an image part */
									FileBody fileBody = new FileBody(f); //image should be a String

									builder.addBinaryBody(HttpPostConstant.REPORT_IMAGE+count,f , ContentType.MULTIPART_FORM_DATA, "serverWillChangeTheName.jpg");
						            count++;
									Log.d(classTag,"Image : "+dataList.get(i).getName()+","+dataList.get(i).getValue() + " | key :"+HttpPostConstant.REPORT_IMAGE+count);
							}
					} else {
						builder.addTextBody(dataList.get(i).getName(),dataList.get(i).getValue());
						Log.d(classTag,"(key,value):"+dataList.get(i).getName()+","+dataList.get(i).getValue());
					
				}

			}
			HttpEntity entity = builder.build();
			post.setEntity(entity);

			// create response handler
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setCookieStore(User.getInstance().getCookies());
			// execute and get response
            HttpResponse httpResponse = client.execute(post, localContext);
    		InputStream inputStream = httpResponse.getEntity().getContent();
            
			return   new String(convertInputStreamToString(inputStream));


		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;

	}
	@Override
	protected void onPostExecute(String result) {
		if(parent!=null) parent.onFormSubmitted(result);
		super.onPostExecute(result);
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

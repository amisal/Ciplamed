package com.ciplamed.ciplamedlanding;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class GuidelinesMain extends Activity{
	
	
	final String mimeType = "text/html";     
	final String encoding = "utf-8"; 
	int nid;
	WebView w;
	public static String filename="Ciplamedprefs";
	SharedPreferences someData;
	String dataReturned;
	int fontsize;
	String article,url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guidemain);
		Bundle extras=getIntent().getExtras();
		nid=extras.getInt("guidenid");
		Log.d("Anish", String.valueOf(nid));
		w=(WebView) findViewById(R.id.web1);
		WebSettings ws=w.getSettings();
		someData=getSharedPreferences(filename, 0);
		dataReturned=someData.getString("text_size", "0");
		fontsize=Integer.valueOf(dataReturned);
		ws.setDefaultFontSize(fontsize);
		new LoadGuidelinedata().execute();
	}

	public class LoadGuidelinedata extends AsyncTask<Void, Integer, Integer>{
    	
    	ProgressDialog progressBar;
    	
    	protected void onPreExecute(){
    	
    		progressBar = new ProgressDialog(GuidelinesMain.this);
    		progressBar.setCancelable(true);
    		progressBar.setMessage("Downloading Guideline...");
    		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	
    		progressBar.setMax(100);
    		progressBar.show();
    	
    		
    	}


    	@Override
    	protected Integer doInBackground(Void... arg0) {
    		// TODO Auto-generated method stub
    	
    		InputStream is = null;

    		String result = "";
    	    //the year data to send
    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	  
    	    nameValuePairs.add(new BasicNameValuePair("nid",String.valueOf(nid)));

    	    //http post
    	    try{
    	    	 

    	    	 
    	    		DefaultHttpClient httpclient = new DefaultHttpClient();
    	    		
    	            HttpPost httppost = new HttpPost("http://ciplamed.com/ciplamed_app/ciplamed_fullguideline.php");
    	           
    	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	            
    	            HttpResponse response = httpclient.execute(httppost);
    	          
    	            HttpEntity entity = response.getEntity();
    	            
    	            is = entity.getContent();
    	           

    	    }catch(Exception e){
    	    	Log.e("log_tag", "Enters FIRST CATCH BLOCK");    
    	    	Log.e("log_tag", "Error in http connection "+e.toString());
    	    }

    	    //convert response to string
    	    try{
    	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
    	            StringBuilder sb = new StringBuilder();
    	            String line = null;
    	            while ((line = reader.readLine()) != null) {
    	                    sb.append(line + "\n");
    	                    
    	            }
    	            is.close();
    	            Log.e("log_tag", sb.toString());
    	            result=sb.toString();
    	    }catch(Exception e){
    	            Log.e("log_tag", "Error converting result "+e.toString());
    	    }
    	    //parse json data
    	    try
    	    {
    	    	//Log.e("log_tag", "Enters SECOND TRY BLOCK 1");
    	    		JSONArray jArray = new JSONArray(result);
    	    	
    	            for(int i=0;i<jArray.length();i++){
    	            
    	                    JSONObject json_data = jArray.getJSONObject(i);
    	                   Log.i("Anish","id: "+json_data.getString("article"));
    	                   article=json_data.getString("article");
    	                    url=json_data.getString("refurl");
    	                    Log.i("Anish","id: "+json_data.getString("refurl"));
    	                  
    	              
    	                    publishProgress(1);
    	                    
    	             }
    	           
    	            
    	    }
    	catch(JSONException e){
    	            Log.e("log_tag", "Error parsing data "+e.toString());  
    	    }
    		return 1;
    	}
    	protected void onProgressUpdate(Integer...progress){
    		progressBar.incrementProgressBy(progress[0]);
    	}
    		
    	protected void onPostExecute(Integer result){
    		progressBar.dismiss();
    		//title.setText(nw_title);
    		//java.util.Date d = new java.util.Date(nw_changed*1000);  
            //date.setText(date_display);
    		
    		if(article.contentEquals(""))
    		{
    			otherDomain(url);
    		}
    		else
    		{
    			w.loadDataWithBaseURL("", article, mimeType, encoding, "");
    		}
           
    	
    	}
    	
    }

	public void otherDomain(final String url2) {
		// TODO Auto-generated method stub
		 AlertDialog.Builder builder= new AlertDialog.Builder(GuidelinesMain.this);
    	 builder.setMessage("You are redirecting to another domain. Do you want to continue?");
    	 builder.setCancelable(false);
    	 builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
			
			@SuppressLint("SetJavaScriptEnabled")
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//text.setText("You clicked Update now");
				//webservice(0);
				w.getSettings().setJavaScriptEnabled(true);
				
				w.setHorizontalScrollBarEnabled(true);
				w.setVerticalScrollBarEnabled(true);
				w.loadUrl(url2);
				
			}
		});
    	 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
				Intent i=new Intent(GuidelinesMain.this, GuidelinesList.class);
				startActivity(i);
				//text.setText("You clicked Update later");
			}
		});
    	 AlertDialog alert=builder.create();
    	 alert.show();
		
	}
   
	
}

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class NewsMain extends Activity{
	
	int nid;
	TextView title;
	TextView date;
	WebView w;
	final String mimeType = "text/html";     
	final String encoding = "utf-8"; 
	int nw_changed;
	String nw_title;
	String nw_news;
	String date_display;
	public static String filename="Ciplamedprefs";
	SharedPreferences someData;
	String dataReturned;
	int fontsize;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsmain);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Bundle extras=getIntent().getExtras();
		nid=extras.getInt("newsnid");
		date_display=extras.getString("newsdate");
		Log.d("Anish", String.valueOf(nid));
		title=(TextView) findViewById(R.id.newsmaintitle);
		date=(TextView) findViewById(R.id.newsmaindate);
		w=(WebView) findViewById(R.id.wvnews);
		WebSettings ws=w.getSettings();
		someData=getSharedPreferences(filename, 0);
	    dataReturned=someData.getString("text_size", "0");
	    fontsize=Integer.valueOf(dataReturned);
		ws.setDefaultFontSize(15);
		new Loadnewsdata().execute();
		Button home=(Button) findViewById(R.id.button1);
		Button list=(Button) findViewById(R.id.button2);
		
		home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(NewsMain.this, Main.class);
				startActivity(i);
			}
		});
		
		list.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(NewsMain.this, Newslist.class);
				startActivity(i);
			}
		});
	}
	  public class Loadnewsdata extends AsyncTask<Void, Integer, Integer>{
	    	
	    	ProgressDialog progressBar;
	    	
	    	protected void onPreExecute(){
	    	
	    		progressBar = new ProgressDialog(NewsMain.this);
	    		progressBar.setCancelable(true);
	    		progressBar.setMessage("Downloading News...");
	    		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    		//#####################REPLACE WITH RETURNED COUNT####################
	    		progressBar.setMax(100);
	    		progressBar.show();
	    	
	    		
	    	}


	    	@Override
	    	protected Integer doInBackground(Void... arg0) {
	    		// TODO Auto-generated method stub
	    		//db.execSQL("Create table if not exists calendar(cal_id INTEGER , nid NUMERIC, title TEXT, start_date TEXT, speciality TEXT);");
	    		InputStream is = null;

	    		String result = "";
	    	    //the year data to send
	    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    	    //nameValuePairs.add(new BasicNameValuePair("updated_date",Long.toString(dataReturned)));
	    	    nameValuePairs.add(new BasicNameValuePair("nid",String.valueOf(nid)));

	    	    //http post
	    	    try{
	    	    	 	//Log.e("log_tag", "Enters TRY BLOCK 1");
	    	    	 	//String auth = android.util.Base64.encodeToString(
	    	    	 		//	("oh22:022M3d!@").getBytes("UTF-8"), 
	    	    	 		//	android.util.Base64.NO_WRAP
	    	    	 		//);
	    	    	 		//HttpGet request = new HttpGet(url);
	    	    	 		//request.addHeader("Authorization", "Basic "+ auth);

	    	    	 	//HttpHost targetHost = new HttpHost("ciplamed.oh2two.com", 80, "http");
	    	    		DefaultHttpClient httpclient = new DefaultHttpClient();
	    	    		//httpclient.getCredentialsProvider().setCredentials(
	    	    		//new AuthScope(targetHost.getHostName(), targetHost.getPort()), 
	    	    		//new UsernamePasswordCredentials("oh22", "022M3d!@"));
	    	            HttpPost httppost = new HttpPost("http://ciplamed.com/ciplamed_app/ciplamed_fullnews.php");
	    	            //httppost.addHeader("Authorization", "Basic "+ auth);
	    	            //Log.e("log_tag", "Enters TRY BLOCK 2");
	    	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    	            //Log.e("log_tag", "Enters TRY BLOCK 3");
	    	            HttpResponse response = httpclient.execute(httppost);
	    	            //Log.e("log_tag", "Enters TRY BLOCK 4");
	    	            HttpEntity entity = response.getEntity();
	    	            //Log.e("log_tag", "Enters TRY BLOCK 5");
	    	            is = entity.getContent();
	    	            //Log.e("log_tag", "Enters TRY BLOCK 6");

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
	    	    		//Log.e("log_tag", "Enters SECOND TRY BLOCK 2");
	    	            for(int i=0;i<jArray.length();i++){
	    	            	//Log.e("log_tag", "Enters SECOND TRY BLOCK 3");
	    	                    JSONObject json_data = jArray.getJSONObject(i);
	    	                   
	    	                    int nw_id=json_data.getInt("news_id");
	    	                    Log.i("Anish","id: "+json_data.getInt("news_id"));
	    	                    int nw_nid=json_data.getInt("nid");
	    	                    Log.i("Anish","id: "+json_data.getInt("nid"));
	    	                    nw_title=json_data.getString("title");
	    	                    Log.i("Anish","id: "+json_data.getString("title"));
	    	                    nw_changed=json_data.getInt("changed");
	    	                    Log.i("Anish","id: "+json_data.getInt("changed"));
	    	                    Log.i("Anish","id: "+json_data.getString("speciality"));
	    	                    String spec=json_data.getString("speciality");
	    	                    nw_news=json_data.getString("whole_news");
	    	                    Log.i("Anish","id: "+json_data.getString("whole_news"));
	    	                  
	    	                   // db.execSQL("Insert into  calendar VALUES ('" + cal_id + "','" + cal_nid + "','" + cal_title + "','" + cal_start + "','" + cal_spec + "');");
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
	    		title.setText(nw_title);
	    		//java.util.Date d = new java.util.Date(nw_changed*1000);  
                date.setText(date_display);
                w.loadDataWithBaseURL("", nw_news, mimeType, encoding, "");
	    	
	    	}
	    	
	    }
	   
}

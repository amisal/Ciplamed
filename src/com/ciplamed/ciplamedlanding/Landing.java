/* *########## ANISH MISAL ##############*
 * Call this activity for only one time
 * when app is installed otherwise call
 * its dummy activity which will have 
 * webservices for only updates
 * IN NEW ONE:
 * Fetch last updated date for each type 
 * on click on update in options menu
 * and retrieve data only greater than 
 * this date
 *#################################### */

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

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;



public class Landing extends Activity {
	
	SQLiteDatabase db;
	ConnectivityManager connec;
	long cur_timestamp;
	int prod_count,guide_count;
	int news_count;
	int cal_count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		db=openOrCreateDatabase("ciplamedDB", MODE_PRIVATE, null);
		//#####################INITIALLY CREATE TABLE DATE####################
		//cur_timestamp=(System.currentTimeMillis()/1000L);
		//#####################WITH DEFAULT 1st Jan 2009 DATE FOR ALL THREE TYPES#########################
		db.execSQL("Create table if not exists updated_date (type TEXT , timestamp NUMERIC);");
		db.execSQL("Insert into updated_date VALUES ('news','1230768000');");
		db.execSQL("Insert into updated_date VALUES ('product','1230768000');");
		db.execSQL("Insert into updated_date VALUES ('calendar','1230768000');");
		db.execSQL("Insert into updated_date VALUES ('guidelines','1230768000');");
		connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //final TextView text=(TextView) findViewById(R.id.text);
       
        
        if ((connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)) 
        {

        	 Log.d("Anish", connec.getNetworkInfo(1).getState().toString());
        	 Log.d("Anish", connec.getNetworkInfo(0).getState().toString());
	        	 //text.setText("hey you are online!!!")     ;               
				//Do something in here when we are connected
        	
            	 AlertDialog.Builder builder= new AlertDialog.Builder(Landing.this);
            	 builder.setMessage("New Updates found, You want to");
            	 builder.setCancelable(false);
            	 builder.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
    				
    				public void onClick(DialogInterface dialog, int which) {
    					// TODO Auto-generated method stub
    					//text.setText("You clicked Update now");
    					
    				
    					//###########################RUN A WEBSERVICE FOR ITEM COUNT########################
    					new Loadcounts().execute();
    					
    					
    				}
    			});
            	 builder.setNegativeButton("Update Later", new DialogInterface.OnClickListener() {
    				
    				public void onClick(DialogInterface dialog, int which) {
    					// TODO Auto-generated method stub
    					dialog.cancel();
    					//text.setText("You clicked Update later");
    				}
    			});
            	 AlertDialog alert=builder.create();
            	 alert.show();

            }
        
    else  {
        	Log.d("STATE", connec.getNetworkInfo(1).getState().toString());
        	//text.setText("Look your not online");           

        }
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_landing, menu);
        return true;
    }

    public void webservice(int web)
    {
    	switch(web)
    	{
    	case 0:
    		{
    			new Loadnews().execute();
    		}
    		break;
    	case 1:
			{
				new Loadcal().execute();
			}
			break;
    	case 2:
			{
				new Loadproducts().execute();
			}
			break;
    	case 3:
			{
				new Loadguidelines().execute();
			}
			break;
	    		
	    	}
	    }
 public class Loadcounts extends AsyncTask<Void, Integer, Integer>{
    	
    	ProgressDialog progressBar;
    	
    	protected void onPreExecute(){
    	
    		progressBar = new ProgressDialog(Landing.this);
    		progressBar.setCancelable(true);
    		progressBar.setMessage("Preparing to Download...");
    		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    		//#####################REPLACE WITH RETURNED COUNT####################
    		//progressBar.setMax(100);
    		progressBar.show();
    	
    		
    	}
    	

    	@Override
    	protected Integer doInBackground(Void... arg0) {
    		// TODO Auto-generated method stub
    		//db.execSQL("Create table if not exists product_index(product_id INTEGER , nid NUMERIC, title TEXT,speciality TEXT);");
    		InputStream is = null;

    		String result = "";
    	    //the year data to send
    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	    //nameValuePairs.add(new BasicNameValuePair("updated_date",Long.toString(dataReturned)));
    	   nameValuePairs.add(new BasicNameValuePair("name","tom"));

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
    	            HttpPost httppost = new HttpPost("http://ciplamed.com/ciplamed_app/article_count.php");
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
    	                    prod_count=json_data.getInt("prodcount");
    	                    Log.i("Anish","id: "+json_data.getInt("prodcount"));
    	                    guide_count=json_data.getInt("guidecount");
    	                    Log.i("Anish","id: "+json_data.getInt("guidecount"));
    	                    news_count=json_data.getInt("newscount");
    	                    Log.i("Anish","id: "+json_data.getInt("newscount"));
    	                    cal_count=json_data.getInt("calcount");
    	                    Log.i("Anish","id: "+json_data.getInt("calcount"));
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
    		webservice(0);
    	   // db.close();
    	}
    	
    }
    
    //#############################################################################################
    public class Loadnews extends AsyncTask<Void, Integer, Integer>{
    	
    	
    	ProgressDialog progressBar;
    	
    	protected void onPreExecute(){
    	
    		progressBar = new ProgressDialog(Landing.this);
    		progressBar.setCancelable(true);
    		progressBar.setMessage("Downloading News...");
    		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		//#####################REPLACE WITH RETURNED COUNT####################
    		progressBar.setMax(news_count);
    		progressBar.show();
    	
    		
    	}

    	@Override
    	protected Integer doInBackground(Void... arg0) {
    		// TODO Auto-generated method stub
    		db.execSQL("Create table if not exists  medical_news(news_id INTEGER , nid NUMERIC, title TEXT, changed NUMERIC, speciality TEXT);");
    		InputStream is = null;

    		String result = "";
    	    //the year data to send
    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	    //nameValuePairs.add(new BasicNameValuePair("updated_date",Long.toString(dataReturned)));
    	    nameValuePairs.add(new BasicNameValuePair("name","tom"));

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
    	            HttpPost httppost = new HttpPost("http://ciplamed.com/ciplamed_app/ciplamed_news.php");
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
    	                    String nw_title=json_data.getString("title");
    	                    Log.i("Anish","id: "+json_data.getString("title"));
    	                    int nw_changed=json_data.getInt("changed");
    	                    Log.i("Anish","id: "+json_data.getInt("changed"));
    	                    Log.i("Anish","id: "+json_data.getString("speciality"));
    	                    String spec=json_data.getString("speciality");
    	                    db.execSQL("Insert into medical_news VALUES ('" + nw_id + "','" + nw_nid + "','" + nw_title + "','" + nw_changed + "','" + spec + "');");
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
    		//#####################UPDATE DATE FOR NEWS FOR CURRENT TIMESTAMP####################
    		long cur_timestamp_news=(System.currentTimeMillis()/1000L);
    		db.execSQL("update updated_date set timestamp='" + cur_timestamp_news + "' where type='news';");
    		webservice(1);
    	    
    	}
    	
    }    
    public class Loadcal extends AsyncTask<Void, Integer, Integer>{
    	
    	ProgressDialog progressBar;
    	
    	protected void onPreExecute(){
    	
    		progressBar = new ProgressDialog(Landing.this);
    		progressBar.setCancelable(true);
    		progressBar.setMessage("Downloading Calendar...");
    		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		//#####################REPLACE WITH RETURNED COUNT####################
    		progressBar.setMax(cal_count);
    		progressBar.show();
    	
    		
    	}


    	@Override
    	protected Integer doInBackground(Void... arg0) {
    		// TODO Auto-generated method stub
    		db.execSQL("Create table if not exists calendar(cal_id INTEGER , nid NUMERIC, title TEXT, start_date TEXT, speciality TEXT);");
    		InputStream is = null;

    		String result = "";
    	    //the year data to send
    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	    //nameValuePairs.add(new BasicNameValuePair("updated_date",Long.toString(dataReturned)));
    	    nameValuePairs.add(new BasicNameValuePair("name","tom"));

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
    	            HttpPost httppost = new HttpPost("http://ciplamed.com/ciplamed_app/ciplamed_calendar.php");
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
    	                    int cal_id=json_data.getInt("cal_id");
    	                    Log.i("Anish","id: "+json_data.getInt("cal_id"));
    	                    int cal_nid=json_data.getInt("nid");
    	                    Log.i("Anish","id: "+json_data.getInt("nid"));
    	                    String cal_title=json_data.getString("title");
    	                    Log.i("Anish","id: "+json_data.getString("title"));
    	                    String cal_start=json_data.getString("start_date");
    	                    Log.i("Anish","id: "+json_data.getString("start_date"));
    	                    Log.i("Anish","id: "+json_data.getString("speciality"));
    	                    String cal_spec=json_data.getString("speciality");
    	                    db.execSQL("Insert into  calendar VALUES ('" + cal_id + "','" + cal_nid + "','" + cal_title + "','" + cal_start + "','" + cal_spec + "');");
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
    		//#####################UPDATE DATE FOR CALENDAR FOR CURRENT TIMESTAMP####################
    		long cur_timestamp_cal=(System.currentTimeMillis()/1000L);
    		db.execSQL("update updated_date set timestamp='" + cur_timestamp_cal + "' where type='calendar';");
    	    webservice(2);
    	}
    	
    }
    public class Loadproducts extends AsyncTask<Void, Integer, Integer>{
    	
    	ProgressDialog progressBar;
    	
    	protected void onPreExecute(){
    	
    		progressBar = new ProgressDialog(Landing.this);
    		progressBar.setCancelable(true);
    		progressBar.setMessage("Downloading Products...");
    		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		//#####################REPLACE WITH RETURNED COUNT####################
    		progressBar.setMax(prod_count);
    		progressBar.show();
    	
    		
    	}
    	

    	@Override
    	protected Integer doInBackground(Void... arg0) {
    		// TODO Auto-generated method stub
    		db.execSQL("Create table if not exists product_index(product_id INTEGER , nid NUMERIC, title TEXT,speciality TEXT);");
    		InputStream is = null;

    		String result = "";
    	    //the year data to send
    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	    //nameValuePairs.add(new BasicNameValuePair("updated_date",Long.toString(dataReturned)));
    	   nameValuePairs.add(new BasicNameValuePair("name","tom"));

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
    	            HttpPost httppost = new HttpPost("http://ciplamed.com/ciplamed_app/prodname.php");
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
    	                    int p_id=json_data.getInt("product_id");
    	                    Log.i("Anish","id: "+json_data.getInt("product_id"));
    	                    int p_nid=json_data.getInt("nid");
    	                    Log.i("Anish","id: "+json_data.getInt("nid"));
    	                    String p_title=json_data.getString("title");
    	                    Log.i("Anish","id: "+json_data.getString("title"));
    	                   // int cal_start=json_data.getInt("start_date");
    	                    //Log.i("Anish","id: "+json_data.getInt("start_date"));
    	                    Log.i("Anish","id: "+json_data.getString("speciality"));
    	                    String p_spec=json_data.getString("speciality");
    	                    db.execSQL("Insert into  product_index VALUES ('" + p_id + "','" + p_nid + "','" + p_title + "','" + p_spec + "');");
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
    		//#####################UPDATE DATE FOR PRODUCTS FOR CURRENT TIMESTAMP####################
    		long cur_timestamp_prod=(System.currentTimeMillis()/1000L);
    		db.execSQL("update updated_date set timestamp='" + cur_timestamp_prod + "' where type='product';");
    	    //db.close();
    	    webservice(3);
    	
    	}
    	
    }
public class Loadguidelines extends AsyncTask<Void, Integer, Integer>{
    	
    	ProgressDialog progressBar;
    	
    	protected void onPreExecute(){
    	
    		progressBar = new ProgressDialog(Landing.this);
    		progressBar.setCancelable(true);
    		progressBar.setMessage("Downloading Guidelines...");
    		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		//#####################REPLACE WITH RETURNED COUNT####################
    		progressBar.setMax(guide_count);
    		progressBar.show();
    	
    		
    	}
    	

    	@Override
    	protected Integer doInBackground(Void... arg0) {
    		// TODO Auto-generated method stub
    		db.execSQL("Create table if not exists guidelines(guideline_id INTEGER , nid NUMERIC, title TEXT, created NUMERIC,speciality TEXT);");
    		InputStream is = null;

    		String result = "";
    	    //the year data to send
    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	    //nameValuePairs.add(new BasicNameValuePair("updated_date",Long.toString(dataReturned)));
    	   nameValuePairs.add(new BasicNameValuePair("name","tom"));

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
    	            HttpPost httppost = new HttpPost("http://ciplamed.com/ciplamed_app/ciplamed_guide.php");
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
    	                    int g_id=json_data.getInt("guideline_id");
    	                    Log.i("Anish","id: "+json_data.getInt("guideline_id"));
    	                    int g_nid=json_data.getInt("nid");
    	                    Log.i("Anish","id: "+json_data.getInt("nid"));
    	                    String g_title=json_data.getString("title");
    	                    Log.i("Anish","id: "+json_data.getString("title"));
    	                    int g_date=json_data.getInt("changed");
    	                    Log.i("Anish","id: "+json_data.getInt("changed"));
    	                    Log.i("Anish","id: "+json_data.getString("speciality"));
    	                    String g_spec=json_data.getString("speciality");
    	                    db.execSQL("Insert into  guidelines VALUES ('" + g_id + "','" + g_nid + "','" + g_title + "','" + g_date + "','" + g_spec + "');");
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
    		//#####################UPDATE DATE FOR GUIDELINES FOR CURRENT TIMESTAMP####################
    		long cur_timestamp_prod=(System.currentTimeMillis()/1000L);
    		db.execSQL("update updated_date set timestamp='" + cur_timestamp_prod + "' where type='guidelines';");
    	    db.close();
    	    Intent i1=new Intent(Landing.this, Main.class);
    	    startActivity(i1);
    	}
    	
    }
}








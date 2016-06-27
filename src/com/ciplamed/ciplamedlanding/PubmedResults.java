package com.ciplamed.ciplamedlanding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;


public class PubmedResults extends Activity{
	
	String ar_title, full_article;
	int ar_id;
	WebView w;
	final String mimeType = "text/html";     
	final String encoding = "utf-8"; 
	public static String filename="Ciplamedprefs";
	SharedPreferences someData;
	String dataReturned;
	int fontsize;
	TextView tv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras=getIntent().getExtras();
		ar_title=extras.getString("title");
		ar_id=extras.getInt("id");
		formUrlandLoad(ar_id);
		
	}

	private void formUrlandLoad(int ar_id2) {
		// TODO Auto-generated method stub
		String url="http://ciplamed.com/ciplamed_app/ws_pubmed_result.php?id="+ar_id2;
		new LoadData().execute(url);
	}

	
	public void loadScreen() {
		// TODO Auto-generated method stub
		Log.d("Anish", full_article);
		setContentView(R.layout.pubmedresults);
		tv=(TextView) findViewById(R.id.resulttitle);
		tv.setText(ar_title);
		w=(WebView) findViewById(R.id.webView1);
		WebSettings ws=w.getSettings();
		someData=getSharedPreferences(filename, 0);
	    dataReturned=someData.getString("text_size", "0");
	    fontsize=Integer.valueOf(dataReturned);
		ws.setDefaultFontSize(fontsize);
		w.loadDataWithBaseURL("", full_article, mimeType, encoding, "");
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inf=getMenuInflater();
		inf.inflate(R.menu.pubmedmenu, menu);
		return true;
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            // For "Title only": Examples of matching an ID with one assigned in
	            //                   the XML
	            case R.id.item1:
	               
	            	/*Intent i1=new Intent(PubmedResults.this, DownloadPDF.class);
	            	i1.putExtra("prodname", ar_title);
	            	i1.putExtra("fullpi", full_article);
		            startActivity(i1);*/
	            	ar_title.replace(" ", "_");
	            	ar_title.replace(".", "");
	            	Log.d("Anish", ar_title);
	            	generateNoteOnSD(ar_title+".txt", Html.fromHtml(full_article));
	         
	                return true;

	            case R.id.item2:
	               
	            	mailArticle(full_article);
	                return true;

	         
	        }

	        return false;
	    }

	
	private void mailArticle(String full_article2) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
		intent.setType("text/html");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from Ciplamed "+ ar_title);
		intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(full_article2));
		intent.setData(Uri.parse("mailto:")); // or just "mailto:" for blank
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
		startActivity(intent);
		
	}
	
	public void generateNoteOnSD(String sFileName, Spanned spanned){
	    try
	    {
	        File root = new File("/sdcard/download/ciplamed/pubmed");
	        if (!root.exists()) {
	            root.mkdirs();
	        }
	        File gpxfile = new File(root, sFileName);
	        FileWriter writer = new FileWriter(gpxfile);
	        writer.append(spanned);
	        writer.flush();
	        writer.close();
	        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
	    }
	    catch(IOException e)
	    {
	         e.printStackTrace();
	        
	    }
	   }  


	public class LoadData extends AsyncTask<String, Integer, Integer>{

		ProgressDialog progressBar;
		
		protected void onPreExecute(){
		
			progressBar = new ProgressDialog(PubmedResults.this);
			progressBar.setCancelable(true);
			progressBar.setMessage("Downloading Data...");
			progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			//#####################REPLACE WITH RETURNED COUNT####################
			//progressBar.setMax(100);
			progressBar.show();
		
			
		}
		
		
		@Override
		protected Integer doInBackground(String... urls) {
			// TODO Auto-generated method stub
			
			String URL=urls[0];
			
			Log.d("Anish", URL);
			
			InputStream is = null;
		
			String result = "";
		    //the year data to send
		    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		   //nameValuePairs.add(new BasicNameValuePair("name","tom"));
		
		    //http post
		    try{
		    	 	
		    		DefaultHttpClient httpclient = new DefaultHttpClient();
		    		
		            HttpPost httppost = new HttpPost(URL);
		            
		            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		          
		            HttpResponse response = httpclient.execute(httppost);
		            
		            HttpEntity entity = response.getEntity();
		            
		            is = entity.getContent();
		            
		
		    }
		    
		    catch(Exception e){
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
		    }
		    
		    catch(Exception e){
		            Log.e("log_tag", "Error converting result "+e.toString());
		    }
		    //parse json data
		    try
		    {
		    	
		    		JSONArray jArray = new JSONArray(result);
		    		
		            for(int i=0;i<jArray.length();i++)
		            {
		            	
		                    JSONObject json_data = jArray.getJSONObject(i);
		                    
		                    full_article=json_data.getString("result");
		                    Log.i("Anish","full_article: "+json_data.getString("result"));
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
			loadScreen();
			
		   // db.close();
		}
		
  }


	
	

}

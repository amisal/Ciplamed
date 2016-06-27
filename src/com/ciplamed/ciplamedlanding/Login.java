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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity{
	
	Button login;
	Button register;
	String username, pass,spec,name;
	EditText ed_name,ed_pass;
	int uid;
	TextView tv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		tv=(TextView) findViewById(R.id.textView1);
		tv.setVisibility(View.INVISIBLE);
		ed_name=(EditText) findViewById(R.id.editText1);
		ed_pass=(EditText) findViewById(R.id.editText2);
		login=(Button) findViewById(R.id.button1);
		register=(Button) findViewById(R.id.button2);
		login.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(tv.isShown())
				{
					 tv.setVisibility(View.INVISIBLE);
				}
				username=ed_name.getText().toString();
				pass=ed_pass.getText().toString();
				Log.d("Anish", username);
				Log.d("Anish", pass);
				new Loaduid().execute();
			}
		});
		register.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
public class Loaduid extends AsyncTask<Void, Integer, Integer>{
    	
    	ProgressDialog progressBar;
    	
    	protected void onPreExecute(){
    	
    		progressBar = new ProgressDialog(Login.this);
    		progressBar.setCancelable(true);
    		progressBar.setMessage("Please Wait...");
    		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    		progressBar.setMax(100);
    		progressBar.show();
    	
    		
    	}
    	

    	@Override
    	protected Integer doInBackground(Void... arg0) {
    		// TODO Auto-generated method stub
    		
    		InputStream is = null;

    		String result = "";
    	   
    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	  
    	    nameValuePairs.add(new BasicNameValuePair("uname",username));
    	    nameValuePairs.add(new BasicNameValuePair("pass",pass));

    	    //http post
    	    try{
    	    	 
    	    		DefaultHttpClient httpclient = new DefaultHttpClient();
    	    		
    	            HttpPost httppost = new HttpPost("http://ciplamed.com/ciplamed_app/ciplamed_login.php");
    	          
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
    	           // tv.setVisibility(View.VISIBLE);
    	    }
    	    //parse json data
    	    try
    	    {
    	    	
    	    		JSONArray jArray = new JSONArray(result);
    	    		
    	            for(int i=0;i<jArray.length();i++){
    	            	
    	                    JSONObject json_data = jArray.getJSONObject(i);
    	                    uid=json_data.getInt("uid");
    	                    Log.i("Anish","id: "+json_data.getInt("uid"));
    	                    spec=json_data.getString("spec");
    	                    Log.i("Anish","id: "+json_data.getString("spec"));
    	                    name=json_data.getString("fullname");
    	                    Log.i("Anish","id: "+json_data.getString("fullname"));
    	                    publishProgress(1);
    	                    
    	             }
    	           
    	            
    	    }
    	catch(JSONException e){
    	            Log.e("log_tag", "Error parsing data "+e.toString());  
    	           // tv.setVisibility(View.VISIBLE);
    	    }
    		return 1;
    	}
    	protected void onProgressUpdate(Integer...progress){
    		progressBar.incrementProgressBy(progress[0]);
    	}
    		
    	protected void onPostExecute(Integer result){
    		progressBar.dismiss();
    		//webservice(0);
    	   // db.close();
    		if(uid==0 && spec.contains("NA"))
    		{
    			showErrorMsg();
    		}
    		else
    		{
    			SharedPreferences someData=getSharedPreferences("Ciplamedprefs", 0);
                SharedPreferences.Editor editor= someData.edit();
 				editor.putInt("uid", uid);
 				editor.putString("selected_spec", spec);
 				editor.putString("doctor_spec", spec);
 				editor.putString("fullname", name);
 				editor.commit();
 				Intent i=new Intent(Login.this, Landing.class);
 				startActivity(i);
    			
    		}
    	}
    	
    }
	public void showErrorMsg() {
		// TODO Auto-generated method stub
		 tv.setVisibility(View.VISIBLE);
	}
}

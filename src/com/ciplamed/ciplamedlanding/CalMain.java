package com.ciplamed.ciplamedlanding;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
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
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class CalMain extends Activity{
	
	
	String name,date,nid,start_date,end_date,cal_title,cal_venue,cal_refurl,type;
	long startDate,endDate;
	TextView title,date_event,speakers,venue,s_date,e_date,speakers1,venue_header;
	Button visit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calmain);
		ScrollView sv=(ScrollView) findViewById(R.id.scrollView1);
		sv.setVerticalScrollBarEnabled(true);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		title=(TextView) findViewById(R.id.textView1);
		date_event=(TextView) findViewById(R.id.textView2);
		venue=(TextView) findViewById(R.id.textView6);
		venue_header=(TextView) findViewById(R.id.textView5);
		s_date=(TextView) findViewById(R.id.textView7);
		e_date=(TextView) findViewById(R.id.textView8);
		Bundle extras=getIntent().getExtras();
		name=extras.getString("eventtitle");
		Log.d("Anish", name);
		date=extras.getString("eventdate");
		Log.d("Anish", date);
		nid=extras.getString("eventnid");
		Log.d("Anish", nid);
		visit=(Button) findViewById(R.id.button4);
		visit.setVisibility(View.INVISIBLE);
		
		Button home=(Button) findViewById(R.id.button2);
		Button list=(Button) findViewById(R.id.button3);
		
		home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(CalMain.this, Main.class);
				startActivity(i);
			}
		});
		
		list.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(CalMain.this, Calenderlist.class);
				startActivity(i);
			}
		});
		
		
		new LoadCalMain().execute();
		
		//####################################################################################
		startDate=(System.currentTimeMillis());
		endDate = startDate + 1000 * 60 * 60; // For next 1hr
		Button set=(Button) findViewById(R.id.button1);
		set.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/***************** Event: note(without alert) *******************/

			    String eventUriString = "content://com.android.calendar/events";
			    ContentValues eventValues = new ContentValues();

			    eventValues.put("calendar_id", 1); // id, We need to choose from
			                                        // our mobile for primary
			                                        // its 1
			    eventValues.put("title", name);
			    eventValues.put("description", "BLAH BLAH");
			    eventValues.put("eventLocation", "BLAH BLAH");

			    

			    eventValues.put("dtstart", startDate);
			    eventValues.put("dtend", endDate);

			    // values.put("allDay", 1); //If it is bithday alarm or such
			    // kind (which should remind me for whole day) 0 for false, 1
			    // for true
			    eventValues.put("eventStatus", 1); // This information is
			    // sufficient for most
			    // entries tentative (0),
			    // confirmed (1) or canceled
			    // (2):
			    eventValues.put("visibility", 3); // visibility to default (0),
			                                        // confidential (1), private
			                                        // (2), or public (3):
			    eventValues.put("transparency", 0); // You can control whether
			                                        // an event consumes time
			                                        // opaque (0) or transparent
			                                        // (1).
			    eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

			    Uri eventUri = getContentResolver().insert(Uri.parse(eventUriString), eventValues);
			    long eventID = Long.parseLong(eventUri.getLastPathSegment());

			   
			        /***************** Event: Reminder(with alert) Adding reminder to event *******************/

			        String reminderUriString = "content://com.android.calendar/reminders";

			        ContentValues reminderValues = new ContentValues();

			        reminderValues.put("event_id", eventID);
			        reminderValues.put("minutes", 5); // Default value of the
			                                            // system. Minutes is a
			                                            // integer
			        reminderValues.put("method", 1); // Alert Methods: Default(0),
			                                            // Alert(1), Email(2),
			                                            // SMS(3)

			        Uri reminderUri = getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
			    }

			
		});
		
	}
	public class LoadCalMain extends AsyncTask<Void, Integer, Integer>{
    	
    	ProgressDialog progressBar;
    	
    	protected void onPreExecute(){
    	
    		progressBar = new ProgressDialog(CalMain.this);
    		progressBar.setCancelable(true);
    		progressBar.setMessage("Downloading Calendar...");
    		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    		//#####################REPLACE WITH RETURNED COUNT####################
    		progressBar.setMax(100);
    		progressBar.show();
    	
    		
    	}


    	@Override
    	protected Integer doInBackground(Void... arg0) {
    	
    		InputStream is = null;

    		String result = "";
    	    //the year data to send
    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	  
    	    nameValuePairs.add(new BasicNameValuePair("nid",nid));

    	    //http post
    	    try{
    	    	 
    	    		DefaultHttpClient httpclient = new DefaultHttpClient();
    	    		
    	            HttpPost httppost = new HttpPost("http://ciplamed.com/ciplamed_app/ciplamed_fullcal.php");
    	            
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
    	    	
    	    		JSONArray jArray = new JSONArray(result);
    	    		
    	            for(int i=0;i<jArray.length();i++){
    	            	
    	                    JSONObject json_data = jArray.getJSONObject(i);
    	             
    	                    cal_title=json_data.getString("title");
    	                    Log.i("Anish","id: "+json_data.getString("title"));
    	                   
    	                    cal_venue=json_data.getString("venue");
    	                    Log.i("Anish","id: "+json_data.getString("venue"));
    	                  
    	                    start_date=json_data.getString("start_date");
    	                    Log.i("Anish","id: "+json_data.getString("start_date"));
    	                    
    	                    end_date=json_data.getString("end_date");
    	                    Log.i("Anish","id: "+json_data.getString("end_date"));
    	                    
    	                    cal_refurl=json_data.getString("refurl");
    	                    Log.i("Anish","id: "+json_data.getString("refurl"));
    	                    
    	                    type=json_data.getString("type");
    	                    Log.i("Anish","id: "+json_data.getString("type"));
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
    		title.setText(cal_title);
    		String[] temp_date=start_date.split("-");
        	String month_no=temp_date[1];
        	String monthString = new DateFormatSymbols().getMonths()[Integer.valueOf(month_no)-1];
    		if(type.contains("Important Days"))
    		{
    			visit.setVisibility(View.INVISIBLE);
    			venue.setVisibility(View.INVISIBLE);
    			e_date.setVisibility(View.INVISIBLE);
    			s_date.setVisibility(View.INVISIBLE);
    			venue_header.setVisibility(View.INVISIBLE);
    			date_event.setText("Date: "+temp_date[2].replace("00:00:00", "")+" "+monthString+" "+temp_date[0]);
    		}
    		else
    		{
    			
            	String[] temp_date1=end_date.split("-");
            	String month_no1=temp_date1[1];
            	String monthString1 = new DateFormatSymbols().getMonths()[Integer.valueOf(month_no1)-1];
            	date_event.setText("Date: "+temp_date[2].replace("00:00:00", "")+" "+monthString+" "+temp_date[0]);
        		venue.setText(cal_venue);
        		s_date.setText("Start Date: "+temp_date[2].replace("00:00:00", "")+" "+monthString+" "+temp_date[0]);
        		e_date.setText("End Date: "+temp_date1[2].replace("00:00:00", "")+" "+monthString1+" "+temp_date1[0]);
    			visit.setVisibility(View.VISIBLE);
    		}
    		
    		visit.setOnClickListener(new OnClickListener() {
    			
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				Log.d("Anish", cal_refurl);
    				Intent i1=new Intent(CalMain.this,Visitlink.class);
    				i1.putExtra("refurl", cal_refurl);
    				i1.putExtra("title", cal_title);
    				startActivity(i1);
    				
    				
    			}
    		});
    		
    		
    	
         
    	
    	}
    	
    }
   
}

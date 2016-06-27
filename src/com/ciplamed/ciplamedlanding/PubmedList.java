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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PubmedList extends Activity implements OnScrollListener,OnItemClickListener{
	
	
	 int currentFirstVisibleItem;
	 int currentVisibleItemCount;
	 int currentScrollState;
	 boolean isLoading;
	 ListView lview;  
	 int start=0;
	 ListViewAdapter lviewAdapter;
	 ArrayList<String> arr_title=new ArrayList<String>();
	 ArrayList<String> arr_authors=new ArrayList<String>();
	 ArrayList<String> arr_citations=new ArrayList<String>();
	 ArrayList<Integer> arr_id=new ArrayList<Integer>();
	 int id;
	 String citations,title,authors;
	 String url;
	 String pl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stubgf
		super.onCreate(savedInstanceState);
		Bundle extras=getIntent().getExtras();
		pl=extras.getString("payload");
		generateURL(start,pl);
		//new LoadData().execute();
		
		
	}
	
	

	private void generateURL(int start2, String pl) {
		// TODO Auto-generated method stub
		
		url="http://ciplamed.com/ciplamed_app/ws_pubmed.php?text="+pl+"&start="+start2+"&max=30";
		new LoadData().execute(url);
		
		
	}



	public void showList() {
		// TODO Auto-generated method stub
		setContentView(R.layout.pubmedlist);
		lview= (ListView) findViewById(R.id.listView1);
		lviewAdapter = new ListViewAdapterPubmed(this, arr_title, arr_authors,arr_citations);  
        lview.setAdapter(lviewAdapter);  
        lview.setOnItemClickListener(this);  
        lview.setOnScrollListener(this);
        isLoading=false;
        lviewAdapter.notifyDataSetChanged();
        lview.setSelection(start);
    }  
  
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {  
        // TODO Auto-generated method stub  
      Toast.makeText(this,arr_title.get(position), Toast.LENGTH_SHORT).show();
      Intent i=new Intent(PubmedList.this, PubmedResults.class);
      i.putExtra("title", arr_title.get(position));
      i.putExtra("id", arr_id.get(position));
      startActivity(i);
        
    } 

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
		int lastVisibleElement = firstVisibleItem + visibleItemCount;
        if(lastVisibleElement == totalItemCount && totalItemCount> 5)
        {
            //Load elements
            //lviewAdapter.notifyDataSetChanged();
        	 if(!isLoading)
        	 {
		        isLoading = true;
	            Log.d("Anish", "Load More Data");
	            start=start+30;
	            Log.d("Anish", String.valueOf(start));
	            generateURL(start,pl);
        	 }
        }
		 	//this.currentFirstVisibleItem = firstVisibleItem;
		   // this.currentVisibleItemCount = visibleItemCount;
		
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
		 //this.currentScrollState = scrollState;
		   // this.isScrollCompleted();
		 }

		private void isScrollCompleted() {
		    if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
		        /*** In this way I detect if there's been a scroll which has completed ***/
		        /*** do the work for load more date! ***/
		        if(!isLoading){
		             isLoading = true;
		             //loadMoreDAta();
		           //  Log.d("Anish", "Load More Data");
		           //  start=start+30;
		           //  Log.d("Anish", String.valueOf(start));
		            // generateURL(start);
		        }
		    }

		}
		
		
		
		
		public class LoadData extends AsyncTask<String, Integer, Integer>{

			ProgressDialog progressBar;
			
			protected void onPreExecute(){
			
				progressBar = new ProgressDialog(PubmedList.this);
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
			                    id=json_data.getInt("id");
			                    Log.i("Anish","id: "+json_data.getInt("id"));
			                    citations=json_data.getString("citations");
			                    Log.i("Anish","citations: "+json_data.getString("citations"));
			                    title=json_data.getString("title");
			                    Log.i("Anish","title: "+json_data.getString("title"));
			                    authors=json_data.getString("authors");
			                    Log.i("Anish","authors: "+json_data.getString("authors"));
			                    arr_title.add(title);
			                    arr_authors.add(authors);
			                    arr_id.add(id);
			                    arr_citations.add(citations);
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
				showList();
				
			   // db.close();
			}
			
	  }

		
			
}

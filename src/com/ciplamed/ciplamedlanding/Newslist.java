package com.ciplamed.ciplamedlanding;

import java.util.ArrayList;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.text.DateFormatSymbols;

public class Newslist extends Activity implements OnItemClickListener{

	/** Called when the activity is first created. */  
	  
    ListView lview;  
    ListViewAdapter lviewAdapter;
    ArrayList<String> newstitle=new ArrayList<String>();
    ArrayList<String> newsdate=new ArrayList<String>();
    ArrayList<Integer> news_nid=new ArrayList<Integer>();
    public static String filename="Ciplamedprefs";
	SharedPreferences someData;
	String dataReturned, spec;
  
   
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.newslist);  
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        someData=getSharedPreferences(filename, 0);
	    dataReturned=someData.getString("selected_spec", "0");
	    spec=dataReturned;
        SQLiteDatabase db=openOrCreateDatabase("ciplamedDB", MODE_PRIVATE, null);
        Cursor cur = db.rawQuery("SELECT DISTINCT nid,title,date(datetime(changed,'unixepoch')) as created FROM medical_news where title<>'null' and speciality='"+spec+"' order by created desc", null);
        cur.moveToFirst();
        int n=cur.getCount();
       
        //int i=0;
        for(int i=0;i<n-1;i++)
        {
        	//p.add();
        	cur.moveToNext();
        	newstitle.add(cur.getString(cur.getColumnIndex("title")));
        	String date=cur.getString(cur.getColumnIndex("created"));
        	String[] temp_date=date.split("-");
        	String month_no=temp_date[1];
        	String monthString = new DateFormatSymbols().getMonths()[Integer.valueOf(month_no)-1];
        	newsdate.add("Published on: "+temp_date[2]+" "+monthString+" "+temp_date[0]);
        	Log.d("Anish",cur.getString(cur.getColumnIndex("title")));
        	news_nid.add(cur.getInt(cur.getColumnIndex("nid")));
        	Log.d("Anish", String.valueOf(cur.getInt(cur.getColumnIndex("nid"))));
        	
        	//i++;
        }
  
        lview = (ListView) findViewById(R.id.listView1);
        db.close();
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.guidelinesheader, lview, false);
        lview.addHeaderView(header, null, false);
        TextView tvheader=(TextView) findViewById(R.id.textViewHeader);
        tvheader.setText("Medical News");
        //TextView head=(TextView) findViewById(R.id.headertxt);
        //head.setText("Medical News");
        /////////HEADER CODE///////////////
		Button home=(Button) findViewById(R.id.homeicon);
		//Button search=(Button) findViewById(R.id.zoom);
		home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Newslist.this, Main.class);
				startActivity(i);
				
			}
		});
		/*search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Newslist.this, searchsort.class);
				startActivity(i);
			}
		});*/
		
		//////////////////////////////////////////
       lviewAdapter = new ListViewAdapter(this, newstitle, newsdate);  
  
        System.out.println("adapter => "+lviewAdapter.getCount());  
  
        lview.setAdapter(lviewAdapter);  
  
        lview.setOnItemClickListener(this);  
    }  
  
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {  
        // TODO Auto-generated method stub  
      // Toast.makeText(this,"NID="+news_nid.get(position), Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Newslist.this, NewsMain.class);
        i.putExtra("newsnid", news_nid.get(position));
        i.putExtra("newsdate", newsdate.get(position));
        startActivity(i);
    } 
    
}  
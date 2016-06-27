package com.ciplamed.ciplamedlanding;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class GuidelinesList extends Activity implements OnItemClickListener{

	/** Called when the activity is first created. */  
	  
    ListView lview;  
    ListViewAdapter lviewAdapter;
    ArrayList<String> guidelinetitle=new ArrayList<String>();
    ArrayList<String> guidelinedate=new ArrayList<String>();
    ArrayList<Integer> guideline_nid=new ArrayList<Integer>();
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
        Cursor cur = db.rawQuery("SELECT nid,title,date(datetime(created,'unixepoch')) as created FROM guidelines where speciality='"+spec+"' order by created desc", null);
        int n=cur.getCount();
	    cur.moveToFirst();
       Log.d("Anish", String.valueOf(n));
        //int i=0;
        for(int i=1;i<=n;i++)
        {
        	//p.add();
        	 Log.d("Anish", String.valueOf(i));
        	
        	guidelinetitle.add(cur.getString(cur.getColumnIndex("title")));
        	String date=cur.getString(cur.getColumnIndex("created"));
        	String[] temp_date=date.split("-");
        	String month_no=temp_date[1];
        	String monthString = new DateFormatSymbols().getMonths()[Integer.valueOf(month_no)-1];
        	//guidelinedate.add("Published on: "+temp_date[2]+" "+monthString+" "+temp_date[0]);
        	guidelinedate.add(" ");
        	//Log.d("Anish",cur.getString(cur.getColumnIndex("title")));
        	guideline_nid.add(cur.getInt(cur.getColumnIndex("nid")));
        	//Log.d("Anish", String.valueOf(cur.getInt(cur.getColumnIndex("nid"))));
        	cur.moveToNext();
        	
        	//i++;
        }
  
        lview = (ListView) findViewById(R.id.listView1);
        cur.close();
        db.close();
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.guidelinesheader, lview, false);
        
        lview.addHeaderView(header, null, false);
        //TextView head=(TextView) findViewById(R.id.headertxt);
        //head.setText("Medical News");
        /////////HEADER CODE///////////////
        TextView tvheader=(TextView) findViewById(R.id.textViewHeader);
        tvheader.setText("Guidelines");
		Button home=(Button) findViewById(R.id.homeicon);
		//Button search=(Button) findViewById(R.id.zoom);
		home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(GuidelinesList.this, Main.class);
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
       lviewAdapter = new ListViewAdapter(this, guidelinetitle, guidelinedate);  
  
        System.out.println("adapter => "+lviewAdapter.getCount());  
  
        lview.setAdapter(lviewAdapter);  
  
        lview.setOnItemClickListener(this);  
    }  
  
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {  
        // TODO Auto-generated method stub  
      // Toast.makeText(this,"NID="+news_nid.get(position), Toast.LENGTH_SHORT).show();
        Intent i=new Intent(GuidelinesList.this, GuidelinesMain.class);
        i.putExtra("guidenid",guideline_nid.get(position));
       // i.putExtra("newsdate", newsdate.get(position));
        startActivity(i);
    } 
    
}  

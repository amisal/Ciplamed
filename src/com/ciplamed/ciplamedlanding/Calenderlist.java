package com.ciplamed.ciplamedlanding;

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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Calenderlist extends Activity  implements OnItemClickListener{
	  ListView lview;  
	    ListViewAdapter lviewAdapter;
	    ArrayList<String> eventtitle=new ArrayList<String>();
	    ArrayList<String> eventdate=new ArrayList<String>();
	    ArrayList<Integer> eventnid=new ArrayList<Integer>();
	    SQLiteDatabase db;
	    public static String filename="Ciplamedprefs";
		SharedPreferences someData;
		String dataReturned, spec;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calenderlist);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 someData=getSharedPreferences(filename, 0);
		    dataReturned=someData.getString("selected_spec", "0");
		    spec=dataReturned;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		db=openOrCreateDatabase("ciplamedDB", MODE_PRIVATE, null);
      Cursor cur = db.rawQuery("SELECT nid,title,start_date FROM calendar where speciality='"+spec+"' order by start_date", null);
     
      int n1=cur.getCount();
      cur.moveToFirst();
      Log.d("Anish", String.valueOf(n1));
      //int i=0;
      for(int i=1;i<n1;i++)
      {
      	//p.add();
      	
      	eventtitle.add(cur.getString(cur.getColumnIndex("title")));
      	eventdate.add(cur.getString(cur.getColumnIndex("start_date")));
      	eventnid.add(cur.getInt(cur.getColumnIndex("nid")));
      	Log.d("Anish",cur.getString(cur.getColumnIndex("title")));
      	cur.moveToNext();
      	//i++;
      }

      lview = (ListView) findViewById(R.id.listView1);  
      db.close();
      LayoutInflater inflater = getLayoutInflater();
      ViewGroup header = (ViewGroup)inflater.inflate(R.layout.guidelinesheader, lview, false);
      lview.addHeaderView(header, null, false);
      TextView tvheader=(TextView) findViewById(R.id.textViewHeader);
      tvheader.setText("Event Calendar");
      //TextView head=(TextView) findViewById(R.id.headertxt);
      //head.setText("Medical News");
      /////////HEADER CODE///////////////
		Button home=(Button) findViewById(R.id.homeicon);
		//Button search=(Button) findViewById(R.id.zoom);
		home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Calenderlist.this, Main.class);
				startActivity(i);
				
			}
		});    
		
	lviewAdapter = new ListViewAdaptercal(this, eventtitle, eventdate);
      lview.setAdapter(lviewAdapter);  
      lview.setOnItemClickListener(this);  
  }  

  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {  
      // TODO Auto-generated method stub  
      //Toast.makeText(this,eventtitle.get(position), Toast.LENGTH_SHORT).show();
      Intent i=new Intent(Calenderlist.this, CalMain.class);
      i.putExtra("eventtitle", eventtitle.get(position));
      i.putExtra("eventdate", eventdate.get(position));
      i.putExtra("eventnid", String.valueOf(eventnid.get(position-1)));
      startActivity(i);
  } 
  
	

}


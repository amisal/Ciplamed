package com.ciplamed.ciplamedlanding;

import java.io.IOException;
import java.util.ArrayList;

import com.ciplamed.ciplamedlanding.AllCiplaProducts.MyAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class DrugClass extends ListActivity{
	
	String dtype;
	ArrayList<String> p=new ArrayList<String>();
	SQLiteDatabase db;
	Button b1;
	TextView tv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drugclass);
		
		Bundle extras=getIntent().getExtras();
		dtype=extras.getString("drug_type");
		//###############################
		DataBaseHelper myDbHelper = new DataBaseHelper(this);
	    
	        try {
	        	 
	        	myDbHelper.createDataBase();
	 
	 	} catch (IOException ioe) {
	 
	 		throw new Error("Unable to create database");
	 
	 	}
	 
	 	try {
	 
	 		myDbHelper.openDataBase();
	 
	 	}catch(SQLException sqle){
	 
	 		throw sqle;
	 
	 	}
	 	 db=openOrCreateDatabase("Drugs", MODE_PRIVATE, null);
	     Cursor cur = db.rawQuery("select distinct drugclass as class from drugs where typeofdrugs='"+dtype+"'", null);
	     int n=cur.getCount();
	     cur.moveToFirst();
	     Log.d("Anish", String.valueOf(n));
	 
	     for(int i=1;i<=n;i++)
	     {
	     	//p.add();
	    	 Log.d("Anish", String.valueOf(i));
	     	p.add(cur.getString(cur.getColumnIndex("class")));
	     	Log.d("Anish",cur.getString(cur.getColumnIndex("class")));
	    	cur.moveToNext();
	     	
	     	//i++;
	     }
	    cur.close();
	    ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.antibactheader, lv, false);
		lv.addHeaderView(header, null, false);
		tv1=(TextView) findViewById(R.id.titletv);
		tv1.setText("Pocket Guide For Antimicrobial Dosing");
		b1=(Button) findViewById(R.id.button2);
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(DrugClass.this, Main.class);
				startActivity(i);
			}
		});
	   setListAdapter(new MyAdapter(this, R.layout.list_prod,R.id.prodname, p));
       // TextView textview = new TextView(this);
        //textview.setText("This is the Songs tab");
        //setContentView(textview)
    }
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	 // TODO Auto-generated method stub
	 super.onListItemClick(l, v, position, id);
	 db.close(); 
	 String selection = l.getItemAtPosition(position).toString();
	 Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
	 Intent i=new Intent(DrugClass.this, DrugSubClass.class);
	 i.putExtra("drug_type",dtype);
	 i.putExtra("drug_class",l.getItemAtPosition(position).toString());
	 startActivity(i);
    }
    public class MyAdapter extends ArrayAdapter<String>{

    	public MyAdapter(Context context, int resource, int textViewResourceId,
    			ArrayList<String> p) {
			super(context, resource, textViewResourceId, p);
			// TODO Auto-generated constructor stub
		}

		public View getView(int position,View convertView, ViewGroup parent) {
			
			LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
			View row=inflater.inflate(R.layout.list_prod, parent, false);
			TextView title=(TextView) row.findViewById(R.id.prodname);
			title.setText(p.get(position));
			
			return super.getView(position, convertView, parent);
		}
}

}

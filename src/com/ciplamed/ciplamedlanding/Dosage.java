package com.ciplamed.ciplamedlanding;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Dosage extends Activity{
	
	String dtype,dclass,dsubclass,dname,cc;
	SQLiteDatabase db;
	TextView name,drugclass,subclass,crcl,ud,da,type;
	TextView tv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dosage);
		Bundle extras=getIntent().getExtras();
		dclass=extras.getString("drug_class");
		dtype=extras.getString("drug_type");
		dsubclass=extras.getString("drug_subclass");
		dname=extras.getString("drug_name");
		cc=extras.getString("crcl");
		tv1=(TextView) findViewById(R.id.titletv);
		tv1.setText("Pocket Guide For Antimicrobial Dosing");
		///////////////////////////////////////////////
		type=(TextView) findViewById(R.id.textView1);
		drugclass=(TextView) findViewById(R.id.textView2);
		subclass=(TextView) findViewById(R.id.textView3);
		name=(TextView) findViewById(R.id.textView4);
		crcl=(TextView) findViewById(R.id.textView5);
		ud=(TextView) findViewById(R.id.textView6);
		da=(TextView) findViewById(R.id.textView7);
		/////////////////////////////////////////////////
		
		type.setText("Drug Type: "+dtype);
		drugclass.setText("Drug Class: "+dclass);
		subclass.setText("Drug Subclass: "+dsubclass);
		name.setText("Drug Name: "+dname);
		crcl.setText("Creatitnine Clearance: "+cc);
		
		db=openOrCreateDatabase("Drugs", MODE_PRIVATE, null);
	     Cursor cur = db.rawQuery("select usualdosage,doseadjusments from drugs where drugclass='"+dclass+"' and typeofdrugs='"+dtype+"' and drugsubclass='"+dsubclass+"' and drugname='"+dname+"' and min<='"+30+"' and max>='"+30+"'", null);
	     int n=cur.getCount();
	     cur.moveToFirst();
	     Log.d("Anish", String.valueOf(n));
	 
	     for(int i=1;i<=n;i++)
	     {
	     	//p.add();
	    	 Log.d("Anish", String.valueOf(i));
	     	//p.add(cur.getString(cur.getColumnIndex("name")));
	     	Log.d("Anish",cur.getString(cur.getColumnIndex("usualdosage")));
	     	ud.setText("Usual Dosage: "+cur.getString(cur.getColumnIndex("usualdosage")));
	     	da.setText("Dose Adjusments: "+cur.getString(cur.getColumnIndex("doseadjusments")));
	     	Log.d("Anish",cur.getString(cur.getColumnIndex("doseadjusments")));
	    	cur.moveToNext();
	     	
	     	//i++;
	     }
	    cur.close();
	   db.close(); 
	}

}

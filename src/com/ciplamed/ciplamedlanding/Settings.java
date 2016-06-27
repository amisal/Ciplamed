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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class Settings extends Activity{
	
	String sel_spec,sel_size;
	SQLiteDatabase db;
	ArrayList<String> p=new ArrayList<String>();
	ArrayList<String> p1=new ArrayList<String>();
	public static String filename="Ciplamedprefs";
	SharedPreferences someData;
	String dataReturned,spec;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 someData=getSharedPreferences(filename, 0);
		    dataReturned=someData.getString("selected_spec", "0");
		    spec=dataReturned;
		 db=openOrCreateDatabase("ciplamedDB", MODE_PRIVATE, null);
	     Cursor cur = db.rawQuery("SELECT Distinct speciality from product_index where speciality <> 'General Medicine'  order by speciality", null);
	     cur.moveToFirst();
	     int n=cur.getCount();
	     p.add("Select Speciality");
	     p.add("General Medicine");
	     //int i=0;
	     for(int i=0;i<n-1;i++)
	     {
	     	//p.add();
	     	cur.moveToNext();
	     	p.add(cur.getString(cur.getColumnIndex("speciality")));
	     	Log.d("Anish",cur.getString(cur.getColumnIndex("speciality")));
	     	
	     	//i++;
	     }
	    cur.close();
	   db.close(); 
		Button home=(Button) findViewById(R.id.button1);
		//Button search=(Button) findViewById(R.id.zoom);
		home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Settings.this, Main.class);
				startActivity(i);
				
			}
		});    
		Spinner state=(Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> stateadapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,p);
		stateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    state.setAdapter(stateadapter);
	    int i = stateadapter.getPosition(spec);
	    state.setSelection(i);
	    state.setOnItemSelectedListener(new MyOnItemSelectedListener(Settings.this, android.R.layout.simple_spinner_item,p));
	    p1.add("Select Size");
	    p1.add("12");
	    p1.add("15");
	    p1.add("20");
	    Spinner size=(Spinner) findViewById(R.id.spinner2);
	    ArrayAdapter<String> sizeadapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,p1);
		sizeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    size.setAdapter(sizeadapter);
	    int i2 = sizeadapter.getPosition(someData.getString("text_size", "0"));
	    size.setSelection(i2);
	    size.setOnItemSelectedListener(new MyOnItemSelectedListener2(Settings.this, android.R.layout.simple_spinner_item,p1));
	}
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public MyOnItemSelectedListener(Settings settings,
				int simpleSpinnerItem, ArrayList<String> p) {
			// TODO Auto-generated constructor stub
		}


		public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(pos!=0)
	    	{
	    		//Toast.makeText(parent.getContext(), "You have selected " +
	    		//parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    		sel_spec=parent.getItemAtPosition(pos).toString();
	    		sel_size=parent.getItemAtPosition(pos).toString();
	    		SharedPreferences someData=getSharedPreferences("Ciplamedprefs", 0);
                SharedPreferences.Editor editor= someData.edit();
 				//editor.putString("text_size", sel_spec);
 				editor.putString("selected_spec",sel_spec);
 				editor.commit();
	    		//return sel_sex;
	    	}
	    }
	    	

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    }
	}

	public class MyOnItemSelectedListener2 implements OnItemSelectedListener {

	    public MyOnItemSelectedListener2(Settings settings,
				int simpleSpinnerItem, ArrayList<String> p) {
			// TODO Auto-generated constructor stub
		}


		public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(pos!=0)
	    	{
	    		//Toast.makeText(parent.getContext(), "You have selected " +
	    		//parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    		sel_size=parent.getItemAtPosition(pos).toString();
	    		SharedPreferences someData=getSharedPreferences("Ciplamedprefs", 0);
                SharedPreferences.Editor editor= someData.edit();
 				
 				editor.putString("text_size", sel_size);
 				//editor.putString("doctor_spec",sel_spec);
 				editor.commit();
	    		//return sel_sex;
	    	}
	    }
	    	

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    }
	}

}

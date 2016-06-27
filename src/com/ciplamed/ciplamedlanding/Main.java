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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class Main extends Activity{
	
	Button b1;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	Button b6;
	Button b7;
	Button b8;
	Button b9;
	Button b10;
	TextView name;
	public static String filename="Ciplamedprefs";
	SharedPreferences someData;
	String fullname;
	ArrayList<String> prod=new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ScrollView sv=(ScrollView) findViewById(R.id.scrollView1);
		sv.setVerticalScrollBarEnabled(true);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		someData=getSharedPreferences(filename, 0);
		fullname=someData.getString("fullname", "0");
		b1=(Button) findViewById(R.id.button1);
		b2=(Button) findViewById(R.id.button2);
		b3=(Button) findViewById(R.id.button3);
		b4=(Button) findViewById(R.id.button4);
		b5=(Button) findViewById(R.id.button5);
		b6=(Button) findViewById(R.id.button6);
		b7=(Button) findViewById(R.id.button7);

		b9=(Button) findViewById(R.id.button9);
		b10=(Button) findViewById(R.id.button10);
		name=(TextView) findViewById(R.id.textView1);
		name.setText("Welcome "+fullname);
		
		   SQLiteDatabase db=openOrCreateDatabase("ciplamedDB", MODE_PRIVATE, null);
	        Cursor cur = db.rawQuery("SELECT title from product_index;", null);
	        cur.moveToFirst();
	        int n=cur.getCount();
	        
	        //int i=0;
	        for(int i=0;i<n-1;i++)
	        {
	        	//p.add();
	        	cur.moveToNext();
	        	prod.add(cur.getString(cur.getColumnIndex("title")));
	        	Log.d("Anish",cur.getString(cur.getColumnIndex("title")));
	        	
	        	//i++;
	        }
	    
	        db.close();
	       
	  
	       	    	 
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_dropdown_item_1line,  prod);
	        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
	        textView.setAdapter(adapter);
	        textView.setFocusable(false);
	        textView.setOnTouchListener(new View.OnTouchListener() {

		        public boolean onTouch(View v, MotionEvent event) {

		        	textView.setFocusable(true);
		        	textView.setFocusableInTouchMode(true);
		            return false;
		        }
		    });
		
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Main.this, Newslist.class);
				startActivity(i);
				
			}
		});
		b2.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i2=new Intent(Main.this, WebcastMain.class);
					startActivity(i2);
					
				}
			});
		
		b3.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i3=new Intent(Main.this, GuidelinesList.class);
				startActivity(i3);
				
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i4=new Intent(Main.this, Calenderlist.class);
				startActivity(i4);
				
			}
		});
		b5.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i5=new Intent(Main.this, ClinicalTools.class);
				startActivity(i5);
				
			}
		});
		b6.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i6=new Intent(Main.this, PubmedLanding.class);
				startActivity(i6);
				
			}
		});
		b7.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i7=new Intent(Main.this, Product.class);
				startActivity(i7);
			}
		});
	
		b9.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i9=new Intent(Main.this, Settings.class);
				startActivity(i9);
				
			}
		});
		b10.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(textView.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(Main.this, "Please select product",Toast.LENGTH_SHORT).show();
				}
				else
				{
				
					Intent i10=new Intent(Main.this, ProductPrescribtion.class);
					i10.putExtra("product", textView.getText().toString());
					startActivity(i10);
				}
				
			}
		});
	} 

}

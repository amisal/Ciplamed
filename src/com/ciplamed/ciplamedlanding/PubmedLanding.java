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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class PubmedLanding  extends Activity{
	
	int id;
	EditText ed,ed1,ed2;
	RelativeLayout l2;
	Button b,b1,b2;
	int count=0;
	String sel_filter_And_Or,sel_filter_And_Or1;
	String[] andOr=new String[]{"AND","OR"}; 
	String citations,title,authors,sel_filter_val, url,sel_filter_val1,sel_filter_val2;
	String[] filter_items=new String[]{"Any Field","Author","First Author","Last Author","Full Author Name","Corporate Author","Author Affilliation","Title","Title/Abstract","Text Words","Journal","Volume","Issue","Page Number","Keyword(Mesh)","Langauge","ISSN","PMID"};
	String[] filter_items_values=new String[]{"","[au]","[1au]","[LASTAU]","[FAV]","[CN]","[ad]","[ti]","[tiab]","[tw]","[ta]","[vi]","[ip]","[pg]","[mh]","[la]","[ta]","[pmid]"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pubmedlanding);
		loadScreen();
	}

	private void loadScreen() {
		// TODO Auto-generated method stub
		l2=(RelativeLayout) findViewById(R.id.linearlayout2);
		
		
		
		Spinner spinner= new Spinner(this);
		RelativeLayout.LayoutParams sp_lp=new RelativeLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT);
		sp_lp.setMargins(45, 20, 0, 0);
		
		spinner.setLayoutParams(sp_lp);
		spinner.setBackgroundResource(R.drawable.spec_spinner);
		//spinner.setPadding(2, 0, 0, 0);
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,filter_items);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter); 
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		l2.addView(spinner);
		
		ed=new EditText(this);
		RelativeLayout.LayoutParams ed_lp=new RelativeLayout.LayoutParams(400,LayoutParams.WRAP_CONTENT);
		ed_lp.setMargins(45, 100, 0, 0);
		ed_lp.addRule(RelativeLayout.ALIGN_BOTTOM, spinner.getId());
		ed.setLayoutParams(ed_lp);
		ed.setText("");
		ed.setFocusable(false);
		ed.setOnTouchListener(new View.OnTouchListener() {

	        public boolean onTouch(View v, MotionEvent event) {

	            ed.setFocusable(true);
	            ed.setFocusableInTouchMode(true);
	            return false;
	        }
	    });
		l2.addView(ed);
		
		
		Button add=new Button(this);
		RelativeLayout.LayoutParams add_lp=new RelativeLayout.LayoutParams(100, LayoutParams.WRAP_CONTENT);
		add_lp.addRule(RelativeLayout.ALIGN_BOTTOM, ed.getId());
		add_lp.setMargins(45, 175, 0, 0);
		add.setLayoutParams(add_lp);
		add.setText("Add");
		add.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Log.d("Anish", "ADD");
				count++;
				loadScreenTwo();
				
			}
		});
		l2.addView(add);
	
		
		
		b=new Button(this);
		RelativeLayout.LayoutParams b_lp=new RelativeLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT);
		b_lp.addRule(RelativeLayout.ALIGN_BOTTOM,ed.getId());
		b_lp.setMargins(45, 250, 0, 0);
		b.setLayoutParams(b_lp);
		b.setBackgroundResource(R.drawable.calculate_button);
		b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	new LoadData().execute();
				url=generateURL();
				Intent i1 = new Intent(PubmedLanding.this, PubmedList.class);
				i1.putExtra("payload", url);
				startActivity(i1);
			}
		});
		l2.addView(b);
		
		
		
	}

	protected void loadScreenTwo() {
		// TODO Auto-generated method stub
		l2.removeView(b);
		
		Spinner spinner1= new Spinner(this);
		RelativeLayout.LayoutParams sp_lp1=new RelativeLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT);
		sp_lp1.setMargins(45, 250, 0, 0);
		
		spinner1.setLayoutParams(sp_lp1);
		spinner1.setBackgroundResource(R.drawable.spec_spinner);
		//spinner.setPadding(2, 0, 0, 0);
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,andOr);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner1.setAdapter(adapter); 
        spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener1());
		l2.addView(spinner1);
		
		Spinner spinner2= new Spinner(this);
		RelativeLayout.LayoutParams sp_lp2=new RelativeLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT);
		sp_lp2.setMargins(45, 330, 0, 0);
		
		spinner2.setLayoutParams(sp_lp2);
		spinner2.setBackgroundResource(R.drawable.spec_spinner);
		//spinner.setPadding(2, 0, 0, 0);
		ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,filter_items);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner2.setAdapter(adapter2); 
        spinner2.setOnItemSelectedListener(new MyOnItemSelectedListener2());
		l2.addView(spinner2);
		
		
		ed1=new EditText(this);
		RelativeLayout.LayoutParams ed_lp1=new RelativeLayout.LayoutParams(400,LayoutParams.WRAP_CONTENT);
		ed_lp1.setMargins(45, 410, 0, 0);
		ed_lp1.addRule(RelativeLayout.ALIGN_BOTTOM, spinner1.getId());
		ed1.setLayoutParams(ed_lp1);
		ed1.setText("");
		ed1.setFocusable(false);
		ed1.setOnTouchListener(new View.OnTouchListener() {

	        public boolean onTouch(View v, MotionEvent event) {

	            ed1.setFocusable(true);
	            ed1.setFocusableInTouchMode(true);
	            return false;
	        }
	    });
		l2.addView(ed1);
		
		
		Button add1=new Button(this);
		RelativeLayout.LayoutParams add_lp1=new RelativeLayout.LayoutParams(100, LayoutParams.WRAP_CONTENT);
		add_lp1.addRule(RelativeLayout.ALIGN_BOTTOM, ed.getId());
		add_lp1.setMargins(45, 490, 0, 0);
		add1.setLayoutParams(add_lp1);
		add1.setText("Add");
		add1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Log.d("Anish", "ADD");
				count++;
				loadScreenThree();
				
				
			}
		});
		l2.addView(add1);
	
		
		
		b1=new Button(this);
		RelativeLayout.LayoutParams b_lp1=new RelativeLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT);
		b_lp1.addRule(RelativeLayout.ALIGN_BOTTOM,ed1.getId());
		b_lp1.setMargins(45, 570, 0, 0);
		b1.setLayoutParams(b_lp1);
		b1.setBackgroundResource(R.drawable.calculate_button);
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	new LoadData().execute();
				url=generateURL();
				Intent i1 = new Intent(PubmedLanding.this, PubmedList.class);
				i1.putExtra("payload", url);
				startActivity(i1);
			}
		});
		l2.addView(b1);
		
		
		
		
	}

	protected void loadScreenThree() {
		// TODO Auto-generated method stub
		
		l2.removeView(b1);
		
		Spinner spinner3= new Spinner(this);
		RelativeLayout.LayoutParams sp_lp3=new RelativeLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT);
		sp_lp3.setMargins(45, 570, 0, 0);
		
		spinner3.setLayoutParams(sp_lp3);
		spinner3.setBackgroundResource(R.drawable.spec_spinner);
		//spinner.setPadding(2, 0, 0, 0);
		ArrayAdapter<String> adapter3 =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,andOr);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner3.setAdapter(adapter3); 
        spinner3.setOnItemSelectedListener(new MyOnItemSelectedListener3());
		l2.addView(spinner3);
		
		Spinner spinner4= new Spinner(this);
		RelativeLayout.LayoutParams sp_lp4=new RelativeLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT);
		sp_lp4.setMargins(45, 650, 0, 0);
		
		spinner4.setLayoutParams(sp_lp4);
		spinner4.setBackgroundResource(R.drawable.spec_spinner);
		//spinner.setPadding(2, 0, 0, 0);
		ArrayAdapter<String> adapter4 =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,filter_items);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner4.setAdapter(adapter4); 
        spinner4.setOnItemSelectedListener(new MyOnItemSelectedListener4());
		l2.addView(spinner4);
		
		
		ed2=new EditText(this);
		RelativeLayout.LayoutParams ed_lp2=new RelativeLayout.LayoutParams(400,LayoutParams.WRAP_CONTENT);
		ed_lp2.setMargins(45, 730, 0, 0);
		ed_lp2.addRule(RelativeLayout.ALIGN_BOTTOM, spinner4.getId());
		ed2.setLayoutParams(ed_lp2);
		ed2.setText("");
		ed2.setFocusable(false);
		ed2.setOnTouchListener(new View.OnTouchListener() {

	        public boolean onTouch(View v, MotionEvent event) {

	            ed2.setFocusable(true);
	            ed2.setFocusableInTouchMode(true);
	            return false;
	        }
	    });
		l2.addView(ed2);
		
		
		
	
		
		
		b2=new Button(this);
		RelativeLayout.LayoutParams b_lp2=new RelativeLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT);
		b_lp2.addRule(RelativeLayout.ALIGN_BOTTOM,ed2.getId());
		b_lp2.setMargins(45, 830, 0, 0);
		b2.setLayoutParams(b_lp2);
		b2.setBackgroundResource(R.drawable.calculate_button);
		b2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	new LoadData().execute();
				//url=generateURL();
				url=generateURL();
				Intent i1 = new Intent(PubmedLanding.this, PubmedList.class);
				i1.putExtra("payload", url);
				startActivity(i1);
			}
		});
		l2.addView(b2);
		
		
	}

	public  String generateURL() {
		// TODO Auto-generated method stub
		
	
		switch (count)
		{
			case 0:
				Log.d("Anish",String.valueOf(count));
				
				String partURL=ed.getText().toString().replaceAll(" ", "%20")+sel_filter_val;
				
				Log.d("Anish", partURL);
				
				return partURL;
				
		case 1:
				Log.d("Anish",String.valueOf(count));
				
				String partURL1=ed.getText().toString().replaceAll(" ", "%20")+sel_filter_val+"%20"+sel_filter_And_Or+"%20"+ed1.getText().toString().replaceAll(" ", "%20")+sel_filter_val1;
				
				Log.d("Anish", partURL1);
				
				return partURL1;
				
		case 2:
				Log.d("Anish",String.valueOf(count));
				
				String partURL2=ed.getText().toString().replaceAll(" ", "%20")+sel_filter_val+"%20"+sel_filter_And_Or+"%20"+ed1.getText().toString().replaceAll(" ", "%20")+sel_filter_val1+"%20"+sel_filter_And_Or1+"%20"+ed2.getText().toString().replaceAll(" ", "%20")+sel_filter_val2;
				
				//partURL2.replace(" ", "%20");
				
				Log.d("Anish", partURL2);
				
				return partURL2;
		
		}
		return null;
		
		
		//return partURL;
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(pos!=0)
	    	{
	    		
	    		sel_filter_val=filter_items_values[pos];
	    		Toast.makeText(parent.getContext(), sel_filter_val, Toast.LENGTH_LONG).show();
	    		
	    		
	    		//return sel_sex;
	    	}
	    }
	    	

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    	
	    }
	}
	
	public class MyOnItemSelectedListener1 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(pos!=0)
	    	{
	    		
	    		sel_filter_And_Or="OR";
	    		Toast.makeText(parent.getContext(),sel_filter_And_Or, Toast.LENGTH_LONG).show();
	    		
	    		
	    		//return sel_sex;
	    	}
	    	else
	    	{
	    		sel_filter_And_Or="AND";
	    		Toast.makeText(parent.getContext(),sel_filter_And_Or, Toast.LENGTH_LONG).show();
	    		
	    	}
	    }
	    	

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    	
	    }
	}
	
	public class MyOnItemSelectedListener2 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(pos!=0)
	    	{
	    		
	    		sel_filter_val1=filter_items_values[pos];
	    		Toast.makeText(parent.getContext(), sel_filter_val1, Toast.LENGTH_LONG).show();
	    		
	    		
	    		//return sel_sex;
	    	}
	    }
	    	

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    	
	    }
	}
	
	public class MyOnItemSelectedListener3 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(pos!=0)
	    	{
	    		
	    		sel_filter_And_Or1="OR";
	    		Toast.makeText(parent.getContext(),sel_filter_And_Or, Toast.LENGTH_LONG).show();
	    		
	    		
	    		//return sel_sex;
	    	}
	    	else
	    	{
	    		sel_filter_And_Or1="AND";
	    		Toast.makeText(parent.getContext(),sel_filter_And_Or, Toast.LENGTH_LONG).show();
	    		
	    	}
	    }
	    	

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    	
	    }
	}
	
	public class MyOnItemSelectedListener4 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(pos!=0)
	    	{
	    		
	    		sel_filter_val2=filter_items_values[pos];
	    		Toast.makeText(parent.getContext(), sel_filter_val2, Toast.LENGTH_LONG).show();
	    		
	    		
	    		//return sel_sex;
	    	}
	    }
	    	

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    	
	    }
	}
	
}

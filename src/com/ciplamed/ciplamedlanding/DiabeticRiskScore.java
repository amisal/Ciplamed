package com.ciplamed.ciplamedlanding;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class DiabeticRiskScore extends Activity{
	
	String[] age=new String[]{"Choose your age","Less than 35","Between 35-49","More than 50"};
	String[] wc=new String[]{"Choose your waist circumference","Waist <  80 cm [female], < 90 cm [male]","Waist <  80-89 cm [female], < 90-99 cm [male]","Waist >= 90 cm [female], >= 100 cm [male]"};
	String[] pa=new String[]{"Type of physical activity","Vigorous exercise [regular] or strenuous [manual] work at home/work","Moderate exercise [regular] or moderate physical activity at home/work","Mild exercise [regular] or mild physical activity at work at home/work","No exercise and sedentary activities at home/work"};
	String[] fh=new String[]{"Family history of diabetes","No diabetes in parents","One parent is diabetic","Both parents are diabetic"};
	String sel_age="Choose your age";
	String sel_wc="Choose your waist circumference";
	String sel_pa="Type of physical activity";
	String sel_fh="Family history of diabetes";
	int score,score1,score2,score3,finalscore,riskscore;
	int score_ini=0;
	String risk,treatment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diabeticriskscore);
		ScrollView sv=(ScrollView) findViewById(R.id.scrollView1);
		sv.setVerticalScrollBarEnabled(true);
		TextView title=(TextView) findViewById(R.id.textView1);
		title.setText("Indian Diabetic Risk Score");
		Spinner spinner1=(Spinner) findViewById(R.id.spinner1);
		  SpinnerAdapter adapter1 = new SpinnerAdapter(this,
		            android.R.layout.simple_spinner_item, age);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1); 
        spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener1());
        ///////////////////////////////////////////////////////////////////
        Spinner spinner2=(Spinner) findViewById(R.id.spinner2);
        SpinnerAdapter adapter2 = new SpinnerAdapter(this,
	            android.R.layout.simple_spinner_item, wc);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2); 
        spinner2.setOnItemSelectedListener(new MyOnItemSelectedListener2());
        ///////////////////////////////////////////////////////////////////
        Spinner spinner3=(Spinner) findViewById(R.id.spinner3);
        SpinnerAdapter adapter3 = new SpinnerAdapter(this,
	            android.R.layout.simple_spinner_item, pa);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3); 
        spinner3.setOnItemSelectedListener(new MyOnItemSelectedListener3());
        ///////////////////////////////////////////////////////////////////
        Spinner spinner4=(Spinner) findViewById(R.id.spinner4);
        SpinnerAdapter adapter4 = new SpinnerAdapter(this,
	            android.R.layout.simple_spinner_item, fh);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4); 
        spinner4.setOnItemSelectedListener(new MyOnItemSelectedListener4());
        ///////////////////////////////////////////////////////////////////
        Button b1=(Button) findViewById(R.id.button1);
        b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Log.d("Anish", sel_age);
				Log.d("Anish", sel_wc);
				Log.d("Anish", sel_pa);
				Log.d("Anish", sel_fh);
				
				score=calculate();
				Log.d("Anish", String.valueOf(score));
				score1=calculate1();
				Log.d("Anish", String.valueOf(score1));
				score2=calculate2();
				Log.d("Anish", String.valueOf(score2));
				finalscore=calculate3();
				riskscore=finalscore+score+score1+score2;
				Log.d("Anish", String.valueOf(finalscore));
				Log.d("Anish", String.valueOf(finalscore+score+score1+score2));
				if(riskscore > 0 && riskscore < 30)
				{
					risk="LOW";
					treatment="";
					//treatment="Treatment: Oral Glucose Tolerance Test(OGTT) is recommended to rule out diabetes. If this is not possible then atleast random blood sugar or fasting blood sugar should be done";
				}
				else if(riskscore > 30 && riskscore < 50)
				{
					risk="MODERATE";
					//treatment="";
					treatment="Treatment: Oral Glucose Tolerance Test(OGTT) is recommended to rule out diabetes. If this is not possible then atleast random blood sugar or fasting blood sugar should be done";
					
					
				}
				else if(riskscore >= 60 )
				{
					risk="HIGH RISK";
					treatment="Treatment: Oral Glucose Tolerance Test(OGTT) is recommended to rule out diabetes. If this is not possible then atleast random blood sugar or fasting blood sugar should be done";
					
				}
				 AlertDialog.Builder builder= new AlertDialog.Builder(DiabeticRiskScore.this);
		    	 builder.setMessage("Youe have '"+ risk+"' risk of having Diabetes\n" +treatment );
		    	 builder.setCancelable(false);
		    	 builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
					
					@SuppressLint("SetJavaScriptEnabled")
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//text.setText("You clicked Update now");
						//webservice(0);
						dialog.cancel();
						
					}
				});
		    	 builder.setNegativeButton("Main Menu", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
						Intent i=new Intent(DiabeticRiskScore.this, Main.class);
						startActivity(i);
						//text.setText("You clicked Update later");
					}
				});
		    	 AlertDialog alert=builder.create();
		    	 alert.show();
				
			}
		});
        
	}
	public class MyOnItemSelectedListener1 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(pos!=0)
	    	{
	    		//Toast.makeText(parent.getContext(), "You have selected " +
	    		//parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    		sel_age=parent.getItemAtPosition(pos).toString();
	    		//return sel_sex;
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
	    		//Toast.makeText(parent.getContext(), "You have selected " +
	    		//parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    		sel_wc=parent.getItemAtPosition(pos).toString();
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
	    		//Toast.makeText(parent.getContext(), "You have selected " +
	    		//parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    		sel_pa=parent.getItemAtPosition(pos).toString();
	    		//return sel_sex;
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
	    		//Toast.makeText(parent.getContext(), "You have selected " +
	    		//parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    		sel_fh=parent.getItemAtPosition(pos).toString();
	    		//return sel_sex;
	    	}
	    }
	    	

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    	
	    }
	}
	
	public class SpinnerAdapter extends ArrayAdapter<String> {
	     Context context;
	     String[] items = new String[] {};
	     private int textSize=15; //initial default textsize  (might be a bit big)

	        public SpinnerAdapter(final Context context, final int textViewResourceId, final String[] objects) {
	            super(context, textViewResourceId, objects);
	            this.items = objects;
	            this.context = context;

	        }
	        public SpinnerAdapter(final Context context, final int resource, final int textViewResourceId ){
	            super(context, resource, textViewResourceId);
	            this.items = context.getResources().getStringArray(resource);

	            Toast.makeText(context, String.valueOf(this.getSpinnerTextSize()), Toast.LENGTH_LONG).show();
	        }


	        @Override
	        public View getDropDownView(int position, View convertView,
	                ViewGroup parent) {

	            if (convertView == null) {
	                LayoutInflater inflater = LayoutInflater.from(context);
	                convertView = inflater.inflate(
	                        android.R.layout.simple_spinner_dropdown_item, parent, false);
	            }

	            TextView tv = (TextView) convertView
	                    .findViewById(android.R.id.text1);
	            tv.setText(items[position]);
	            //tv.setTextColor(Color.BLUE);
	            tv.setTextSize(15);
	            tv.setSingleLine(false);
	            
	            return convertView;
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            if (convertView == null) {
	                LayoutInflater inflater = LayoutInflater.from(context);
	                convertView = inflater.inflate(
	                        android.R.layout.simple_spinner_item, parent, false);
	            }

	            // android.R.id.text1 is default text view in resource of the android.
	            // android.R.layout.simple_spinner_item is default layout in resources of android.

	            TextView tv = (TextView) convertView
	                    .findViewById(android.R.id.text1);
	            tv.setText(items[position]);
	            //tv.setTextColor(Color.BLUE);
	            tv.setTextSize(textSize);
	            return convertView;
	        }

	            //set the textsize
	        public void setSpinnerTextSize(int size){

	            textSize= size;
	        }

	            //return the textsize
	        public int getSpinnerTextSize(){
	            return textSize;
	        }

	}
	
	public int calculate()
	{
		int sc=0;
		
		if(sel_age.contentEquals("Less than 35"))
		{
			sc=0;
		}
		else if(sel_age.contentEquals("Between 35-49"))
		{
			sc=10;
		}
		else if(sel_age.contentEquals("More than 50"))
		{
			sc=20;
		}
		
		return sc;
		
	}
	
	public int calculate1()
	{
		int sc1 = 0;
		
		if(sel_wc.contentEquals("Waist <  80 cm [female], < 90 cm [male]"))
		{
			sc1=0;
		}
		else if(sel_wc.contentEquals("Waist <  80-89 cm [female], < 90-99 cm [male]"))
		{
			sc1=10;
		}
		else if(sel_wc.contentEquals("Waist >= 90 cm [female], >= 100 cm [male]"))
		{
			sc1=20;
		}
		
		return sc1;
		
	}
	public int calculate2()
	{
		int sc2 = 0;
		
		if(sel_pa.contentEquals("Vigorous exercise [regular] or strenuous [manual] work at home/work"))
		{
			sc2=0;
		}
		else if(sel_pa.contentEquals("Moderate exercise [regular] or moderate physical activity at home/work"))
		{
			sc2=10;
		}
		else if(sel_pa.contentEquals("Mild exercise [regular] or mild physical activity at work at home/work"))
		{
			sc2=20;
		}
		else if(sel_pa.contentEquals("No exercise and sedentary activities at home/work"))
		{
			sc2=30;
		}
		
		return sc2;
		
	}
	
	public int calculate3()
	{
		int sc3 = 0;
		
		if(sel_fh.contentEquals("No diabetes in parents"))
		{
			sc3=0;
		}
		else if(sel_fh.contentEquals("One parent is diabetic"))
		{
			sc3=10;
		}
		else if(sel_fh.contentEquals("Both parents are diabetic"))
		{
			sc3=20;
		}
		
		
		return sc3;
		
	}
}

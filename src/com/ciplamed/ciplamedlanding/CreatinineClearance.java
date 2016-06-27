package com.ciplamed.ciplamedlanding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;


public class CreatinineClearance extends Activity {
	int weight,age;
    float sc;
    String gender;
    float cc;
    String dtype,dclass,dsubclass,dname;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc);
        ScrollView sv=(ScrollView) findViewById(R.id.scrollView1);
		sv.setVerticalScrollBarEnabled(true);
    	Bundle extras=getIntent().getExtras();
		dclass=extras.getString("drug_class");
		dtype=extras.getString("drug_type");
		dsubclass=extras.getString("drug_subclass");
		dname=extras.getString("drug_name");
        Button bb= (Button) findViewById(R.id.button1);
        final RadioButton male = (RadioButton) findViewById(R.id.radio0);
        final RadioButton female = (RadioButton) findViewById(R.id.radio1);
        final EditText et1 =(EditText) findViewById(R.id.editText1);
      //  final EditText et2 =(EditText) findViewById(R.id.editText2);
        final EditText et3 =(EditText) findViewById(R.id.editText3);
        final EditText et4 =(EditText) findViewById(R.id.editText4);
        
     
   //et2.setEnabled(false);
   //////
        male.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(male.isChecked())
			        {
			        	//et2.setText("Male");
					gender="Male";
			        }
			        
			}
		});
        /////////////////////
 female.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(female.isChecked())
			        {
			        	//et2.setText("Female");
					gender="Female";
			        }
			        
			}
		});
 //////////////////////////////
 bb.setOnClickListener(new OnClickListener()
{
	
	public void onClick(View arg0) 
	{
		////////////////////////
	
		if(female.isChecked() || male.isChecked())
		{
			if(et1.getText().toString().equals(""))
			{
				Toast.makeText(CreatinineClearance.this,"Please enter weight",Toast.LENGTH_SHORT).show(); 
			}
			else
			{
				weight=Integer.valueOf(et1.getText().toString());
				 if(weight<1 || weight>600)
				 {
					 Toast.makeText(CreatinineClearance.this,"Please enter valid weight",Toast.LENGTH_SHORT).show();
					 
				 }
				 else
				 {
					 if(weight<37)
					 {
			  Toast.makeText(CreatinineClearance.this,"Weight should not be less than 37",Toast.LENGTH_SHORT).show();
					 }
					 else{
					 if(et3.getText().toString().equals(""))
					 {
						 Toast.makeText(CreatinineClearance.this,"Please enter age",Toast.LENGTH_SHORT).show();
					 }
					 else
					 {
						 age=Integer.valueOf(et3.getText().toString());
						 if(age<1 || age>150)
						 {
							 Toast.makeText(CreatinineClearance.this,"Please enter valid age",Toast.LENGTH_SHORT).show(); 
						 }
						 else
						 {
							 if(et4.getText().toString().equals(""))
							 {
								 Toast.makeText(CreatinineClearance.this,"Please enter serum creatinine",Toast.LENGTH_SHORT).show(); 
							 }
							 else
							 {
								 sc=Float.valueOf(et4.getText().toString());
								 if(sc<0.0 || sc>20.0)
								 {
									 Toast.makeText(CreatinineClearance.this,"Please enter valid serum creatinine",Toast.LENGTH_SHORT).show();  
								 }
								 else
								 {
									 
								String s1 = String.valueOf(weight);
								String s2 = gender;
								String s3 = String.valueOf(age);
								String s4 = String.valueOf(sc);
								 if (s2.equalsIgnoreCase("Male"))
							   	 {
							   		 cc=(float) ((140.0-age)*weight/(72.0*sc));
							   		
							   	 }
							   	 else
								 {
							   		 cc=(float) ((140.0-age)*weight*0.85/(72.0*sc));
							   		
							   	 }
								 Log.d("Anish", String.valueOf(cc));
								Intent i = new Intent(CreatinineClearance.this, Dosage.class);
								i.putExtra("drug_type",dtype);
								i.putExtra("drug_class",dclass);
								i.putExtra("drug_subclass",dsubclass);
								i.putExtra("drug_name",dname);
								i.putExtra("crcl",String.valueOf(cc));
								startActivity(i); 
								 }
							 }
						 }
					 }
				 }
			}
			}
		}
		else
		{
			Toast.makeText(CreatinineClearance.this,"Please select gender",Toast.LENGTH_SHORT).show(); 
		}
			
	}
});

 
}
}

package com.ciplamed.ciplamedlanding;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;



public class FatCalculator extends Activity{
	
	EditText et_age,et_height,et_weight;
	Button b1;
	double age,ht,wt,bmi,body_fat;
	String[] sex=new String[]{"Select your sex","Male","Female"};
	String sel_sex="Select your sex";
	String range;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fatcalculator);
		ScrollView sv=(ScrollView) findViewById(R.id.scrollView1);
		sv.setVerticalScrollBarEnabled(true);
		TextView title=(TextView) findViewById(R.id.textView1);
		title.setText("CUN-BAE (Clinica Universidad de Navarra - Body Adiposity Estimator)");
		et_age=(EditText) findViewById(R.id.editText1);
		et_height=(EditText) findViewById(R.id.editText2);
		et_weight=(EditText) findViewById(R.id.editText3);
		Spinner spinner=(Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sex);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter); 
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		b1=(Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(et_age.getText().toString().contentEquals(""))
				{
					Toast.makeText(FatCalculator.this, "Please enter age ", Toast.LENGTH_LONG).show();
				}
				else
				{
					if(sel_sex.contentEquals("Select your sex"))
					{
						Toast.makeText(FatCalculator.this, "Please select your gender ", Toast.LENGTH_LONG).show();
					}
					else
					{
						if(et_height.getText().toString().contentEquals(""))
						{
							Toast.makeText(FatCalculator.this, "Please enter height ", Toast.LENGTH_LONG).show();
						}
						else
						{
							if(et_weight.getText().toString().contentEquals(""))
							{
								Toast.makeText(FatCalculator.this, "Please enter weight ", Toast.LENGTH_LONG).show();
							}
							else
							{
								age=Double.valueOf(et_age.getText().toString());
								ht=Double.valueOf(et_height.getText().toString());
								wt=Double.valueOf(et_weight.getText().toString());
								bmi=wt/(ht*ht);
								
								AlertDialog.Builder builder= new AlertDialog.Builder(FatCalculator.this);
								builder.setMessage("Patients Body Mass Index is: "+bmi+" kg/m2");
								builder.setCancelable(true);
								builder.setPositiveButton("Calculate Body Fat", new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										dialog.cancel();
										if(sel_sex.contentEquals("Female"))
										{
											body_fat=(0.503*age)+(10.689*1)+(3.172*bmi)-(0.026*bmi*bmi)+(0.181*bmi*1)-(0.02*bmi*age)-(0.005*bmi*bmi*1)+(0.00021*bmi*bmi*age)-44.988;
											if(body_fat > 0.0 || body_fat < 30.0 )
											{
												range="Lean";
											}
											else if(body_fat > 31.1 || body_fat < 35.0 )
											{
												range="Overweight";
											}
											else if(body_fat > 35.1)
											{
												range="Obese";
											}
										}
										else
										{
											body_fat=(0.503*age)+(10.689*0)+(3.172*bmi)-(0.026*bmi*bmi)+(0.181*bmi*0)-(0.02*bmi*age)-(0.005*bmi*bmi*0)+(0.00021*bmi*bmi*age)-44.988;
											if(body_fat < 0.0 || body_fat > 20.0 )
											{
												range="Lean";
											}
											else if(body_fat < 21.1 || body_fat > 25.0 )
											{
												range="Overweight";
											}
											else if(body_fat < 25.1)
											{
												range="Obese";
											}
										}	
										
										
									
										//#############################################
										AlertDialog.Builder builder= new AlertDialog.Builder(FatCalculator.this);
										builder.setMessage("Patients Predicted Body Fat is: "+body_fat+" % \nBody Fat Range is: "+range);
										builder.setCancelable(true);
										builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
											
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												dialog.cancel();
												
												
											}
										});
										builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
											
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												dialog.cancel();
												
											}
										});
							        	 AlertDialog alert=builder.create();
							        	 alert.show();
							        	 //####################################################################
										
									}
								});
								builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										dialog.cancel();
										
									}
								});
					        	 AlertDialog alert=builder.create();
					        	 alert.show();
							}
						}
					}
					
					
				}
				
		
			}
		});
	}
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(pos!=0)
	    	{
	    		//Toast.makeText(parent.getContext(), "You have selected " +
	    		//parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    		sel_sex=parent.getItemAtPosition(pos).toString();
	    		//return sel_sex;
	    	}
	    }
	    	

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    	
	    }
	}
}

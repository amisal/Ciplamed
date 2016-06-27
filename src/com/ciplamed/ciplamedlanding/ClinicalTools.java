package com.ciplamed.ciplamedlanding;

import com.ciplamed.ciplamedlanding.AntimicrobialDosing.MyAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ClinicalTools extends  ListActivity{
	
	String[] tool_name=new String[]{"Indian Diabetic Risk Score","CUN-BAE (Clinica Universidad de Navarra - Body Adiposity Estimator)","Pocket Guide For Antimicrobial Dosing In Renal Failure"};
	Button b1;
	TextView tv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clinicaltools);
		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.tools_header, lv, false);
		lv.addHeaderView(header, null, false);
		
		b1=(Button) findViewById(R.id.button2);
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(ClinicalTools.this, Main.class);
				startActivity(i);
			}
		});
		setListAdapter(new MyAdapter(this, R.layout.list_tools,R.id.textView1, tool_name));
	}
	
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	 // TODO Auto-generated method stub
	 super.onListItemClick(l, v, position, id);
	 String selection = l.getItemAtPosition(position).toString();
	 Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
	 if(selection.contentEquals("Indian Diabetic Risk Score"))
	 {
		  Intent i1=new Intent(ClinicalTools.this,DiabeticRiskScore.class);
		  startActivity(i1);
	 }
	 else if(selection.contentEquals("CUN-BAE (Clinica Universidad de Navarra - Body Adiposity Estimator)"))
	 {
		  Intent i1=new Intent(ClinicalTools.this, FatCalculator.class);
		  startActivity(i1);
	 }
	 else if(selection.contentEquals("Pocket Guide For Antimicrobial Dosing In Renal Failure"))
	 {
		  Intent i1=new Intent(ClinicalTools.this, AntimicrobialDosing.class);
		  startActivity(i1);
	 }
	
	
    }

	public class MyAdapter extends ArrayAdapter<String>{

		public MyAdapter(Context context, int resource, int textViewResourceId,
				String[] countries) {
			super(context, resource, textViewResourceId, countries);
			// TODO Auto-generated constructor stub
		}
		public View getView(int position,View convertView, ViewGroup parent) {
			
			LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
			View row=inflater.inflate(R.layout.list_tools, parent, false);
			TextView title=(TextView) row.findViewById(R.id.textView1);
			title.setText(tool_name[position]);
			
			return super.getView(position, convertView, parent);
		}
}
}

package com.ciplamed.ciplamedlanding;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




public class AntimicrobialDosing extends ListActivity{
	
	String[] drug_type=new String[]{"Antibacterials","Antifungals","Antivirals"};
	Button b1;
	TextView tv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.antimicrobialdosing);
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
				Intent i=new Intent(AntimicrobialDosing.this, Main.class);
				startActivity(i);
			}
		});
		setListAdapter(new MyAdapter(this, R.layout.list_tools,R.id.textView1, drug_type));
	}
	
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	 // TODO Auto-generated method stub
	 super.onListItemClick(l, v, position, id);
	String selection = l.getItemAtPosition(position).toString();
	// Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
	 Intent i1=new Intent(AntimicrobialDosing.this, DrugClass.class);
	 i1.putExtra("drug_type", selection);
	 startActivity(i1);
	
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
			title.setText(drug_type[position]);
			
			return super.getView(position, convertView, parent);
		}
}
}

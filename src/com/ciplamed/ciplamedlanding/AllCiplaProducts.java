package com.ciplamed.ciplamedlanding;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class AllCiplaProducts extends ListActivity{
	ArrayList<String> p=new ArrayList<String>();
	SQLiteDatabase db;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allciplaproducts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        db=openOrCreateDatabase("ciplamedDB", MODE_PRIVATE, null);
        Cursor cur = db.rawQuery("SELECT Distinct title FROM product_index order by title", null);
        cur.moveToFirst();
        int n=cur.getCount();
       
        //int i=0;
        for(int i=0;i<n-1;i++)
        {
        	//p.add();
        	cur.moveToNext();
        	p.add(cur.getString(cur.getColumnIndex("title")));
        	Log.d("Anish",cur.getString(cur.getColumnIndex("title")));
        	
        	//i++;
        }
       // final String [] arr = p.toArray(new String[p.size()]);
        db.close();
        setListAdapter(new MyAdapter(this, R.layout.list_prod,R.id.prodname, p));
       // TextView textview = new TextView(this);
        //textview.setText("This is the Songs tab");
        //setContentView(textview)
    }
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	 // TODO Auto-generated method stub
	 super.onListItemClick(l, v, position, id);
	 String selection = l.getItemAtPosition(position).toString();
	//Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
	Intent i=new Intent(AllCiplaProducts.this, ProductPrescribtion.class);
	 i.putExtra("product",l.getItemAtPosition(position).toString());
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

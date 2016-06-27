package com.ciplamed.ciplamedlanding;

import java.util.ArrayList;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListViewAdapterPubmed extends ListViewAdapter {
	
	 Activity context;  
	    ArrayList<String> title;  
	    ArrayList<String> description;  
	    ArrayList<String> description2;

	public ListViewAdapterPubmed(Activity context, ArrayList<String> newstitle,
			ArrayList<String> newsdate, ArrayList<String> arr_citations) {
		super(context, newstitle, newsdate);
		 
	        this.context = context;  
	        this.title = newstitle;  
	        this.description = newsdate;  
	        this.description2 = arr_citations;  
	    }  
	  
	    public int getCount() {  
	        // TODO Auto-generated method stub  
	        return title.size();  
	    }  
	  
	    public Object getItem(int position) {  
	        // TODO Auto-generated method stub  
	        return null;  
	    }  
	  
	    public long getItemId(int position) {  
	        // TODO Auto-generated method stub  
	        return 0;  
	    }  
	  
	    private class ViewHolder {  
	        TextView txtViewTitle;  
	        TextView txtViewDescription;  
	        TextView txtViewDescription2;  
	    }  
	  
	    public View getView(int position, View convertView, ViewGroup parent)  
	    {  
	        // TODO Auto-generated method stub  
	        ViewHolder holder;  
	        LayoutInflater inflater =  context.getLayoutInflater();  
	  
	        if (convertView == null)  
	        {  
	            convertView = inflater.inflate(R.layout.pubmed_list, null);  
	            holder = new ViewHolder();  
	            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.txt_title);  
	            holder.txtViewDescription = (TextView) convertView.findViewById(R.id.txt_citations);  
	            holder.txtViewDescription2 = (TextView) convertView.findViewById(R.id.txt_authors);  
	            convertView.setTag(holder);  
	        }  
	        else  
	        {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
	  
	        holder.txtViewTitle.setText(title.get(position));  
	        holder.txtViewDescription.setText(description.get(position));  
	        holder.txtViewDescription.setLines(2);
	        holder.txtViewDescription2.setText(description2.get(position));  
	        holder.txtViewDescription2.setLines(2);
	  
	    return convertView;  
	    }  
	  
	}  

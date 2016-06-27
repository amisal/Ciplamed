package com.ciplamed.ciplamedlanding;

import java.util.ArrayList;

import android.R.string;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class ListViewAdaptercal extends ListViewAdapter {
	 Activity context;  
	    ArrayList<String> title;  
	    ArrayList<String> date;  
	    String[] months=new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
	  
	    public ListViewAdaptercal(Activity context, ArrayList<String> caltitle, ArrayList<String> caldate) {  
	        super(context, caldate, caldate);  
	        this.context = context;  
	        this.title = caltitle;  
	        this.date = caldate;  
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
	    }  
	  
	    public View getView(int position, View convertView, ViewGroup parent)  
	    {  
	        // TODO Auto-generated method stub  
	        ViewHolder holder;  
	        LayoutInflater inflater =  context.getLayoutInflater();  
	  
	        if (convertView == null)  
	        {  
	            convertView = inflater.inflate(R.layout.news_list, null);  
	            holder = new ViewHolder();  
	            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.newstitle);  
	            holder.txtViewDescription = (TextView) convertView.findViewById(R.id.newsdate);  
	            convertView.setTag(holder);  
	        }  
	        else  
	        {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
	        String[] mydate=date.get(position).split("-");
	        int temp=Integer.parseInt(mydate[1]);
	        holder.txtViewTitle.setText(title.get(position));  
	        holder.txtViewDescription.setText(months[temp-1]+" "+mydate[0]);  
	  
	    return convertView;  
	    }  
	  

}

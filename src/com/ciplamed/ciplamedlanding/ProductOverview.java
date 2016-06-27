package com.ciplamed.ciplamedlanding;

import com.ciplamed.ciplamedlanding.NewsMain.Loadnewsdata;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ProductOverview extends Activity{
	
	String prod_name;
	final String mimeType = "text/html";     
	final String encoding = "utf-8"; 
	WebView w;
	public static String filename="Ciplamedprefs";
	SharedPreferences someData;
	String dataReturned;
	int fontsize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview);
		Bundle extras=getIntent().getExtras();
		prod_name=extras.getString("product");
		w=(WebView) findViewById(R.id.webView1);
		WebSettings ws=w.getSettings();
		someData=getSharedPreferences(filename, 0);
	    dataReturned=someData.getString("text_size", "0");
	    fontsize=Integer.valueOf(dataReturned);
		ws.setDefaultFontSize(fontsize);
		//new Loadoverview().execute();
	}

}

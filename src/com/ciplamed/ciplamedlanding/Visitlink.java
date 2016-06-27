package com.ciplamed.ciplamedlanding;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class Visitlink extends Activity{
	
	String url,title;
	final String mimeType = "text/html";     
	final String encoding = "utf-8"; 
	WebView w;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visitlink);
		Bundle extras=getIntent().getExtras();
		url=extras.getString("refurl");
		title=extras.getString("title");
		tv=(TextView) findViewById(R.id.textView1);
		tv.setText(title);
		w=(WebView) findViewById(R.id.webView1);
		w.getSettings().setJavaScriptEnabled(true);
		w.setHorizontalScrollBarEnabled(true);
		w.setVerticalScrollBarEnabled(true);
		w.loadUrl(url);
	
		
		
	}

}

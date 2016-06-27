package com.ciplamed.ciplamedlanding;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebcastMain extends Activity{
	
	WebView w;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webcastmain);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		w=(WebView) findViewById(R.id.webView1);
		w.getSettings().setJavaScriptEnabled(true);
		w.setWebViewClient(new MyWebViewClient());
		//w.loadUrl("https://www.example.com?queryParam1=value1"); 
		w.loadUrl("http://www.streamonindia.com/00056/vlogin.aspx?Name=Anish&Location=NA&emailID=anish@oh22media.com&contactNo=9920279555");
		
	}
	private class MyWebViewClient extends WebViewClient
	{
	    @Override
	    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
	    {
	       handler.proceed();
	    }

	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url)
	   {
	    	view.loadUrl(url);
	        return true;
	   }
	}
}

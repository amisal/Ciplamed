package com.ciplamed.ciplamedlanding;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;


public class Splash extends Activity {
	
	File dbFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Handler hand=new Handler();
	    hand.postDelayed(new SplashHandler(), 2000);
		dbFile = new File("/data/data/com.ciplamed.ciplamedlanding/databases/"+"ciplamedDB");
	    }
	    class SplashHandler implements Runnable {
	    	public void run() {
				// TODO Auto-generated method stub
	    	if(dbFile.exists())
	        {
				Intent intent = new Intent(Splash.this, Main.class);
				startActivity(intent);
				Splash.this.finish();
	            	
	        }
	        else
	        {
	        	Intent i = new Intent(Splash.this, Login.class);
				startActivity(i);
				Splash.this.finish();
	        }
			
		}
		
		
	}
	}

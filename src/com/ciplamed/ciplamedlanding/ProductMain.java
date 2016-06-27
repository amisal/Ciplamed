package com.ciplamed.ciplamedlanding;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


public class ProductMain extends TabActivity{
	int tabHeight = 100;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.productmain);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    Bundle extras=getIntent().getExtras();
	    String prod=extras.getString("product");
	   
	   // FrameLayout tabcontent=(FrameLayout) findViewById(android.R.id.tabhost);
	   // tabcontent.setPadding(0, 0, 150, 0);
	    //Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    //tabHost.addView(tabcontent);
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, ProductOverview.class);
	    intent.putExtra("product", prod);
	    spec = tabHost.newTabSpec("albums").setIndicator(makeTabIndicator("Product Summary"))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    

	    intent = new Intent().setClass(this, ProductPrescribtion.class);
	    intent.putExtra("product", prod);
	    spec = tabHost.newTabSpec("songs").setIndicator(makeTabIndicator("Prescribing \nInfo"))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(1);
	}
	private TextView makeTabIndicator(String text){

		TextView tabView = new TextView(this);
		LayoutParams lp3 = new LayoutParams(LayoutParams.WRAP_CONTENT, tabHeight, 1);
		lp3.setMargins(1, 0, 1, 0);
		tabView.setLayoutParams(lp3);
		tabView.setText(text);
		tabView.setTextColor(Color.WHITE);
		tabView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
		tabView.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_indicator));
		tabView.setPadding(13, 0, 13, 0);
		tabView.setTextSize(18);
		return tabView;

		}

}


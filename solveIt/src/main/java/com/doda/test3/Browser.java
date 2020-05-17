package com.doda.test3;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class Browser extends Activity {
	WebView myBrowser;
	TextView url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser);
		url = (TextView) findViewById(R.id.tvurl);
		myBrowser=(WebView)findViewById(R.id.mybrowser);
		
		String myURL = "http://www.google.com";       
	    url.setText(myURL);
	    
	    myBrowser.getSettings().setJavaScriptEnabled(true);                
	    myBrowser.loadUrl(myURL);
	}

	
}

package com.ezer.shashen;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.doda.test3.R;

public class Info extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		TextView info = (TextView) findViewById(R.id.textView2);
	}

		
}

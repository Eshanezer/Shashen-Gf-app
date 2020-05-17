package com.doda.test3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class Start extends Activity{

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		
		Thread timer = new Thread(){
			public void run(){
				try {
					sleep(3000);
					
					
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally{
					
					
					Intent i0 =new Intent(Start.this,SolveIt.class);
					startActivity(i0);
					
				}
			}
		};
		timer.start();
	}

	
}

package com.doda.test3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AskQuestions extends Activity{

	EditText etfilename,etmessage,serverip,port;
	
	SocketC soc2;
	Button bsave,bsend;
	String ip3,port3;
	FileOutputStream fos;
	String [] list;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.askquestions);
		
		 etmessage = (EditText) findViewById(R.id.etmessage);
		 etfilename = (EditText) findViewById(R.id.etfilename);
			 
		 bsend = (Button) findViewById(R.id.bsendquestion);		
		
			Intent i3 = getIntent();
			ip3 = i3.getStringExtra("ipaddress");
			port3 = i3.getStringExtra("portnumber");
		
		
		bsend.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {
						String fileName = etfilename.getText().toString();
						String question = etmessage.getText().toString();
						String content = etmessage.getText().toString();
												
						
						String [] values = new String[3];
						values[0] = fileName;
						values[1] = content;
						values[2] = question;
					
						soc2 = new SocketC(getApplicationContext(),ip3,port3);
						new BackgroundAsyncTask().execute(values);
						Toast.makeText(AskQuestions.this, "Question Successfully Sent",Toast.LENGTH_LONG).show();
						 Toast.makeText(AskQuestions.this, fileName + " saved",Toast.LENGTH_LONG).show();
				 				   	
				    
				} catch (FileNotFoundException e) {
				    // TODO Auto-generated catch block
					Toast.makeText(AskQuestions.this, "File Not found",Toast.LENGTH_LONG).show();
				    //e.printStackTrace();
				   } catch (IOException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				   }catch (Exception e) {
						// TODO: handle exception
					}
				
				
			}
		});
		
		
			
				
	}

	public class BackgroundAsyncTask extends AsyncTask<String, Boolean,String> {
	  
	   

	 //@Override
	 protected String doInBackground(String... params) {
	  // TODO Auto-generated method stub
	 
		  
			String answer;
				try {
					
					answer = soc2.sendQuestion(params[2]);
									
					fos = openFileOutput(params[0], Context.MODE_PRIVATE);
					fos.write("Question \n".getBytes());
				    fos.write(params[1].getBytes());
				    fos.write("\n Answer \n".getBytes());
				    fos.write(answer.getBytes());
				    fos.close();
				    
				   
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 		
				return params[0];
		 
			  
	 }
	 
	}
			 
	 }
	 


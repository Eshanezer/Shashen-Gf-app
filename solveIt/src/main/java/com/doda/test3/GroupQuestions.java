package com.doda.test3;

import java.io.FileOutputStream;
import java.io.IOException;

import com.doda.test3.AnswerQuestion.BackgroundAsyncTask;
import com.doda.test3.AnswerQuestion.BackgroundAsyncTask1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GroupQuestions extends Activity {

	TextView question1,tvmac1;
	EditText answer1,file1;
	Button bsendanswer1;
	String ip1,port2,answe1,filename1,que1;
	SocketC soc5,soc6;
	FileOutputStream fos2;
	ListView listQuestions1;
	Void d1;
	
	
	private BackgroundAsyncTask task1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupquestions);
		
		question1 = (TextView) findViewById(R.id.tvquestion1);
		answer1 = (EditText) findViewById(R.id.etanswer1);
		file1 = (EditText) findViewById(R.id.etfilename1);
		bsendanswer1 = (Button) findViewById(R.id.bsendanswer1);
		listQuestions1 = (ListView) findViewById(R.id.listView3);
		tvmac1 = (TextView) findViewById(R.id.tvmac1);
		Intent i2 = getIntent();
		ip1 = i2.getStringExtra("ipaddress");
		port2 = i2.getStringExtra("portnumber");
					
	
		
		task1 = new BackgroundAsyncTask();
		task1.execute();
		listQuestions1.setOnItemClickListener(listSavedFilesOnItemClickListener1); 
		
		
		
		
		
		bsendanswer1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
								
				try {
						answe1 = answer1.getText().toString();
						filename1 = file1.getText().toString();
						que1 = question1.getText().toString();
						
						String [] values2 = new String[3];
						values2[0] = filename1;
						values2[1] = answe1;
						values2[2] = que1;
						soc6 = new SocketC(getApplicationContext(), ip1, port2);
											
						new BackgroundAsyncTask1().execute(values2);
						Toast.makeText(GroupQuestions.this, "Answer Successfully Sent",Toast.LENGTH_LONG).show();
						Toast.makeText(GroupQuestions.this, filename1 + " saved",Toast.LENGTH_LONG).show();
						
										    					
				} catch(IOException e1){
					e1.printStackTrace();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
			
		
	}
	public class BackgroundAsyncTask1 extends AsyncTask<String, Boolean,String> {
		  
		   

		 //@Override
		 protected String doInBackground(String... params) {
		  // TODO Auto-generated method stub
		 
					try {
						
						soc6.sendtheanswer(params[1],params[2]);
						fos2 = openFileOutput(params[0], Context.MODE_PRIVATE);
						fos2.write("Question \n".getBytes());
					    fos2.write(params[2].getBytes());
					    fos2.write("\n Answer \n".getBytes());
					    fos2.write(params[1].getBytes());
					    fos2.close();
					    
					   
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 		
					return params[0];
			 
				  
		 }
		 
		}
	
	public class BackgroundAsyncTask extends AsyncTask<String,Void,Void> {
		  
		   		 protected Void doInBackground(String... params) {
			 // TODO Auto-generated method stub
		 
		   				
				try {
					String mac = tvmac1.getText().toString();	
					System.out.println(mac);
					soc5 = new SocketC(getApplicationContext(),ip1,port2);
					
					String p1 = soc5.listenQuestions1(mac);
					
					String modifiedline[]=p1.split(",");
				  	  String second[]=new String[modifiedline.length-1];
				  	
				  	  String check=modifiedline[0];
				  	  
				  	  if(check.compareTo("000")==0){  
				  		  
				  		String [] d = new String[1];
				  		d[0] = "No Questions";
				  		ShowSavedFiles(d);
				  		
				  	  }
				  	  else{
				  		  
				  	  for(int i=1; i<modifiedline.length;i++){
				  		
				  		 second[i-1]=modifiedline[i];
				  		   
				  	  }
				  	  
						ShowSavedFiles(second);
						
						task1.cancel(isCancelled());
				  	  }
					 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
				return d1;
			 	 		
		   }
		 }
	
	 void ShowSavedFiles(String []r) throws IOException{
				  
		 
			ArrayAdapter<String> adapter1	= new ArrayAdapter<String>(GroupQuestions.this,android.R.layout.simple_list_item_1,r);
			adapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
			  listQuestions1.setAdapter(adapter1);
	 
		
		  }
	
	 OnItemClickListener listSavedFilesOnItemClickListener1 = new OnItemClickListener(){

		  
		  public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		   // TODO Auto-generated method stub
			  
		    String clickedFile = (String) parent.getItemAtPosition(position);
		  
		   question1.setText(clickedFile);
		   
		  }
		    
		   };
	
		   private BroadcastReceiver myWifiReceiver = new BroadcastReceiver(){

			  	  @Override
			  	  public void onReceive(Context arg0, Intent arg1) {
			  	   // TODO Auto-generated method stub
			  	   NetworkInfo networkInfo = (NetworkInfo) arg1.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			  	   if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
			  	    DisplayWifiState();
			  	   }
			  	  }};
			  	  
			  	 public void DisplayWifiState(){
			 	    
			 	    ConnectivityManager myConnManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			 	    NetworkInfo myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			 	    WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
			 	    WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
			 	  
			 	    tvmac1.setText(myWifiInfo.getMacAddress());
			 	  
			 	     
			 	    
			 	   }
	
}

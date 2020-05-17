package com.doda.test3;

import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	
	TextView tvmac,tvserverstatus,tvusername,tvpassword,tvserverreply;
	Button bjoin;
	EditText edpassword,edusername,test,serverip,port;
	SocketC soc;
	String ip1,port1,rply1;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(com.doda.test3.R.layout.activity_test3main);
	                
	        
			 bjoin = (Button) findViewById(com.doda.test3.R.id.bjoin);
			
			
			 edusername = (EditText) findViewById(com.doda.test3.R.id.etusername);
			 edpassword = (EditText) findViewById(com.doda.test3.R.id.etpassword); 
			 test = (EditText) findViewById(com.doda.test3.R.id.etmessage);
			 
			
			 tvserverstatus = (TextView) findViewById(com.doda.test3.R.id.tvserverstatus);
			 tvusername = (TextView) findViewById(com.doda.test3.R.id.tvusername);
			 tvpassword = (TextView) findViewById(com.doda.test3.R.id.tvpassword); 
			
			
			 tvmac= (TextView) findViewById(com.doda.test3.R.id.tvserverstatus);
			 
			 Intent i1 = getIntent();
			  ip1 = i1.getStringExtra("ipaddress");
			  port1 = i1.getStringExtra("portnumber");
			  	 
			 DisplayWifiState();
			 this.registerReceiver(this.myWifiReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));	
			 
			 			 
	      	bjoin.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
												
					try {
												
						
						 soc = new SocketC(getApplicationContext(),ip1,port1);
						 
									 
						 							
									String us = edusername.getText().toString();
									String pass = edpassword.getText().toString();
									
									String mac = tvmac.getText().toString();
									rply1 = soc.login(mac,us, pass);
									//System.out.println(rply1);
								
						if(rply1.compareTo("Authenticated")==0){
							
							Toast.makeText(Login.this, "Login Sucessfull !",Toast.LENGTH_LONG).show();	
							
						Intent i2 = new Intent(Login.this, Menu.class);
						 i2.putExtra("ipaddress", ip1);
						 i2.putExtra("portnumber", port1);
						startActivity(i2);
														
						}
						else{
						 
							Toast.makeText(Login.this, "Error In Login",Toast.LENGTH_LONG).show();
						}
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
			});
	        
	            }
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
	 	  
	 	    tvmac.setText(myWifiInfo.getMacAddress());
	 	  
	 	     
	 	    
	 	   }
	
}

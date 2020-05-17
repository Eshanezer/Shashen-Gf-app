package com.doda.test3;

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
import android.widget.TextView;

public class Connection_status extends Activity{

	TextView textConnected, textIp, textSsid, textBssid, textMac, textSpeed, textRssi;
	 
	   /** Called when the activity is first created. */
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.connection_status);

	       textConnected = (TextView)findViewById(R.id.Connected);
	       textIp = (TextView)findViewById(R.id.Ip);
	      
	       textSsid = (TextView)findViewById(R.id.Ssid);
	       textBssid = (TextView)findViewById(R.id.Bssid);
	       textMac = (TextView)findViewById(R.id.Mac);
	       textSpeed = (TextView)findViewById(R.id.Speed);
	       textRssi = (TextView)findViewById(R.id.Rssi);
	      
	       DisplayWifiState();
	      
	       this.registerReceiver(this.myWifiReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

	   }
	  
	   private BroadcastReceiver myWifiReceiver	   = new BroadcastReceiver(){

	  @Override
	  public void onReceive(Context arg0, Intent arg1) {
	   // TODO Auto-generated method stub
	   NetworkInfo networkInfo = (NetworkInfo) arg1.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
	   if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
	    DisplayWifiState();
	   }
	  }};
	  
	   private void DisplayWifiState(){
	    
	    ConnectivityManager myConnManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
	    NetworkInfo myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
	  WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
	  
	  textMac.setText(myWifiInfo.getMacAddress());
	  
	    if (myNetworkInfo.isConnected()){
	     int myIp = myWifiInfo.getIpAddress();
	      
	     textConnected.setText("--- CONNECTED ---");
	      
	     int intMyIp3 = myIp/0x1000000;
	     int intMyIp3mod = myIp%0x1000000;
	      
	     int intMyIp2 = intMyIp3mod/0x10000;
	     int intMyIp2mod = intMyIp3mod%0x10000;
	      
	     int intMyIp1 = intMyIp2mod/0x100;
	     int intMyIp0 = intMyIp2mod%0x100;
	      
	     textIp.setText(String.valueOf(intMyIp0)
	       + "." + String.valueOf(intMyIp1)
	       + "." + String.valueOf(intMyIp2)
	       + "." + String.valueOf(intMyIp3)
	       );
	      
	     textSsid.setText(myWifiInfo.getSSID());
	     textBssid.setText(myWifiInfo.getBSSID());
	     
	     textSpeed.setText(String.valueOf(myWifiInfo.getLinkSpeed()) + " " + WifiInfo.LINK_SPEED_UNITS);
	     textRssi.setText(String.valueOf(myWifiInfo.getRssi()));
	    }
	    else{
	     textConnected.setText("--- DIS-CONNECTED! ---");
	     textIp.setText("---");
	     textSsid.setText("---");
	     textBssid.setText("---");
	     textSpeed.setText("---");
	     textRssi.setText("---");
	    }
	    
	   }
	
}

package com.doda.test3;


import java.io.IOException;
import java.security.PublicKey;

import android.R;
import android.R.string;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class SolveIt extends Activity {
	
	EditText serverip,port;
	Button connect;
		
	 SocketC soc1;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.doda.test3.R.layout.connect);
        
		 serverip = (EditText) findViewById(com.doda.test3.R.id.etip);
		 port = (EditText) findViewById(com.doda.test3.R.id.editTextport);
		 connect = (Button) findViewById(com.doda.test3.R.id.bconnect);
		 
		 			
		 connect.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
					try {
						String ip = serverip.getText().toString();
						String port1 = port.getText().toString();
						
						 soc1 = new SocketC(getApplicationContext(),ip,port1);
						 
						
						 Intent i1 = new Intent(SolveIt.this, Login.class);
						 i1.putExtra("ipaddress", ip);
						 i1.putExtra("portnumber", port1);
						 startActivity(i1);
						 
						 
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(SolveIt.this,"Server Not available",Toast.LENGTH_LONG).show();
					}
			}
		});
     }
    
     
}

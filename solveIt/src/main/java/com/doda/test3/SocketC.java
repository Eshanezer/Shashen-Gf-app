package com.doda.test3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.PublicKey;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.sax.StartElementListener;
import android.test.suitebuilder.TestMethod;
import android.util.Log;

public class SocketC {

	 /* The socket to the server */
    private Socket connection;

    /* Streams for reading and writing the socket */
    private BufferedReader fromServer;
    private DataOutputStream toServer;

    /* application context */
    Context mCtx;

    private static final String CRLF = "\r\n";
        
    public SocketC(Context ctx,String ip, String port) throws IOException {
        mCtx=ctx;
        int p = Integer.parseInt(port);
        this.open(ip,p);
      
        fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        toServer = new DataOutputStream(connection.getOutputStream());
    }

   
	public boolean open(String host, int port) {
        try {
            connection = new Socket(host, port);
            return true;
        } catch(IOException e) {
            Log.v("smswifi", "cannot open connection: " + e.toString());
        }
        return false;
    }

    /* Close the connection. */
    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
            Log.v("smswifi","Unable to close connection: " + e.toString());
        }
    }

    /* Send an SMS command to the server. Check that the reply code
       is what is is supposed to be according to RFC 821. */
    public String sendCommand(String command) throws IOException {

        /* Write command to server. */
        this.toServer.writeBytes(command+this.CRLF);

        /* read reply */
        String reply = this.fromServer.readLine();
        		return reply;
    }
    public String login(String mac,String us,String pass) throws IOException{
    	
    	this.toServer.writeBytes(mac);
    	this.toServer.writeBytes(",Username "+us);
    	this.toServer.writeBytes(",Password "+pass+this.CRLF);
    	String loginstatus = this.fromServer.readLine();
    	    	
    	return loginstatus;
    }
    
   public String sendQuestion(String question)throws IOException{
	   
	   this.toServer.writeBytes("question123 "+question+this.CRLF);
	   String answer = this.fromServer.readLine();
	   return answer;
   }
    
  public String listenQuestions()throws IOException{
	  String g = "kdgehy"; 
	  String question = "";
	  	  	  		  	
	  	  this.toServer.writeBytes(g+this.CRLF);
	  	
	  	  question = this.fromServer.readLine();
	  	  	  	   	  
	  	return question;
	  	  
	  	
	 	 	  
  }
  
  public String listenQuestions1(String m)throws IOException{
	  String g = "group"; 
	  String question1 = "";
	  	  	  		  	
	  	  this.toServer.writeBytes(g);
	  	this.toServer.writeBytes(m+this.CRLF);
	  	  question1 = this.fromServer.readLine();
	  	  	  	   	  
	  	return question1;
	  	  
	  	
	 	 	  
  }
  
  public String sendtheanswer(String answer,String question)throws IOException {
		
	 this.toServer.writeBytes("Question789 "+question); 
	 this.toServer.writeBytes(",Answer "+answer+this.CRLF);
	 String reply = this.fromServer.readLine();
	 return reply;
  }
  
  }

package com.ezer.shashen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.doda.test3.R;

import java.io.FileOutputStream;
import java.io.IOException;

public class AnswerQuestion extends Activity {

	TextView question;
	EditText answer,file;
	Button bsendanswer;
	String ip1,port2,answe,filename,que;
	SocketC soc3,soc4;
	FileOutputStream fos1;
	ListView listQuestions;
	Void d;
	
	
	private BackgroundAsyncTask task;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answerquestions);
		
		question = (TextView) findViewById(R.id.tvquestion);
		answer = (EditText) findViewById(R.id.etanswer);
		file = (EditText) findViewById(R.id.etfilename);
		bsendanswer = (Button) findViewById(R.id.bsendanswer);
		listQuestions = (ListView) findViewById(R.id.listView2);
		
		Intent i2 = getIntent();
		ip1 = i2.getStringExtra("ipaddress");
		port2 = i2.getStringExtra("portnumber");
					
	
		
		task = new BackgroundAsyncTask();
		task.execute();
		listQuestions.setOnItemClickListener(listSavedFilesOnItemClickListener); 
		
		
		
		
		
		bsendanswer.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
								
				try {
						answe = answer.getText().toString();
						filename = file.getText().toString();
						que = question.getText().toString();
						
						String [] values1 = new String[3];
						values1[0] = filename;
						values1[2] = answe;
						values1[1] = que;
						soc4 = new SocketC(getApplicationContext(), ip1, port2);
											
						new BackgroundAsyncTask1().execute(values1);
					    Toast.makeText(AnswerQuestion.this, "Answer Successfully Sent",Toast.LENGTH_LONG).show();
						Toast.makeText(AnswerQuestion.this, filename + " saved",Toast.LENGTH_LONG).show();
						
										    					
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
						
						soc4.sendtheanswer(params[1],params[2]);
						fos1 = openFileOutput(params[0], Context.MODE_PRIVATE);
						fos1.write("Question \n".getBytes());
					    fos1.write(params[2].getBytes());
					    fos1.write("\n Answer \n".getBytes());
					    fos1.write(params[1].getBytes());
					    fos1.close();
					    
					   
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
					
					
					
					soc3 = new SocketC(getApplicationContext(),ip1,port2);
					
					String p1 = soc3.listenQuestions();
					
					String modifiedline[]=p1.split(",");
				  	  String second[]=new String[modifiedline.length-1];
				  	  
				  	  String check=modifiedline[0];
				  	  if(check.compareTo("000")==0){  	  
				  	  
				  	  for(int i=1; i<modifiedline.length;i++){
				  		  
				  		second[i-1]=modifiedline[i];
				  		 
				  		 
				  	  }
				  	  				  							
						ShowSavedFiles(second);
						//task.cancel(isCancelled());
				  	  }
				  	  else{
				  		  
				  		String [] d1 = new String[1];
				  		d1[0] = "No Questions";
				  		ShowSavedFiles(d1);
				  	  }
					 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
				return d;
			 	 		
		   }
		 }
	
	
	 void ShowSavedFiles(String []r) throws IOException{
			/*for(int j=0;j<r.length;j++){	  
		 if(r[j].contains("--")){
			   
  			 String an[]=r[j].split("--");
  			 for(int k=1;k<an.length;k++){
  			  
  				 r[j] = an[0]+"\n"+an[k];
  				 
  			 }
  		   }
			}*/
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,r);
			adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
			  listQuestions.setAdapter(adapter);
	 
		
		  }
	
	 OnItemClickListener listSavedFilesOnItemClickListener = new OnItemClickListener(){

		  
		  public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		   // TODO Auto-generated method stub
			  
		    String clickedFile = (String) parent.getItemAtPosition(position);
		    
		   	  
		   question.setText(clickedFile);
		   
		  }
		    
		   };
	 

		  
}

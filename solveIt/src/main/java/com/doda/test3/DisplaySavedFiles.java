package com.doda.test3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

public class DisplaySavedFiles extends Activity{
	String[] SavedFiles;
	ListView listSavedFiles;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaysavedfiles);
		listSavedFiles = (ListView) findViewById(R.id.lvsavedfiles);
		
		ShowSavedFiles();
		listSavedFiles.setOnItemClickListener(listSavedFilesOnItemClickListener);
	}

	 void ShowSavedFiles(){
		 	
		   SavedFiles = getApplicationContext().fileList();
		   
		  ArrayAdapter<String> adapter	= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,SavedFiles);

		   listSavedFiles.setAdapter(adapter);
		   
		  }
	
	 OnItemClickListener listSavedFilesOnItemClickListener = new OnItemClickListener(){

		  
		  public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		   // TODO Auto-generated method stub
			  
		    String clickedFile = (String) parent.getItemAtPosition(position);
		   OpenFileDialog(clickedFile);
		   
		  }
		    
		   };
	  
	   void OpenFileDialog(final String file){
	    
	    //Read file in Internal Storage
	    FileInputStream fis;
	    String content = "";
	    try {
	    	
	     fis = openFileInput(file);
	     byte[] input = new byte[fis.available()];
	     while (fis.read(input) != -1) {}
	     content += new String(input);
	     
	    } catch (FileNotFoundException e) {
	     e.printStackTrace();
	    } catch (IOException e) {
	     e.printStackTrace(); 
	    }
	    
	    //Create a custom Dialog
	    AlertDialog.Builder fileDialog	= new AlertDialog.Builder(DisplaySavedFiles.this);
	    fileDialog.setTitle(file);
	    
	    
	    TextView textContent = new TextView(DisplaySavedFiles.this);
	    textContent.setTextColor(-1);
	    textContent.setTextSize(18);
	    textContent.setText(content);
	   
	    
	       LayoutParams textViewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	       textContent.setLayoutParams(textViewLayoutParams);
	      
	       fileDialog.setView(textContent);
	      
	       fileDialog.setPositiveButton("OK", null);
	      
	     //Delete file in Internal Storage
	        OnClickListener DeleteListener = new OnClickListener(){

	   public void onClick(DialogInterface dialog, int which) {
	    // TODO Auto-generated method stub
		   
	    deleteFile(file);
	    Toast.makeText(DisplaySavedFiles.this,file + " deleted",Toast.LENGTH_LONG).show();
	    ShowSavedFiles();
	   }
	 
	        };
	  
	        fileDialog.setNeutralButton("DELETE", DeleteListener);
	       	      	       
	       fileDialog.show();
	   }
	
}

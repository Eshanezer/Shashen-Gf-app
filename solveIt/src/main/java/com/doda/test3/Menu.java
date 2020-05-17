package com.doda.test3;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity{
	String ip1,port2;
	String menuList [] = {"AskQuestions","AnswerQuestion","DisplaySavedFiles","GroupQuestions","Browser","Connection_status","Info"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, menuList));
		
		Intent i2 = getIntent();
		ip1 = i2.getStringExtra("ipaddress");
		port2 = i2.getStringExtra("portnumber");
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String list= menuList[position];
		
		try {
			
				Class myclass = Class.forName("com.doda.test3." + list);
				Intent myintent = new Intent(Menu.this, myclass);
				myintent.putExtra("ipaddress", ip1);
				myintent.putExtra("portnumber", port2);
			startActivity(myintent);
			
			
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
	
}

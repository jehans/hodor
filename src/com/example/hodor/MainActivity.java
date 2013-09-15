package com.example.hodor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	String email;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*
		 * Connects to database and creates table if needed
		 */
		db = openOrCreateDatabase("MyDB1", MODE_PRIVATE,null);
		db.execSQL("CREATE TABLE IF NOT EXISTS Stu(email VARCHAR);");
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();  // Always call the superclass
	    
	    // Stop method tracing that the activity started during onCreate()
	    android.os.Debug.stopMethodTracing();
	}
	
	@Override
	public void onPause() {
		/*
		 * This function will always store the text in the textfield.
		 * That is if there is text there for later use.
		 */
	    super.onPause();  // Always call the superclass method first
	    
		SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		EditText text = (EditText)findViewById(R.id.email);
		String textValue = text.getText().toString();

		editor.putString("EditTextString", textValue);
		editor.commit();
	}
	
	@Override
	public void onResume() {
		/*
		 * This function will get the text which was stored earlier.
		 */
	    super.onResume();  // Always call the superclass method first
	    
		SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
		String textValue = sharedPreferences.getString("EditTextString", "");
		EditText text = (EditText)findViewById(R.id.email);
		text.setText(textValue);
	}
	
	
	public void addData(View view) 
	{
		/*
		 * A simple function for storing your email-address to the local
		 * database.
		 */
		Context context = getApplicationContext(); 
		TextView mail = (TextView) findViewById(R.id.email);
		email =  mail.getText().toString();
		
		db.execSQL("INSERT INTO  Stu VALUES('"+email+"');");
		Toast.makeText(context,"'" + email+"' Saved to database!", Toast.LENGTH_LONG).show();
	}
	
	public void viewHttp(View view)
	{
		/*
		 * Starts the activity DisplayMessageActivity
		 * which will download a picture.
		 */
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}

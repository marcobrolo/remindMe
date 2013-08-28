/*
 * tutorial
 * http://www.youtube.com/watch?v=J5a1gD1p_8w
 */
package com.example.remindme;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.Intent;
import android.view.MenuItem;


public class MainActivity extends Activity
{
	private final String TAG = "Main Activity";
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_menu, menu);
		setTitle("my new title");
		getActionBar().setIcon(R.drawable.navigation_back);
		return true;
	}
	
	/*
	 * click actions when a menu is clicked.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.event:
			Log.i(TAG, "event item clicked");
		return true;
		
		case R.id.home:
			Log.i(TAG, "go home clicked");
		return true;
		
		case R.id.refresh:
			Log.i(TAG, "refresh clicked");
		return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//setContentView(R.layout.activity_main);
		Log.i(TAG, "onCreate");
	}

	@Override
	public void onStart()
	{
		super.onStart();
		Log.i(TAG, "OnStart");
	}
	
	@Override
	public void onResume() 
	{
		super.onResume();
		Log.i(TAG, "OnResume");
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		Log.i(TAG, "onPause");
	}

}

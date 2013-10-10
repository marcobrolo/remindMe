/*
 * tutorial
 * http://www.youtube.com/watch?v=J5a1gD1p_8w
 */
package com.example.remindme;
/*
 * The main activity for the app
 */
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.Intent;
import android.view.MenuItem;


public class MainActivity extends BaseActivity
{
	private final String TAG = "Main Activity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//setContentView(R.layout.activity_main);
		this.deleteDatabase("comments.db");
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

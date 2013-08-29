package com.example.remindme;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

// used so the action bar menu is kept throughout each activity
// without having redundant code, by having all the other
// activity classes extends this one rather than the
// usualy Activity class
public class BaseActivity extends Activity
{
	private final String TAG = "Base Activity";
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_menu, menu);
		// Changes the action bar properties (such as title and icon)
		setTitle("my new title");
		//getActionBar().setIcon(R.drawable.navigation_back);
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
			Intent newEventActivity = new Intent(this, EventActivity.class);
        	this.startActivity(newEventActivity);
			Log.i(TAG, "event item clicked");
			break;
		case R.id.note:
			Intent startNewActivityOpen = new Intent(this, NoteActivity.class);
        	this.startActivity(startNewActivityOpen);
			Log.i(TAG, "note item clicked");
			break;
		
		case R.id.home:
			Log.i(TAG, "go home clicked");
			// a back button to return to home activity
			// only available on activities besides MainActivity
			startActivity(new Intent(this, MainActivity.class));
			break;
		
		case R.id.refresh:
			Log.i(TAG, "refresh clicked");
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}
	
}

package com.example.remindme;
/*
 *  Template activity which other activities extend so they can use the action bar
 *  menu without having repeated code.
 */
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity
{
	private static final int ACTIVITY_ADD_NOTE = 1;
	private final String TAG = "Base Activity";
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu, this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_menu, menu);
		// Changes the action bar properties (such as title and icon)
		setTitle("my new title");
		//getActionBar().setIcon(R.drawable.navigation_back);
		return true;
	}
	
	/*
	 * Events that happen when the items on the action menu is clicked
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.event:
			/*
			 *  Starts the event activity
			 */
			Intent newEventActivity = new Intent(this, EventActivity.class);
        	this.startActivity(newEventActivity);
			Log.i(TAG, "event item clicked");
			break;
		case R.id.note:
			/*
			 * starts the note activity
			 */
			Intent startNewActivityOpen = new Intent(this, NoteActivity.class);
        	this.startActivity(startNewActivityOpen);
			Log.i(TAG, "note item clicked");
			break;
		
		case R.id.home:
			/*
			 *  returns home (from other activities)
			 */
			Log.i(TAG, "go home clicked");
			// a back button to return to home activity
			// only available on activities besides MainActivity
			startActivity(new Intent(this, MainActivity.class));
			break;
		
		case R.id.add_note:
			Log.i(TAG, "adding_note");
			//startActivity(new Intent(this, AddNoteActivity.class));
			startActivityForResult(new Intent(this, AddNoteActivity.class), ACTIVITY_ADD_NOTE);
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}
	
}

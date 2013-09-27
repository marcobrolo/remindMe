package com.example.remindme;
/*
 *  Template activity which other activities extend so they can use the action bar
 *  menu without having repeated code. Just like BaseActivity.Java, except this
 *  extends for Listactivity
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivityList extends ListActivity
{	
	private final String TAG = "Base Activity List ";
	
    public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_menu, menu);
		// Changes the action bar properties (such as title and icon)
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
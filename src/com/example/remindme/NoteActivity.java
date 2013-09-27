package com.example.remindme;
/*
 * The note activity, where users can view and modify previous notes
 * As well as make new ones
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ExpandableListView;

public class NoteActivity extends BaseActivity
{
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private EventsDataSource datasource;
    private final String TAG = "NoteActivity";
    private static final int ACTIVITY_EDIT_NOTE = 0;
    
    /*
     * own option menu since we want different action bar menu than other activities
     */
    @Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{  	
    	// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_activity_menu, menu);
		// Changes the action bar properties (such as title and icon)
		setTitle("Notes");
		//getActionBar().setIcon(R.drawable.navigation_back);
		// set home button in action bar
		
		getActionBar().setHomeButtonEnabled(true);
		return true;
	}
    
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
        setContentView(R.layout.note_main);
        this.deleteDatabase("comments.db");
        // add database
    	datasource = new EventsDataSource(this);
        Log.i(TAG, "create new event data source");
    	datasource.open();
        Log.i(TAG, "open new event data source");
        datasource.createComment("hiho");
    	List<note> notes = datasource.getAllNotes();
    	
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
 
        // preparing list data
        prepareListData(notes);
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
	}
	
	private void prepareListData(List<note> notes)
	{
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");
        listDataHeader.add(notes.get(0).getComment());
 
        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");
 
        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");
 
        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");
 
        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		Log.d("getting back header", "come here");
		//super.onActivityResult(requestCode, resultCode, intent);
		Bundle extras = intent.getExtras();
		
		switch(requestCode)
		{
		case ACTIVITY_EDIT_NOTE:
			String title = extras.getString("Title");
			String comment = extras.getString("Comments");
			Log.d("getting back header", title);
			Log.d("getting back child", comment);
		}
	}
}

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
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.util.Log;
import android.view.Menu;
import android.widget.ExpandableListView;

public class NoteActivity extends BaseActivity
{
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private NotesDataSource datasource;
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
    	datasource = new NotesDataSource(this);
        Log.i(TAG, "create new event data source");
    	datasource.open();
        Log.i(TAG, "open new event data source");
        datasource.createComment("Custom Cat2", "custom com2");
        
    	List<note> notes = datasource.getAllNotes();
    	
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
 
        // preparing list data
        prepareListData(notes);
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
	}
	
	/*
	 * For testing purposes, create sample data
	 */
	private void prepareListData(List<note> notes)
	{
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");
        listDataHeader.add(notes.get(0).getCategory());
        Log.d("double check notes update", notes.get(0).getComment()+notes.get(0).getCategory());
        
        // add child data for the notes
        List<String> notesArray = new ArrayList<String>();
        notesArray.add(String.valueOf(notes.get(0).getId()) + ":"
        		+ notes.get(0).getComment());
 
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
        listDataChild.put(listDataHeader.get(3), notesArray);
    }
	
	/*
	 * Used for modifying a comment, it will return to this screen with the newly
	 * proposed changes by the user. The changes will be processed and updated
	 * here
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		Log.d(TAG, "onActivityResult");
		//super.onActivityResult(requestCode, resultCode, intent);
		Bundle extras = intent.getExtras();
		
		switch(requestCode)
		{
		case ACTIVITY_EDIT_NOTE:
			/*
			 * Upon finis editing a comment, this view will collect
			 * the proposed changes and process them
			 */
			String category = extras.getString("Title");
			String comment = null ;
			long id = 0 ;
			String deliminator = ":";
			String[] tokens = extras.getString("Comments").split(deliminator);

			for (int i = 0; i < tokens.length; i++)
			{
				if (i ==0)
				{
					id = Long.valueOf(tokens[0]);
				}
				else
				{
					if (tokens[i] != null)
					{
						comment += tokens[i];
					}
				}
			}
			
			note noteVal = new note();
			noteVal.setId(id);
			noteVal.setCategory(category);
			noteVal.setComment(comment);
			editNote(noteVal);
		}
	}
	
	/*
	 * Upon editing a comment, process the proposed changes into the database
	 */
	private void editNote(note noteVal)
	{
		int err = datasource.updateContact(noteVal);
		List<note> notes = datasource.getAllNotes();
		prepareListData(notes);
		refreshList();
	}
	
	/*
	 * Refresh the view with changes made to the list
	 */
	private void refreshList()
	{
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
		//listAdapter.notifyDataSetChanged();
		expListView.setAdapter(listAdapter);
	}
}

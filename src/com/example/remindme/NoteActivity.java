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
    private static final int ACTIVITY_ADD_NOTE = 1;
    
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
        //this.deleteDatabase("comments.db");
        // add database
    	datasource = new NotesDataSource(this);
        Log.i(TAG, "create new event data source");
    	datasource.open();
        Log.i(TAG, "open new event data source");
        
    	List<note> notes = datasource.getAllNotes();
    	
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
 
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
	}
	
	/*
	 * Grabs information from the database and sets it out for
	 * the expandableListView to list each category and the according
	 * comments
	 */
	private void prepareListData()
	{
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        List<String> catagoryList = datasource.getCategory();
        for (int i = 0; i<catagoryList.size(); i++)
        {	
        	Log.d("inside loop", "Getting specific notes");
        	List<note> categoryNotes = datasource.getCategoryNotes(catagoryList.get(i));
        	List<String> notesArray = new ArrayList<String>();
        	
        	listDataHeader.add(catagoryList.get(i));
        	Log.d("getting specifi notes", "Getting specific notes");
        	
        	
        	for (int j = 0; j<categoryNotes.size(); j++)
        	{	
        		Log.d("getting catnotes", categoryNotes.get(j).getComment());
        		notesArray.add(String.valueOf(categoryNotes.get(j).getId()) + ":"
        				+ categoryNotes.get(j).getComment());
        	}
        	listDataChild.put(listDataHeader.get(i), notesArray);
        }
        
       
    }
	
	/*
	 * For testing purposes, create sample data
	 */
	private void addTestData()
	{
		listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        
		// Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");
        
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
	
	/*
	 * Used for modifying a comment, it will return to this screen with the newly
	 * proposed changes by the user. The changes will be processed and updated
	 * here
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		Log.d(TAG, "onActivityResult");
		String category, comment, deliminator = null;
		long id = 0;
		
		Bundle extras = intent.getExtras();
		switch(requestCode)
		{
		case ACTIVITY_EDIT_NOTE:
			/*
			 * Upon finish editing a comment, this view will collect
			 * the proposed changes and process them
			 */
			category = extras.getString("Title");
			comment = null ;
			id = 0 ;
			deliminator = ":";
			String[] tokens = extras.getString("Comments").split(deliminator);

			for (int i = 0; i < tokens.length; i++)
			{
				Log.d(TAG+"see tokens", tokens[i]);
				if (i ==0)
				{
					id = Long.valueOf(tokens[0]);
				}
				else
				{
					if (tokens[i] != null && tokens[i] !="null")
					{
						if (comment == null)
						{
							comment = tokens[i];
						}
						else
						{
							comment += tokens[i];
						}
					}
				}
			}
			note noteVal = new note();
			noteVal.setId(id);
			noteVal.setCategory(category);
			noteVal.setComment(comment);
			editNote(noteVal);
			break;
			
		case ACTIVITY_ADD_NOTE:
			/*
			 * Upon finishing adding a comment, this view will collect
			 * the proposed changes and process them
			 */
			Log.d(TAG, "Finised adding now coming to noteactivity");
			category = extras.getString("Title");
			Log.d(TAG, "1");
			comment = extras.getString("Comments");
			addNote(category, comment);
			break;
		default:
			Log.d(TAG+"onACtivityResult", "invalid switch result");
		}
	}
	
	/*
	 * Upon editing a comment, process the proposed changes into the database
	 */
	private void editNote(note noteVal)
	{
		datasource.updateComment(noteVal);
		refreshList();
	}
	
	/*
	 * Used to process information from AddNoteActivity
	 */
	private void addNote(String category, String comment)
	{
		Log.d(TAG, "addNote()");
		datasource.createComment(category, comment);
		refreshList();
	}
	
	/*
	 * Refresh the view with changes made to the list
	 */
	private void refreshList()
	{
		prepareListData();
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
		//listAdapter.notifyDataSetChanged();
		expListView.setAdapter(listAdapter);
	}
}

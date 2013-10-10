package com.example.remindme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends Activity
{
	private EditText mTitleText;	// Title (category) of the note, should not be editable
	private EditText mBodyText;		// Content of the note
    private final String TAG = "AddNoteActivity";
	
    /*
     * own option menu since we want different action bar menu than other activities
     */
    @Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{  	
    	// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editadd_note_activity_menu, menu);
		// Changes the action bar properties (such as title and icon)
		setTitle("Add note");
		// set home button in action bar
		getActionBar().setHomeButtonEnabled(true);
		return true;
	}
    
    @Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit_note_main);
	    setTitle("Adding notes");
	    
	    mTitleText = (EditText) findViewById(R.id.title);
	    mBodyText = (EditText) findViewById(R.id.body);	    
	    
	    Button confirmButton = (Button) findViewById(R.id.confirm);
	    confirmButton.setOnClickListener(new View.OnClickListener() 
	    {
	    	
            public void onClick(View view) 
            {
            	// check to make sure there are text in the field before
            	// finishing activity with an okay result
            	if (!(mTitleText.getText().toString().equals("") ||
            			mBodyText.getText().toString().equals("")))
            	{
            		Bundle bundle = new Bundle();	// Creates a bundle to hold the edits
                    
                    bundle.putString("Title", mTitleText.getText().toString());
                    bundle.putString("Comments", mBodyText.getText().toString());

                    Intent mIntent = new Intent();
                    mIntent.putExtras(bundle);

                    setResult(RESULT_OK, mIntent);
                    finish();
            	}
            	else if (mTitleText.getText().toString().equals(""))
            	{
            		Toast.makeText(AddNoteActivity.this,
            						"Missing comments",
            						Toast.LENGTH_LONG).show();
            	}
            	else if (mBodyText.getText().toString().equals(""))
            	{
            		Toast.makeText(AddNoteActivity.this,
            						"Missing category",
            						Toast.LENGTH_LONG).show();
            	}
            	else 
            	{
            		Toast.makeText(AddNoteActivity.this,
    						"Missing comments and category",
    						Toast.LENGTH_LONG).show();
            	}
            }

        });
	    
	    Button cancelButton = (Button) findViewById(R.id.cancel);
	    cancelButton.setOnClickListener(new View.OnClickListener() 
	    {
	    	
            public void onClick(View view) 
            {	
                //setResult(RESULT_CANCELED, null);
                finish();
            }

        });
	}
    
    
	
}

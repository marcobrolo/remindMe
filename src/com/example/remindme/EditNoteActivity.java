package com.example.remindme;
/*
 * Handles the editing of Notes from NoteActivity, user will be able
 * to edit the contents of the note
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteActivity extends Activity
{
	private EditText mTitleText;	// Title (category) of the note, should not be editable
	private EditText mBodyText;		// Content of the note
	private Long mRowId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit_note_main);
	    setTitle("EDiting notes");
	    
	    mTitleText = (EditText) findViewById(R.id.title);
	    mBodyText = (EditText) findViewById(R.id.body);
	    
	    Button confirmButton = (Button) findViewById(R.id.confirm);
	    
	    mRowId = null;
	    Bundle extras = getIntent().getExtras();	// Contains contents of the note
	    if (extras != null)
	    {
	    	// Extract the contents of the note and paste them
	    	String title = extras.getString("Title");
	    	String body = extras.getString("Comment");
	    	
	    	if (title != null)
	    	{
                mTitleText.setText(title);
            }
            if (body != null)
            {
                mBodyText.setText(body);
            }
	    }
	    /*
	     * Upon hitting confirm button, we will pack up all the edits
	     * and send them to the previous activity (NoteActivity) for
	     * processing
	     */
	    confirmButton.setOnClickListener(new View.OnClickListener() 
	    {
	    	
            public void onClick(View view) 
            {
                Bundle bundle = new Bundle();	// Creates a bundle to hold the edits
                
                bundle.putString("Title", mTitleText.getText().toString());
                bundle.putString("Comments", mBodyText.getText().toString());
                if (mRowId != null) 
                {
                    bundle.putLong("RowId", mRowId);
                }

                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
	    
	    
	}
}

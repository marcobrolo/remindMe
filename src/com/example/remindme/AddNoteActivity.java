package com.example.remindme;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends Activity
{
	private EditText mTitleText;	// Title (category) of the note, should not be editable
	private EditText mBodyText;		// Content of the note
	private Long mRowId;
	private NotesDataSource datasource;
    private final String TAG = "AddNoteActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit_note_main);
	    setTitle("Adding notes");
	    

	    
	    datasource = new NotesDataSource(this);
	    
	    Button confirmButton = (Button) findViewById(R.id.confirm);
	    
	    
	    confirmButton.setOnClickListener(new View.OnClickListener() 
	    {
	    	
            public void onClick(View view) 
            {
                
            }

        });
	}
	
}

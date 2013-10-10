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
	    
	    mTitleText = (EditText) findViewById(R.id.title);
	    mBodyText = (EditText) findViewById(R.id.body);
	    
	    datasource = new NotesDataSource(this);
	    datasource.open();
	    
	    Button confirmButton = (Button) findViewById(R.id.confirm);
	    
	    
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
	
	private void createNote(note note)
	{
		datasource.createComment(mTitleText.getText().toString()
								, mBodyText.getText().toString());
	}
	
}

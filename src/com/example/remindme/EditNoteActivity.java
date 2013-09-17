package com.example.remindme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteActivity extends Activity
{
	private EditText mTitleText;
	private EditText mBodyText;
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
	    Bundle extras = getIntent().getExtras();
	    if (extras != null)
	    {
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

	    confirmButton.setOnClickListener(new View.OnClickListener() 
	    {

            public void onClick(View view) 
            {
                Bundle bundle = new Bundle();

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

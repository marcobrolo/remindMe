package com.example.remindme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ConfirmCancelDialogActivity extends Activity
{
	private final String TAG = "ConfirmCancelDialogActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.confirmcanceldialog_activity_main);
		
		Button confirmButton = (Button) findViewById(R.id.confirm);
		confirmButton.setOnClickListener(new View.OnClickListener() 
	    {
	    	
            public void onClick(View view) 
            {	
            	Log.d(TAG, getIntent().getExtras().getString("Comment"));
                setResult(RESULT_OK, getIntent());
                finish();
            	
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

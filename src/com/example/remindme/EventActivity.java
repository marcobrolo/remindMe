package com.example.remindme;
/*
 * Handles the event activity where users can create upcoming events which
 * will count down as the deadline approaches
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends BaseActivityList
{	
	private final String TAG = "Event Activity"; 
    private ArrayList<event> mDataList = new ArrayList<event>();	// holds events

    private Handler mHandler;
    private ArrayAdapter<event> mListAdapter;						// expandable list adapter responsible for
    																// the actions for list adapter
    private boolean mCountersActive;
    private GregorianCalendar gToday = new GregorianCalendar();		// stores today's time for
    																// calculating when events expire
    
    private EventsDataSource datasource;
    /*
     * Setting own option menu for this activity
     */
    public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_activity_menu, menu);
		// Changes the action bar properties (such as title and icon)
		setTitle("Events");
		getActionBar().setIcon(R.drawable.navigation_back);
		return true;
	}
    
    public void CountDownList() 
    {
        mHandler = new Handler();
    }

    private final Runnable mRunnable = new Runnable()
    {	
    	
        public void run() 
        {
        	Log.i(TAG, "countdownloop2");
        	event myData;            
            // if counters are active
                      
        	Log.i(TAG, "countdownloop");
            if (mDataList != null) 
            {
                for (int i=0; i < mDataList.size(); i++) 
                {
                    myData = mDataList.get(i);
                    
                    if (myData.eventFinished() == true)
                    {
                    	// when the event timer expires we remove the
                    	// entry and update the listview to reflect the change
                    	mDataList.remove(i);
                    	mListAdapter.notifyDataSetChanged();
                    }
                }
                // notify that data has been changed (update the time)
                mListAdapter.notifyDataSetChanged();
            }
            // update every second
            mHandler.postDelayed(this, 1000);
            
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	Log.i(TAG, "creating activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_main);
        
        // load top menu
        
        Log.i(TAG, "adding test data");
        // add some test data
        mDataList = new ArrayList<event>();
        
        datasource = new EventsDataSource(this);
        datasource.open();
        
        createTestData();
        CountDownList();
        Log.i(TAG, "initiating data");
        initData();
    }
    
    public void createTestData()
    {
    	int yearNow = gToday.get(Calendar.YEAR);
    	int monthNow = gToday.get(Calendar.MONTH);
    	int dateNow = gToday.get(Calendar.DATE);
    	int hourNow = gToday.get(Calendar.HOUR_OF_DAY);
    	int minuteNow = gToday.get(Calendar.MINUTE);
    	
    	Log.d(TAG + ":checking nowTime",
				String.valueOf(yearNow) +
				String.valueOf(monthNow) +
				String.valueOf(dateNow) +
				String.valueOf(hourNow) +
				String.valueOf(minuteNow));
    	
    	mDataList.add(
    			datasource.createEvent("1 minute expiry", yearNow, monthNow, dateNow, hourNow, minuteNow+1));
    	mDataList.add(	
    			datasource.createEvent("school", 2013, 11, 12, 11, 10));
    	
    }
    
    private void initData() 
    {
    	Log.i(TAG, "inside initData()");
        // set the list adapter
        mListAdapter = new MyListAdapter(this, R.layout.row, mDataList);
        setListAdapter(mListAdapter); 
        mHandler.post(mRunnable);
    }    


    /*
     * List adapter to handle the list view functions
     */
    private class MyListAdapter extends ArrayAdapter<event>
    {
    	
        private ArrayList<event> items;
        private LayoutInflater vi = (LayoutInflater) 
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        public MyListAdapter(Context context,
        					int textViewResourceId,
                			ArrayList<event> items
                			)
        {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {                                    
            if (convertView == null)
            {
                convertView = vi.inflate(R.layout.row, null);
            }

            event myData = items.get(position);

            if (myData != null)
            {
                TextView text = (TextView) 
                        convertView.findViewById(R.id.myTextView);
                if (text != null)
                {
                    text.setText(myData.getText());
                }

                TextView counter = (TextView) 
                        convertView.findViewById(R.id.myTextViewTwo);
                if (counter != null)
                {
                    counter.setText(myData.getCountAsString());                
                }
            }            

            return convertView;
        }        
    }

    @Override
    /*
     * When item is clicked itll prompt the user to verify the wish to delete the event
     */
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	// TODO: implement some removal feature
    	super.onListItemClick(l,v, position, id);
    	// Popup alert
    	AlertDialog.Builder alertDiag = new AlertDialog.Builder(this);
    	alertDiag.setTitle("Delete?");
    	alertDiag.setMessage("Are you sure you wanna delete?");
    	final int positionToRemove = position;
    	alertDiag.setNegativeButton("Cancel", null);
    	// Upon hitting confirm to delete event, delete the event
    	alertDiag.setPositiveButton("Ok", new AlertDialog.OnClickListener()
    	{
    		public void onClick(DialogInterface dialog, int which)
    		{
    			mDataList.remove(positionToRemove);
    			mListAdapter.notifyDataSetChanged();
    		}
    		
    	});
    	alertDiag.show();
    }
    
    /*
     * Object used to hold the event, has the event title as well
     * as the deadline
     */
        

}

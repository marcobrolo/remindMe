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
    private ArrayList<MyData> mDataList = new ArrayList<MyData>();	// holds events

    private Handler mHandler;
    private ArrayAdapter<MyData> mListAdapter;						// expandable list adapter responsible for
    																// the actions for list adapter
    private boolean mCountersActive;
    private GregorianCalendar gToday = new GregorianCalendar();		// stores today's time for
    																// calculating when events expire
    
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
            MyData myData;            
            // if counters are active
            if (mCountersActive) 
            {                
                if (mDataList != null) 
                {
                    for (int i=0; i < mDataList.size(); i++) 
                    {
                        myData = mDataList.get(i);

                        if (myData.eventFinished()== true)
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
        mDataList = new ArrayList<MyData>();
        MyData data;
        int j = 3;
        for (int i=0; i < 32; i++)
        {
            data = new MyData("hi");
            mDataList.add(data);
            j = j + 3;
        }
        CountDownList();
        Log.i(TAG, "initiating data");
        initData();
    }
    
    private void initData() 
    {
    	Log.i(TAG, "inside initData()");
        // set the list adapter
        mListAdapter = new MyListAdapter(this, R.layout.row, mDataList);
        setListAdapter(mListAdapter);        
        // start counters
        Log.i(TAG, "starting stopStart()");
    }    


    /*
     * List adapter to handle the list view functions
     */
    private class MyListAdapter extends ArrayAdapter<MyData>
    {
    	
        private ArrayList<MyData> items;
        private LayoutInflater vi = (LayoutInflater) 
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        public MyListAdapter(Context context,
        					int textViewResourceId,
                			ArrayList<MyData> items
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

            MyData myData = items.get(position);

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
    private class MyData
    {
        private String text;
        private int seconds, minutes, hours,days;
        
        private GregorianCalendar Gdate; 
        
        public void setSeconds(int secondVal)
        {
        	seconds = secondVal;
        }
        public void setMinutes(int minuteVal)
        {
        	seconds = minuteVal;
        }
        public void setDays(int dayVal)
        {
        	seconds = dayVal;
        }
        public void setHours(int hourVal)
        {
        	seconds = hourVal;
        }
        public boolean eventFinished()
        {
        	if (this.seconds == 0 && this.minutes == 0 && this.hours == 0 && this.days == 0)
        	{
        		return true;
        	}
        	return false;
        }
        
        public MyData(String text)
        {
            this.text = text;
            // NOTE: gregorian Calendar offsets month and minutes by 1 ie Jan = 0 Dec =11
            
            //this.Gdate = new GregorianCalendar(2013,8,16,12,41);
            this.Gdate = new GregorianCalendar(2013,Calendar.SEPTEMBER, 16, 12, 50);
            CalculateDaysHours();
        }
        public GregorianCalendar getGDate()
        {
        	return Gdate;
        }
        
        /*
         * calculates when the event will expire in terms of days hours
         * minutes and seconds
         */
        public void CalculateDaysHours()
        {
        	GregorianCalendar gtoday = new GregorianCalendar();
        	long diffInMs = Gdate.getTimeInMillis()-gToday.getTimeInMillis();
        	Log.i(TAG, "event time is" + Gdate.getTime());
        	Log.i(TAG, "current time is" + gToday.getTime());
        	new CountDownTimer(diffInMs, 1000)
        	{
        		public void onTick(long millisUntilFinished)
        		{
        			seconds = (int)(millisUntilFinished / 1000) % 60;
        			minutes = (int)((millisUntilFinished / (1000*60)) % 60);
        			hours = (int)((millisUntilFinished / (1000*60*60)) % 24);
        			days = (int)((millisUntilFinished / (1000*60*60*24)) % 365);
        		}
        		public void onFinish()
        		{
        	         seconds = 0;
        	         minutes = 0;
        	         hours = 0;
        	         days = 0;
        	    }
        	}.start();
        	
        }
        
        /*
         * return event name
         */
        public String getText()
        {
            return text;
        	
        }

        /*
         * returns the hours,days,minutes, and seconds in form of string for
         * displaying purposes
         */
        public String getCountAsString()
        {
            //return Integer.toString(seconds);
        	return String.valueOf(days)+"d:"+String.valueOf(hours)+"h:"+
            		String.valueOf(minutes)+"m:"+String.valueOf(seconds)+"s";
        }

    }

}

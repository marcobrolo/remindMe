package com.example.remindme;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends ListActivity
{	
	private final String TAG = "Event Activity"; 
	private Button mButton;
    private ArrayList<MyData> mDataList = new ArrayList<MyData>();

    private Handler mHandler;
    private ArrayAdapter<MyData> mListAdapter;
    private boolean mCountersActive;
    private GregorianCalendar gToday = new GregorianCalendar();
    
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
                        //myData.CalculateDaysHours();
                        if (myData.getCount() > 0) 
                        {
                        	// decrement the counters
                            myData.reduceCount();
                        }
                        else
                        {
                        	// otherwise when the event timer expires we remove the
                        	// entry and update the listview to reflect the change
                        	mDataList.remove(i);
                        	mListAdapter.notifyDataSetChanged();
                        }
                    }
                    // notify that data has been changed
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
        Log.i(TAG, "current time is" + gToday.getTime());
        
        Log.i(TAG, "loaded shit");
        
        // start and stop button
        mButton = (Button) findViewById(R.id.myButton);
        mButton.setText("Stop");
        mButton.setOnClickListener(new OnClickListener() 
        {            
            @Override
            public void onClick(View v) 
            {            
                stopStart();
            }
        });
        
        Log.i(TAG, "adding test data");
        // add some test data
        mDataList = new ArrayList<MyData>();
        MyData data;
        int j = 3;
        for (int i=0; i < 32; i++)
        {
            data = new MyData("hi", j);
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
        stopStart();
    }    

    private void stopStart() 
    {
    	Log.i(TAG, "inside stopStart()");
        if (mCountersActive) 
        {
            mCountersActive = false;
            mButton.setText("Start");
        }
        else
        {
            mCountersActive = true;
            mHandler.post(mRunnable);
            mButton.setText("Stop");
        }
    }

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
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	// TODO: implement some removal feature
    	super.onListItemClick(l,v, position, id);
    	AlertDialog.Builder alertDiag = new AlertDialog.Builder(this);
    	alertDiag.setTitle("Delete?");
    	alertDiag.setMessage("Are you sure you wanna delete?");
    	final int positionToRemove = position;
    	alertDiag.setNegativeButton("Cancel", null);
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
    
    private class MyData
    {
        private String text;
        private int count;
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
        
        public MyData(String text, int count)
        {
            this.text = text;
            this.count = count;
            this.Gdate = new GregorianCalendar(2013,9,12,20,30);
            CalculateDaysHours();
        }
        public GregorianCalendar getGDate()
        {
        	return Gdate;
        }
        
        public void CalculateDaysHours()
        {
        	GregorianCalendar gtoday = new GregorianCalendar();
        	long diffInMs = Gdate.getTimeInMillis()-gToday.getTimeInMillis();
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
        	         
        	    }
        	}.start();
        	
        }
        public String getText()
        {
            return text;
        	
        }

        public int getCount()
        {
            return count;
        }

        public String getCountAsString()
        {
            //return Integer.toString(seconds);
        	return String.valueOf(days)+"d:"+String.valueOf(hours)+"h:"+
            		String.valueOf(minutes)+"m:"+String.valueOf(seconds)+"s";
        }

        public void reduceCount()
        {
            if (count > 0)
            {
                count--;
            }
        }
    }

}

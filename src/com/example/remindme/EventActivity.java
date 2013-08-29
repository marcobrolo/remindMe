package com.example.remindme;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class EventActivity extends ListActivity
{	
	private final String TAG = "Event Activity"; 
	private Button mButton;
    private ArrayList<MyData> mDataList = new ArrayList<MyData>();

    private Handler mHandler;
    private ArrayAdapter<MyData> mListAdapter;
    private boolean mCountersActive;

    public void CountDownList() 
    {
        mHandler = new Handler();
    }

    private final Runnable mRunnable = new Runnable() {
        public void run() {
            MyData myData;            
            // if counters are active
            if (mCountersActive) {                
                if (mDataList != null) {
                    for (int i=0; i < mDataList.size(); i++) {
                        myData = mDataList.get(i);
                        if (myData.getCount() >= 0) {
                            myData.reduceCount();
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
        
        Log.i(TAG, "loaded shit");
        
        // start and stop button
        mButton = (Button) findViewById(R.id.myButton);
        mButton.setText("Stop");
        mButton.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {            
                stopStart();
            }
        });
        
        Log.i(TAG, "adding test data");
        // add some test data
        mDataList = new ArrayList<MyData>();
        MyData data;
        int j = 10;
        for (int i=0; i < 1000; i++)
        {
            data = new MyData(Integer.toString(j), j);
            mDataList.add(data);
            j = j + 10;
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

    private class MyData
    {
        private String text;
        private int count;

        public MyData(String text, int count) {
            this.text = text;
            this.count = count;
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
            return Integer.toString(count);
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

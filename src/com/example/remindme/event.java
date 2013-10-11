package com.example.remindme;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.os.CountDownTimer;
import android.util.Log;

public class event 
{
	private long id;
	private String text;
    private int seconds, minutes, hours ,days,year;
    private final String TAG = "event";
    
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
    
    public event(long id, String event, int year, int month, int date,
			int hour,int minute)
    {
        this.text = event;
        this.id = id;
        GregorianCalendar gToday = new GregorianCalendar();
        gToday.setTimeZone(TimeZone.getTimeZone("PST"));
        // NOTE: gregorian Calendar offsets month and minutes by 1 ie Jan = 0 Dec =11
        
        //this.Gdate = new GregorianCalendar(2013,8,16,12,41);
        //note for month january = 0 rather than 1
        Log.d(TAG + ":before conversion",
				event + String.valueOf(year) +
				String.valueOf(month) +
				String.valueOf(date) +
				String.valueOf(hour) +
				String.valueOf(minute));
        this.Gdate = new GregorianCalendar(year,month, date, hour, minute);
        Log.d(TAG + ":after conversion",
				event + 
				String.valueOf(Gdate.get(Calendar.YEAR)) +
				String.valueOf(Gdate.get(Calendar.MONTH)) +
				String.valueOf(Gdate.get(Calendar.DATE)) +
				String.valueOf(Gdate.get(Calendar.HOUR)) +
				String.valueOf(Gdate.get(Calendar.MINUTE)));
        CalculateDaysHours(gToday);
    }
    public GregorianCalendar getGDate()
    {
    	return Gdate;
    }
    
    /*
     * calculates when the event will expire in terms of days hours
     * minutes and seconds
     */
    public void CalculateDaysHours(GregorianCalendar gToday)
    {
    	GregorianCalendar gtoday = new GregorianCalendar();
    	long diffInMs = Gdate.getTimeInMillis()-gToday.getTimeInMillis();
    	Log.i(TAG, "event time is" + Gdate.getTime());
    	Log.i(TAG, "current time is" + gToday.getTime());
    	Log.i(TAG, "diff in ms is" + String.valueOf(diffInMs));
    	new CountDownTimer(diffInMs, 1000)
    	{	
    		
    		public void onTick(long millisUntilFinished)
    		{
    			//Log.i(TAG ,"ontick");
				seconds = (int)(millisUntilFinished / 1000) % 60;
				minutes = (int)((millisUntilFinished / (1000*60)) % 60);
				hours = (int)((millisUntilFinished / (1000*60*60)) % 24);
				days = (int)((millisUntilFinished / (1000*60*60*24)) % 365);
				/*Log.i(TAG +"Countdown info", 
		    			"days" + days +
		    			"hours" + hours +
		    			"minutes" + minutes +
		    			"seconds" + seconds);*/
    		}
    		public void onFinish()
    		{
    			Log.i(TAG ,"on finish");
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


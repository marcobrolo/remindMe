package com.example.remindme;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 *  Maintains database connection and supports adding new notes etc
 */
public class EventsDataSource 
{
	// Database fields
	private SQLiteDatabase database;
	private EventSqlHelper dbHelper;
	private String[] allColumns = { EventSqlHelper.COLUMN_ID,
									EventSqlHelper.COLUMN_EVENT,
									EventSqlHelper.COLUMN_DUE_YEAR,
									EventSqlHelper.COLUMN_DUE_MONTH,
									EventSqlHelper.COLUMN_DUE_DATE,
									EventSqlHelper.COLUMN_DUE_HOUR,
									EventSqlHelper.COLUMN_DUE_MINUTE
									};
	private final String TAG = "EventsDataSource";
	
	public EventsDataSource(Context context)
	{
		dbHelper = new EventSqlHelper(context);
	}
	
	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close()
	{
		dbHelper.close();
	}
	
	/*
	 * creates new event and adds into the db
	 */
	public event createEvent(String event, int year, int month, int date,
							int hour,int minute)
	{
		Log.d(TAG + ":Creating event",
				event + String.valueOf(year) +
				String.valueOf(month) +
				String.valueOf(date) +
				String.valueOf(hour) +
				String.valueOf(minute));
		
		ContentValues values = new ContentValues();
		values.put(EventSqlHelper.COLUMN_EVENT, event);
		values.put(EventSqlHelper.COLUMN_DUE_YEAR, year);
		values.put(EventSqlHelper.COLUMN_DUE_MONTH, month);
		values.put(EventSqlHelper.COLUMN_DUE_DATE, date);
		values.put(EventSqlHelper.COLUMN_DUE_HOUR, hour);
		values.put(EventSqlHelper.COLUMN_DUE_MINUTE, minute);
		
		long insertId = database.insert(EventSqlHelper.TABLE_NAME, null, values);
		Cursor cursor = database.query(EventSqlHelper.TABLE_NAME,
				allColumns, EventSqlHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		event newEvent = cursorToEvent(cursor);
		cursor.close();
		return newEvent;
	}
	
	/*
	 * deletes event from db
	 */
	public void deleteEvent(long id)
	{
		//long id = note.getId();
		database.delete(EventSqlHelper.TABLE_NAME, EventSqlHelper.COLUMN_ID
			+ " = " + id, null);
	}
	
	/*
	 * Grabs all events from db
	 */
	public List<event> getAllEvents()
	{
		List<event> events = new ArrayList<event>();
		Cursor cursor = database.query(EventSqlHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			event eventVal = cursorToEvent(cursor);
			events.add(eventVal);
			cursor.moveToNext();
		}
		cursor.close();
		return events;
	}
	
	private void debugDisplayALLEvents()
	{
		long id = 0;
		String event = null;
		int year,month,date,hour,minute = 0;
		Cursor cursor = database.query(EventSqlHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			id = cursor.getLong(0);
			event = cursor.getString(1);
			year = cursor.getInt(2);
			month = cursor.getInt(3);//month
			date = cursor.getInt(4);//date
			hour = cursor.getInt(5);//hour
			minute = cursor.getInt(6);//minute
			
			Log.d(TAG + ":debugDisplayAllNotes",
					String.valueOf(id) + event + String.valueOf(year)+
					String.valueOf(month)+ String.valueOf(date)+
					String.valueOf(hour)+ String.valueOf(minute));
			cursor.moveToNext();
		}
		cursor.close();
		
	}
	
	/*
	 * converts the query our cursor is pointing to into
	 * a notes object
	 */
	private event cursorToEvent(Cursor cursor)
	{
		event eventVal = new event(cursor.getLong(0),//id
									cursor.getString(1),//event
									cursor.getInt(2),//year
									cursor.getInt(3),//month
									cursor.getInt(4),//date
									cursor.getInt(5),//hour
									cursor.getInt(6)//minute
						);
		
		//Log.d("inserting comment", cursor.getString(1)+cursor.getString(2));
		return eventVal;
	}
}

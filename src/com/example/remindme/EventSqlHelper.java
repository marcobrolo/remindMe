package com.example.remindme;
/*
 * database helper file that contains many basic functions
 * that interfaces with the db
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventSqlHelper extends SQLiteOpenHelper
{
	public static final String TABLE_NAME = "eventsTable";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_EVENT = "event";
	public static final String COLUMN_DUE_YEAR = "yearDue";
	public static final String COLUMN_DUE_MONTH = "monthDue";
	public static final String COLUMN_DUE_DATE = "dateDue";
	public static final String COLUMN_DUE_HOUR = "hourDue";
	public static final String COLUMN_DUE_MINUTE = "minuteDue";
	
	private static final String DATABASE_NAME = "events.db";
	private static final int DATABASE_VERSION = 1;
	
	
	private static final String DATABASE_CREATE = "create table "
		+ TABLE_NAME + "(" + COLUMN_ID
		+ " integer primary key autoincrement, "
		+ COLUMN_EVENT + " string not null, "
		+ COLUMN_DUE_YEAR + " int not null, "
		+ COLUMN_DUE_MONTH + " int not null, "
		+ COLUMN_DUE_DATE + " int not null, "
		+ COLUMN_DUE_HOUR + " int not null, "
		+ COLUMN_DUE_MINUTE + " int not null );";
	

	public EventSqlHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(EventSqlHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(db);
	}
}

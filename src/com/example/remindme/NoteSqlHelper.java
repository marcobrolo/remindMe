package com.example.remindme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteSqlHelper extends SQLiteOpenHelper
{
	public static final String TABLE_NAME = "notesTable";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";
	public static final String COLUMN_CATEGORY = "category";
	
	private static final String DATABASE_NAME = "comments.db";
	private static final int DATABASE_VERSION = 1;
	
	/*
	private static final String DATABASE_CREATE = "create table "
		+ TABLE_NAME + "(" + COLUMN_ID
		+ " integer primary key autoincrement, " + COLUMN_CATEGORY
		+ " text not null, " + COLUMN_COMMENT + " text not null );";
	*/
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_COMMENT
			+ " text not null);";
	public NoteSqlHelper(Context context)
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
		Log.w(NoteSqlHelper.class.getName(),
				"Upgrading database from bersion " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(db);
	}
}

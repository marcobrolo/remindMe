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
public class NotesDataSource 
{
	// Database fields
	private SQLiteDatabase database;
	private NoteSqlHelper dbHelper;
	private String[] allColumns = { NoteSqlHelper.COLUMN_ID, 
			NoteSqlHelper.COLUMN_CATEGORY, NoteSqlHelper.COLUMN_COMMENT};
	private final String TAG = "NotesDataSource";
	
	public NotesDataSource(Context context)
	{
		dbHelper = new NoteSqlHelper(context);
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
	 * creates new note and adds into the db
	 */
	public note createComment(String category, String comment)
	{
		ContentValues values = new ContentValues();
		values.put(NoteSqlHelper.COLUMN_CATEGORY, category);
		values.put(NoteSqlHelper.COLUMN_COMMENT, comment);
		long insertId = database.insert(NoteSqlHelper.TABLE_NAME, null, values);
		Cursor cursor = database.query(NoteSqlHelper.TABLE_NAME,
				allColumns, NoteSqlHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		note newNote = cursorToNote(cursor);
		cursor.close();
		return newNote;
	}
	
	/*
	 * deletes comment from db
	 */
	public void deleteComment(note note)
	{
		long id = note.getId();
		database.delete(NoteSqlHelper.TABLE_NAME, NoteSqlHelper.COLUMN_ID
			+ " = " + id, null);
	}
	
	/*
	 * Grabs all comments (and categories) from db
	 */
	public List<note> getAllNotes()
	{
		List<note> notes = new ArrayList<note>();
		Cursor cursor = database.query(NoteSqlHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			note noteVal = cursorToNote(cursor);
			notes.add(noteVal);
			cursor.moveToNext();
		}
		cursor.close();
		return notes;
	}
	
	/*
	 * Returns list of all categories (unique)
	 */
	public List<String> getCategory()
	{
		List<String> categories = new ArrayList<String>();
		Cursor cursor = database.rawQuery("Select distinct " + NoteSqlHelper.COLUMN_CATEGORY 
											+ " from " + NoteSqlHelper.TABLE_NAME,
											null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Log.d(TAG + ":getting categories", cursor.getString(0));
			categories.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		return categories;
	}
	
	/*
	 * Returns all the comments under a category in form of
	 * note objects
	 */
	public List<note> getCategoryNotes(String category)
	{
		List<note> notes = new ArrayList<note>();
		Cursor cursor = database.rawQuery("Select * from " + NoteSqlHelper.TABLE_NAME +
											" where " + NoteSqlHelper.COLUMN_CATEGORY + 
											" = '" + category+"'",
											null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			note noteVal = cursorToNote(cursor);
			//Log.d(TAG, noteVal.getCategory()+noteVal.getComment());
			notes.add(noteVal);
			cursor.moveToNext();
		}
		cursor.close();
		return notes;
	}
	
	/*
	 * updates comment from database
	 */
	public int updateComment(note note)
	{	
		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(NoteSqlHelper.COLUMN_CATEGORY, note.getCategory());
		values.put(NoteSqlHelper.COLUMN_COMMENT, note.getComment());
		
		return database.update(NoteSqlHelper.TABLE_NAME, values, NoteSqlHelper.COLUMN_ID + " LIKE ?",
				new String[]{String.valueOf(note.getId())});
	}
	
	/*
	 * converts the query our cursor is pointing to into
	 * a notes object
	 */
	private note cursorToNote(Cursor cursor)
	{
		note noteVal = new note();
		noteVal.setId(cursor.getLong(0));
		noteVal.setCategory(cursor.getString(1));
		noteVal.setComment(cursor.getString(2));
		//Log.d("inserting comment", cursor.getString(1)+cursor.getString(2));
		return noteVal;
	}
}

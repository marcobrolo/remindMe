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
	
	public int updateContact(note note)
	{	
		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(NoteSqlHelper.COLUMN_CATEGORY, note.getCategory());
		values.put(NoteSqlHelper.COLUMN_COMMENT, note.getComment());
		
		return database.update(NoteSqlHelper.TABLE_NAME, values, NoteSqlHelper.COLUMN_ID + " LIKE ?",
				new String[]{String.valueOf(note.getId())});
	}
	
	private note cursorToNote(Cursor cursor)
	{
		note noteVal = new note();
		noteVal.setId(cursor.getLong(0));
		noteVal.setCategory(cursor.getString(1));
		noteVal.setComment(cursor.getString(2));
		Log.d("inserting comment", cursor.getString(1)+cursor.getString(2));
		return noteVal;
	}
}

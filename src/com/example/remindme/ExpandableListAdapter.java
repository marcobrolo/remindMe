
package com.example.remindme;
/*
 * handles the actions for expandable list adapter in our NotesActivity
 * template taken from tutorial
 * 	//http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */
import android.widget.BaseExpandableListAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter
{
	private Context _context;
	private List<String> _listDataHeader;
	private HashMap<String, List<String>> _listDataChild;
	private final String TAG = "Expandable list adapter";
	
	private static final int ACTIVITY_EDIT_NOTE = 0;
	
	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData)
	{
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent)
	{
		Log.d(TAG, "expanding parent");
		final String childText = (String) getChild(groupPosition, childPosition);
		final String headerText = (String) getGroup(groupPosition);
		if (convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}
		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		txtListChild.setText(childText);
		/*
		 * when a child is clicked, this indicates the desire to edit the note
		 * upon which we should prompt user and open up a new activity for editing
		 * the note
		 * ref://http://stackoverflow.com/questions/16754734/launch-new-activity-from-within-expandable-list-view-adapter
		 * 
		 */
		
		convertView.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{	
				Log.d(TAG, "child item clicked");
				Log.d(TAG, "header is " + headerText);
				Log.d(TAG, "child is "+ childText);
				Intent intent = new Intent (ExpandableListAdapter.this._context, EditNoteActivity.class);
				intent.putExtra("Title", headerText);
				intent.putExtra("Comment", childText);
				((Activity)ExpandableListAdapter.this._context).startActivityForResult(intent,ACTIVITY_EDIT_NOTE);

			}
			
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		Log.d(TAG, "getting children count");
		try 
		{
			return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
		} catch (NullPointerException e)
		{
			Log.d(TAG, "header has no child");
			return 0;
		}
		
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() 
	{
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{	
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}
		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);
		return convertView;
	}

	@Override
	public boolean hasStableIds()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return true;
	}

}

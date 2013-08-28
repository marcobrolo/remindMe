//http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
package com.example.remindme;

import android.widget.BaseExpandableListAdapter;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter
{
	private Context _context;
	private List<String> _listDataHeader;
	private HashMap<String, List<String>> _listDataChild;
	
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
		final String childText = (String) getChild(groupPosition, childPosition);
		if (convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}
		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		txtListChild.setText(childText);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
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
		Log.i("HJIHIHIHIH:", "1d");
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}
		Log.i("HJIHIHIHIH:", "2d");
		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		Log.i("HJIHIHIHIH:", "3d");
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

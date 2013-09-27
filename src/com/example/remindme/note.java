package com.example.remindme;

/*
 * Note class used to store user defined notes
 * Will be stored in a db
 */
public class note 
{
	private long id;
	private String comment;
	private String category;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getComment()
	{
		return comment;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	public void setCategory(String category)
	{
		this.category = category;
	}
	
	@Override
	public String toString()
	{
		return comment;
	}
}

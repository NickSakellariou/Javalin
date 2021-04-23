package javalinvue;

public class genre {

	private int genre_id;
	private String name;
	private int count;
	
	genre(int genre_id, String name, int count)
	{
		this.setGenre_id(genre_id);
		this.setName(name);
		this.setCount(count);
	}
	
	public void setGenre_id(int genre_id)
	{
		this.genre_id=genre_id;
	}
	public int getGenre_id()
	{
		return this.genre_id;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	
	public void setCount(int count)
	{
		this.count=count;
	}
	public int getCount()
	{
		return this.count;
	}
	
}

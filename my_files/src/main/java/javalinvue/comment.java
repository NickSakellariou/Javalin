package javalinvue;

public class comment {

	private int book_id;
	private String text;
	private int rating;
	private String username;
	private int user_id;
	
	comment(int book_id,String text,int rating,String username,int user_id)
	{
		this.setBook_id(book_id);
		this.setText(text);
		this.setRating(rating);
		this.setUsername(username);
		this.setUser_id(user_id);
	}
	
	public void setBook_id(int book_id)
	{
		this.book_id=book_id;
	}
	public int getBook_id()
	{
		return this.book_id;
	}
	
	public void setText(String text)
	{
		this.text=text;
	}
	public String getText()
	{
		return this.text;
	}
	
	public void setRating(int rating)
	{
		this.rating=rating;
	}
	public int getRating()
	{
		return this.rating;
	}
	
	public void setUsername(String username)
	{
		this.username=username;
	}
	public String getUsername()
	{
		return this.username;
	}
	
	public void setUser_id(int user_id)
	{
		this.user_id=user_id;
	}
	public int getUser_id()
	{
		return this.user_id;
	}
	
}

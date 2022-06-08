package javalinvue;

public class user {

	private int user_id;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String username;
	private int count_comments;
	
	user(int user_id,String name,String surname,String email,String phone,String username,int count_comments)
	{
		this.setUser_id(user_id);
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
		this.setPhone(phone);
		this.setUsername(username);
		this.setCount_comments(count_comments);
	}
	
	public void setUser_id(int user_id)
	{
		this.user_id=user_id;
	}
	public int getUser_id()
	{
		return this.user_id;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	
	public void setSurname(String surname)
	{
		this.surname=surname;
	}
	public String getSurname()
	{
		return this.surname;
	}
	
	public void setEmail(String email)
	{
		this.email=email;
	}
	public String getEmail()
	{
		return this.email;
	}
	
	public void setPhone(String phone)
	{
		this.phone=phone;
	}
	public String getPhone()
	{
		return this.phone;
	}
	
	public void setUsername(String username)
	{
		this.username=username;
	}
	public String getUsername()
	{
		return this.username;
	}
	
	public void setCount_comments(int count_comments)
	{
		this.count_comments=count_comments;
	}
	public int getCount_comments()
	{
		return this.count_comments;
	}
	
}

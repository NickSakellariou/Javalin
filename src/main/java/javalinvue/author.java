package javalinvue;

public class author {
	
	private int author_id;
	private String name;
	private int count;

	public author(int author_id,String name,int count){
		this.author_id=author_id;
	    this.name=name;
	    this.setCount(count);
	}
	
	public int getAuthor_id() {
	    return author_id;
	}
	public void setAuthor_id(int author_id) {
	    this.author_id = author_id;
	 }

	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
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

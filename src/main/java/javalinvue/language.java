package javalinvue;

public class language {
	
	private int language_id;
	private String name;
	private int count;

	public language(int language_id,String name, int count){
		this.language_id=language_id;
	    this.name=name;
	    this.setCount(count);
	}
	
	public int getLanguage_id() {
	    return language_id;
	}
	public void setLanguage_id(int language_id) {
	    this.language_id = language_id;
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

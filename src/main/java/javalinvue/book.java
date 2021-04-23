package javalinvue;

public class book {

	private int book_id;
	private String title;
	private String genre;
	private float price;
	private String author;
	private String image;
	private String publisher;
	private long isbn;
	private int pages;
	private int year;
	private int availability;
	private String language;
	private String description;
	
	book(int book_id,String title,String genre,float price,String author, String image,String publisher,long isbn,int pages,int year,int availability,String language,String description)
	{
		this.setBook_id(book_id);
		this.setTitle(title);
		this.setGenre(genre);
		this.setPrice(price);
		this.setAuthor(author);
		this.setImage(image);
		this.setPublisher(publisher);
		this.setIsbn(isbn);
		this.setPages(pages);
		this.setYear(year);
		this.setAvailability(availability);
		this.setLanguage(language);
		this.setDescription(description);
	}
	
	public void setBook_id(int book_id)
	{
		this.book_id=book_id;
	}
	public int getBook_id()
	{
		return this.book_id;
	}
	
	public void setTitle(String title)
	{
		this.title=title;
	}
	public String getTitle()
	{
		return this.title;
	}
	
	public void setGenre(String genre)
	{
		this.genre=genre;
	}
	public String getGenre()
	{
		return this.genre;
	}
	
	public void setPrice(float price)
	{
		this.price=price;
	}
	public float getPrice()
	{
		return this.price;
	}
	
	public void setAuthor(String author)
	{
		this.author=author;
	}
	public String getAuthor()
	{
		return this.author;
	}
	
	public void setImage(String  image)
	{
		this.image=image;
	}
	public String  getImage()
	{
		return this.image;
	}
	
	public void setPublisher(String publisher)
	{
		this.publisher=publisher;
	}
	public String getPublisher()
	{
		return this.publisher;
	}
	
	public void setIsbn(long isbn)
	{
		this.isbn=isbn;
	}
	public long getIsbn()
	{
		return this.isbn;
	}
	
	public void setPages(int pages)
	{
		this.pages=pages;
	}
	public int getPages()
	{
		return this.pages;
	}
	
	public void setYear(int year)
	{
		this.year=year;
	}
	public int getYear()
	{
		return this.year;
	}
	
	public void setAvailability(int availability)
	{
		this.availability=availability;
	}
	public int getAvailability()
	{
		return this.availability;
	}
	
	public void setLanguage(String language)
	{
		this.language=language;
	}
	public String getLanguage()
	{
		return this.language;
	}
	
	public void setDescription(String description)
	{
		this.description=description;
	}
	public String getDescription()
	{
		return this.description;
	}
	
}

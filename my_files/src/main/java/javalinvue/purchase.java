package javalinvue;

public class purchase {

	private String title;
	private float price;
	private String email;
	private String phone;
	private String date;
	private String street;
	private int streetNumber;
	private String area;
	private int postcode;
	private int book_id;
	private int quantity;
	
	purchase(String title,float price,String email,String phone,String  date,String street,int streetNumber,String area,int postcode,int book_id,int quantity)
	{
		this.setTitle(title);
		this.setPrice(price);
		this.setEmail(email);
		this.setPhone(phone);
		this.setDate(date);
		this.setStreet(street);
		this.setStreetNumber(streetNumber);
		this.setArea(area);
		this.setPostcode(postcode);
		this.setBook_id(book_id);
		this.setQuantity(quantity);
	}
	
	public void setTitle(String title)
	{
		this.title=title;
	}
	public String getTitle()
	{
		return this.title;
	}
	
	public void setPrice(float price)
	{
		this.price=price;
	}
	public float getPrice()
	{
		return this.price;
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
	
	public void setDate(String date)
	{
		this.date=date;
	}
	public String getDate()
	{
		return this.date;
	}
	
	public void setStreet(String street)
	{
		this.street=street;
	}
	public String getStreet()
	{
		return this.street;
	}
	
	public void setStreetNumber(int streetNumber)
	{
		this.streetNumber=streetNumber;
	}
	public int getStreetNumber()
	{
		return this.streetNumber;
	}
	
	public void setArea(String area)
	{
		this.area=area;
	}
	public String getArea()
	{
		return this.area;
	}
	
	public void setPostcode(int postcode)
	{
		this.postcode=postcode;
	}
	public int getPostcode()
	{
		return this.postcode;
	}
	
	public void setBook_id(int book_id)
	{
		this.book_id=book_id;
	}
	public int getBook_id()
	{
		return this.book_id;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity=quantity;
	}
	public int getQuantity()
	{
		return this.quantity;
	}
	
}

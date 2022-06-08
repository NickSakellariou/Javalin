package javalinvue;

public class cookie {

	private String name;
	private String price;
	private String quantity;
	private float sum;
	
	cookie(String name,String price,String quantity,float sum)
	{
		this.setName(name);
		this.setPrice(price);
		this.setQuantity(quantity);
		this.setSum(sum);
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	
	public void setPrice(String price)
	{
		this.price=price;
	}
	public String getPrice()
	{
		return this.price;
	}
	
	public void setQuantity(String quantity)
	{
		this.quantity=quantity;
	}
	public String getQuantity()
	{
		return this.quantity;
	}
	
	public void setSum(float sum)
	{
		this.sum=sum;
	}
	public float getSum()
	{
		return this.sum;
	}
	
}

package javalinvue;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.mindrot.jbcrypt.BCrypt;

import io.javalin.http.Context;

public class UserController {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/booktime?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	
	static final String USER = "admin";
	static final String PASS = "psw1";
	
	private UserController(){ }
	
	public static void search(Context ctx) {
		String search = ctx.formParam("search").replace(' ', '.');
		
		ctx.redirect("/search-results/"+search);
	 }
	
	public static void searchAdmin(Context ctx) {
		String search = ctx.formParam("search").replace(' ', '.');
		
		ctx.redirect("/search-results-admin/"+search);
	 }
	 
	 public static void getSearchResultsBooks(Context ctx) throws IOException{
		 String search = ctx.pathParam("search").replace('.', ' ');
		 
		 ArrayList<book> books = new ArrayList<book>();
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM books WHERE title LIKE '%"+search+"%'");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{						
					
					InputStream input = rs1.getBinaryStream("image");
					byte[] bytes = IOUtils.toByteArray(input);
					 
					String image = new String(Base64.getEncoder().encode(bytes));
					
					book book = new book(rs1.getInt("book_id"),rs1.getString("title"),rs1.getString("genre"),rs1.getFloat("price"),rs1.getString("author"),image,rs1.getString("publisher"),rs1.getLong("isbn"),rs1.getInt("pages"),rs1.getInt("year"),rs1.getInt("availability"),rs1.getString("language"),rs1.getString("description"));
					
					books.add(book);
					
				}
				
				ctx.json(books);
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
	 }
	
	public static void addContactForm(Context ctx) {
		String name = ctx.formParam("name");
		String surname = ctx.formParam("surname");
		String email = ctx.formParam("email");
		String phone = ctx.formParam("phone");
		String message = ctx.formParam("message");
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			int i = 1;
			
			while(true) {
				PreparedStatement pst1 = conn.prepareStatement("SELECT * FROM contact WHERE contact_id  = '"+i+"'");
				ResultSet res1 = pst1.executeQuery();
				
				if(res1.next())	{
					i++;
					continue;
				}
				else {
					PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (contact_id, name, surname, email, phone, message) VALUES (?, ?, ?, ?, ?, ?)");
					ps.setInt(1, i);
					ps.setString(2, name);
					ps.setString(3, surname);
					ps.setString(4, email);
					ps.setString(5, phone);
					ps.setString(6, message);
					
					ps.executeUpdate();
					
					ctx.redirect("/contact-success");
					
					break;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void registerUser(Context ctx) {
		String name = ctx.formParam("name");
		String surname = ctx.formParam("surname");
		String email = ctx.formParam("email");
		String phone = ctx.formParam("phone");
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			PreparedStatement prst = conn.prepareStatement("SELECT * FROM users WHERE email = '"+email+"'");
			ResultSet resu = prst.executeQuery();
			
			if(resu.next())
			{
				ctx.redirect("/register-failure");
			}
			else {
				
				PreparedStatement prst1 = conn.prepareStatement("SELECT * FROM users WHERE username = '"+username+"'");
				ResultSet resu1 = prst1.executeQuery();
				
				if(resu1.next())
				{
					ctx.redirect("/register-failure");
				}
				else {
					
					int i = 1;
					
					while(true) {
						PreparedStatement pst1 = conn.prepareStatement("SELECT * FROM users WHERE user_id = '"+i+"'");
						ResultSet res1 = pst1.executeQuery();
						
						if(res1.next())	{
							i++;
							continue;
						}
						else {
							PreparedStatement ps = conn.prepareStatement("INSERT INTO users (user_id, name, surname, email, phone, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)");
							ps.setInt(1, i);
							ps.setString(2, name);
							ps.setString(3, surname);
							ps.setString(4, email);
							ps.setString(5, phone);
							ps.setString(6, username);
							ps.setString(7, hashed);
							
							ps.executeUpdate();
							
							ctx.cookie("username", username);
							ctx.redirect("/users/"+username);
							
							break;
						}
						
					}
				}
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void login(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
		if(username.equals("admin") && password.equals("admin")) {
			ctx.cookie("username", "admin");
			ctx.redirect("/admin");
		}
		else {
			Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement prst1 = conn.prepareStatement("SELECT * FROM users WHERE username = '"+username+"'");
				ResultSet resu1 = prst1.executeQuery();
				
				if(resu1.next()) {
					String hashed = resu1.getString("password");
					
					if (BCrypt.checkpw(password, hashed)) {
						
						ctx.cookie("username", username);
						ctx.redirect("/users/"+username);
					}
					else {
						ctx.redirect("/login-failure2");
					}
				}
				else {
					ctx.redirect("/login-failure1");
				}
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	 }
	
	public static void changePassword(Context ctx) {
		String password = ctx.formParam("password");
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			PreparedStatement pst1 = conn.prepareStatement("UPDATE users SET password = '"+hashed+"' WHERE username in ('"+ctx.cookie("username")+"')");
			pst1.executeUpdate();
			
			ctx.redirect("/users/"+ctx.cookie("username"));
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void username(Context ctx) {
		String cookie = ctx.cookie("username");
		
		ArrayList<username> usernames = new ArrayList<username>();
		
		username username = new username(cookie);
		
		usernames.add(username);
		
		ctx.json(usernames);
	 }
	
	public static void logout(Context ctx) {
		ctx.removeCookie("username");
		
		ctx.redirect("/");
	 }
	
	public static void getBestSellingBooks(Context ctx) throws IOException {
		 
		 ArrayList<book> books = new ArrayList<book>();
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM books LIMIT 10");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{						
					
					InputStream input = rs1.getBinaryStream("image");
					byte[] bytes = IOUtils.toByteArray(input);
					 
					String image = new String(Base64.getEncoder().encode(bytes));
					
					book book = new book(rs1.getInt("book_id"),rs1.getString("title"),rs1.getString("genre"),rs1.getFloat("price"),rs1.getString("author"),image,rs1.getString("publisher"),rs1.getLong("isbn"),rs1.getInt("pages"),rs1.getInt("year"),rs1.getInt("availability"),rs1.getString("language"),rs1.getString("description"));
					
					books.add(book);
					
				}
				
				ctx.json(books);
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	
	public static void genres(Context ctx) {
		ArrayList<genre> genres = new ArrayList<genre>();
		
		Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM genres");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{	
					
					PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(book_id) AS totalBooks FROM books WHERE genre = '"+rs1.getString("genre")+"'");
	 				ResultSet rs2 = ps2.executeQuery();
	 				
	 				if(rs2.next())
	 				{
	 					genre genre = new genre(rs1.getInt("genre_id"),rs1.getString("genre"),rs2.getInt("totalBooks"));
	 					
	 					genres.add(genre);	
	 				}
				}
				
				ctx.json(genres);
				
			}catch (SQLException e) {
				e.printStackTrace();
			} 
	 }
	
	public static void authors(Context ctx) {
		ArrayList<author> authors = new ArrayList<author>();
		
		Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM authors");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{	
					PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(book_id) AS totalBooks FROM books WHERE author = '"+rs1.getString("author")+"'");
	 				ResultSet rs2 = ps2.executeQuery();
	 				
	 				if(rs2.next())
	 				{
	 					author author = new author(rs1.getInt("author_id"),rs1.getString("author"),rs2.getInt("totalBooks"));
	 					
	 					authors.add(author);
	 				}
				}
				
				ctx.json(authors);
				
			}catch (SQLException e) {
				e.printStackTrace();
			} 
	 }
	
	public static void languages(Context ctx) {
		ArrayList<language> languages = new ArrayList<language>();
		
		Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM languages");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{	
					PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(book_id) AS totalBooks FROM books WHERE language = '"+rs1.getString("language")+"'");
	 				ResultSet rs2 = ps2.executeQuery();
	 				
	 				if(rs2.next())
	 				{
	 					language language = new language(rs1.getInt("language_id"),rs1.getString("language"),rs2.getInt("totalBooks"));
	 					
	 					languages.add(language);
	 				}
				}
				
				ctx.json(languages);
				
			}catch (SQLException e) {
				e.printStackTrace();
			} 
	 }
	
	public static void adminAllUsers(Context ctx) {
		ArrayList<user> users = new ArrayList<user>();
		
		Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM users");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{	
					PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) AS totalComments FROM comments WHERE user_id = '"+rs1.getInt("user_id")+"'");
	 				ResultSet rs2 = ps2.executeQuery();
	 				
	 				if(rs2.next())
	 				{
	 					user user = new user(rs1.getInt("user_id"),rs1.getString("name"),rs1.getString("surname"),rs1.getString("email"),rs1.getString("phone"),rs1.getString("username"),rs2.getInt("totalComments"));
	 					
						users.add(user);
	 				}
				}
				
				ctx.json(users);
				
			}catch (SQLException e) {
				e.printStackTrace();
			} 
	 }
	
	public static void adminAddGenre(Context ctx) {
		String name = ctx.formParam("name");
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			int i = 1;
			
			while(true) {
				PreparedStatement pst1 = conn.prepareStatement("SELECT * FROM genres WHERE genre_id = '"+i+"'");
				ResultSet res1 = pst1.executeQuery();
				
				if(res1.next())	{
					i++;
					continue;
				}
				else {
					
					PreparedStatement ps = conn.prepareStatement("INSERT INTO genres (genre_id, genre) VALUES (?, ?)");
					ps.setInt(1, i);
					ps.setString(2, name);
					
					ps.executeUpdate();
					
					break;
				}
			}		
			
			ctx.redirect("/admin-genre");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void adminModifyGenre(Context ctx) {
		String name = ctx.formParam("name");
		String genre_id = ctx.pathParam("genre_id");
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM genres WHERE genre_id in ('"+genre_id+"')");
			ResultSet rs1 = ps1.executeQuery();
			
			while(rs1.next())
			{			
				PreparedStatement pst1 = conn.prepareStatement("UPDATE books SET genre = '"+name+"' WHERE genre in ('"+rs1.getString("genre")+"')");
				pst1.executeUpdate();	
			}
				
			
			PreparedStatement pst2 = conn.prepareStatement("UPDATE genres SET genre = '"+name+"' WHERE genre_id in ('"+genre_id+"')");
			pst2.executeUpdate();	
			
			ctx.redirect("/admin-genre");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void adminAddAuthor(Context ctx) {
		String name = ctx.formParam("name");
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			int i = 1;
			
			while(true) {
				PreparedStatement pst1 = conn.prepareStatement("SELECT * FROM authors WHERE author_id = '"+i+"'");
				ResultSet res1 = pst1.executeQuery();
				
				if(res1.next())	{
					i++;
					continue;
				}
				else {
					
					PreparedStatement ps = conn.prepareStatement("INSERT INTO authors (author_id, author) VALUES (?, ?)");
					ps.setInt(1, i);
					ps.setString(2, name);
					
					ps.executeUpdate();
					
					break;
				}
			}		
			
			ctx.redirect("/admin-author");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void adminModifyAuthor(Context ctx) {
		String name = ctx.formParam("name");
		String author_id = ctx.pathParam("author_id");
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM authors WHERE author_id in ('"+author_id+"')");
			ResultSet rs1 = ps1.executeQuery();
			
			while(rs1.next())
			{			
				PreparedStatement pst1 = conn.prepareStatement("UPDATE books SET author = '"+name+"' WHERE author in ('"+rs1.getString("author")+"')");
				pst1.executeUpdate();	
			}
				
			
			PreparedStatement pst2 = conn.prepareStatement("UPDATE authors SET author = '"+name+"' WHERE author_id in ('"+author_id+"')");
			pst2.executeUpdate();	
			
			ctx.redirect("/admin-author");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void adminAddLanguage(Context ctx) {
		String name = ctx.formParam("name");
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			int i = 1;
			
			while(true) {
				PreparedStatement pst1 = conn.prepareStatement("SELECT * FROM languages WHERE language_id = '"+i+"'");
				ResultSet res1 = pst1.executeQuery();
				
				if(res1.next())	{
					i++;
					continue;
				}
				else {
					
					PreparedStatement ps = conn.prepareStatement("INSERT INTO languages (language_id, language) VALUES (?, ?)");
					ps.setInt(1, i);
					ps.setString(2, name);
					
					ps.executeUpdate();
					
					break;
				}
			}		
			
			ctx.redirect("/admin-language");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void adminModifyLanguage(Context ctx) {
		String name = ctx.formParam("name");
		String language_id = ctx.pathParam("language_id");
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM languages WHERE language_id in ('"+language_id+"')");
			ResultSet rs1 = ps1.executeQuery();
			
			while(rs1.next())
			{			
				PreparedStatement pst1 = conn.prepareStatement("UPDATE books SET language = '"+name+"' WHERE language in ('"+rs1.getString("language")+"')");
				pst1.executeUpdate();	
			}
				
			
			PreparedStatement pst2 = conn.prepareStatement("UPDATE languages SET language = '"+name+"' WHERE language_id in ('"+language_id+"')");
			pst2.executeUpdate();	
			
			ctx.redirect("/admin-language");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void adminAddBook(Context ctx) {
		String title = ctx.formParam("title");
		String genre = ctx.formParam("genre");
		float price = Float.parseFloat(ctx.formParam("price"));
		String author = ctx.formParam("author");
		String publisher = ctx.formParam("publisher");
		long isbn = Long.parseLong(ctx.formParam("isbn"));
		int pages = Integer.parseInt(ctx.formParam("pages"));
		int year = Integer.parseInt(ctx.formParam("year"));
		int availability = Integer.parseInt(ctx.formParam("availability"));
		String language = ctx.formParam("language");
		String description = ctx.formParam("description");
		
		InputStream image = ctx.uploadedFile("image").getContent();
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			int i = 1;
			
			while(true) {
				PreparedStatement pst1 = conn.prepareStatement("SELECT * FROM books WHERE book_id = '"+i+"'");
				ResultSet res1 = pst1.executeQuery();
				
				if(res1.next())	{
					i++;
					continue;
				}
				else {
					
					PreparedStatement ps = conn.prepareStatement("INSERT INTO books (book_id, title, genre, price, author, image, publisher, isbn, pages, year, availability, language, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					ps.setInt(1, i);
					ps.setString(2, title);
					ps.setString(3, genre);
					ps.setFloat(4, price);
					ps.setString(5, author);
					ps.setBinaryStream (6, image );
					ps.setString(7, publisher);
					ps.setLong(8, isbn);
					ps.setInt(9, pages);
					ps.setInt(10, year);
					ps.setInt(11, availability);
					ps.setString(12, language);
					ps.setString(13, description);
					
					ps.executeUpdate();
					
					break;
				}
			}	
			
			PreparedStatement prst = conn.prepareStatement("SELECT * FROM authors WHERE author = '"+author+"'");
			ResultSet resu = prst.executeQuery();
			
			if(resu.next())
			{
				
			}
			else {
				
				int j = 1;
				
				while(true) {
					PreparedStatement pst2 = conn.prepareStatement("SELECT * FROM authors WHERE author_id = '"+j+"'");
					ResultSet res2 = pst2.executeQuery();
					
					if(res2.next())	{
						j++;
						continue;
					}
					else {
						PreparedStatement ps3 = conn.prepareStatement("INSERT INTO authors (author_id, author) VALUES (?, ?)");
						ps3.setInt(1, j);
						ps3.setString(2, author);
						
						ps3.executeUpdate();
						
						break;
					}				
				}
				
			}
			
			PreparedStatement prst3 = conn.prepareStatement("SELECT * FROM genres WHERE genre = '"+genre+"'");
			ResultSet resu3 = prst3.executeQuery();
			
			if(resu3.next())
			{
				
			}
			else {
				
				int k = 1;
				
				while(true) {
					PreparedStatement pst3 = conn.prepareStatement("SELECT * FROM genres WHERE genre_id = '"+k+"'");
					ResultSet res3 = pst3.executeQuery();
					
					if(res3.next())	{
						k++;
						continue;
					}
					else {
						PreparedStatement ps4 = conn.prepareStatement("INSERT INTO genres (genre_id, genre) VALUES (?, ?)");
						ps4.setInt(1, k);
						ps4.setString(2, genre);
						
						ps4.executeUpdate();
						
						break;
					}				
				}
				
			}
			
			PreparedStatement prst4 = conn.prepareStatement("SELECT * FROM languages WHERE language = '"+language+"'");
			ResultSet resu4 = prst4.executeQuery();
			
			if(resu4.next())
			{
				
			}
			else {
				
				int l = 1;
				
				while(true) {
					PreparedStatement pst4 = conn.prepareStatement("SELECT * FROM languages WHERE language_id = '"+l+"'");
					ResultSet res4 = pst4.executeQuery();
					
					if(res4.next())	{
						l++;
						continue;
					}
					else {
						PreparedStatement ps5 = conn.prepareStatement("INSERT INTO languages (language_id, language) VALUES (?, ?)");
						ps5.setInt(1, l);
						ps5.setString(2, language);
						
						ps5.executeUpdate();
						
						break;
					}				
				}
				
			}
			
			ctx.redirect("/admin-book");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void adminModifyBook(Context ctx) {
		String title = ctx.formParam("title");
		String genre = ctx.formParam("genre");
		float price = Float.parseFloat(ctx.formParam("price"));
		String author = ctx.formParam("author");
		String publisher = ctx.formParam("publisher");
		long isbn = Long.parseLong(ctx.formParam("isbn"));
		int pages = Integer.parseInt(ctx.formParam("pages"));
		int year = Integer.parseInt(ctx.formParam("year"));
		int availability = Integer.parseInt(ctx.formParam("availability"));
		String language = ctx.formParam("language");
		String description = ctx.formParam("description");
		
		InputStream image = ctx.uploadedFile("image").getContent();
		
		int book_id = Integer.parseInt(ctx.pathParam("book_id"));
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			PreparedStatement ps = conn.prepareStatement("UPDATE books SET title = ?,genre = ?,price = ?,author = ?,image = ?,publisher = ?,isbn = ?,pages = ?,year = ?,availability = ?,language = ?,description = ? WHERE book_id in ('"+book_id+"')");
			ps.setString(1, title);
			ps.setString(2, genre);
			ps.setFloat(3, price);
			ps.setString(4, author);
			ps.setBinaryStream (5, image );
			ps.setString(6, publisher);
			ps.setLong(7, isbn);
			ps.setInt(8, pages);
			ps.setInt(9, year);
			ps.setInt(10, availability);
			ps.setString(11, language);
			ps.setString(12, description);
			
			ps.executeUpdate();	
			

			PreparedStatement prst = conn.prepareStatement("SELECT * FROM authors WHERE author = '"+author+"'");
			ResultSet resu = prst.executeQuery();
			
			if(resu.next())
			{
				
			}
			else {
				
				int j = 1;
				
				while(true) {
					PreparedStatement pst2 = conn.prepareStatement("SELECT * FROM authors WHERE author_id = '"+j+"'");
					ResultSet res2 = pst2.executeQuery();
					
					if(res2.next())	{
						j++;
						continue;
					}
					else {
						PreparedStatement ps3 = conn.prepareStatement("INSERT INTO authors (author_id, author) VALUES (?, ?)");
						ps3.setInt(1, j);
						ps3.setString(2, author);
						
						ps3.executeUpdate();
						
						break;
					}				
				}
				
			}
			
			PreparedStatement prst3 = conn.prepareStatement("SELECT * FROM genres WHERE genre = '"+genre+"'");
			ResultSet resu3 = prst3.executeQuery();
			
			if(resu3.next())
			{
				
			}
			else {
				
				int k = 1;
				
				while(true) {
					PreparedStatement pst3 = conn.prepareStatement("SELECT * FROM genres WHERE genre_id = '"+k+"'");
					ResultSet res3 = pst3.executeQuery();
					
					if(res3.next())	{
						k++;
						continue;
					}
					else {
						PreparedStatement ps4 = conn.prepareStatement("INSERT INTO genres (genre_id, genre) VALUES (?, ?)");
						ps4.setInt(1, k);
						ps4.setString(2, genre);
						
						ps4.executeUpdate();
						
						break;
					}				
				}
				
			}
			
			PreparedStatement prst4 = conn.prepareStatement("SELECT * FROM languages WHERE language = '"+language+"'");
			ResultSet resu4 = prst4.executeQuery();
			
			if(resu4.next())
			{
				
			}
			else {
				
				int l = 1;
				
				while(true) {
					PreparedStatement pst4 = conn.prepareStatement("SELECT * FROM languages WHERE language_id = '"+l+"'");
					ResultSet res4 = pst4.executeQuery();
					
					if(res4.next())	{
						l++;
						continue;
					}
					else {
						PreparedStatement ps5 = conn.prepareStatement("INSERT INTO languages (language_id, language) VALUES (?, ?)");
						ps5.setInt(1, l);
						ps5.setString(2, language);
						
						ps5.executeUpdate();
						
						break;
					}				
				}
				
			}
			
			ctx.redirect("/admin-book");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public static void genresBooks(Context ctx) throws IOException {
		String genre = ctx.pathParam("name");
		
		ArrayList<book> books = new ArrayList<book>();
		 
		Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM books WHERE genre = '"+genre+"'");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{						
					
					InputStream input = rs1.getBinaryStream("image");
					byte[] bytes = IOUtils.toByteArray(input);
					 
					String image = new String(Base64.getEncoder().encode(bytes));
					
					book book = new book(rs1.getInt("book_id"),rs1.getString("title"),rs1.getString("genre"),rs1.getFloat("price"),rs1.getString("author"),image,rs1.getString("publisher"),rs1.getLong("isbn"),rs1.getInt("pages"),rs1.getInt("year"),rs1.getInt("availability"),rs1.getString("language"),rs1.getString("description"));
					
					books.add(book);
					
				}
				
				ctx.json(books);
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	
	public static void authorsBooks(Context ctx) throws IOException {
		String author = ctx.pathParam("name");
		
		ArrayList<book> books = new ArrayList<book>();
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM books WHERE author = '"+author+"'");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{						
					
					InputStream input = rs1.getBinaryStream("image");
					byte[] bytes = IOUtils.toByteArray(input);
					 
					String image = new String(Base64.getEncoder().encode(bytes));
					
					book book = new book(rs1.getInt("book_id"),rs1.getString("title"),rs1.getString("genre"),rs1.getFloat("price"),rs1.getString("author"),image,rs1.getString("publisher"),rs1.getLong("isbn"),rs1.getInt("pages"),rs1.getInt("year"),rs1.getInt("availability"),rs1.getString("language"),rs1.getString("description"));
					
					books.add(book);
					
				}
				
				ctx.json(books);
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	
	public static void languagesBooks(Context ctx) throws IOException {
		String language = ctx.pathParam("name");
		
		ArrayList<book> books = new ArrayList<book>();
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM books WHERE language = '"+language+"'");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{						
					
					InputStream input = rs1.getBinaryStream("image");
					byte[] bytes = IOUtils.toByteArray(input);
					 
					String image = new String(Base64.getEncoder().encode(bytes));
					
					book book = new book(rs1.getInt("book_id"),rs1.getString("title"),rs1.getString("genre"),rs1.getFloat("price"),rs1.getString("author"),image,rs1.getString("publisher"),rs1.getLong("isbn"),rs1.getInt("pages"),rs1.getInt("year"),rs1.getInt("availability"),rs1.getString("language"),rs1.getString("description"));
					
					books.add(book);
					
				}
				
				ctx.json(books);
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	
	public static void addToCart(Context ctx) {
		String book_id = ctx.pathParam("book_id");
		String quantity = ctx.formParam("quantity");
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM books WHERE book_id = '"+book_id+"'");
			ResultSet rs1 = ps1.executeQuery();
			int i = 1;
			
			if(rs1.next())
			{				
				while(true){
					if(ctx.cookie("name"+i) != null){
						i++;
						continue;
					}
					else{
						break;
					}
				}
				
				ctx.cookie("name"+i,rs1.getString("title").replace(' ', '.'));
				ctx.cookie("price"+i,rs1.getString("price"));
				ctx.cookie("quantity"+i,quantity);
				
				ctx.redirect("/cart");
				
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
    } 
	 }
	
	public static void removeCookies(Context ctx) {
		int i = 1;
		
		while(true){
			if(ctx.cookie("name"+i) != null){
				ctx.removeCookie("name"+i,"/");
				ctx.removeCookie("price"+i,"/");
				ctx.removeCookie("quantity"+i,"/");
				i++;
				continue;
			}
			else{
				break;
			}
		}
		
		ctx.redirect("/cart");
	 }
	
	public static void insertPurchaseSignedIn(Context ctx) {
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String date = sdf.format(dt);
		
		String street = ctx.formParam("street");
		int streetNumber = Integer.parseInt(ctx.formParam("streetNumber"));
		String area = ctx.formParam("area");
		int postcode = Integer.parseInt(ctx.formParam("postcode"));
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			PreparedStatement prst = conn.prepareStatement("SELECT * FROM users WHERE username = '"+ctx.cookie("username")+"'");
			ResultSet resu = prst.executeQuery();
			
			if(resu.next())
			{
				String email = resu.getString("email");
				String phone = resu.getString("phone");
				
				int i = 1;			
					
				while(true){
					if(ctx.cookie("name"+i) != null){
						
						PreparedStatement prst2 = conn.prepareStatement("SELECT * FROM books WHERE title = '"+ctx.cookie("name"+i).replace('.', ' ')+"'");
						ResultSet resu2 = prst2.executeQuery();
						
						if(resu2.next())
						{
							PreparedStatement ps = conn.prepareStatement("INSERT INTO purchases (email, phone, date, street, streetNumber, area, postcode, book_id, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
							ps.setString(1, email);
							ps.setString(2, phone);
							ps.setString (3, date);
							ps.setString(4, street);
							ps.setInt(5, streetNumber);
							ps.setString (6, area );
							ps.setInt(7, postcode);
							ps.setInt(8, resu2.getInt("book_id"));
							ps.setInt(9, Integer.parseInt(ctx.cookie("quantity"+i)));
							
							ps.executeUpdate();
							
							PreparedStatement pst1 = conn.prepareStatement("UPDATE books SET availability = availability - '"+Integer.parseInt(ctx.cookie("quantity"+i))+"' WHERE book_id in ('"+resu2.getInt("book_id")+"')");
							pst1.executeUpdate();
							
							ctx.removeCookie("name"+i,"/");
							ctx.removeCookie("price"+i,"/");
							ctx.removeCookie("quantity"+i,"/");
							
							i++;
							continue;
						}
						
					}
					else{
						break;
					}
				}
			}
			
			ctx.redirect("/past-orders");
		}catch (SQLException e) {
				e.printStackTrace();
			} 
	 }
	
	public static void insertPurchaseNotSignedIn(Context ctx) {
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = 
		new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String date = sdf.format(dt);
		
		String email = ctx.formParam("email");
		String phone = ctx.formParam("phone");
		String street = ctx.formParam("street");
		int streetNumber = Integer.parseInt(ctx.formParam("streetNumber"));
		String area = ctx.formParam("area");
		int postcode = Integer.parseInt(ctx.formParam("postcode"));
		
		Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
						
			int i = 1;			
					
			while(true){
				if(ctx.cookie("name"+i) != null){
						
					PreparedStatement prst2 = conn.prepareStatement("SELECT * FROM books WHERE title = '"+ctx.cookie("name"+i).replace('.', ' ')+"'");
					ResultSet resu2 = prst2.executeQuery();
						
					if(resu2.next())
					{
						PreparedStatement ps = conn.prepareStatement("INSERT INTO purchases (email, phone, date, street, streetNumber, area, postcode, book_id, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
						ps.setString(1, email);
						ps.setString(2, phone);
						ps.setString (3, date);
						ps.setString(4, street);
						ps.setInt(5, streetNumber);
						ps.setString (6, area );
						ps.setInt(7, postcode);
						ps.setInt(8, resu2.getInt("book_id"));
						ps.setInt(9, Integer.parseInt(ctx.cookie("quantity"+i)));
							
						ps.executeUpdate();
						
						PreparedStatement pst1 = conn.prepareStatement("UPDATE books SET availability = availability - '"+Integer.parseInt(ctx.cookie("quantity"+i))+"' WHERE book_id in ('"+resu2.getInt("book_id")+"')");
						pst1.executeUpdate();
							
						ctx.removeCookie("name"+i,"/");
						ctx.removeCookie("price"+i,"/");
						ctx.removeCookie("quantity"+i,"/");
							
						i++;
						continue;
					}
						
				}
				else{
					break;
				}
			}
			
			ctx.redirect("/cart");
		}catch (SQLException e) {
				e.printStackTrace();
			} 
	 }
	 
	public static void getAllBooks(Context ctx) throws IOException {
		 
		 ArrayList<book> books = new ArrayList<book>();
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM books");
				ResultSet rs1 = ps1.executeQuery();
				
				while(rs1.next())
				{						
					
					InputStream input = rs1.getBinaryStream("image");
					byte[] bytes = IOUtils.toByteArray(input);
					 
					String image = new String(Base64.getEncoder().encode(bytes));
					
					book book = new book(rs1.getInt("book_id"),rs1.getString("title"),rs1.getString("genre"),rs1.getFloat("price"),rs1.getString("author"),image,rs1.getString("publisher"),rs1.getLong("isbn"),rs1.getInt("pages"),rs1.getInt("year"),rs1.getInt("availability"),rs1.getString("language"),rs1.getString("description"));
					
					books.add(book);
					
				}
				
				ctx.json(books);
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	 
	 public static void getUser(Context ctx) {
	
		 String username = ctx.pathParam("username");
		 
		 ArrayList<user> users = new ArrayList<user>();
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement prst1 = conn.prepareStatement("SELECT * FROM users WHERE username = '"+username+"'");
				ResultSet resu1 = prst1.executeQuery();
				
				if(resu1.next())
				{
					PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) AS totalComments FROM comments WHERE user_id = '"+resu1.getInt("user_id")+"'");
 	 				ResultSet rs2 = ps2.executeQuery();
 	 				
 	 				if(rs2.next())
 	 				{
 	 					user user = new user(resu1.getInt("user_id"),resu1.getString("name"),resu1.getString("surname"),resu1.getString("email"),resu1.getString("phone"),resu1.getString("username"),rs2.getInt("totalComments"));
 	 					
 						users.add(user);
 	 				}
				}
				
				ctx.json(users);
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	 
	 public static void removeUser(Context ctx) {
			
		 String username = ctx.pathParam("username");
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM users WHERE username = '"+username+"'");
				ResultSet rs2 = ps2.executeQuery();
				
				if(rs2.next())
				{	
					PreparedStatement ps3 = conn.prepareStatement("DELETE FROM comments WHERE user_id = '"+rs2.getInt("user_id")+"'");
					ps3.executeUpdate();
				}
				
				PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE username = '"+username+"'");
				ps.executeUpdate();
				
				ctx.redirect("/admin-remove-user");
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	 
	 public static void removeGenre(Context ctx) {
			
		 String name = ctx.pathParam("name");
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("DELETE FROM books WHERE genre = '"+name+"'");
				ps1.executeUpdate();
				
				PreparedStatement ps2 = conn.prepareStatement("DELETE FROM genres WHERE genre = '"+name+"'");
				ps2.executeUpdate();
				
				ctx.redirect("/admin-genre");
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	 
	 public static void removeAuthor(Context ctx) {
			
		 String name = ctx.pathParam("name");
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("DELETE FROM books WHERE author = '"+name+"'");
				ps1.executeUpdate();
				
				PreparedStatement ps2 = conn.prepareStatement("DELETE FROM authors WHERE author = '"+name+"'");
				ps2.executeUpdate();
				
				ctx.redirect("/admin-author");
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	 
	 public static void removeLanguage(Context ctx) {
			
		 String name = ctx.pathParam("name");
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("DELETE FROM books WHERE language = '"+name+"'");
				ps1.executeUpdate();
				
				PreparedStatement ps2 = conn.prepareStatement("DELETE FROM languages WHERE language = '"+name+"'");
				ps2.executeUpdate();
				
				ctx.redirect("/admin-language");
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	 
	 public static void removeBook(Context ctx) {
		 
		 int book_id = Integer.parseInt(ctx.pathParam("book_id"));
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement ps1 = conn.prepareStatement("DELETE FROM books WHERE book_id = '"+book_id+"'");
				ps1.executeUpdate();
				
				ctx.redirect("/admin-book");
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	 
	 public static void getAllCookies(Context ctx) {
		 
		 ArrayList<cookie> cookies = new ArrayList<cookie>();
		 
		 int i = 1;
		 float sum = 0;
		 
		 while(true){
			if(ctx.cookie("name"+i) != null){
				sum += Float.parseFloat(ctx.cookie("price"+i)) * Float.parseFloat(ctx.cookie("quantity"+i));
				cookie cookie = new cookie(ctx.cookie("name"+i).replace('.', ' '),ctx.cookie("price"+i),ctx.cookie("quantity"+i),sum);
				cookies.add(cookie);
				i++;
				continue;
			}
			else{
				break;
			}
		}
		
		ctx.json(cookies);
	 }
	 
	 
	 public static void getAllPurchases(Context ctx){
		 
		 ArrayList<purchase> purchases = new ArrayList<purchase>();
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement prst = conn.prepareStatement("SELECT * FROM users WHERE username = '"+ctx.cookie("username")+"'");
				ResultSet resu = prst.executeQuery();
				
				if(resu.next())
				{
					String email = resu.getString("email");
					
					PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM purchases WHERE email = '"+email+"'");
					ResultSet rs1 = ps1.executeQuery();
					
					while(rs1.next())
					{		
						PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM books WHERE book_id = '"+rs1.getInt("book_id")+"'");
						ResultSet rs2 = ps2.executeQuery();
						
						if(rs2.next())
						{	
							
						}
						purchase purchase = new purchase(rs2.getString("title"),rs2.getFloat("price"),rs1.getString("email"),rs1.getString("phone"),rs1.getString("date"),rs1.getString("street"),rs1.getInt("streetNumber"),rs1.getString("area"),rs1.getInt("postcode"),rs1.getInt("book_id"),rs1.getInt("quantity"));
						
						purchases.add(purchase);
					}
					
					ctx.json(purchases);
				}
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 
	 }
	 
	 public static void removeComment(Context ctx) {
		 
		 int book_id = Integer.parseInt(ctx.pathParam("book_id"));
		 
		 Connection conn = null;
			try {
				
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				PreparedStatement prst = conn.prepareStatement("SELECT * FROM users WHERE username = '"+ctx.cookie("username")+"'");
				ResultSet resu = prst.executeQuery();
				
				if(resu.next())
				{
					PreparedStatement ps1 = conn.prepareStatement("DELETE FROM comments WHERE book_id = '"+book_id+"' && user_id = '"+resu.getInt("user_id")+"'");
					ps1.executeUpdate();
					
					ctx.redirect("/book-comments/"+book_id);
				}
				
				
			}catch (SQLException e) {
				e.printStackTrace();
	    } 	
	 }
	 
	 
	 public static void getAllComments(Context ctx) {
		 
		 String book_id = ctx.pathParam("book_id");
			
			ArrayList<comment> comments = new ArrayList<comment>();
			 
			 Connection conn = null;
				try {
					
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					
					PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM comments WHERE book_id = '"+book_id+"'");
					ResultSet rs1 = ps1.executeQuery();
					
					while(rs1.next())
					{		
						
						PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM users WHERE user_id = '"+rs1.getInt("user_id")+"'");
						ResultSet rs2 = ps2.executeQuery();
						
						if(rs2.next())
						{	
							comment comment = new comment(rs1.getInt("book_id"),rs1.getString("comment"),rs1.getInt("rating"),rs2.getString("username"),rs1.getInt("user_id"));
							
							comments.add(comment);	
						}
					}
					
					ctx.json(comments);
					
				}catch (SQLException e) {
					e.printStackTrace();
		    } 
	 }
	 
	 public static void getBook(Context ctx) throws IOException {
		 
		 String book_id = ctx.pathParam("book_id");

			ArrayList<book> books = new ArrayList<book>();
			 
			 Connection conn = null;
				try {
					
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					
					PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM books WHERE book_id = '"+book_id+"'");
					ResultSet rs1 = ps1.executeQuery();
					
					while(rs1.next())
					{						
						
						InputStream input = rs1.getBinaryStream("image");
						byte[] bytes = IOUtils.toByteArray(input);
						 
						String image = new String(Base64.getEncoder().encode(bytes));
						
						book book = new book(rs1.getInt("book_id"),rs1.getString("title"),rs1.getString("genre"),rs1.getFloat("price"),rs1.getString("author"),image,rs1.getString("publisher"),rs1.getLong("isbn"),rs1.getInt("pages"),rs1.getInt("year"),rs1.getInt("availability"),rs1.getString("language"),rs1.getString("description"));
						
						books.add(book);
						
					}
					
					ctx.json(books);
					
				}catch (SQLException e) {
					e.printStackTrace();
		    } 
	 }

}

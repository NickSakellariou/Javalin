package javalinvue;

import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.vue.VueComponent;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.col;

import org.apache.commons.io.IOUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;

public class Main {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/booktime?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	
	static final String USER = "admin";
	static final String PASS = "psw1";

	public static void main(String[] args) {
		
		Javalin app = Javalin.create(config -> {
			config.enableWebjars();
            config.addStaticFiles("/vue");
        }).start(7777);
		
		 SparkSession spark = SparkSession.builder()
		            .appName("spark")
		            .config("spark.master", "local")
		            .getOrCreate();
        
		app.get("/", new VueComponent("<home-page></home-page>"));
		
		app.get("/matrix-factorization", ctx -> {
			if(ctx.cookie("username") != null)
			{		
				Connection conn = null;
				try {
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					
					PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = '"+ctx.cookie("username")+"'");
					ResultSet rs = ps.executeQuery();
					
					if(rs.next())
					{	
						PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM comments WHERE user_id = '"+rs.getInt("user_id")+"'");
		 				ResultSet rs2 = ps2.executeQuery();
		 				
		 				if(rs2.next())
		 				{
		 					
		 					String username = ctx.cookie("username");
		 					int[] result = runJdbcDatasetExample(spark,username);
		 					
		 					ArrayList<book> books = new ArrayList<book>();
		 					
		 					for(int i = 0;i<5;i++)
		 					{			
		 						PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM books WHERE book_id = '"+result[i]+"'");
		 						ResultSet rs1 = ps1.executeQuery();
		 							
		 						while(rs1.next())
		 						{						
		 								
		 							InputStream input = rs1.getBinaryStream("image");
		 							byte[] bytes = IOUtils.toByteArray(input);
		 								 
		 							String image = new String(Base64.getEncoder().encode(bytes));
		 								
		 							book book = new book(rs1.getInt("book_id"),rs1.getString("title"),rs1.getString("genre"),rs1.getFloat("price"),rs1.getString("author"),image,rs1.getString("publisher"),rs1.getLong("isbn"),rs1.getInt("pages"),rs1.getInt("year"),rs1.getInt("availability"),rs1.getString("language"),rs1.getString("description"));
		 								
		 							books.add(book);
		 								
		 						}
		 					}
		 					ctx.json(books);
		 					
		 				}
					}
					
				}catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		});
		
		app.error(404, "html", new VueComponent("<not-found></not-found>"));
		
		app.post("/search", UserController::search);
		
		app.get("/search-failure", new VueComponent("<search-failure></search-failure>"));
		
		app.get("/faq", new VueComponent("<faq></faq>"));
		
		app.get("/contact", new VueComponent("<contact></contact>"));
		
		app.get("/register", new VueComponent("<register></register>"));
		
		app.post("/registerUser", UserController::registerUser);
		
		app.get("/register-failure", new VueComponent("<register-failure></register-failure>"));
		
		app.get("/login-failure1", new VueComponent("<login-failure1></login-failure1>"));
		
		app.get("/login-failure2", new VueComponent("<login-failure2></login-failure2>"));
		
		app.post("/login", UserController::login);
		
		app.post("/change-password", UserController::changePassword);
    	
    	app.get("/api/username", UserController::username);
    	
    	app.get("/logout", UserController::logout);
    	
    	app.get("/users/:username", new VueComponent("<user-profile></user-profile>"));
    	
    	app.get("/api/users/:username", UserController::getUser);
    	
    	app.get("/admin", new VueComponent("<admin></admin>"));
    	
    	app.get("/admin-statistics", new VueComponent("<admin-statistics></admin-statistics>"));
    	
    	app.get("/genres", new VueComponent("<genres></genres>"));
    	
    	app.get("/genres/:name", new VueComponent("<genres-books></genres-books>"));
    	
    	app.get("/api/genres", UserController::genres);
    	
    	app.get("/authors", new VueComponent("<authors></authors>"));
    	
    	app.get("/authors/:name", new VueComponent("<authors-books></authors-books>"));
    	
    	app.get("/api/authors", UserController::authors);
    	
    	app.get("/languages", new VueComponent("<languages></languages>"));
    	
    	app.get("/languages/:name", new VueComponent("<languages-books></languages-books>"));
    	
    	app.get("/api/languages", UserController::languages);
    	
    	app.get("/api/genres-books/:name", UserController::genresBooks);
    	
    	app.get("/api/authors-books/:name", UserController::authorsBooks);
    	
    	app.get("/api/languages-books/:name", UserController::languagesBooks);
    	
    	app.get("/admin-remove-user", new VueComponent("<admin-remove-user></admin-remove-user>"));
    	
    	app.get("/api/admin-all-users", UserController::adminAllUsers);
    	
    	app.get("/api/admin-remove-user/:username", UserController::removeUser);
    	
    	app.get("/admin-genre", new VueComponent("<admin-genre></admin-genre>"));
    	
    	app.post("/api/admin-add-genre", UserController::adminAddGenre);
    	
    	app.get("/api/admin-remove-genre/:name", UserController::removeGenre);
    	
    	app.post("/api/admin-modify-genre/:genre_id", UserController::adminModifyGenre);
    	
    	app.get("/admin-author", new VueComponent("<admin-author></admin-author>"));
    	
    	app.post("/api/admin-add-author", UserController::adminAddAuthor);
    	
    	app.get("/api/admin-remove-author/:name", UserController::removeAuthor);
    	
    	app.post("/api/admin-modify-author/:author_id", UserController::adminModifyAuthor);
    	
    	app.get("/admin-language", new VueComponent("<admin-language></admin-language>"));
    	
    	app.post("/api/admin-add-language", UserController::adminAddLanguage);
    	
    	app.get("/api/admin-remove-language/:name", UserController::removeLanguage);
    	
    	app.post("/api/admin-modify-language/:language_id", UserController::adminModifyLanguage);
    	
    	app.get("/admin-book", new VueComponent("<admin-book></admin-book>"));
    	
    	app.get("/api/all-books", UserController::getAllBooks);
    	
    	app.post("/api/admin-add-book", UserController::adminAddBook);
    	
    	app.get("/api/admin-remove-book/:book_id", UserController::removeBook);
    	
    	app.post("/api/admin-modify-book/:book_id", UserController::adminModifyBook);
    	
    	app.get("/books", new VueComponent("<books></books>"));
    	
    	app.post("/add-to-cart/:book_id", UserController::addToCart);
    	
    	app.get("/cart", new VueComponent("<cart></cart>"));
    	
    	app.get("/api/cart", UserController::getAllCookies);
    	
    	app.get("/api/remove-cookies", UserController::removeCookies);
    	
    	app.get("/checkout-signed-in", new VueComponent("<checkout-signed-in></checkout-signed-in>"));
    	
    	app.get("/checkout-not-signed-in", new VueComponent("<checkout-not-signed-in></checkout-not-signed-in>"));
    	
    	app.post("/api/insert-purchase-signed-in", UserController::insertPurchaseSignedIn);
    	
    	app.post("/api/insert-purchase-not-signed-in", UserController::insertPurchaseNotSignedIn);
    	
    	app.get("/past-orders", new VueComponent("<past-orders></past-orders>"));
    	
    	app.get("/api/all-purchases", UserController::getAllPurchases);
    	
    	app.get("/book-comments/:book_id", new VueComponent("<book-comments></book-comments>"));
    	
    	app.get("/api/book-comments-book/:book_id", UserController::getBook);
    	
    	app.get("/api/book-comments-comment/:book_id", UserController::getAllComments);
    	
    	app.post("/add-comment/:book_id", UserController::addComment);
    	
    	app.get("/api/remove-comment/:book_id", UserController::removeComment);
	}
	
	private static int[] runJdbcDatasetExample(SparkSession spark, String username) {
		
		Dataset<Row> jdbcDF = spark.read()
			.format("jdbc")
			.option("url", "jdbc:mysql://localhost/booktime?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true")
			.option("dbtable", "booktime.comments")
			.option("user", "admin")
			.option("password", "psw1")
			.load();
		
		Dataset<Row>[] splits = jdbcDF.randomSplit(new double[]{0.8, 0.2});
		Dataset<Row> training = splits[0];
		Dataset<Row> test = splits[1];
		
		ALS als = new ALS()
				.setRank(3)
				.setNumBlocks(1)
				.setMaxIter(10)
			    .setUserCol("user_id")
			    .setItemCol("book_id")
			    .setRatingCol("rating");
		ALSModel model = als.fit(training);
		
		model.setColdStartStrategy("drop");
	    Dataset<Row> predictions = model.transform(test);
	    
	    RegressionEvaluator evaluator = new RegressionEvaluator()
	    		.setMetricName("rmse")
	    	    .setLabelCol("rating")
	    	    .setPredictionCol("prediction");
	    double rmse = evaluator.evaluate(predictions);
	    System.out.println("Root-mean-square error = " + rmse);
	    
	    Connection conn = null;
		try {
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			PreparedStatement prst1 = conn.prepareStatement("SELECT * FROM users WHERE username = '"+username+"'");
			ResultSet resu1 = prst1.executeQuery();
			
			if(resu1.next()) {
				
				Dataset<Row> user = jdbcDF.select(als.getUserCol()).where("user_id =="+resu1.getInt("user_id"));
				
				Dataset<Row> userSubsetRecs = model.recommendForUserSubset(user, 5);
				
				Object[] recs = (Object[]) userSubsetRecs.take(1);
				
				Object results = recs[0];
				
				System.out.println(userSubsetRecs.where("user_id =="+resu1.getInt("user_id")).showString(3, 0, true));
				
			    List<Object> list = ((Row) results).getList(1);
			   	    
			    int[] result = new int[5];
			      result[0] = ((Row) list.get(0)).getInt(0);
			      result[1] = ((Row) list.get(1)).getInt(0);
			      result[2] = ((Row) list.get(2)).getInt(0);
			      result[3] = ((Row) list.get(3)).getInt(0);
			      result[4] = ((Row) list.get(4)).getInt(0);
			      
			    return result;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

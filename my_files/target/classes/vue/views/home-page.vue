<template id="home-page">
	<div>
		<nav class="navbar navbar-expand-sm bg-info navbar-dark">
		  <ul class="navbar-nav">
			<li class="nav-item active">
			  <a class="nav-link" href="/">HOME</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/books">BOOKS</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/cart">CART</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/past-orders">ORDERS</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/faq">F.A.Q.</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/contact">CONTACT</a>
			</li>
			
			<div v-for="username in usernames">
				<div v-if="username.value !== null && username.value !== 'admin'">
					<div class="profile">
						<a :href="`/users/${username.value}`" class="material-icons" style="font-size:48px;color:purple">account_circle</a>	
					</div>
					<div class="logout">
						<form action="/logout">
							<input type="submit" class="btn btn-danger" value="LogOut">
						</form>
					</div>
				</div>
				<div style="cursor: pointer;" v-if="username.value == null">
				  <i onclick="document.getElementById('id01').style.display='block'" class="material-icons" style="font-size:48px">account_circle</i>
				</div>
				<div v-if="username.value == 'admin'">
				  <div style="cursor: pointer;" class="profile">
						<i onclick="location.href = '/admin';" class="material-icons" style="font-size:48px;color:purple">account_circle</i>
					</div>
					<div class="logout">
						<form action="/logout">
							<input type="submit" class="btn btn-danger" value="LogOut">
						</form>
					</div>
				</div>
			</div>
		</ul>
		<div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item">
					<form class="search" action="/search" method="post">
					  <input type="text" placeholder="Search.." name="search">
					  <button type="submit" style="font-size:1px"><i class="material-icons">search</i></button>
					</form>
				</li>
			</ul>
		</div>
		</nav>
		<br>
		<div class="homePageRow">
			<h1><b>Welcome to BookTime!</b></h1>
			<h4>Now your favourite bookstores can help you buy any book you want easily and quickly with our new website!</h4>
			<h4>Nonetheless, you can still find us at these addresses :</h4>
			<h4><b>Kalumnou 105, Athens</b></h4>
			<h4><b>Aristotelous 82, Thessaloniki</b></h4>
		</div>
		<br>
		<div class="homePageImages">
			<span >
				<img src="http://localhost:7777/icons/homePage1.jpg">
			</span>
			<span>
				<img src="http://localhost:7777/icons/homePage2.jpg">
			</span>		
		</div>
		<br>
		<br>
		<br>
		<div v-for="username in usernames">
			<div v-if="username.value !== null && username.value !== 'admin' && books.length>0">
				<div class="homePageRow1">
					<h1>Suggestions for you</h1>
				</div>
			</div>
		</div>
		<br>
		<div class="homePageRow3">
			<div v-for="book in books">
				<div class="homePageRow2">
					<div>
						<a :href="`/book-comments/${book.book_id}`"><img :src="`data:image/jpeg;base64,${book.image}`"/></a>
						<br>
						<br>
						<h4><b>Title : </b>{{book.title}}</h4>
						<br>
						<h4><b>Genre : </b>{{book.genre}}</h4>
						<br>
						<h4><b>Price : </b>{{book.price}} €</h4>
						<br>
						<h4><b>Author : </b>{{book.author}}</h4>
						<br>
						<h4><b>Language : </b>{{book.language}}</h4>
						<br>
						<span v-if="book.availability > 0">
							<form :action="`/add-to-cart/${book.book_id}`" method="POST">
								<label for="quantity" > Quantity (between 1 and {{book.availability}}):</label>
								<input type="number" name="quantity"  min="1":max="book.availability" required>
								<br>
								<br>
								<input type="submit" class="btn btn-primary" value="Add to cart" >
							</form>
						</span>
						<span v-if="book.availability == 0">
							<h4>This book is unavailable to purchase for the moment</h4>
						</span>
					</div>
				</div>
			</div>
			<br>
			<br>
		</div>
		<br>
		<br>
		<div class="footer">
			&copy;
			<a href="http://www.ds.unipi.gr/" target="_blank">2021 DS_UNIPI
			</a>.
			All rights reserved.
		</div>
	</div>
</template>

<script>
    Vue.component("home-page", {
    	template: "#home-page",
    	data: () => ({
    		usernames: [],
    		books: [],
        }),
        created() {
            fetch("/api/username")
                .then(res => res.json())
                .then(res => this.usernames = res)
            fetch("/suggestions")
                .then(res => res.json())
                .then(res => this.books = res);
        }
    });
</script>

<style>
	hr {
	  border: 1px solid #f1f1f1;
	  margin-bottom: 25px;
	}
	
	.homePageRow{
		padding: 15px;
		background-color: white;
		text-align: center;
		width:950px;
		margin: auto;
		border-radius:25px;
		box-shadow:0px 3px 12px white;
	}
	
	.homePageimages{
		width:950px;
		padding: 20px;
		text-align: center;
		margin: auto;
		display:flex;
		flex:33%;
	}
	
	.homePageimages img{
		width: 450px;
		height: 275px;
		margin:10px;
	}
	
	.homePageRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:380px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	.homePageRow2{
		background-color: white;
		padding: 20px;
		margin: 15px;
		width:600px;
		text-align: center;
	}
	
	.homePageRow2 div{
		padding:8px;
		border-style: solid;
		flex:33%;
	}
	
	.homePageRow2 img{
		width: 200px;
		height:330px;
	}
	
	.homePageRow3{
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
	}
	
	.footer{
		position:relative;
		top:250px;
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
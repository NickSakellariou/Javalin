<template id="books">
	<div>
		<nav class="navbar navbar-expand-sm bg-info navbar-dark">
		  <ul class="navbar-nav">
			<li class="nav-item">
			  <a class="nav-link" href="/">HOME</a>
			</li>
			<li class="nav-item active">
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
		<br>
		<div class="booksRow1">
			<h1>Sort books by</h1>
		</div>
		<br>
		<div class="booksRow3">
			<div class="booksRow2">
				<a href="/genres">
					<h1>Genre</h1>
				</a>
			</div>
			<br>
			<br>
			<div class="booksRow2">
				<a href="/authors">
					<h1>Author</h1>
				</a>
			</div>
			<br>
			<br>
			<div class="booksRow2">
				<a href="/languages">
					<h1>Language</h1>
				</a>
			</div>
		</div>
		<br>
		<br>
		<br>
		<div class="booksRow4">
			<h1>Best selling books</h1>
		</div>
		<br>
		<br>
		<div class="booksRow6">
			<div v-for="book in books">
				<div class="booksRow5">
					<div>
						<br>
						<a :href="`/book-comments/${book.book_id}`"><img :src="`data:image/jpeg;base64,${book.image}`"/></a>
						<br>
						<br>
						<h4><b>Title : </b>{{book.title}}</h4>
						<br>
						<h4><b>Price : </b>{{book.price}} €</h4>
						<br>
						<h4><b>Author : </b>{{book.author}}</h4>
						<br>
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
				<br>
				<br>
			</div>
		</div>
		<br>
		<br>
		<div class="booksFooter">
			&copy;
			<a href="http://www.ds.unipi.gr/" target="_blank">2021 DS_UNIPI
			</a>.
			All rights reserved.
		</div>
	</div>
</template>

<script>
    Vue.component("books", {
    	template: "#books",
    	data: () => ({
    		usernames: [],
    		books: [],
        }),
        created() {
            fetch("/api/username")
                .then(res => res.json())
                .then(res => this.usernames = res)
            fetch("/api/best-selling-books")
	            .then(res => res.json())
	            .then(res => this.books = res);
        }
    });
</script>

<style>
	.booksRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:275px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}

	.booksRow2{
		background-color: white;
		color: black;
		text-align: center;
		width:180px;
		margin: 25px;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.booksRow2 a{
		color:black;	
	}
	
	.booksRow3{
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
	}
	
	.booksRow4{
		background-color: white;
		color: black;
		text-align: center;
		width:350px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.booksRow5{
		background-color: white;
		padding: 20px;
		margin: 15px;
		width:600px;
		text-align: center;
	}
	
	.booksRow5 div{
		padding:8px;
		border-style: solid;
		flex:33%;
	}
	
	.booksRow5 img{
		width: 200px;
		height:330px;
	}
	
	.booksRow6{
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
	}
	
	.booksFooter{
		position:relative;
		top:250px;
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
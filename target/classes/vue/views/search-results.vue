<template id="search-results">
	<div>
		<nav class="navbar navbar-expand-sm bg-info navbar-dark">
		  <ul class="navbar-nav">
			<li class="nav-item">
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
		<br>
		<br>
		<div v-if="books.length>0">
			<div class="searchResultsRow1">
				<h1>Search results</h1>
			</div>
		</div>
		<br>
		
		<div v-if="books.length == 0">
			<div class="searchResultsRow">
				<h1>No results</h1>
			</div>
		</div>
		
		<div class="searchResultsRow3">
			<div v-for="book in books">
				<div class="searchResultsRow2">
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
		<br>
		<br>
		<br>
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
    Vue.component("search-results", {
    	template: "#search-results",
    	data: () => ({
    		usernames: [],
    		books: [],
        }),
        created() {
    		const search = this.$javalin.pathParams["search"];
            fetch("/api/username")
                .then(res => res.json())
                .then(res => this.usernames = res)
            fetch(`/search-results-books/${search}`)
                .then(res => res.json())
                .then(res => this.books = res);
        }
    });
</script>

<style>
	.searchResultsRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:280px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.searchResultsRow2{
		background-color: white;
		padding: 20px;
		margin: 15px;
		width:600px;
		text-align: center;
	}
	
	.searchResultsRow2 div{
		padding:8px;
		border-style: solid;
		flex:33%;
	}
	
	.searchResultsRow2 img{
		width: 200px;
		height:330px;
	}
	
	.searchResultsRow{
		background-color: white;
		color: black;
		text-align: center;
		width:210px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.searchResultsRow3{
		margin:auto;
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
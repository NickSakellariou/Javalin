<template id="admin-book">
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
			<div style="cursor: pointer;" class="adminProfile">
				<i onclick="location.href = '/admin';" class="material-icons" style="font-size:48px;color:purple">account_circle</i>
			</div>
			<div class="adminLogout">
				<form action="/logout">
					<input type="submit" class="btn btn-danger" value="LogOut">
				</form>
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
		<div class="adminBookRow2">
			<div>
				<form action="/api/admin-add-book" method="POST" enctype="multipart/form-data">
					<label for="title" ><b> ADD A BOOK </b></label>
					<br>
					<br>
					<input type="text" name="title" placeholder="Title of book" required>
					<br>
					<br>
					<input type="text" name="genre" placeholder="Genre of book" required>
					<br>
					<br>
					<input type="text" name="price" placeholder="Price of book" required>
					<br>
					<br>
					<input type="text" name="author" placeholder="Author of book" required>
					<br>
					<br>
					<input type="text" name="publisher" placeholder="Publisher of book" required>
					<br>
					<br>
					<input type="text" name="isbn" pattern="[0-9]{13}" title="Must contain 13 numbers" placeholder="ISBN of book" required>
					<br>
					<br>
					<input type="text" name="pages" pattern="[0-9]{1,}" title="Must contain only numbers" placeholder="Pages of book" required>
					<br>
					<br>
					<input type="text" name="year" pattern="[0-9]{1,}" title="Must contain only numbers" placeholder="Year of book" required>
					<br>
					<br>
					<input type="text" name="availability" pattern="[0-9]{1,}" title="Must contain only numbers" placeholder="Availability of book" required>
					<br>
					<br>
					<input type="text" name="language" placeholder="Language of book" required>
					<br>
					<br>
					<input type="text" name="description" placeholder="Description of book" required>
					<br>
					<br>
					<label for="image" ><b> Image </b></label>
					<br>
					<input type="file" name="image" required>
					<br>
					<br>
					<button type="submit" class="btn btn-primary">Add</button>
				</form>
			</div>
		</div>
		<br>
		<br>
		<br>
		<div class="adminBookRow1">
			<h1>All books</h1>
		</div>
		<br>
		<div v-for="book in books">
			<div class="adminBookRow2">
				<div>
					<br>
					<img :src="`data:image/jpeg;base64,${book.image}`"/>
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
					<h4><b>Publisher : </b>{{book.publisher}}</h4>
					<br>
					<h4><b>ISBN : </b>{{book.isbn}}</h4>
					<br>
					<h4><b>Pages : </b>{{book.pages}}</h4>
					<br>
					<h4><b>Year published: </b>{{book.year}}</h4>
					<br>
					<h4><b>Availability : </b>{{book.availability}}</h4>
					<br>
					<h4><b>Language : </b>{{book.language}}</h4>
					<br>
					<h4><b>Description : </b>{{book.description}}</h4>
					<br>
					<form :action="`/api/admin-modify-book/${book.book_id}`" method="POST" enctype="multipart/form-data">
						<br>
						<label for="title" ><b> MODIFY THE BOOK </b></label>
						<br>
						<input type="text" name="title" placeholder="New title of book" required>
						<br>
						<br>
						<input type="text" name="genre" placeholder="New genre of book" required>
						<br>
						<br>
						<input type="text" name="price" placeholder="New price of book" required>
						<br>
						<br>
						<input type="text" name="author" placeholder="New author of book" required>
						<br>
						<br>
						<input type="text" name="publisher" placeholder="New publisher of book" required>
						<br>
						<br>
						<input type="text" name="isbn" pattern="[0-9]{13}" title="Must contain 13 numbers" placeholder="New ISBN of book" required>
						<br>
						<br>
						<input type="text" name="pages" pattern="[0-9]{1,}" title="Must contain only numbers" placeholder="New pages of book" required>
						<br>
						<br>
						<input type="text" name="year" pattern="[0-9]{1,}" title="Must contain only numbers" placeholder="New year of book" required>
						<br>
						<br>
						<input type="text" name="availability" pattern="[0-9]{1,}" title="Must contain only numbers" placeholder="New availability of book" required>
						<br>
						<br>
						<input type="text" name="language" placeholder="New language of book" required>
						<br>
						<br>
						<input type="text" name="description" placeholder="New description of book" required>
						<br>
						<br>
						<label for="image" ><b> Image </b></label>
						<br>
						<input type="file" name="image" required>
						<br>
						<br>
						<input type="submit" class="btn btn-primary" value="Modify">
					</form>
					<br>
					<a :href="`/api/admin-remove-book/${book.book_id}`" class="btn btn-danger">Remove book</a>
					<br>
					<br>
				</div>
			</div>
			<br>
			<br>
		</div>
		
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
    Vue.component("admin-book", {
    	template: "#admin-book",
    	data: () => ({
    		books: [],
        }),
        created() {
            fetch("/api/all-books")
                .then(res => res.json())
                .then(res => this.books = res);
        }
    });
</script>

<style>
	.adminProfile{
		width:5%;
		margin-right:9.5%;
		float: left;
	}
		
	.adminLogout{
		width:10%;
	    float: left;
		position:relative;
		top: 6px;
	}
	
	.adminBookRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:210px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.adminBookRow2{
		background-color: white;
		padding: 20px;
		margin: auto;
		width:700px;
		text-align: center;
	}
	
	.adminBookRow2 div{
		padding:8px;
		border-style: solid;
		flex:33%;
	}
	
	.adminBookRow2 img{
		width: 250px;
	}
	
	.footer{
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
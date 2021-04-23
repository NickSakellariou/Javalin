<template id="book-comments">
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
		<br>
		<div v-for="book in books">
			<div class="bookCommentsRow1">
				<div>
					<br>
					<img :src="`data:image/jpeg;base64,${book.image}`"/>
					<br>
					<br>
					<h4><b>Title : </b>{{book.title}}</h4>
					<br>
					<h4><b>Genre : </b>{{book.genre}}</h4>
					<br>
					<h4><b>Price : </b>{{book.price}}</h4>
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
					<h4><b>Language : </b>{{book.language}}</h4>
					<br>
					<h4><b>Description : </b>{{book.description}}</h4>
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
					<br>
					<br>
				</div>
			</div>
			<br>
			<br>
		</div>
		
		<br>
		<div class="bookCommentsRow">
			<h1>Comments and ratings of users</h1>
		</div>
		<br>
		
		<div v-for="username in usernames">
			<div v-if="username.value !== null && username.value !== 'admin'">
				<div v-if="comments.length>0" :set="mycomment = 0">
					<div v-for="(comment, key) in comments">
						<br>
						<br>
						<div v-if="username.value == comment.username">
							<div class="bookCommentsRow2">
								<div v-for="book in books" :set="mycomment = 1">
									<h2><b> Your comment : </b></h2>
									<h4><b> Username : </b>{{comment.username}}</h4>
									<h4><b> Comment : </b>{{comment.text}}</h4>
									<h4><b> Rating : </b>{{comment.rating}}</h4>
									<br>
									<a class="btn btn-danger" :href="`/api/remove-comment/${book.book_id}`">
										Remove comment
									</a>
									<br>
									<br>
								</div>
							</div>
						</div>
						<div v-if="username.value !== comment.username">
							<div class="bookCommentsRow2">
								<br>
								<div>
									<h4><b> Username : </b>{{comment.username}}</h4>
									<h4><b> Comment : </b>{{comment.text}}</h4>
									<h4><b> Rating : </b>{{comment.rating}}</h4>	
								</div>
							</div>
							<div v-if="key == comments.length - 1 && mycomment == 0">
								<br>
								<br>
								<br>
								<br>
								<div class="bookCommentsRow3">
									<h1>Add a comment and a rating</h1>
								</div>
								<br>
								<div v-for="book in books">			
									<div class="bookCommentsRow4">
										<form :action="`/add-comment/${book.book_id}`" method="post">
											<br>
											<textarea maxlength="200" rows="4" cols="50" name="comment" required></textarea>
											<br>
											<br>
											<input type="text" name="rating" pattern="[1-5]" placeholder="Add a rating 1-5" required>
											<br>
											<br>
											<input type="submit" class="btn btn-primary" value="Add">
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div v-if="comments.length==0">
					<div class="bookCommentsRow5">
						<div>
							<h4>There are no comments yet</h4>
						</div>
					</div>
					<br>
					<br>
					<br>
					<br>
					<div class="bookCommentsRow3">
						<h1>Add a comment and a rating</h1>
					</div>
					<br>
					<div v-for="book in books">			
						<div class="bookCommentsRow4">
							<form :action="`/add-comment/${book.book_id}`" method="post">
								<br>
								<textarea maxlength="200" rows="4" cols="50" name="comment" required></textarea>
								<br>
								<br>
								<input type="text" name="rating" pattern="[1-5]" placeholder="Add a rating 1-5" required>
								<br>
								<br>
								<input type="submit" class="btn btn-primary" value="Add">
							</form>
						</div>
					</div>
				</div>
			</div>
			<div v-if="username.value == null || username.value == 'admin'">
				<div v-if="comments.length>0">
					<div v-for="comment in comments">
						<br>
						<br>
						<div class="bookCommentsRow2">
							<div>
								<h4><b> Username : </b>{{comment.username}}</h4>
								<h4><b> Comment : </b>{{comment.text}}</h4>
								<h4><b> Rating : </b>{{comment.rating}}</h4>	
							</div>
						</div>
					</div>
				</div>
				<div v-if="comments.length==0">
					<div class="bookCommentsRow5">
						<div>
							<h4>There are no comments yet</h4>
						</div>
					</div>
				</div>
			</div>	
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
    Vue.component("book-comments", {
    	template: "#book-comments",
    	data: () => ({
    		usernames: [],
    		books: [],
    		comments: []
        }),
        created() {
    		const book_id = this.$javalin.pathParams["book_id"];
            fetch("/api/username")
                .then(res => res.json())
                .then(res => this.usernames = res)
                .catch(() => alert("Error while fetching users"))
            fetch(`/api/book-comments-book/${book_id}`)
                .then(res => res.json())
                .then(res => this.books = res)
                .catch(() => alert("Error while fetching books"))
            fetch(`/api/book-comments-comment/${book_id}`)
                .then(res => res.json())
                .then(res => this.comments = res)
                .catch(() => alert("Error while fetching comments"));
        }
    });
</script>

<style>
	.bookCommentsRow{
		background-color: white;
		color: black;
		text-align: center;
		width:590px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	.bookCommentsRow1{
		background-color: white;
		padding: 20px;
		margin: auto;
		width:700px;
		text-align: center;
	}
	
	.bookCommentsRow1 div{
		padding:8px;
		border-style: solid;
		flex:33%;
	}
	
	.bookCommentsRow1 img{
		width: 250px;
	}
	
	.bookCommentsRow2{
		background-color: white;
		display:flex;
		padding: 20px;
		margin: auto;
		width:600px;
		text-align: center;
	}
	.bookCommentsRow2 div{
		padding:10px;
		border-style: solid;
		flex:33%;
	}
	.bookCommentsRow2 h4{
		color: black;
		text-align: center;
	}
	
	.bookCommentsRow3{
		background-color: white;
		color: black;
		text-align: center;
		width:530px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.bookCommentsRow4{
		padding: 20px;
		margin: auto;
		width:630px;
		height:330px;
		text-align: center;
		background-color: #006699;
		border-radius:25px;
		box-shadow:0px 3px 12px white;
	}
	.bookCommentsRow4 div{
		padding:10px;
		border-style: solid;
		flex:33%;
	}
	
	.bookCommentsRow5{
		background-color: white;
		display:flex;
		padding: 20px;
		margin: auto;
		width:400px;
		text-align: center;
	}
	.bookCommentsRow5 div{
		padding:10px;
		border-style: solid;
		flex:33%;
	}
	.bookCommentsRow5 h4{
		color: black;
		text-align: center;
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
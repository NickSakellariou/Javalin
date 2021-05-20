<template id="admin-statistics">
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
		<div class="adminStatisticsRow1">
			<h1>Statistics of marketplace</h1>
		</div>
		<br>
		<div class="adminStatisticsRow2">
			<div class="adminStatisticsRow3">
				<br>
				<div :set="adminBooks = 0">
					<div v-for="(book, key) in books" :set="adminBooks += 1">
						<div v-if="key == books.length - 1">
							<h4><b>Total number of books : {{adminBooks}}</b></h4>
						</div>
					</div>
				</div>
				<br>
				<div :set="adminGenres = 0">
					<div v-for="(genre, key) in genres" :set="adminGenres += 1">
						<div v-if="key == genres.length - 1">
							<h4><b>Total number of genres : {{adminGenres}}</b></h4>
						</div>
					</div>
					<div v-for="genre in genres">
						<div v-if="genre.count == 0">
							<h5> For the genre <b>{{genre.name}}</b>, there are <b>no</b> books</h5>
						</div>
						<div v-if="genre.count == 1">
							<h5> For the genre <b>{{genre.name}}</b>, there is <b>1</b> book</h5>
						</div>
						<div v-if="genre.count > 1">
							<h5> For the genre <b>{{genre.name}}</b>, there are <b>{{genre.count}}</b> books</h5>
						</div>
					</div>
				</div>
				<br>
				<div :set="adminAuthors = 0">
					<div v-for="(author, key) in authors" :set="adminAuthors += 1">
						<div v-if="key == authors.length - 1">
							<h4><b>Total number of authors : {{adminAuthors}}</b></h4>
						</div>
					</div>
					<div v-for="author in authors">
						<div v-if="author.count == 0">
							<h5> For the author <b>{{author.name}}</b>, there are <b>no</b> books</h5>
						</div>
						<div v-if="author.count == 1">
							<h5> For the author <b>{{author.name}}</b>, there is <b>1</b> book</h5>
						</div>
						<div v-if="author.count > 1">
							<h5> For the author <b>{{author.name}}</b>, there are <b>{{author.count}}</b> books</h5>
						</div>
					</div>
				</div>
				<br>
				<div :set="adminLanguages = 0">
					<div v-for="(language, key) in languages" :set="adminLanguages += 1">
						<div v-if="key == languages.length - 1">
							<h4><b>Total number of languages : {{adminLanguages}}</b></h4>
						</div>
					</div>
					<div v-for="language in languages">
						<div v-if="language.count == 0">
							<h5> For the language <b>{{language.name}}</b>, there are <b>no</b> books</h5>
						</div>
						<div v-if="language.count == 1">
							<h5> For the language <b>{{language.name}}</b>, there is <b>1</b> book</h5>
						</div>
						<div v-if="language.count > 1">
							<h5> For the language <b>{{language.name}}</b>, there are <b>{{language.count}}</b> books</h5>
						</div>
					</div>
				</div>
				<br>
				<div :set="adminUsers = 0">
					<div v-for="(user, key) in users" :set="adminUsers += 1">
						<div v-if="key == users.length - 1">
							<h4><b>Total number of registered users : {{adminUsers}}</b></h4>
						</div>
					</div>
					<div v-for="user in users">
						<div v-if="user.count_comments == 0">
							<h5> User <b>{{user.username}}</b> has made <b>no</b> comments</h5>
						</div>
						<div v-if="user.count_comments == 1">
							<h5> User <b>{{user.username}}</b> has made <b>1</b> comment</h5>
						</div>
						<div v-if="user.count_comments > 1">
							<h5> User <b>{{user.username}}</b> has made <b>{{user.count_comments}}</b> comments</h5>
						</div>
					</div>
				</div>
				<br>
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
    Vue.component("admin-statistics", {
    	template: "#admin-statistics",
    	data: () => ({
    		users: [],
    		books: [],
    		genres: [],
    		authors: [],
    		languages: [],
        }),
        created() {
            fetch("/api/admin-all-users")
                .then(res => res.json())
                .then(res => this.users = res)
            fetch("/api/all-books")
                .then(res => res.json())
                .then(res => this.books = res)
            fetch("/api/genres")
                .then(res => res.json())
                .then(res => this.genres = res)
            fetch("/api/authors")
                .then(res => res.json())
                .then(res => this.authors = res)
            fetch("/api/languages")
                .then(res => res.json())
                .then(res => this.languages = res);
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
		left: 13px;
	}
	
	.adminProfileRow{
		background-color: white;
		color: black;
		text-align: center;
		width:315px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.adminStatisticsRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:480px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.adminStatisticsRow2{
		background-color: white;
		padding: 15px;
		margin: auto;
		width:750px;
	}
	
	.adminStatisticsRow3{
		padding:8px;
		border-style: solid;
		flex:33%;
	}
	
	.footer{
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
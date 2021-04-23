<template id="admin">
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
		<div class="adminProfileRow">
			<h1>Admin overview</h1>
		</div>
		<br>
		<br>
		<br>
		<div class="adminRow">
			<a href="/admin-statistics">
				<h2>Statistics of store</h2>
			</a>
		</div>
		<br>
		<br>
		<div class="adminRow">
			<a href="/admin-book">
				<h2>Add, remove or modify a book</h2>
			</a>
		</div>
		<br>
		<br>
		<div class="adminRow">
			<a href="/admin-genre">
				<h2>Add, remove or modify a genre</h2>
			</a>
		</div>
		<br>
		<br>
		<div class="adminRow">
			<a href="/admin-author">
				<h2>Add, remove or modify an author</h2>
			</a>
		</div>
		<br>
		<br>
		<div class="adminRow">
			<a href="/admin-language">
				<h2>Add, remove or modify a language</h2>
			</a>
		</div>
		<br>
		<br>
		<div class="adminRow">
			<a href="/admin-remove-user">
				<h2>Remove an user</h2>
			</a>
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
    Vue.component("admin", {
    	template: "#admin",
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
	
	.adminRow{
		background-color: white;
		color: black;
		text-align: center;
		width:270px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.adminRow a{
		color:black;	
	}
	
	.footer{
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
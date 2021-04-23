<template id="register">
	<div>
		<nav class="navbar navbar-expand-sm bg-info navbar-dark">
		  <ul class="navbar-nav">
			<li class="nav-item ">
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
			<div style="cursor: pointer;">
				<i onclick="document.getElementById('id01').style.display='block'" class="material-icons" style="font-size:48px">account_circle</i>
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
		<div class="registerRow">
			<h1>Creating user</h1>
		</div>
		<div class="mainrow">
			<h2>Enter your personal information</h2>
			<form name="myform" method="POST" action="/registerUser">
				<hr>
				<div class="form-group">
				  <label for="name">Name:</label>
				  <input type="text" class="form-control" pattern="[a-zA-Z]{1,}" title="Must contain only letters" id="name" name="name" required>
				</div>
				<div class="form-group">
				  <label for="surname">Surname:</label>
				  <input type="text" class="form-control" pattern="[a-zA-Z]{1,}" title="Must contain only letters" id="surname" name="surname"  required>
				</div>
				<div class="form-group">
				  <label for="email">E-mail:</label>
				  <input type="email" class="form-control" pattern="[[a-zA-Z]0-9._%+-]+@[[a-zA-Z]0-9.-]+\.[[a-zA-Z]]{2,}$" title="characters followed by an @ sign, followed by more characters, and then a '.',followed by at least 2 letters from a to z" id="email" name="email" required>
				</div>
				<div class="form-group">
				  <label for="phone">Phone:</label>
				  <input type="text" class="form-control" pattern="[0-9]{10}" title="Must contain 10 numbers"  id="phone" name="phone"  required>
				</div>
				<div class="form-group">
				  <label for="username">Username:</label>
				  <input type="text" class="form-control" pattern=".{5,}" title="Must contain 5 or more characters" id="username" name="username"  required>
				</div>
				<div class="form-group">
				  <label for="password">Password:</label>
				  <input type="password" class="form-control" pattern=".{8,}" title="Must contain 8 or more characters" id="password" name="password"  required>
				</div>
				<button type="submit">Submit</button>
			</form>
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
    Vue.component("register", {
    	template: "#register",
	});
</script>

<style>
	hr {
	  border: 1px solid #f1f1f1;
	  margin-bottom: 25px;
	}
	
	.registerRow{
		background-color: white;
		color: black;
		text-align: center;
		width:370px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	.mainrow{
		padding: 16px;
		color:black;
		margin: auto;
		width:800px;
		background-color: #006699;
		border-radius:25px;
		box-shadow:0px 3px 12px white;
		position:relative;
		top:25px;
	}
	.mainrow form{
		font-weight: bold;
	}
	
	.mainrow button[type=submit] {
	  width: 100%;
	  background-color: #ff0000;
	  color: white;
	  padding: 14px 20px;
	  margin: 8px 0;
	  border: none;
	  border-radius: 4px;
	  cursor: pointer;
	}
	.mainrow button[type=submit]:hover {
	  background-color: #e60000;
	}
	
	.footer{
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
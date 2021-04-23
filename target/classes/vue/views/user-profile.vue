<template id="user-profile">
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
			<div v-for="user in users">
				<div  class="profile">
					<a :href="`/users/${user.username}`" class="material-icons" style="font-size:48px;color:purple">account_circle</a>	
				</div>
				<div class="logout">
					<form action="/logout">
						<input type="submit" class="btn btn-danger" value="LogOut">
					</form>
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
		<div class="userProfileRow">
			<h1>User overview</h1>
		</div>
		<div v-for="user in users" class="userProfileMainrow">
			<h2>Your personal information</h2>
			<hr>
				<h4><b>Name : </b> {{user.name}}</h4>
				<h4><b>Surname : </b> {{user.surname}}</h4>
				<h4><b>E-mail : </b> {{user.email}}</h4>
				<h4><b>Phone : </b> {{user.phone}}</h4>
				<h4><b>Username : </b> {{user.username}}</h4>
			<br>
			<h2>You can change your password</h2>
			<form method="POST" action="/change-password">
				<hr>
				<div class="form-group">
				  <label for="password">New password : </label>
				  <input type="password" class="form-control" pattern=".{8,}" title="Must contain 8 or more characters" id="password" name="password" required>
				</div>
				<button type="submit">Submit</button>
			</form>
		</div>
		<div class="footer">
			&copy;
			<a href="http://www.ds.unipi.gr/" target="_blank">2021 DS_UNIPI
			</a>.
			All rights reserved.
		</div>
	</div>
</template>

<script>
    Vue.component("user-profile", {
    	template: "#user-profile",
    	 data: () => ({
    		 users: [],
 	    }),
 	    created() {
    		 const username = this.$javalin.pathParams["username"];
             fetch(`/api/users/${username}`)
                 .then(res => res.json())
                 .then(res => this.users = res)
                 .catch(() => alert("Error while fetching user"));
 	    }
 	});
</script>

<style>
	.userProfileRow{
		background-color: white;
		color: black;
		text-align: center;
		width:280px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	.userProfileMainrow{
		padding: 16px;
		color:black;
		margin: auto;
		width:580px;
		background-color: #006699;
		border-radius:25px;
		box-shadow:0px 3px 12px white;
		position:relative;
		top:25px;
	}
	.userProfileMainrow form{
		font-weight: bold;
	}
	hr {
	  border: 1px solid #f1f1f1;
	  margin-bottom: 25px;
	}
	.userProfileMainrow button[type=submit] {
	  width: 100%;
	  background-color: #ff0000;
	  color: white;
	  padding: 14px 20px;
	  margin: 8px 0;
	  border: none;
	  border-radius: 4px;
	  cursor: pointer;
	}
	.userProfileMainrow button[type=submit]:hover {
	  background-color: #e60000;
	}
	.footer{
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
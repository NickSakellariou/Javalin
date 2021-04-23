<template id="admin-remove-user">
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
		<div class="adminUsersRow">
			<h1>All users</h1>
		</div>
		<br>
		<div v-for="user in users">
			<div class="adminUsersMainrow">
				<h4><b>Name : </b> {{user.name}}</h4>
				<h4><b>Surname : </b> {{user.surname}}</h4>
				<h4><b>E-mail : </b> {{user.email}}</h4>
				<h4><b>Phone : </b> {{user.phone}}</h4>
				<h4><b>Username : </b> {{user.username}}</h4>
				<br>
				
				<form style="text-align:center;" :action="`/api/admin-remove-user/${user.username}`">
					<input type="submit" class="btn btn-danger" value="Remove user">
				</form>
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
    Vue.component("admin-remove-user", {
    	template: "#admin-remove-user",
    	data: () => ({
    		users: [],
        }),
        created() {
            fetch("/api/admin-all-users")
                .then(res => res.json())
                .then(res => this.users = res);
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
		left: -10px;
	}
	
	.adminUsersRow{
		background-color: white;
		color: black;
		text-align: center;
		width:210px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.adminUsersMainrow{
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
	
	.footer{
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
<template id="admin-language">
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
		<div class="adminLanguageRow4">
			<div>
				<form action="/api/admin-add-language" method="POST">
					<label for="name" ><b> ADD A LANGUAGE </b></label>
					<br>
					<input type="text" name="name" placeholder="New name of language" required>
					<br>
					<br>
					<button type="submit" class="btn btn-primary">Add</button>
				</form>
			</div>
		</div>
		<br>
		<br>
		<br>
		<div class="adminLanguageRow1">
			<h1>All languages</h1>
		</div>
		<br>
		<div class="adminLanguageRow3">
			<div v-for="language in languages">
				<div class="adminLanguageRow2">
					<div>
						<br>
						<h4><b>Language : </b>{{language.name}}</h4>
						<br>
						<form :action="`/api/admin-modify-language/${language.language_id}`" method="POST">
							<br>
							<label for="name" ><b> MODIFY THE LANGUAGE </b></label>
							<br>
							<input type="text" name="name" placeholder="New name of language" required>
							<br>
							<br>
							<input type="submit" class="btn btn-primary" value="Modify">
						</form>
						<br>
						<a :href="`/api/admin-remove-language/${language.name}`" class="btn btn-danger">Remove language</a>
						<br>
						<br>
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
		<div class="footer">
			&copy;
			<a href="http://www.ds.unipi.gr/" target="_blank">2021 DS_UNIPI
			</a>.
			All rights reserved.
		</div>
	</div>
</template>

<script>
    Vue.component("admin-language", {
    	template: "#admin-language",
    	data: () => ({
    		languages: [],
        }),
        created() {
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
	}
	
	.adminLanguageRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:260px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.adminLanguageRow2{
		background-color: white;
		padding: 10px;
		margin: 10px;
		width:600px;
		text-align: center;
	}
	
	.adminLanguageRow2 div{
		padding:8px;
		border-style: solid;
		flex:33%;
	}
	
	.adminLanguageRow3{
		margin:auto;
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
	}
	
	.adminLanguageRow4{
		background-color: white;
		padding: 10px;
		margin: auto;
		width:600px;
		text-align: center;
	}
	
	.adminLanguageRow4 div{
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
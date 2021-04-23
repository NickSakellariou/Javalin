<template id="languages">
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
		<div class="languagesRow">
			<h1>Choose a language</h1>
		</div>
		<br>
		<br>
		<div v-for="language in languages" class="languagesMainrow">
			<hr class="languages_hr">
			<a :href="`/languages/${language.name}`"><h2><b>{{language.name}}</b></h2></a>
			<br>
			<br>
		</div>
		
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
    Vue.component("languages", {
    	template: "#languages",
    	data: () => ({
    		usernames: [],
    		languages: [],
        }),
        created() {
            fetch("/api/username")
                .then(res => res.json())
                .then(res => this.usernames = res)
            fetch("/api/languages")
                .then(res => res.json())
                .then(res => this.languages = res);
        }
    });
</script>

<style>
	hr.languages_hr {
	 	height: 30px;
	    border-style: solid;
	    border-color: black;
	    border-width: 1px 0 0 0;
	    border-radius: 20px;
	}
	hr.languages_hr:after {
		display: block;
	    content: "";
	    height: 30px;
	    margin-top: -31px;
	    border-style: solid;
	    border-color: black;
	    border-width: 0 0 1px 0;
	    border-radius: 20px;
	}
	
	.languagesRow{
		background-color: white;
		color: black;
		text-align: center;
		width:380px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.languagesMainrow{
		background-color: white;
		color: black;
		text-align: center;
		width:280px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	.languagesMainrow a{
		color:black;	
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
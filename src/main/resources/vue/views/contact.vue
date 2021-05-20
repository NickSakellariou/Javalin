<template id="contact">
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
			<li class="nav-item active">
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
		<div class="contactRow1">
			<h2>Contact form</h2>
		</div>
		<div class="contactRow2">
			<h2>Enter your personal information</h2>
			<form name="myform" action="/add-contact-form" method="post">
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
				<br>
				<br>
				<h2>Enter your message</h2>
				<hr>
				<textarea class="form-control" maxlength="200" rows="4" cols="72" name="message" required></textarea>
				<br>
				<br>
				<div style="text-align:center;">
					<input type="submit" class="btn btn-primary" value="Submit form">
				</div>
			</form>
		</div>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<div class="contactFooter">
			&copy;
			<a href="http://www.ds.unipi.gr/" target="_blank">2021 DS_UNIPI
			</a>.
			All rights reserved.
		</div>
	</div>
</template>

<script>
    Vue.component("contact", {
    	template: "#contact",
    	data: () => ({
    		usernames: [],
        }),
        created() {
            fetch("/api/username")
                .then(res => res.json())
                .then(res => this.usernames = res);
        }
    });
</script>

<style>
	hr {
	  border: 1px solid #f1f1f1;
	  margin-bottom: 25px;
	}
	.contactRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:200px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	.contactRow2{
		padding: 16px;
		color:black;
		margin: auto;
		width:650px;
		background-color: #006699;
		border-radius:25px;
		box-shadow:0px 3px 12px white;
		position:relative;
		top:25px;
	}
	
	.contactRow2 form{
		font-weight: bold;
	}
	
	.contactFooter{
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
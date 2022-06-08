<template id="faq">
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
			<li class="nav-item active">
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
		<div class="faqRow1">
			<h1>Frequently Asked Questions</h1>
		</div>
		<br>
		
		<div class="faqOrder">
			<div class="faqQuestions">
				<h4><b>1)</b></h4>
				<h4><b>Do i need an account to make an order?</b></h4>
				<h4>No, anyone can purchase anything from the store without an account.</h4>
				<br>
				<h4><b>2)</b></h4>
				<h4><b>Can i pay with PayPal?</b></h4>
				<h4>Yes, you can.You can also pay on delivery and with card if you want.</h4>
				<br>
				<h4><b>3)</b></h4>
				<h4><b>Can i change my password?</b></h4>
				<h4>Yes, you can change your password whenever you want just by going to your profile.</h4>
				<br>
				<h4><b>4)</b></h4>
				<h4><b>Will the ratings that i give to books, affect the book suggestions i see in home page?</b></h4>
				<h4>Yes, the store uses the collaborative filtering that is a method of making automatic predictions (filtering) about the interests of a user by collecting preferences or taste information from many users (collaborating).</h4>
				<br>
			</div>
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
    Vue.component("faq", {
    	template: "#faq",
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
	.faqRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:540px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	.faqOrder{
		text-align:center;
		padding: 16px;
		color:black;
		margin: auto;
		width:1000px;
		background-color: #339966;
		border-radius:25px;
		box-shadow:0px 3px 12px white;
	}
	.faqQuestions{
		text-align: left;
		font-weight: bold;
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
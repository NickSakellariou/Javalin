<template id="cart">
	<div>
		<nav class="navbar navbar-expand-sm bg-info navbar-dark">
		  <ul class="navbar-nav">
			<li class="nav-item">
			  <a class="nav-link" href="/">HOME</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/books">BOOKS</a>
			</li>
			<li class="nav-item active">
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
		<div class="shoppingCartRow1">
			<h1>Your cart</h1>
		</div>
		<br>
		<div v-if="cookies.length>0">
			<div class="shoppingCart">
				<div v-for="(cookie, key) in cookies" class="cartItems">
					<h3>{{ key + 1}})</h4>
					<h4>
						<b>Title : </b> {{cookie.name}}
						<br>
						<b>Price : </b> {{cookie.price}} €
						<br>
						<b>Quantity : </b> {{cookie.quantity}}
					</h4>
					<br>
					<hr>
					<span style="text-align:center;" v-if="key == cookies.length - 1">
						<h3><b>Total price : </b> {{cookie.sum}} €</h4>
						<br>
						<form action="/api/remove-cookies" >
							<input type="submit" class="btn btn-danger" value="Clear cart" >
						</form>
					</span>
					<br>
					<div style="text-align:center;" v-for="username in usernames">
						<div v-if="username.value !== null && username.value !== 'admin' && key == cookies.length - 1">
							<form action="/checkout-signed-in">
								<input type="submit" class="btn btn-primary" value="Submit" name="submit">		
							</form>
						</div>
						<div v-if="username.value == null && key == cookies.length - 1">
							<form action="/checkout-not-signed-in">
								<input type="submit" class="btn btn-primary" value="Submit" name="submit">		
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div v-if="cookies.length==0">
			<div class="shoppingCart">
				<div class="cartItems">
					<h4>
						<center>You haven't chosen any books yet.</center>
					</h4>
				</div>
			</div>
		</div>
		
		<br>
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
    Vue.component("cart", {
    	template: "#cart",
    	data: () => ({
    		usernames: [],
    		cookies: [],
        }),
        created() {
            fetch("/api/username")
                .then(res => res.json())
                .then(res => this.usernames = res)
            fetch("/api/cart")
                .then(res => res.json())
                .then(res => this.cookies = res);
        }
    });
</script>

<style>
	.shoppingCartRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:190px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	.shoppingCart{
		text-align:center;
		padding: 16px;
		color:black;
		margin: auto;
		width:600px;
		background-color: #339966;
		border-radius:25px;
		box-shadow:0px 3px 12px white;
	}
	.cartItems{
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
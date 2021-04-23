<template id="checkout-signed-in">
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
		<br>
		<div class="shoppingCartRow1">
			<h1>Your cart</h1>
		</div>
		<br>
		<div v-if="cookies.length>0">
			<div class="shoppingCart">
				<div v-for="(cookie, key) in cookies" class="cartItems">
					<br>
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
					</span>
				</div>
			</div>
		</div>
		<br>
		<br>
		<div class="checkoutRow">
			<h2>Enter shipment address</h2>
			<hr>
			<form method="post" action="/api/insert-purchase-signed-in">
				<div class="form-group">
					 <label for="street">Street:</label>
					 <input type="street" class="form-control" pattern=".{1,}" title="Must contain only characters" id="street" name="street" required>
				</div>
				<div class="form-group">
					 <label for="streetNumber">Street number:</label>
					 <input type="streetNumber" class="form-control" pattern="[0-9]+" title="Must contain only numbers" id="streetNumber" name="streetNumber" required>
				</div>
				<div class="form-group">
					 <label for="area">Area:</label>
					 <input type="area" class="form-control" pattern=".{1,}" title="Must contain only characters" id="area" name="area" required>
				</div>
				<div class="form-group">
					 <label for="postcode">Post code:</label>
					 <input type="postcode" class="form-control" pattern="[0-9]{5}" title="Must contain 5 numbers" id="postcode" name="postcode" required>
				</div>
				<br>
				<br>
				
				<h2>Pay on delivery, with card or with PayPal?</h2>
				<hr>
				<div class="form-check">
				  <label class="form-check-label">
					<input type="radio" id="cash"  name="payment" value="cash" class="form-check-input" onclick="cardOption1()"><b>Pay on delivery</b>
				  </label>
				</div>
				<br>
				<div class="form-check">
				  <label class="form-check-label">
					<input type="radio" id="card"  name="payment" value="card" class="form-check-input" onclick="cardOption2()"><b>Card</b>
				  </label>
				</div>
				<br>
				<div class="form-check">
				  <label class="form-check-label">
					<input type="radio" id="paypal"  name="payment" value="paypal" class="form-check-input" onclick="cardOption3()"><b>PayPal</b>
				  </label>
				</div>
				<br>
				<br>
				<div id="cardOptionID" style="display:none">
					<div class="form-group">
					  <label for="typeCard">Type of card (ð.÷. Visa,Mastercard):</label>
					  <input type="typeCard" class="form-control" pattern=".{1,}" title="Must contain only characters" id="typeCard" name="typeCard" required>
					</div>
					<div class="form-group">
					  <label for="numberCard">16-digit number card:</label>
					  <input type="numberCard" class="form-control" pattern="[0-9]{16}" title="Must contain 16 numbers" id="numberCard" name="numberCard" required>
					</div>
					<div class="form-group">
					  <label for="nameCard">Full name:</label>
					  <input type="nameCard" class="form-control" pattern=".{1,}" title="Must contain only characters" id="nameCard" name="nameCard" required>
					</div>
					<br>
				</div>
				<div id="PayPalOptionID" style="display:none">
					<div class="form-group">
					  <label for="email1">E-mail:</label>
					  <input type="email1" class="form-control" pattern="[[a-zA-Z]0-9._%+-]+@[[a-zA-Z]0-9.-]+\.[[a-zA-Z]]{2,}$" title="characters followed by an @ sign, followed by more characters, and then a '.',followed by at least 2 letters from a to z" id="email1" name="email1" required>
					</div>
					<div class="form-group">
					  <label for="password">Password:</label>
					  <input type="password" class="form-control" pattern=".{8,}" title="Must contain 8 or more characters" id="password" name="password" required>
					</div>
					<br>
				</div>
				<div style="text-align:center;">
					<input type="submit" class="btn btn-primary" value="Confirm order" name="submit">
				</div>
			</form>
		<div class="checkoutFooter">
			&copy;
			<a href="http://www.ds.unipi.gr/" target="_blank">2021 DS_UNIPI
			</a>.
			All rights reserved.
		</div>
	</div>
</template>

<script>
    Vue.component("checkout-signed-in", {
    	template: "#checkout-signed-in",
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

	<script >
		function cardOption1(){
			document.getElementById("typeCard").disabled = true;
			document.getElementById("numberCard").disabled = true;
			document.getElementById("nameCard").disabled = true;
			document.getElementById("email1").disabled = true;
			document.getElementById("password").disabled = true;
			document.getElementById("cardOptionID").style.display = "none";
			document.getElementById("PayPalOptionID").style.display = "none";
		}
		function cardOption2(){
			document.getElementById("typeCard").disabled = false;
			document.getElementById("numberCard").disabled = false;
			document.getElementById("nameCard").disabled = false;
			document.getElementById("email1").disabled = true;
			document.getElementById("password").disabled = true;
			document.getElementById("cardOptionID").style.display = "block";
			document.getElementById("PayPalOptionID").style.display = "none";
		}
		function cardOption3(){
			document.getElementById("typeCard").disabled = true;
			document.getElementById("numberCard").disabled = true;
			document.getElementById("nameCard").disabled = true;
			document.getElementById("email1").disabled = false;
			document.getElementById("password").disabled = false;
			document.getElementById("cardOptionID").style.display = "none";
			document.getElementById("PayPalOptionID").style.display = "block";
		}
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
	
	.checkoutRow{
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
	
	.checkoutFooter{
		position:relative;
		top:300px;
		background-color: black;
		padding: 30px;
		text-align: center;
		color:white;
	}
</style>
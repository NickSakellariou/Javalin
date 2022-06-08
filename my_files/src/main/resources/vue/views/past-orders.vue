<template id="past-orders">
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
			<li class="nav-item active">
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
		<div class="pastOrdersRow1">
			<h1>All your past orders</h1>
		</div>
		<br>
		<br>
		
		<div v-for="username in usernames">
			<div v-if="username.value !== null && username.value !== 'admin'">
				<div v-if="purchases.length>0">
					<div v-for="purchase in purchases">
						<div class="order">
							<div class="orderItems">
								<h2><b>Datetime : </b>{{purchase.date}}</h2>
								<br>
								<h4><b>Book : </b>{{purchase.title}}</h4>
								<h4><b>Price : </b>{{purchase.price}} €</h4>
								<h4><b>Quantity : </b>{{purchase.quantity}}</h4>
								<br>
								<br>
								<h3><b>Shipment address : </b></h3>
								<h4><b>Street : </b>{{purchase.street}}</h4>
								<h4><b>Street number : </b>{{purchase.streetNumber}}</h4>
								<h4><b>Area : </b>{{purchase.area}}</h4>
								<h4><b>Post code : </b>{{purchase.postcode}}</h4>
								<br>				
							</div>
						</div>
						<br>
						<br>
					</div>
				</div>
				<div v-if="purchases.length==0">
					<div class="order">
						<div class="orderItems">
							<h4>
								<center>You haven't made any orders yet.</center>
							</h4>
						</div>
					</div>
				</div>
			</div>
			<div v-if="username.value == null || username.value == 'admin'">
				<div class="order">
					<div class="orderItems">
						<h4>
							<center>You haven't signed in yet.</center>
						</h4>
					</div>
				</div>
			</div>
			
		</div>
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
    Vue.component("past-orders", {
    	template: "#past-orders",
    	data: () => ({
    		usernames: [],
    		purchases: [],
        }),
        created() {
            fetch("/api/username")
                .then(res => res.json())
                .then(res => this.usernames = res)
            fetch("/api/all-purchases")
                .then(res => res.json())
                .then(res => this.purchases = res);
        }
    });
</script>

<style>
	.pastOrdersRow1{
		background-color: white;
		color: black;
		text-align: center;
		width:380px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.pastOrdersRow2{
		background-color: white;
		color: black;
		text-align: center;
		width:450px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	
	.order{
		text-align:center;
		padding: 16px;
		color:black;
		margin: auto;
		width:700px;
		background-color: #339966;
		border-radius:25px;
		box-shadow:0px 3px 12px white;
	}
	.orderItems{
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
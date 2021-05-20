<template id="contact-success">
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
			<li class="nav-item active">
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
		<br>
		<br>
		<div class="contactSuccessRow">
			<h2>Message was sent successfully!</h2>
		</div>
		<div class="contactSuccessFooter">
			&copy;
			<a href="http://www.ds.unipi.gr/" target="_blank">2021 DS_UNIPI
			</a>.
			All rights reserved.
		</div>
	</div>
</template>

<script>
	Vue.component("contact-success", {
	    template: "#contact-success",
	});
</script>

<style>
	.contactSuccessRow{
		background-color: white;
		color: black;
		text-align: center;
		width:485px;
		margin: auto;
		border-radius:25px;
		box-shadow:1px 1px 1px white;
	}
	.contactSuccessFooter{
		position: fixed;
		left: 0;
		bottom: 0;
		width: 100%;
		background-color: black;
		padding: 30px;
		color: white;
		text-align: center;
	}
</style>
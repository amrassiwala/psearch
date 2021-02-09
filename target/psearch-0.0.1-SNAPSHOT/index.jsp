<%@page import="com.psearch.model.ProductSearchQuery"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.psearch.model.SProduct"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Search Project</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
</head>
<body>

	<%
		ProductSearchQuery psq = new ProductSearchQuery();
		if (request.getAttribute("psq") != null) {
			psq = (ProductSearchQuery) request.getAttribute("psq");
		}
	%>
	<div class="container">
		<h2>Search For Products</h2>
		<nav class="navbar navbar-light bg-light">

			<form action="search" method="POST" class="form-inline">
				<div class="form-group">
					<input class="form-control" type="search" placeholder="Search"
						aria-label="Search" name="query"
						value="<%=(psq.query == null) ? "" : psq.query%>" />
				</div>
				<div class="form-group">
					<select name="category" id="category" class="form-control">

						<option value="">Select Category</option>
						<option value="Women's Clothing"
							<%if (psq.category != null && psq.category.equals("Women's Clothing")) {%>
							selected="selected" <%}%>>Women's Clothing</option>
						<option value="Jewelry & Watches"
							<%if (psq.category != null && psq.category.equals("Jewelry & Watches")) {%>
							selected="selected" <%}%>>Jewelry & Watches</option>
						<option value="Home & Garden"
							<%if (psq.category != null && psq.category.equals("Home & Garden")) {%>
							selected="selected" <%}%>>Home & Garden</option>
						<option value="T-shirts"
							<%if (psq.category != null && psq.category.equals("T-shirts")) {%>
							selected="selected" <%}%>>T-shirts</option>
						<option value="Toys"
							<%if (psq.category != null && psq.category.equals("Toys")) {%>
							selected="selected" <%}%>>Toys</option>
						<option value="Skincare"
							<%if (psq.category != null && psq.category.equals("Skincare")) {%>
							selected="selected" <%}%>>Skincare</option>
					</select>
				</div>
				<div class="form-group">
					<select name="price_range" id="price_range" class="form-control">
						<option value="">Price Filter</option>
						<option value="0 to 5"
							<%if (psq.price_range != null && psq.price_range.equals("0 to 5")) {%>
							selected="selected" <%}%>>$0 to $5</option>
						<option value="5 to 10"
							<%if (psq.price_range != null && psq.price_range.equals("5 to 10")) {%>
							selected="selected" <%}%>>$5 to $10</option>
						<option value="10 to 20"
							<%if (psq.price_range != null && psq.price_range.equals("10 to 20")) {%>
							selected="selected" <%}%>>$10 to $20</option>
						<option value="20 to 100"
							<%if (psq.price_range != null && psq.price_range.equals("20 to 100")) {%>
							selected="selected" <%}%>>$20 to $100</option>
						<option value="100 to 1000"
							<%if (psq.price_range != null && psq.price_range.equals("100 to 1000")) {%>
							selected="selected" <%}%>>$100 to $1000</option>
					</select>
				</div>
				<div class="form-group">
					<select name="ships_from" id="ships_from" class="form-control">
						<option value="">Ships From</option>
						<option value="United States"
							<%if (psq.ships_from != null && psq.ships_from.equals("United States")) {%>
							selected="selected" <%}%>>United States</option>
						<option value="Canada"
							<%if (psq.ships_from != null && psq.ships_from.equals("Canada")) {%>
							selected="selected" <%}%>>Canada</option>
						<option value="China"
							<%if (psq.ships_from != null && psq.ships_from.equals("China")) {%>
							selected="selected" <%}%>>China</option>
						<option value="Greece"
							<%if (psq.ships_from != null && psq.ships_from.equals("Greece")) {%>
							selected="selected" <%}%>>Greece</option>
					</select>
				</div>
				<div class="form-group">
					<select name="supplier_name" id="supplier_name"
						class="form-control">
						<option value="">Select Supplier</option>
						<option value="Orange Charlie"
							<%if (psq.supplier_name != null && psq.supplier_name.equals("Orange Charlie")) {%>
							selected="selected" <%}%>>Orange Charlie</option>
						<option value="Orange Poppy"
							<%if (psq.supplier_name != null && psq.supplier_name.equals("Orange Poppy")) {%>
							selected="selected" <%}%>>Orange Poppy</option>
						<option value="Ivory Aether"
							<%if (psq.supplier_name != null && psq.supplier_name.equals("Ivory Aether")) {%>
							selected="selected" <%}%>>Ivory Aether</option>
						<option value="Violet Azolla"
							<%if (psq.supplier_name != null && psq.supplier_name.equals("Violet Azolla")) {%>
							selected="selected" <%}%>>Violet Azolla</option>
						<option value="Gold Atalanta"
							<%if (psq.supplier_name != null && psq.supplier_name.equals("Gold Atalanta")) {%>
							selected="selected" <%}%>>Gold Atalanta</option>
					</select>
				</div>
				<div class="form-group">
					<input type="checkbox" id="premium" name="premium" value="true"
						<%if (psq.premium) {%> checked="checked" <%}%>><label>Premium</label>
				</div>
				<div class="form-group">
					<button class="btn btn-success my-2 my-sm-0" type="submit">Search</button>
				</div>
			</form>

		</nav>
	</div>
	<%
		if (request.getAttribute("results") != null) {
			List<SProduct> products = (ArrayList) request.getAttribute("results");
	%>
	
	<% if(products.size() == 0){ %>
	Sorry, no results found ......
	<%} %>
	<section id="products">
		<div class="container">
			<div class="row">
				<%
						for (SProduct p : products) {
				%>

				<div class="col-lg-4 col-md-6 col-sm-10 offset-md-0 offset-sm-1">
					<div class="card">
						<img class="card-img-top" src="<%=p.image_cover_url%>">
						<div class="card-body">
							<h5>
								<b><%=p.title%></b>
							</h5>
							<h6 class="text-success">By <%=p.supplier_name %></h6>
						<div class="d-flex flex-row my-2">
								<div class="text-muted">Price(USD)</div>
								<div class="ml-auto">
									<span class="fa fa-plus" id="orange"></span> <span
										class="px-sm-1">Retail Price(USD)</span>
								</div>
							</div>
							<div class="d-flex flex-row my-2">
								<div class="text-muted"><%=p.formatted_price %></div>
								<div class="ml-auto">
									<span class="fa fa-plus" id="orange"></span> <span
										class="px-sm-1"><%=p.formatted_msrp %></span>
								</div>
							</div>
						</div>
					</div>
				</div>


				<%} %>
			</div>
		</div>
	</section>
	<%} %>

</body>
</html>
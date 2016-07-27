<%@include file='header.jsp'%>
<div class="container">
	<h2>Manage Product</h2>
	<br>
	<br>
	<div class="form-group">
		<div class="row">
			<div class="col-md-1"></div>
	
			<div class="col-md-4">
				<div class="input-group">
					<div class="input-group-addon">Select Catalog:</div>
					<select id="catid" name="catid" class="form-control"
						id="catalogSelect" onChange="getDetails()">
						<c:forEach var="catalogPres" items="${catalogs}">
							<option value="${catalogPres.id}">${catalogPres.name}</option>
						</c:forEach>
					</select>

				</div>
			</div>
					<div class="col-md-1">
			 <input class="btn btn-primary" type="submit" value="Add Product"
		onclick="window.location='${pageContext.servletContext.contextPath}/addnewproduct?id=${selectedId}&value=${name}'">
			</div>
			<div class="col-md-5"></div>
		</div>

	</div>
<br>



	<table  id="tbProduct">
	<thead>
		<tr>
			<th>Name</th>
			<th>Quantity</th>
			<th>Manufacture Date</th>
			<th>Unit Price</th>
			<th>Delete</th>
		</tr>
		</thead>
 <tbody>
		<c:forEach var="productPres" items="${products}">
			<tr>

				<td>${productPres.getName() }</td>

				<td>${productPres.getQuantityAvail() }</td>


				<td>${productPres.getMfg() }</td>


				<td>${productPres.getUnitPrice() }</td>
			
				<td><button class="btnDelete btn btn-danger">Delete</button>
			</tr>
		</c:forEach>
	</table>
	<br>

</div>

<script>
	$(document).ready(function() {
		
		$('#tbProduct').DataTable();
		
		$("#tbProduct").on('click', '.btnDelete', function() {

			if (confirm("Are you sure you want to delete?")) {
				$(this).closest('tr').remove();
			}

			return false;

		});
	});

	document.getElementById("catid").value = "${selectedId}";

	function getDetails() {
		var userSelection = document.getElementById("catid").value;
		var text = document.getElementById("catid").options[document
				.getElementById('catid').selectedIndex].text;
		//alert(userSelection);
		window.location = 'productlist?catId=' + userSelection + '&name='
				+ text;
	}
</script>


<%@include file='footer.jsp'%>

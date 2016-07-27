<%@include file='header.jsp'%>
<div class="container">
<h2>Order Detail</h2>
	<table id="orderdetailtable">
	 <thead>
	<tr>
	<th>Product Name</th>
	<th>Quantity</th>
	<th>Unit Price</th>
	<th>Total Price</th>
	</tr>
</thead>
 <tbody>
		<c:forEach var="orderItemPres" items="${orderItemlist}">
			<tr>
			<td>${orderItemPres.productNameProperty().value}</td>
			<td>${orderItemPres.quantityProperty().value}</td>
			<td>${orderItemPres.unitPriceProperty().value}</td>
			<td>${orderItemPres.totalPriceProperty().value}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<script>
$('#orderdetailtable').DataTable();
</script>
 <%@include file='footer.jsp'%>
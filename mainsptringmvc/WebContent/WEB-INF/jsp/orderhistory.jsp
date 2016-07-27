<%@include file='header.jsp'%>
<div class="container">
<h2>Order History</h2>
	<table id="orderhistorytable">
	 <thead>
	<tr>
	<th>Date</th>
	<th>Total Price</th>
	<th>Detail</th>
	</tr>
</thead>
 <tbody>
		<c:forEach var="orderPres" items="${orderlist}">
			<tr>
			<td>${orderPres.dateProperty().value}</td>
			<td>${orderPres.totalPriceProperty().value}</td>
			<td><a href="${pageContext.servletContext.contextPath}/orderdetail/${orderPres.getOrderId()}"> View Detail </a></td>
			</tr>
		</c:forEach>
</tbody>
	</table>
</div>
<script>
$('#orderhistorytable').DataTable();
</script>
 <%@include file='footer.jsp'%>

<%@include file='header.jsp'%>
<div class="container">
	<h2>Final Order</h2>
	<br><br>
	<p class="validation">${message }</p>
	<form action="${pageContext.servletContext.contextPath}/finalOrder" method="post">
	
		<p class="buttonRow">
		<input class="btn btn-success" type="submit" value="Submit Order" >
		<input type="button" class="btn btn-secondary"  value="Back To Shopping Cart" onclick="window.location='${pageContext.servletContext.contextPath}/backShoppingCart'">
		<input type="button" class="btn btn-danger"  value="Cancel Order" onclick="window.location='${pageContext.servletContext.contextPath}'">
		
	</p>
	<table id="cartTable">
	<thead>
            <tr>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Unit Price</th>
                <th>Total Price</th>
            </tr>
        </thead>
        <tbody>
		<c:forEach var="cartItemPres" items="${cartItems}">
			<tr>
				<td>${cartItemPres.itemName }</td>
				<td>${cartItemPres.quantity }</td>
				<td>${cartItemPres.price }</td>
				<td>${cartItemPres.totalPrice }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	</form>
	
	
</div>
<script>

$('#cartTable').DataTable();
	
	</script>
<%@include file='footer.jsp'%>
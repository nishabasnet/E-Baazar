<%@include file='header.jsp'%>
<div class="container">
	<h2>Shopping Carts</h2>
	<br><br>
	<p class="validation">${message}</p>
		<p class="buttonRow">
		<input type="submit"  class="btn btn-success" value="Proceed to Checkout"  onclick="window.location='${pageContext.servletContext.contextPath}/shippingbilling'">
		<input type="submit" class="btn btn-primary" value="Continue Shopping" onclick="window.location='${pageContext.servletContext.contextPath}'">
		<input type="submit" class="btn btn-warning" value="Save Cart" onclick="window.location='${pageContext.servletContext.contextPath}/savecart'">
		
	</p>
	<table id="cartTable">

	<thead>
            <tr>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Unit Price</th>
                <th>Total Price</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
		<c:forEach var="cartItemPres" items="${cartItems}">
			<tr>
				<td>${cartItemPres.itemName }</td>
				<td>${cartItemPres.quantity }</td>
				<td>${cartItemPres.price }</td>
				<td>${cartItemPres.totalPrice }</td>
				<td><a href="${pageContext.servletContext.contextPath}/deleteItem/${cartItemPres}" class="btn btn-danger" role="button"> Delete</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	
	</div>
<script>

$('#cartTable').DataTable();
	if ('${message}'.length > 0) {
		$("#message").show();
		$("#messageData").replaceWith(
				"<strong id='messageData'>" + '${message}' + "</strong>");

	}
	</script>
<%@include file='footer.jsp'%>
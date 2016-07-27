<%@include file='header.jsp'%>
<link rel="stylesheet"
	href="<spring:url value='/resources/style/login/login.css'/>">
<div class="container">
	<h2>Credit Card Information</h2>
	<br>
	<br>
	<p class="validation">${message}</p>
	<form action="${pageContext.servletContext.contextPath}/terms"
		method="post">

		<table class="table-fill">
			<tbody class="table-hover">

				<tr>
					<td class="text-left"><strong>Name</strong></td>
					<td class="text-left"><input class="form-control" type="text"
						value="${ccard.name }" name="name" /></td>
				</tr>
				<tr>
					<td class="text-left"><strong>Card Number</strong></td>
					<td class="text-left"><input class="form-control" type="text"
						value="${ccard.ccNumber }" name="ccNumber" /></td>
				</tr>
				<tr>
					<td class="text-left"><strong>Card Type</strong></td>
					<td class="text-left"><select class="form-control"
						name="ccType" value="${ccard.ccType}">
							<option value="Master Card">Master Card</option>
							<option value="Visa">Visa</option>
							<option value="Discover">Discover</option>
					</select></td>
				</tr>
				<tr>
					<td class="text-left"><strong>Expiration Date</strong></td>
					<td class="text-left"><input class="form-control " type="text"
						value="${ccard.ccExpDate}" name="ccExpDate" /></td>
				</tr>
			
				<tr>
					<td class="text-left"></td>
					<td class="text-left">
					<input class="btn btn-secondary" type="submit" value="Previous Page"
						onclick="goBack()">
					<input class="btn btn-success"
						type="submit" value="Checkout"></td>
				</tr>
			</tbody>
		</table>

	</form>


</div>
<!--  <input class="btn btn-primary"
						type="submit" value="Back to Shipping Cart"
						onclick="window.location='${pageContext.servletContext.contextPath}/backShoppingCart'">
-->
<script>
	function goBack() {
		window.history.back();
	}
</script>
<%@include file='footer.jsp'%>
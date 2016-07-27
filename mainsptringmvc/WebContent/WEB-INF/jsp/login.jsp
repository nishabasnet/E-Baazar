<%@include file='header.jsp'%>
<link rel="stylesheet" href="<spring:url value='/resources/style/login/login.css'/>">

<div class="container">
	<h2>Login</h2>
	<form class="form-inline" method="post"
		action="${pageContext.servletContext.contextPath}/loginsubmit/callback/${callback }">
		<table class="table-fill">
			<tbody class="table-hover">
				<tr>
					<td class="text-left"><label for="customerid">CustomerID</label></td>
					<td class="text-left"><input id=customerid type="text"
						name="custId" /></td>
				</tr>
				<tr>
					<td class="text-left"><label for="txtpassword">Password</label></td>
					<td class="text-left"><input type="password" name="password"
						id="txtpassword" /></td>
				</tr>
				<tr>
					<td class="text-left"></td>
					<td class="text-left">
						<button type="submit" class="btn btn-primary">Sign In</button>
					</td>
				</tr>
			</tbody>
		
		</table>


	</form>


</div>

<script>
	if ('${message}'.length > 0) {
		$("#message").show();
		$("#messageData").replaceWith(
				"<strong id='messageData'>" + '${message}' + "</strong>");

	}

	if ('${redirect}'.length > 0)
		window.location = "${pageContext.servletContext.contextPath}/${redirect}";
</script>

<%@include file='footer.jsp'%>
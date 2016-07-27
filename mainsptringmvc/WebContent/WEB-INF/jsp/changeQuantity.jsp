<%@include file='header.jsp'%>
<link rel="stylesheet" href="<spring:url value='/resources/style/login/login.css'/>">
<div class="container">
<h2>Desired Quantity</h2>
<form action="${pageContext.servletContext.contextPath}/saveQuantity" method="post">
<table class="table-fill">
			<tbody class="table-hover">
				<tr>
					<td class="text-left"><label>Quantity</label></td>
					<td class="text-left"><input name="quantity" type="text" value="${quantity}" /><br><span class="validation">${message} </span></td>
				</tr>
					<tr>
					<td class="text-left"></td>
					<td class="text-left"><input class="btn btn-primary" type="submit" value="Submit"/></td>
				</tr>
</tbody>
</table>

</form>
</div>

<%@include file='footer.jsp'%>
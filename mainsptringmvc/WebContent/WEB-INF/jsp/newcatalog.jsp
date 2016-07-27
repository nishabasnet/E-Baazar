<%@include file='header.jsp'%>
<link rel="stylesheet"
	href="<spring:url value='/resources/style/login/login.css'/>">

<div class="container">
	<h2>Add New Catalog</h2>
	<form action="${pageContext.servletContext.contextPath}/savecatalog"
		method='post'>
		<table class="table-fill">
			<tbody class="table-hover">

				<tr>
					<td class="text-left"><label for="catalogid">Catalog
							ID</label></td>
					<td class="text-left"><input type="text" name="catID" /></td>
				</tr>
				<tr>
					<td class="text-left"><label for="catalogname">Catalog
							Name</label></td>
					<td class="text-left"><input type="text" name="catName" /></td>
				</tr>
				<tr>
					<td class="text-left"></td>
					<td class="text-left">
						<button type="submit" class="btn btn-success">Add</button>
						<input class="btn btn-danger"
						 type="button" value="Cancle"
						onclick="window.location='${pageContext.servletContext.contextPath}/cataloglist'">
						
					</td>
				</tr>

			</tbody>

		</table>


	</form>

	<%@include file='footer.jsp'%>
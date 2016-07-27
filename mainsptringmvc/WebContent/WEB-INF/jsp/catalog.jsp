<%@include file='header.jsp'%>
<link rel="stylesheet"
	href="<spring:url value='/resources/style/catalog/catalog.css'/>">
<div class="container">
	<h2>Browse Catalogs</h2>
	<table id="cataloglist">
		<thead>
			<tr>
				<th>Name</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="catalogPres" items="${catalogs}">
				<tr>
					<td><a class="catalogname"
						href="${pageContext.servletContext.contextPath}/viewProductList/catalogId/${catalogPres.id }/catalogName/${catalogPres.name }">${catalogPres.name }</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
</div>
<script>
$('#cataloglist').DataTable({"bPaginate": false, "bLengthChange": false,  "scrollY": 400});
</script>
<%@include file='footer.jsp'%>
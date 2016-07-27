<%@include file='header.jsp'%>
<link rel="stylesheet"
	href="<spring:url value='/resources/style/catalog/catalog.css'/>">
<div class="container">

	<h2>Catalog List</h2>
	<br><br>
	
	<input class="btn btn-primary" type="submit" value="Add Catalog" onclick="window.location='${pageContext.servletContext.contextPath}/addnewcatalog'">
 
	<table id="cataloglist">
		<thead>
			<tr>
				<th>Name</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="catalogPres" items="${catalogs}">
			<tr>
				<td><a href="${pageContext.servletContext.contextPath}/viewProductList/catalogId/${catalogPres.id }/catalogName/${catalogPres.name }">${catalogPres.name }</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br>
	
<script>
$('#cataloglist').DataTable({"bPaginate": false, "bLengthChange": false,  "scrollY": 400});
</script>
<%@include file='footer.jsp'%>
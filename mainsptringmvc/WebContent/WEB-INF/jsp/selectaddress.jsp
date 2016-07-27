<%@include file='header.jsp'%>

<div class="container">

	<h1>${title}</h1>
	<table border="1">
		<tr>
		    <th>Name</th>
		    <th>Street</th> 
		    <th>City</th>
		    <th>State</th> 
		    <th>ZipCode</th>
		  </tr>
		  <c:forEach var="address" items="${addressList}">
		  <tr>
		  	<td>${address.name }</td>
			<td>${address.street }</td>
			<td>${address.city }</td>
			<td>${address.state }</td>
			<td>${address.zip }</td>
			<td><a href="${pageContext.servletContext.contextPath}/selectAdd/${title}/${address.id}"> Select</a></td>
			<td><a href="${pageContext.servletContext.contextPath}/deleteAdd/${title}/${address.id }"> Delete</a></td>
			
		</tr>
		  </c:forEach>
	</table>
	<input type="button" name="Back" onclick="goBack()" value=Back/>
	</div>
	<script>
		function goBack() {
			window.history.back();
			
		}
		$('.select').on('click', function(e){
		    alert($("#table tr.selected td:first").html());
		});
	</script>
	
<%@include file='footer.jsp'%>

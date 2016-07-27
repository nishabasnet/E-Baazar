<%@include file='header.jsp'%>

<link rel="stylesheet" href="<spring:url value='/resources/style/login/login.css'/>">
<div class="container">
	<h2>Product Page</h2>
<br><br>
	<form action="${pageContext.servletContext.contextPath}/saveproduct" method='post' enctype="multipart/form-data">
		<table class="table-fill">
			<tbody class="table-hover">

				<tr>
					<td class="text-left"><label for="catalogid">Catalog&nbsp;ID</label></td>
					<td class="text-left"><input type="text" name="catId"
						 value = "${catId}"/></td>
						 <td class="text-left"><label for="catalogid">Catalog&nbsp;Name</label></td>
					<td class="text-left"><input type="text" name="catName"
						 value = "${catName}"/></td>
				</tr>
				
				<tr>
					<td class="text-left"><label >Product&nbsp;Name</label></td>
					<td class="text-left"><input type="text"
						name="pName" /></td>
							<td class="text-left"><label >Manufactured&nbsp;Date</label></td>
					<td class="text-left"><input type="text"
						name="mDate" /></td>
				</tr>
			
				<tr>
					<td class="text-left"><label for="totalquantity">Total&nbsp;Quantity</label></td>
					<td class="text-left"><input  type="text"
						name="num" /></td>
					<td class="text-left"><label for="priceperunit">Price&nbsp;Per&nbsp;Unit</label></td>
					<td class="text-left"><input  type="text"
						name="price" /></td>
				</tr>
			
				
				<tr>
					<td class="text-left"><label for="description">Description</label></td>
					<td class="text-left"><input  type="text"
						name="des" /></td>
						<td class="text-left">File to upload:</td>
						<td class="text-left"><input type="file" name="file" />
						<input type="hidden" id="filePath" name="filepath">
						</td>
				</tr>
				<tr>
				<td class="text-left"></td>
				<td class="text-left"></td>
				<td class="text-left"></td>
					<td class="text-left"><p class="buttonRow">
			<input type="submit" class="btn btn-success" value="Save" > 
			<input type="button" class="btn btn-secondary"
				value="Clear" onclick="window.location='${pageContext.servletContext.contextPath}/productlist'" >
		</p></td>
				</tr>
			</tbody>
			
		</table>
		<br>
		


	</form>
	</div>
	<script>
	$('input[type=file]').change(function () {
		 var filePath = $(this).val();
      
	    $("#filePath").val(filePath);
	   
	});
	</script>
	<%@include file='footer.jsp'%>
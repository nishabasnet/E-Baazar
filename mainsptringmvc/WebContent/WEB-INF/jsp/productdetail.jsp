<%@include file='header.jsp'%>
<link rel="stylesheet" href="<spring:url value='/resources/style/productdetail/productdetail.css'/>">
<div class="container">
<h2>Product Detail - ${product.name }</h2>
<div class="row">
<div class="col-md-2"></div>
<div class="col-md-4"><img height="400" width="350" src="${pageContext.servletContext.contextPath}/imageController/${product.name }.jpg" alt="product_image"/></div>
<div class="col-md-4">	<table class="productdetail table-fill">
	<tbody class="table-hover">
		<tr>
			<td  class="text-left"><strong>Item</strong></td>
			<td  class="text-left">${product.name }</td>
		</tr>
		<tr>
			<td class="text-left"><strong>Price</strong></td>
			<td class="text-left">${product.unitPrice }</td>
		</tr>
		<tr >
			<td class="text-left"><strong>Quantity</strong></td>
			<td class="text-left">${product.quantityAvail }</td>
		</tr>
		<tr >
			<td class="text-left"><strong>Review</strong></td>
			<td class="text-left">${product.description }</td>
		</tr>
		<tr>
			<td class=""><button class="btn btn-link" onclick="goBack()">Back</button></td>
			<td class="">	<form method="post"
		action="${pageContext.servletContext.contextPath}/addToCart">
		<input type="submit" class="btn btn-primary" value="Add to Cart"> <input type="hidden"
			name="prodName" value="${product.name }"> <input
			type="hidden" name="unitPrice" value="${product.unitPrice }">
			<input
			type="hidden" name="quantityAvail" value="${product.quantityAvail }">
	</form></td>
		</tr>
		</tbody>
	</table>
</div>
<div class="col-md-2"></div>
</div>

<div class="row">
  <div class="col-md-10"></div>
    <div class="col-md-2">	</div>
  
</div>

</div>
	<script>
		function goBack() {
			window.history.back();
		}
	</script>

<%@include file='footer.jsp'%>
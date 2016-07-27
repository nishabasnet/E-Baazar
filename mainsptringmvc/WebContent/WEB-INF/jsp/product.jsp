<%@include file='header.jsp'%>
<div class="container">
	<h2>Catalog - ${catalogName }</h2>

<div class="input-group">
  <span class="input-group-addon" id="basic-addon1">Search</span>
  <input type="text" class="form-control" id="search" placeholder="search product of catalog - ${catalogName }" aria-describedby="basic-addon1">
</div>
<br>
	<div class="row">


		<c:forEach var="productPres" items="${products}">
			<div class="col-md-4 productblock"  name="product" value="${productPres.name }">
			<a
					href="${pageContext.servletContext.contextPath}/viewProductDetail?id=${productPres.id }">
				<div class="displayImage">
					<img height="300" width="300"
						src="${pageContext.servletContext.contextPath}/imageController/${productPres.name }.jpg"
						style="border: 1px solid black; max-width: 100%;" alt="">
				</div>
				<br>
				<span class="listproductName"> ${productPres.name }</span>
				</a>
			</div>
		</c:forEach>
	</div>

	<!--  <p class="buttonRow">
		<input type="submit" value="Back" onclick="window.location='${pageContext.servletContext.contextPath}'">
</p>-->

</div>
<script>
	$('#search').on('change keydown paste input', function() {
		console.log($('#search').val());
		$( "div[name='product']" ).each(function(){
			var productName = $(this).context.outerText;
			if (productName.toLowerCase().indexOf($('#search').val()) >= 0){
			
				$(this).show();
			}
			else{
				$(this).hide();
			}
		});
	});
	
</script>
<%@include file='footer.jsp'%>
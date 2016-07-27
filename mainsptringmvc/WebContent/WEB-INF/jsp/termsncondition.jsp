<%@include file='header.jsp'%>
<div class="container">
<h2>Terms And Conditions</h2>
<br><br>
<p>
${terms}
</p>
<div class="row">
<div class="col-md-6"></div>

<div class="col-md-5"><div class="buttonRow">
			<input class="btn btn-secondary" type="submit" value="Back to Shipping Cart" onclick="window.location='${pageContext.servletContext.contextPath}/backShoppingCart'">
			<input class="btn btn-secondary" type="submit" value="Previous Page" onclick="goBack()">
			<input class="btn btn-success" type="submit" value="Accept"  onclick="window.location='${pageContext.servletContext.contextPath}/finalOrder'">
			
		</div></div>
<div class="col-md-1"></div>

		</div>
		</div>
	<script>
		function goBack() {
			window.history.back();
		}
	</script>

<%@include file='footer.jsp'%>
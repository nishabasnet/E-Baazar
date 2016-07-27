<%@include file='header.jsp'%>
<div class="container">
<h2>Successful Submission!</h2>
<br><br>
<p>${message }</p>
<br>
<input class="btn btn-success" type="button" value="Continue Shopping" onclick="window.location='${pageContext.servletContext.contextPath}'">
</div>
<%@include file='footer.jsp'%>
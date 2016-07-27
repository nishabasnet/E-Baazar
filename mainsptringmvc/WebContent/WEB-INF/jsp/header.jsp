<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<spring:url value='/resources/style/bootstrap.min.css'/>">
<link rel="stylesheet" href="<spring:url value='/resources/style/jquery.dataTables.min.css'/>">
<link rel="stylesheet" href="<spring:url value='/resources/style/common.css'/>">

<script src="<spring:url value='/resources/script/jquery-2.1.1.min.js'/>"></script>
<script src="<spring:url value='/resources/script/bootstrap.min.js'/>"></script>
<script src="<spring:url value='/resources/script/jquery.dataTables.min.js'/>"></script>
<script src="<spring:url value='/resources/script/common.js'/>"></script>
<title>E-Bazaar</title>
</head>
<body>

<nav class="navbar navbar-default navbar-inverse">
<div class="container-fluid">
<div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/">E-Bazaar</a>
          </div>
  <ul class="nav navbar-nav">
  	 <li><a href="${pageContext.servletContext.contextPath}/orderhistory">Order History</a></li>
  	 	 <li><a href="${pageContext.servletContext.contextPath}/">Catalog</a></li>
  	 	 	 <li><a href="${pageContext.servletContext.contextPath}/retrievesavecart">Retrieve Saved Cart</a></li>
  	 	 <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><label id="username">Administrator</label> <span class="caret"></span></a>
          <ul class="dropdown-menu">
           <li><a href="${pageContext.servletContext.contextPath}/productlist">Manage Product</a></li>
           <li><a href="${pageContext.servletContext.contextPath}/cataloglist">Manage Catalog</a></li>
	 		</ul>
        </li>
</ul>
 <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><label id="username">Account</label> <span class="caret"></span></a>
          <ul class="dropdown-menu">
           <li><a href="${pageContext.servletContext.contextPath}/login/callback/login">Login</a></li>
	 		<li><a href="${pageContext.servletContext.contextPath}/logout">Logout</a></li>
            </ul>
        </li>
      </ul>
</div>
</nav>
<div class="container">
<div id="message" class="alert alert-success">
  <strong id="messageData"></strong>
</div>
</div>

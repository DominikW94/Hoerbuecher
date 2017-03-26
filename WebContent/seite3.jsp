<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="seitenTag" uri="/WEB-INF/lib/seitenTag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="author" content="Tobais Brakel">
	<link href="seitenStyle.css" type="text/css" rel="stylesheet">
	<title>Seite 3</title>
</head>

<div align="right">
	<span>
		<a id="not" href="WarenFormServlet">Warenkorb</a>
	</span>
		<a id="not" href="IndexServlet?action=logout">Logout</a>
</div>
<div align="left">
	<span>
		<a href="seite2.jsp">Fantasy</a>
	</span><span>
		<a href="seite3.jsp">Horror</a>
	</span><span>
		<a href="seite3.jsp">Krimi</a>
	</span>
		<a href="seite3.jsp">Comedy</a>
</div>

<body>

</body>
<footer>
	<div align="left">
		<a href="index.jsp">Zur Startseite</a>
	</div>
	<seitenTag:activeLink active="Seite3"></seitenTag:activeLink>
</footer>
</html>
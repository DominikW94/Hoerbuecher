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
	<title>Seite 2</title>
</head>

<div align="right">
	<span>
		<a href="warenkorb.jsp">Warenkorb</a>
	</span>
		<a href="">Logout</a>
</div>
<div align="left">
	<span>
		<a href="SucheFormServlet?kategorie=fantasy">Fantasy</a>
	</span><span>
		<a href="SucheFormServlet?kategorie=horror">Horror</a>
	</span><span>
		<a href="SucheFormServlet?kategorie=krimi">Krimi</a>
	</span>
		<a href="SucheFormServlet?kategorie=comedy">Comedy</a>
</div>

<br>

<body>
	
	<table border=2>
		<tr>
			<c:forEach items="${columnNames}" var="name">
				<th>${name}</th>
			</c:forEach>
		</tr>
		<c:forEach items="${resultList}" var="result">
			<tr>
				<c:forEach items="${result}" var="value">
					<td>${value}</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
	<br>
	<br>
	<br>

</body>
<footer>
	<div align="left">
		<a href="indexAnmeldung.jsp">Zur Startseite</a>
	</div>
	<seitenTag:activeLink active="Seite2"></seitenTag:activeLink>
</footer>
</html>
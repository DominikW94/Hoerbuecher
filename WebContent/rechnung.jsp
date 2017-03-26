<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="author"  content="Tobias Brakel">
	<title>Rechnung</title>
</head>
<body>
	Ihnen wurde eine Mail mit folgenden Daten gesendet:
	<br>
	<br>
	<table>
		<tr>
			<c:forEach items="${columnNames}" var="name">
				<th>${name}</th><td></td><td></td>
			</c:forEach>
		</tr>
		<c:forEach items="${resultList}" var="result">
			<tr>
				<c:forEach items="${result}" var="value">
					<td>${value}</td><td></td><td></td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
	<b>Gesamtbetrag: ${summe }</b>
	<br>
	<br>
	Bitte speichern Sie sich die Rechnung ab.
	<br>
	<br>
	<a href="seite1.jsp">zu den Artikeln</a>
	<br>
	<a href="index.jsp">zur Startseite</a>
</body>
</html>
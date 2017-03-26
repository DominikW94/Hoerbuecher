<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="author" content="Tobias Brakel">
	<title>Warenkorb</title>
</head>
<body>
	<div align="center">
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
	</div>
	<br>
	<div align="center">
		Summe: ${summe }
	</div>
	<br>
	<br>
	<a href="seite1.jsp">Weiter shoppen?</a>
	<br>
	<div align="center">
		<form method="post" action="RechnungFormServlet">
			<input type="hidden" name="kauf" value="${summe  }">
			<input type="submit" value="kaufen?">
		</form>
	</div>
</body>
</html>
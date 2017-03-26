<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="author"  content="Jan Schaffland">
<title>Mitgliedsliste</title>
</head>
<body>
	<a href="index.jsp">zurück</a>
<br>
<br>
	<form method="get" action="BearbeitungFormServlet">
		<input type="submit" value="Aktualisieren">
	</form>
<br>
<br>
	<table border="2">
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
				<td>
					<form method="post" action="BearbeitungFormServlet">
						<input type="submit" value="Bearbeiten">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
<br>
<br>

	<form method="post" action="BearbeitungFormServlet">
		<input type="number" size="25" name="loeschID" placeholder="ID zum löschen eingeben">
		<input type="submit" value="Löschen">
	</form>
	<form method="post" action="BearbeitungFormServlet">
		<input type="number" size="25" name="kundenID" placeholder="ID zum Admin ändern">
		<input type="submit" value="Bearbeiten">
	</form>
	
</body>
</html>
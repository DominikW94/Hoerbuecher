<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="menuTag" uri="/WEB-INF/lib/menuTag.tld" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="Beschreibung" content="Startseite der Datenbank zum Verkauf von Hörbüchern">
		<meta name="author" content="Tobias Brakel">
		<title>Startseite</title>
		
		<link href="indexStyle.css" type="text/css" rel="stylesheet">
	</head>
	<body>
	   <div id="header">
	       <menuTag:menu></menuTag:menu>
			<div class="right">
				<c:if test="${sessionScope.name != null}">
					<span>
	              	<a href=warenkorb.jsp>Warenkorb</a>
	              	</span>
	            	<a href="IndexServlet?action=logout">Logout</a>
	            </c:if>
			    <form method="get" action="SucheFormServlet">
			        <input type="text" name="suche" placeholder="Suchanfrage">
			        <input type="submit" value="Suchen!">
			    </form>
			</div>
		</div>
	
		<h1>Willkommen auf Hörbücher!</h1>
	</body>
</html>
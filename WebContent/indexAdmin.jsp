<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="artikelTag" uri="/WEB-INF/lib/artikelTag.tld" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="Beschreibung" content="Startseite der Datenbank zum Verkauf von Hörbüchern">
	<meta name="author" content="Tobias Brakel">
	<title>Startseite</title>
	
	<link href="indexStyle.css" type="text/css" rel="stylesheet">
</head>

<div align="right">
		<b id="nick">${user.nutzername }</b>
	<span>
		<a href="warenkorb.jsp">Warenkorb</a>
	</span>
		<a href="">Logout</a>
</div>

<div align="right">
	<form method="get" action="SucheFormServlet">
		<input type="text" name="suche" placeholder="Suchanfrage">
		<input type="submit" value="Suchen!">
	</form>
</div>

<table border="1">
	<tr><td>
	<a href="ProfilFormServlet">Profil bearbeiten</a></td></tr>
	<tr><td>
	<a href="seite1.jsp">Artikel bearbeiten</a></td></tr>
	<tr><td>
	<a href="mitglieder.jsp">Mitglieder</a></td></tr>
</table>

<body>

</body>
<footer>
		<a href="impressum.jsp">Impressum</a>
 </footer>
</html>
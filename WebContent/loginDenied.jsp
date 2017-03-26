<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="Beschreibung" content="Ausgabe wenn versuchter Login oder Registierung fehlgeschlagen">
	<meta name="author" content="Jan Schaffland">
	<title>Fehlgeschlagen</title>
</head>
<body>
Ihre Anmeldung oder Registration ist leider fehlgeschlagen.
<br>
<br>
${fehler }
<br>
<br>
<table border="1">
	<tr><td>
	<a href="index.jsp">Zur Startseite</a></td></tr>
	<tr><td>
	 <a href="login.jsp">Zurück zur Anmeldung</a></td></tr>
</table>
</body>
</html>
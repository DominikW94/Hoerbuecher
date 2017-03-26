<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="Beschreibung" content="Ausgabe wenn versuchter Login oder Registierung erfolgreich">
	<meta name="author" content="Jan Schaffland">
	<title>Erfolgreich</title>
</head>
<body>
Glückwunsch! Sie haben sich erfolgreich registriert.<br>
Ihnen wird in Kürze eine Bestätigungsmail unter folgender Adresse zugesandt: <b>${user.email }</b> <br>
Mit folgenden Daten: <br>
Nutzername: <b>${user.nutzername }	</b><br>
Vorname: 	<b>${user.vorname }	    </b><br>
Nachname:	<b>${user.nachname }	</b><br>
Alter: 		<b>${user.alter }		</b><br>
<br>
<br>
<br>
<br>
<a href="index.jsp">Weiter</a>
</body>
</html>
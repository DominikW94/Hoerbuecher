<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="Beschreibung" content="Formular zur Anmeldung oder Registrierung von Benutzern oder Besuchern">
	<meta name="author" content="Tobias Brakel">
	<title>Login</title>
	
	<link href="loginStyle.css" type="text/css" rel="stylesheet">
</head>
<body>
	<h2>Anmelden</h2>
	<form method="post" action="AnmeldeFormServlet">
		<input type="text" name="a_nutzer" placeholder="Benutzername">		<br> 
		<input type="password" name="a_passwort" placeholder="Passwort"> 	<br> 
		<input type="submit" value="Anmelden">
		<input type="reset" value="Zurücksetzen">
	</form>
	<br>
	<br>
	<h2>Noch nicht registriert?</h2>
	<form method="post" action="RegistrationFormServlet">
		<input type="text" name="vorname" placeholder="Vorname"> 			<br>
		<input type="text" name="nachname" placeholder="Nachname"> 			<br>
		<input type="number" name="alter" placeholder="Alter">				<br>
		<input type="email" name="mail" placeholder="E-Mail-Adresse"> 		<br>
		<input type="email" name="mail2" placeholder="E-Mail bestätigen"> 	<br>
		<input type="text" name="r_nutzer" placeholder="Benutzername">		<br>
		<input type="password" name="r_passwort" placeholder="Passwort">	<br>
		<input type="password" name="r_passwort2" placeholder="Passwort bestätigen"> <br>
		<input type="submit" value="Registrieren"> 
		<input type="reset" value="Zurücksetzen">
	</form>
	<br>
	<br>
	<a href="index.jsp">Zurück zur Startseite</a>
	<br>
</body>
</html>
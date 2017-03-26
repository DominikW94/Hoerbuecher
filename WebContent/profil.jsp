<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="author" content="Tobias Brakel">
	<link href="profilStyle.css" type="text/css" rel="stylesheet">
	<title>Account Bearbeitung</title>
</head>
<body>
	<div align="right">
		<a href="">Logout</a>
	</div>
	
    <div id="wrapper">
	<div align="center">
		<h2>Ihr Profil</h2>
	</div>
	
	<div class="left">
		Vorname: ${user.vorname}
		<br>Nachname: ${user.nachname }
		<br>Alter:${user.alter}
		<br>Nick: ${user.nutzername}
		<br>Email:${user.email }
		<br><br><br><br>
		<br><span>${fehler }</span>
		<br><br><br><br>
		
	</div>
	
	<div class="right">
	   Email-Adresse:
		<form method="post" action="ProfilFormServlet">
			<input type="text" name= "mail" name="strasse"  placeholder="Emailadresse">
			<br><input type="submit" value="Speichern">
		</form>
		<br>
		<br>
		Benutzernamen ändern:
		<form method="post" action="ProfilFormServlet">
			<input type="text" name = "changeName" placeholder="Neuer Benutzername">
		<br><input type="submit" value="Speichern">
		</form>
		<br>
		<br>
		Passwort ändern:
		<form method="post" action="ProfilFormServlet">
			<input type="password" name = "pwOld" placeholder="altes Passwort">
		<br><input type="password" name = "pwNew" placeholder="neues Passwort">
		<br><input type="password" placeholder="neues Passwort bestätigen">
		<br><input type="submit" value="Speichern">
		</form>
	</div>
	<br>
	<br>
	<div align="center">
	<br>
	<span style="color:#B81A1A">
		Soll derAccount gelöscht werden? <br>
		Kontaktieren Sie einen Admin.
	</span>
	<br>
	<br>
		<a href="indexAnmeldung.jsp">Zur Startseite</a>
	</div>
	</div>
</body>
</html>
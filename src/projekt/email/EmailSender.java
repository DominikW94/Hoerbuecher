package projekt.email;

import java.util.ArrayList;

//www.tutorialspoint.com/javamail_api/javamail_api_sending_simple_email.htm

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class EmailSender {
	
	//Variablen fuer die Methoden
	private final String emailName = "hoerprojekt@gmail.com";
	private final String emailPasswort = "hoerprojekt@";
	private String host = "smtp.gmail.com";
	
	//Klassenvariablen fuer die Registration
	private String vorname;
	private String nachname;
	private int alter;
	private String r_nutzer;
	private String r_passwort;
	private String zielMail;
	
	//erster Konstruktor fuer die Registration
	public EmailSender(String vorname, String nachname, int alter, String r_nutzer,
			String r_passwort, String zielMail) {
		super();
		this.zielMail = zielMail;
		this.vorname = vorname;
		this.nachname = nachname;
		this.alter = alter;
		this.r_nutzer = r_nutzer;
		this.r_passwort = r_passwort;
	}
	//Konstruktor zum Aufrufen bei der Rechnung
	public EmailSender() {
		
	}
	
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public int getAlter() {
		return alter;
	}

	public void setAlter(int alter) {
		this.alter = alter;
	}

	public String getR_nutzer() {
		return r_nutzer;
	}

	public void setR_nutzer(String r_nutzer) {
		this.r_nutzer = r_nutzer;
	}

	public String getR_passwort() {
		return r_passwort;
	}

	public void setR_passwort(String r_passwort) {
		this.r_passwort = r_passwort;
	}

	public String getZielMail() {
		return zielMail;
	}

	public void setZielMail(String zielMail) {
		this.zielMail = zielMail;
	}
	
	public void registrationEmail() {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(emailName, emailPasswort);
			}
		});

		try {
			Message message = new MimeMessage(session);		
			message.setFrom(new InternetAddress(emailName));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(zielMail));
			message.setSubject("Einlogdaten für Hörbücher");
			message.setText("Ihre Daten lauten: " + vorname + ", " + nachname + ", " + alter  + ".\n"
					 + "Ihr Benutzername lautet: " + r_nutzer + ".\n"
					 + "Ihr Passwort lautet: " + r_passwort + ".\n"
					 + "Bitte schreiben sie sich Ihre Daten auf oder speichern sie diese Email!");
			Transport.send(message);
			}
		catch (MessagingException e) {
		throw new RuntimeException(e);
		}
	}
	
	public void payEmail(ArrayList<String[]> artikel, Double preis,  String mail) {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(emailName, emailPasswort);
			}
		});

		//Ausgabe der verschiedenen Artikel
		String ausgabe = "";
		for (int i = 0; i < artikel.size(); i++) {
			for (int j = 0; j < artikel.get(i).length; j++) {
				ausgabe += artikel.get(i)[j] + ", ";
			}
		}
		//Ausgabe des Preises mit einfuegen
		ausgabe += "mit einem Gesamtpreis von " + preis + ".";
		
		try {
			Message message = new MimeMessage(session);		
			message.setFrom(new InternetAddress(emailName));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
			message.setSubject("Einlogdaten für Hörbücher");
			message.setText("Sie haben bei Hörbücher folgende Artikel gekauft:\n" + ausgabe);
						 
			Transport.send(message);
			}
		catch (MessagingException e) {
		throw new RuntimeException(e);
		}
	}

}


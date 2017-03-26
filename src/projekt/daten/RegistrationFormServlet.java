package projekt.daten;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projekt.database.DatabaseConnection;
import projekt.email.EmailSender;
import projekt.user.User;

/**
 * Servlet implementation class RegistrationFormServlet
 */
@WebServlet("/RegistrationFormServlet")
public class RegistrationFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationFormServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Daten zum registrieren
		String vorname;
		String nachname;
		int alter;
		String mail;
		String mail2;
		String r_nutzer;
		String r_passwort;
		String r_passwort2;
		
		//Fehlerausgabe
		String fehler;
		
		//Pruefen ob alle Daten aus dem Formular einen Wert haben und zugewiesen werden koennen
		if (!request.getParameter("vorname").isEmpty() && !request.getParameter("nachname").isEmpty() && !request.getParameter("alter").isEmpty()
				&& !request.getParameter("mail").isEmpty() && !request.getParameter("mail2").isEmpty() && !request.getParameter("r_nutzer").isEmpty()
				&& !request.getParameter("r_passwort").isEmpty() && !request.getParameter("r_passwort2").isEmpty()) {

			// Daten aus dem Anmeldeformular erheben
			vorname = request.getParameter("vorname");
			nachname = request.getParameter("nachname");
			alter = Integer.parseInt(request.getParameter("alter"));
			mail = request.getParameter("mail");
			mail2 = request.getParameter("mail2");
			r_nutzer = request.getParameter("r_nutzer");
			r_passwort = request.getParameter("r_passwort");
			r_passwort2 = request.getParameter("r_passwort2");
			
			//User wird in der Datenbank registriert
			String query = "INSERT INTO nutzer (vorname, nachname, alter, geschlecht, nick, passwort, kunden_id, email, rolle) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			//Höchste Kunden_ID von der Tabelle nutzer bekommen
			String idQuery = "SELECT MAX(kunden_id) FROM nutzer";
			int kundenID;
			
			//Alle vergebenen Email-Adressen mit der Eingabe vergleichen
			String mailQuery = "SELECT email FROM nutzer WHERE email ILIKE ?";
			
			//Alle Benutzernamen die gleich des eingegebenen Nutzernamens sind
			String nickQuery = "SELECT nick FROM nutzer WHERE nick ILIKE ?";
			
			// Überprüfen ob die Eingabewiederholungen der Registrierung übereinstimmen
			if ((r_passwort.equals(r_passwort2)) && (mail.equals(mail2))) {
				
				// ein neues Objekt wird erstellt, falls Eingabewiederholungen übereinstimmen
				User user = new User(vorname, nachname, alter, r_nutzer, r_passwort, mail);
				
				EmailSender sendeMail = new EmailSender(vorname, nachname, alter, r_nutzer, r_passwort, mail);
				sendeMail.registrationEmail();
				
				//Verbindung zur Datenbank wird hergestellt
				Connection connection = DatabaseConnection.getConnection();
				
				try {
					//Gesichertes Statement für das überprüfen der Email-Adresse bei einer Registration
					PreparedStatement mailStatement = connection.prepareStatement(mailQuery);
					mailStatement.setString(1, mail);
					mailStatement.executeQuery();
					
					//Ergebnis als Resultset
					ResultSet ers = mailStatement.executeQuery();
					
					boolean mailExistiert = false;
					
					while(ers.next()) {
						//Wenn die Abfrage nach gleichen Email-Adressen nicht leer ist, wird Boolean true gesetzt
						if(ers.getString(1) != null) {
							mailExistiert = true;
						}
					}
					
					//Falls bereits eine solche Email-Adresse existiert wird Fehlermeldung ausgegeben
					if (mailExistiert) {
						fehler = "Email-Adresse existiert bereits";
						request.setAttribute("fehler", fehler);
						request.getRequestDispatcher("loginDenied.jsp").forward(request, response);
						return;
					} 

					//Gesichertes Statement für das überprüfen des Benutzernamen bei einer Registration
					PreparedStatement nickStatement = connection.prepareStatement(nickQuery);
					nickStatement.setString(1, r_nutzer);
					nickStatement.executeQuery();
					
					//Ergebnis als Resultset
					ResultSet nrs = nickStatement.executeQuery();
					
					boolean nickExistiert = false;
					
					while (nrs.next()) {
						//Wenn die Abfrage der gleichen Benutzernamen nicht leer ist, existiert bereits ein solcher
						if(nrs.getString(1) != null) {
							nickExistiert = true;
						} 
					}
					
					//Wenn ein solcher Nick bereits existiert, wird an LoginDenied weitergeleitet mit Fehlermeldung
					if (nickExistiert) {
						fehler = "Benutzername existiert bereits";
						request.setAttribute("fehler", fehler);
						request.getRequestDispatcher("loginDenied.jsp").forward(request, response);
						return;
					} 

					//Gesichertes Statement für die neue Kunden-ID einer Registration
					PreparedStatement idStatement = connection.prepareStatement(idQuery);
					idStatement.executeQuery();
					
					// Ergebnis als Resultset
		            ResultSet rs = idStatement.executeQuery();
		            while (rs.next()) {
		            	kundenID = Integer.parseInt(rs.getString(1));
		            	++kundenID;
		            
					//Gesichertes Statement mit den Daten der Registration
					PreparedStatement statement = connection.prepareStatement(query);
					
					//Eingegebene Daten des Kunden werden in Form von Parametern in das Query eingetragen
					//Nach Reihenfolge wird jedem 'Fragezeichen' im Query einem Wert hier zugeordnet
					statement.setString(1, vorname);
					statement.setString(2, nachname);
					statement.setInt(3, alter);
					statement.setBoolean(4, true);
					statement.setString(5, r_nutzer);
					statement.setString(6, r_passwort);
					statement.setInt(7, kundenID);
					statement.setString(8, mail);
					statement.setString(9, "Kunde");
					
					//Tabelle wird aktualisiert, somit die Werte eingetragen
					statement.executeUpdate();
		            }	
					//Verbindung wird wieder getrennt
					connection.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();			
				}
				//Session wird gesetzt
			    HttpSession session = request.getSession();
			    session.setAttribute("name", r_nutzer);
			    session.setAttribute("pwd", r_passwort);
				//Mit einer Ausgabe wird an die loginSuccess.jsp weitergeleitet
				request.setAttribute("user", user);
				request.getRequestDispatcher("loginSuccess.jsp").forward(request, response);
			} else {
				// falls Eingabewiederholungen nicht übereinstimmen, entsprechende Nachricht
				response.sendRedirect("loginDenied.jsp");
			}
		//Wenn nicht alle Felder beim Formular ausgefuellt waren
		} else {
			fehler = "Es waren nicht alle Felder ausgefüllt";
			request.setAttribute("fehler", fehler);
			request.getRequestDispatcher("loginDenied.jsp").forward(request, response);
		}
		
		doGet(request, response);
	}
}

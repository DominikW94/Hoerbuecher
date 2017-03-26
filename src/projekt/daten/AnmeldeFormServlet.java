// -- @author Tobias Brakel --

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

/**
 * Servlet implementation class AnmeldeFormServlet
 */
@WebServlet("/AnmeldeFormServlet")
public class AnmeldeFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnmeldeFormServlet() {
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
		
		String a_nutzer;
		String a_passwort;
	
		//Pruefen ob Felder ausgefuellt wurden
		if(!request.getParameter("a_nutzer").isEmpty() && !request.getParameter("a_passwort").isEmpty()) {
			a_nutzer = request.getParameter("a_nutzer");
			a_passwort = request.getParameter("a_passwort");
			
			//Pruefen ob angemeldeter Benutzer Admin ist
			String adminQuery = "SELECT rolle FROM nutzer WHERE nick ILIKE ?";
			
			//Das Passwort raussuchen, wo der Nick entsprechend der Eingabe
			String anmeldeQuery = "SELECT passwort FROM nutzer WHERE nick ILIKE ?";
			
			//Verbindung zur Datenbank wird hergestellt
			Connection connection = DatabaseConnection.getConnection();
			
			try {
				//Gesichertes Statement fuer das ueberpruefen der Zusammengehoerigkeit von Nick und Passwort
				PreparedStatement anmeldeStatement = connection.prepareStatement(anmeldeQuery);
				anmeldeStatement.setString(1, a_nutzer);
				anmeldeStatement.executeQuery();
				
				//Gesichertes Statement fuer das ueberpruefen der Admin-Rolle
				PreparedStatement adminStatement = connection.prepareStatement(adminQuery);
				adminStatement.setString(1, a_nutzer);
				adminStatement.executeQuery();
				
				//Ergebnis als Resultset
				ResultSet ars = anmeldeStatement.executeQuery();
				ResultSet adrs = adminStatement.executeQuery();
				
				if(ars.next() && adrs.next()) {
					//Wenn Ergebnis der Tabelle mit dem vom Benutzer eingegebenen Passwort übereinstimmt und Nick Admin-Rolle hat
					if(adrs.getString(1).equals("Admin") && ars.getString(1).equals(a_passwort)) {
						//Session wird gesetzt
					    HttpSession session = request.getSession();
					    session.setAttribute("name", a_nutzer);
					    session.setAttribute("password", a_passwort);
						response.sendRedirect("indexAdmin.jsp");
					//Wenn Nick und Passwort uebereinstimmen ohne Admin-Rolle
					} else if(ars.getString(1).equals(a_passwort)) {
						//Session wird gesetzt
					    HttpSession session = request.getSession();
					    session.setAttribute("name", a_nutzer);
					    session.setAttribute("password", a_passwort);
						response.sendRedirect("index.jsp");
					//Wenn Passwort und Nick nicht uebereinstimmen wird in die loginDenied weitergeleitet
					} else {
						response.sendRedirect("loginDenied.jsp");
					}
					connection.close();
				} else {
					response.sendRedirect("loginDenied.jsp");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//Wenn beide Felder aus dem Anmeldeformular nicht gefuellt sind
		} else {
			response.sendRedirect("loginDenied.jsp");
		}
		doGet(request, response);
	}
}

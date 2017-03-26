// -- @author Tobias Brakel --

package projekt.daten;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class BewertungFormServlet
 */
@WebServlet("/BewertungFormServlet")
public class BewertungFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BewertungFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("name") != null ? (String)session.getAttribute("name") : null;
		 
		//Wenn keine Session vorhanden, dann verweise auf login.jsp
		 if(username == null) {
			 response.sendRedirect("login.jsp");
			 return;
		 } else {
			 PrintWriter writer = response.getWriter();
			 writer.write(username);
		 }

		// Artikelname vom zu bewertendem Artikel
		String artikelName = request.getParameter("bewertung");
		//Aus dem Artikelnamen die Artikel_ID beziehen
		String artikelQuery = "SELECT artikel_id FROM hoerbuecher WHERE artikelname = ?";
		// Aus dem Nutzernamen die Kunden-ID beziehen
		String nutzerQuery = "SELECT kunden_id FROM nutzer WHERE nick = ?";
		// Bereitsvorhandene Bewertung ausgeben
		String bewertungQuery = "SELECT bewertung FROM bewertung WHERE artikel_id = ?";
		//Pruefen ob der User das Produkt bereits bewertet hat
		String schonBewertetQuery = "SELECT bewertung FROM bewertung WHERE artikel_id = ? AND kunden_id = ?";
		// Bewertung, Artikelname und Kunden-ID in die eintragen
		String eintragQuery = "INSERT INTO bewertung VALUES (?, ?, ?)";
		
		int bewertungZahl = 0;
		
		Connection connection = DatabaseConnection.getConnection();

		try {
			//Statement zum Beziehen der Artikel_ID
			PreparedStatement artikelStatement = connection.prepareStatement(artikelQuery);
			artikelStatement.setString(1, artikelName);
			artikelStatement.executeQuery();
			ResultSet ars = artikelStatement.executeQuery();
			
			//Statement zum Beziehen der Kunden-ID
			PreparedStatement nutzerStatement = connection.prepareStatement(nutzerQuery);
			nutzerStatement.setString(1, username);
			nutzerStatement.executeQuery();
			ResultSet nrs = nutzerStatement.executeQuery();
			
			//Statement zum pruefen ob eine Bewertung des Produktes bereits vorliegt
			PreparedStatement schonBewertetStatement = connection.prepareStatement(schonBewertetQuery);
			
			//Beziehen der bereits vorhandenen Bewertung des Produktes
			PreparedStatement bewertungStatement = connection.prepareStatement(bewertungQuery);
			
			//Um den Artikel-ID und Nutzer-ID einzugeben und daraus die Bewertung zu beziehen
			while (ars.next() && nrs.next()) {
				schonBewertetStatement.setInt(1, ars.getInt(1));
				schonBewertetStatement.setInt(2, nrs.getInt(1));
				bewertungStatement.setInt(1, ars.getInt(1));
			}
			schonBewertetStatement.executeQuery();
			bewertungStatement.executeQuery();
			
			//Ergebnis der Abfrage nach der bereits vorhandenen Bewertung als ResultSet
			ResultSet schonBwRS = schonBewertetStatement.executeQuery();
			while (schonBwRS.next()) {
				//Wenn eine Bewertung vorliegt (Feld ist nicht leer) dann gehe zurueck
				if(!schonBwRS.getString(1).isEmpty()) {
					response.sendRedirect("seite1.jsp");
					return;
				}
			}
			
			ResultSet bewertungRS = bewertungStatement.executeQuery();
			while(bewertungRS.next()) {
				//Wenn eine Bewertung bereits vorhanden ist, lege diese als Variable fest
				if(!bewertungRS.getString(1).isEmpty()) {
					bewertungZahl = bewertungRS.getInt(1);
				//Falls keine Bewertung vorhanden ist, fuege 0 ein
				} else {
					bewertungZahl = 0;
				}
			}
			System.out.println("Bewertung: " + bewertungZahl);
			
			//Statement zum eintragen der Bewertung in die Tabelle
			PreparedStatement eintragStatement = connection.prepareStatement(eintragQuery);
			
			//Um die Werte der Ergebnisse aus den ResultSets in das Statement der Bewertung einzutragen
			while (ars.next() && nrs.next()) {
				eintragStatement.setInt(1, ++bewertungZahl);
				eintragStatement.setInt(2, ars.getInt(1));
				eintragStatement.setInt(3, nrs.getInt(1));
				eintragStatement.executeUpdate();
				System.out.println("im while");
			}
			response.sendRedirect("seite1.jsp");
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

package projekt.daten;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projekt.database.DatabaseConnection;
import projekt.email.EmailSender;

/**
 * Servlet implementation class RechnungFormServlet
 */
@WebServlet("/RechnungFormServlet")
public class RechnungFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RechnungFormServlet() {
        super();
        // TODO Auto-generated constructor stub
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

		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("name");
		 
		Double kauf = Double.parseDouble(request.getParameter("kauf"));
		
		//Ausgabe von Artikelname, ID und Preis fuer die Rechnung
		String warenQuery = "SELECT artikelname, artikel_id, preis FROM warenkorb WHERE nutzername ILIKE ?";
		
		//Warenkorb des Nutzers loeschen
		String loeschWarenQuery = "DELETE FROM warenkorb WHERE nutzername = ?";
		
		//Query zum Beziehen der Email des Nutzers
		String mailQuery = "SELECT email FROM nutzer WHERE nick = ?";
		
		Connection connection = DatabaseConnection.getConnection();
		
		try {
			//Statement um die gespeicherten Inhalte des Warenkorbes eines Nutzers auszugeben
			PreparedStatement warenStatement = connection.prepareStatement(warenQuery);
			warenStatement.setString(1, username);
			warenStatement.executeQuery();
			
			ResultSet wrs = warenStatement.executeQuery();
			ArrayList<String[]> results = new ArrayList<String[]>();
			
			//Aufbau der Tabelle
			while(wrs.next()) {
				// Für jedes next() wird das Ergebnis zwischengespeichert
				String[] s = { wrs.getString(1), wrs.getString(2), wrs.getString(3), };
				// Alle zwischengespeicherten Ergebnisse letztenendes in die Liste einfügen
				results.add(s);
			}
			
			// Spaltennamen und Liste weitersenden
			request.setAttribute("resultList", results);
			request.setAttribute("summe", kauf);
			
			PreparedStatement mailStatement = connection.prepareStatement(mailQuery);
			mailStatement.setString(1, username);
			mailStatement.executeQuery();
			ResultSet mrs = mailStatement.executeQuery();
			if(mrs.next()) {
				//Artikelname mit ID, Preis und Mail werden uebergeben um eine Rechnung als Mail zu sende
				EmailSender sendeMail = new EmailSender();
				sendeMail.payEmail(results, kauf, mrs.getString(1));
			}
			
			//Statement zum loeschen des Warenkorbes nach dem Einkauf
			PreparedStatement loeschStatement = connection.prepareStatement(loeschWarenQuery);
			loeschStatement.setString(1, username);
			loeschStatement.executeUpdate();
			
			request.getRequestDispatcher("rechnung.jsp").forward(request, response);

			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package projekt.daten;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class WarenFormServlet
 */
@WebServlet("/WarenFormServlet")
public class WarenFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WarenFormServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 HttpSession session = request.getSession();
		 String username = (String)session.getAttribute("name");
		    
		 PrintWriter writer = response.getWriter();
		 writer.write(username);
		
		//Query um die ID des Kunden zu ermitteln
		String idQuery = "SELECT kunden_id FROM nutzer WHERE nick ILIKE ?";
		
		//Alle Preise des angelegten Warenkorbes von einem Nutzer aufsummieren und als summe eintragen
		String preisQuery = "SELECT SUM(preis) AS preis FROM warenkorb WHERE nutzername ILIKE ?";
		
		// Zeige die zugehoerige Daten vom entsprechendem Artikelnamen
		String wareQuery = "SELECT artikelname, artikel_id, preis FROM hoerbuecher WHERE artikelname = ?";
		
		//Abfrage ob Artikel-ID bereits im Warenkorb vorhanden
		String existQuery= "SELECT * FROM warenkorb WHERE artikelname = ? AND  nutzername = ? ";
		
		//Daten des Artikels (Name, ID, Preis, Kunde, KundenID) in die Tabelle warenkorb in der Datenbank einfuegen
		String warenkorbQuery = "INSERT INTO warenkorb VALUES (?, ?, ?, ?, ?) ";
		
		//Alle Daten aus der Tabelle warenkorb waehlen
		String ausgabeQuery = "SELECT artikelname, artikel_id, preis FROM warenkorb WHERE nutzername ILIKE ?";
		
		Connection connection = DatabaseConnection.getConnection();

		try {
			//Variable fuer Gesamtpreis
			double summe = 0.0;
			//Variable fuer Kunden-ID
			int kunden_id = 0;
			
			//username wird eingegeben und daraus die zugehoerige kunden_id ermittelt
			PreparedStatement idStatement = connection.prepareStatement(idQuery);
			idStatement.setString(1, username);
			idStatement.executeQuery();
			//Ergebnis des ResultSet in die Variable speichern
			ResultSet krs = idStatement.executeQuery();
			while(krs.next()) {
				kunden_id = krs.getInt(1);
			}
			
			//Pruefen ob Produktname aus dem Formular vom Taglib uebergeben wurde
			if(request.getParameter("artikelname") != null) {
				//Artikelname wird eingefuegt und daraus die restlichen Daten bezogen
				PreparedStatement wareStatement = connection.prepareStatement(wareQuery);
				wareStatement.setString(1, request.getParameter("artikelname"));
				wareStatement.executeQuery();
				
				//Statement zum pruefen ob Artikel bereits im Warenkorb vorhanden
				PreparedStatement existStatement  = connection.prepareStatement(existQuery);
				existStatement.setString(1, request.getParameter("artikelname"));
				existStatement.setString(2, username);
				existStatement.executeQuery();
				
				ResultSet ers = existStatement.executeQuery();
				while(ers.next()) {
					//Wenn Inhalt nicht leer ist, dann ist der Artikel bereits im Warenkorb
					 if(!ers.getString(1).isEmpty()) {
						 //verweise zurueck auf die Artikelliste
						 response.sendRedirect("seite1.jsp");
						 return;
					 }
				}
				//Gesichertes Statement um Daten temporaer in den Warenkorb einzufuegen
				PreparedStatement warenkorbStatement = connection.prepareStatement(warenkorbQuery);
				
				//Ergebnisse von wareStatement als ResultSet um diese in den Warenkorb einfuegen zu koennen
				ResultSet wrs = wareStatement.executeQuery();
				
				//Artikelname, ID, Preis einfuegen 
				while (wrs.next()) {
					warenkorbStatement.setString(1, wrs.getString(1));
					warenkorbStatement.setInt(2, Integer.parseInt(wrs.getString(2)));
					warenkorbStatement.setDouble(3, Double.parseDouble(wrs.getString(3)));
				}
				warenkorbStatement.setString(4, username);
				warenkorbStatement.setInt(5, kunden_id);
				warenkorbStatement.executeUpdate();
			}
			
			//Statement zur Ausgabe des Warenkorbes (Artikelname, ID, Preis)
			PreparedStatement ausgabeStatement = connection.prepareStatement(ausgabeQuery);
			ausgabeStatement.setString(1, username);
			ausgabeStatement.executeQuery();
			
			//Ausgabe des Warenkorbs als RS
			ResultSet ars = ausgabeStatement.executeQuery();
			ArrayList<String[]> results = new ArrayList<String[]>();
			
			//Aufbau der Tabelle
			while(ars.next()) {
				//Formular fuer das entfernen von Artikeln aus dem Warenkorb
				String form = "<form method=post action=WarenFormServlet>"
						+ "<input type=hidden name=entfernen value="+ars.getString(2)+">"
						+ "<input type=submit value=löschen></form>";
				// Für jedes next() wird das Ergebnis zwischengespeichert
				String[] s = { ars.getString(1), ars.getString(2), ars.getString(3), form, };
				// Alle zwischengespeicherten Ergebnisse letztenendes in die Liste einfügen
				results.add(s);
			}
			
			//Summe der Preise aus Warenkorb ermitteln, als Ergebnis vom ResultSet in Variable einspeichern
			PreparedStatement preisStatement = connection.prepareStatement(preisQuery);
			preisStatement.setString(1, username);
			preisStatement.executeQuery();
			ResultSet prs = preisStatement.executeQuery();
			while(prs.next()) {
				summe = prs.getDouble(1);
			}
			
			// Spaltennamen und Liste weitersenden
			request.setAttribute("columnNames", new String[] { "Artikel", "Artikel-ID", "Preis" });
			request.setAttribute("resultList", results);
			request.setAttribute("summe", summe);

			request.getRequestDispatcher("warenkorb.jsp").forward(request, response);

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Artikel-ID wird als aus dem Formular uebergeben
		int entf = Integer.parseInt(request.getParameter("entfernen"));
		
		//Query zum loeschen eines Eintrages aus dem Warenkorb
		String loeschQuery = "DELETE FROM warenkorb WHERE artikel_id = ?";
		
		Connection connection = DatabaseConnection.getConnection();
		
		try {
			//Dem Statement wird die Artikel-ID uebergeben und zugehoerige Zeile geloescht
			PreparedStatement loeschStatement = connection.prepareStatement(loeschQuery);
			loeschStatement.setInt(1, entf);
			loeschStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("WarenFormServlet");
	}

}

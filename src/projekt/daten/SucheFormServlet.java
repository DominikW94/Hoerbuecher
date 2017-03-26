// -- @author Tobias Brakel --

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

import projekt.database.DatabaseConnection;

/**
 * Servlet implementation class SucheFormServlet
 */
@WebServlet("/SucheFormServlet")
public class SucheFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SucheFormServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //Diese Methode wird verwendet um den Nutzer bestimmte Kategorien anzuzeigen
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Kategorie wird uebergeben
		String kategorie = request.getParameter("kategorie");
		
		//Alle hoerbuecher mit uebergebener Kategorie
		String kategorieQuery = "SELECT artikelname, kategorie, preis  FROM hoerbuecher WHERE kategorie = ?";

		Connection connection = DatabaseConnection.getConnection();
		
		try {
			PreparedStatement kategorieStatement = connection.prepareStatement(kategorieQuery);
			kategorieStatement.setString(1, kategorie);
			kategorieStatement.executeQuery();
			
			ResultSet krs = kategorieStatement.executeQuery();
			// Liste in der die Ergebnisse eingefügt werden sollen
			
			ArrayList<String[]> results = new ArrayList<String[]>();
			
			while (krs.next()) {
				//Formular bei dem jeweils der Name des Artikels übergeben wird
				String korb = "<form method=get action=WarenFormServlet>"
						+ "<input type=hidden name=artikelname value='"+krs.getString(1)+"'>"
						+ "<input type=submit value='in den Warenkorb'></form>";
				String bewertung = "<form method=get action=BewertungFormServlet>"
						+ "<input type=hidden name=bewertung value='"+krs.getString(1)+"'>"
						+ "<input type=submit value=Bewerten></form>";
			
				// Für jedes next() wird das Ergebnis zwischengespeichert
				String[] s = { krs.getString(1), krs.getString(2), krs.getString(3), korb, bewertung, };
				// Alle zwischengespeicherten Ergebnisse letztenendes in die Liste einfügen
				results.add(s);
			}
			// Spaltennamen und Liste weitersenden
			request.setAttribute("columnNames", new String[] { "Artikel", "Kategorie", "Preis"});
			request.setAttribute("resultList", results);

			request.getRequestDispatcher("seite2.jsp").forward(request, response);
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	//Diese Methode wird verwendet um dem Nutzer/Besucher beim Nutzen der Suchfunktion die Ergebnisse anzuzeigen
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String suche = request.getParameter("suche");
		
		String suchQuery = "SELECT artikelname, kategorie, preis FROM hoerbuecher WHERE artikelname ILIKE ?";
		
		Connection connection = DatabaseConnection.getConnection();
		
		try {
			//Statement um die Suchanfrage zu bearbeiten. Ergebnisse mit dem eingegebenen Inhalt werden ausgegeben
			PreparedStatement suchStatement = connection.prepareStatement(suchQuery);
			suchStatement.setString(1, "%" + suche + "%");
			suchStatement.execute();
			
			//Ausgabe mit ResultSet ermitteln
			ResultSet srs = suchStatement.executeQuery();
			
			ArrayList<String[]> results = new ArrayList<String[]>();
			System.out.println(suche);
			while (srs.next()) {
				// Für jedes next() wird das Ergebnis zwischengespeichert
				String[] s = { srs.getString(1), srs.getString(2), srs.getString(3), };
				// Alle zwischengespeicherten Ergebnisse letztenendes in die Liste einfügen
				results.add(s);
			}
			// Spaltennamen und Liste weitersenden
			request.setAttribute("columnNames", new String[] { "Artikel", "Kategorie", "Preis"});
			request.setAttribute("resultList", results);

			request.getRequestDispatcher("seite2.jsp").forward(request, response);
			connection.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();			
		}
	}
}

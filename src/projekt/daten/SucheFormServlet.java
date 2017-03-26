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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Kategorie wird uebergeben
		String kategorie = request.getParameter("kategorie");
		
		//Alle hoerbuecher mit uebergebener Kategorie
		String kategorieQuery = "SELECT artikelname, kategorie, preis  FROM hoerbuecher WHERE kategorie ILIKE ?";

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
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		
//		String suche = request.getParameter("suche");
//		
//		String query = "SELECT artikelname FROM hoerbuecher "
//				+ "WHERE artikelname ILIKE %" + suche +"%";
//		
//		Connection connection = DatabaseConnection.getConnection();
//		
//		try {
//			PreparedStatement statement = connection.prepareStatement(query);
//			statement.execute();
//			
//			
//			
//			connection.close();
//		} 
//		catch (SQLException e) {
//			e.printStackTrace();			
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}

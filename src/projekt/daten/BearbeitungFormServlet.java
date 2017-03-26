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
 * Servlet implementation class BearbeitungFormServlet
 */
@WebServlet("/BearbeitungFormServlet")
public class BearbeitungFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BearbeitungFormServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Alle Nutzer und ihre Daten ausgeben aufteigend sortiert nach ihrer ID
		String nutzerQuery = "Select vorname, nachname, alter, geschlecht, nick, passwort, kunden_id, email, rolle FROM nutzer"
				+ " ORDER BY kunden_id ASC";

		Connection connection = DatabaseConnection.getConnection();
		try {
			// Gesichertes Statement um alle Daten aller Nutzer einzuholen
			PreparedStatement nutzerStatement = connection.prepareStatement(nutzerQuery);
			nutzerStatement.executeQuery();

			// Ergebnis als Resultset
			ResultSet nrs = nutzerStatement.executeQuery();

			// Liste in der die Ergebnisse eingefügt werden sollen
			ArrayList<String[]> results = new ArrayList<String[]>();

			while (nrs.next()) {
				// Für jedes next() wird das Ergebnis zwischengespeichert
				String[] s = { nrs.getString(1), nrs.getString(2), nrs.getString(3), nrs.getString(4), nrs.getString(5),
						nrs.getString(6), nrs.getString(7), nrs.getString(8), nrs.getString(9), };
				// Alle zwischengespeicherten Ergebnisse letztenendes in die Liste einfügen
				results.add(s);
			}
			// Spaltennamen und Liste weitersenden
			request.setAttribute("columnNames", new String[] { "Vorname", "Nachname", "Alter", "Geschlecht",
					"Benutzername", "Passwort", "ID", "Email", "Rolle" });
			request.setAttribute("resultList", results);

			request.getRequestDispatcher("mitglieder.jsp").forward(request, response);

			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Nutzer mit eingegebener ID wird gelöscht
		String loeschQuery = "DELETE FROM nutzer WHERE kunden_id = ?";
		//Rolle eines Nutzers wird von Kunde zum Admin geändert
		String bearbeitQuery = "UPDATE nutzer SET rolle = '?' WHERE kunden_id = ?";
		
		int loesch_id;
		int kunden_id;

		Connection connection = DatabaseConnection.getConnection();
		
		try
        {
			// Wenn ein Wert in kunden_id übergeben wird, weise ihn zu und bearbeite das Query
			if (request.getParameter("loeschID") != null) {
				loesch_id = Integer.parseInt(request.getParameter("loeschID"));
				 
				PreparedStatement loeschStatement = connection.prepareStatement(loeschQuery);
				loeschStatement.setInt(1, loesch_id);
				loeschStatement.executeUpdate();
				
			// Wenn ID bearbeitet werden soll, statt gelöscht
			} else if (request.getParameter("kundenID") != null) {
				kunden_id = Integer.parseInt(request.getParameter("kundenID"));
				
				PreparedStatement bearbeitStatement = connection.prepareStatement(bearbeitQuery);
				bearbeitStatement.setInt(1, kunden_id);
				bearbeitStatement.executeUpdate();
			}
			
			connection.close();
        }
		catch (SQLException e)        {
            e.printStackTrace();
        }
		
		doGet(request, response);
	}
}

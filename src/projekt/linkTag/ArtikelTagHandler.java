// -- @author Tobias Brakel --

package projekt.linkTag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import projekt.database.DatabaseConnection;

public class ArtikelTagHandler extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private String artikel;
	
	//Alle Nutzer und ihre Daten ausgeben aufteigend sortiert nach ihrer ID
	private String artikelQuery = "Select artikelname, kategorie, preis  FROM hoerbuecher ORDER BY artikel_id ASC";
	
	@Override
	public int doStartTag() throws JspException {

		Connection connection = DatabaseConnection.getConnection();
		
		//Ausgabe an die JSP
		JspWriter out = pageContext.getOut();
		
		try {
			// Gesichertes Statement um alle Artikel anzuzeigen
			PreparedStatement artikelStatement = connection.prepareStatement(artikelQuery);
			artikelStatement.executeQuery();

			// Ergebnis als Resultset
			ResultSet ars = artikelStatement.executeQuery();
			
			try {
				//Spalte mit Überschriften
				out.println("<table border=2><tr><th>Artikel</th><th>Kategorie</th><th>Preis</th></tr>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while (ars.next()) {
				//Formular bei dem jeweils der Name des Artikels übergeben wird
				String korb = "<form method=get action=WarenFormServlet>"
						+ "<input type=hidden name=artikelname value='"+ars.getString(1)+"'>"
						+ "<input type=submit value='in den Warenkorb'></form>";
				String bewertung = "<form method=get action=BewertungFormServlet>"
						+ "<input type=hidden name=bewertung value='"+ars.getString(1)+"'>"
						+ "<input type=submit value=Bewerten></form>";
				try {
					//Inhalte der Tabelle mit den Artikeln
					out.println("<tr><td>" + ars.getString(1) + "</td><td>" + ars.getString(2) + "</td><td>" + ars.getString(3) 
					+ "</td><td>" + korb + "</td><td>" + bewertung + "</d></tr>" );
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				//Ende der Tabelle
				out.println("</table>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			connection.close();
							
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
		return super.doStartTag();
	}
	
	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}
	
	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public String getArtikel() {
		return artikel;
	}

	public void setArtikel(String artikel) {
		this.artikel = artikel;
	}
}

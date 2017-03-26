// -- @author Jan Schaffland --

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
import projekt.user.User;

/**
 * Servlet implementation class ProfilFormServlet
 */
@WebServlet("/ProfilFormServlet")
public class ProfilFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfilFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    HttpSession session = request.getSession();
	    String username = (String)session.getAttribute("name");
	    
	    PrintWriter writer = response.getWriter();
	    writer.write(username);
	    
	    String changeMail;
        String changeName;
        String pwOld;
        String pwNew;
        
        String fehler;
        
        //Alle vergebenen Email-Adressen mit der Eingabe vergleichen
		String mailQuery = "SELECT email FROM nutzer WHERE email ILIKE ?";
		
		//Alle Benutzernamen die gleich des eingegebenen Nutzernamens sind
		String nickQuery = "SELECT nick FROM nutzer WHERE nick ILIKE ?";
        
        //Alle Spalten von einem angelegten User bekommen
        String userQuery = "SELECT * FROM nutzer WHERE nick ILIKE ?";
     
        String mailUpdate = "UPDATE nutzer SET email = ? WHERE  nick ILIKE ?"; 
        String nickUpdate = "UPDATE nutzer SET nick = ? WHERE nick ILIKE ?";
        String passwordUpdate = "UPDATE nutzer SET passwort = ? WHERE passwort ILIKE ?";
        
	    Connection connection = DatabaseConnection.getConnection();
	    
	    try {
	    	//Pruefen welcher Wert zum Ändern gesetzt wurde
	    	if(request.getParameter("mail") != null) {
	            changeMail  = request.getParameter("mail");
	            
	          //Gesichertes Statement für das überprüfen der Email-Adresse
				PreparedStatement mailStatement = connection.prepareStatement(mailQuery);
				mailStatement.setString(1, changeMail);
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
				} else {
					PreparedStatement mailChangeStatement = connection.prepareStatement(mailUpdate);
					mailChangeStatement.setString(1, changeMail);
					mailChangeStatement.setString(2, username);
					mailChangeStatement.executeUpdate();
				}
	        } else if (request.getParameter("changeName") != null) {
	            changeName = request.getParameter("changeName");
	          //Gesichertes Statement für das überprüfen des Benutzernamen
				PreparedStatement nickStatement = connection.prepareStatement(nickQuery);
				nickStatement.setString(1, changeName);
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
				} else {
					PreparedStatement nickChangeStatement = connection.prepareStatement(nickUpdate);
					nickChangeStatement.setString(1, changeName);
					nickChangeStatement.setString(2, username);
					nickChangeStatement.executeUpdate();
				}
	        } else if((request.getParameter("pwOld") != null) && request.getParameter("pwNew") != null) {
	            pwOld = request.getParameter("pwOld");
	            pwNew = request.getParameter("pwNew");
	            PreparedStatement passwordStatement = connection.prepareStatement(passwordUpdate);
	            passwordStatement.setString(1, pwNew);
	            passwordStatement.setString(2, pwOld);
	            passwordStatement.executeUpdate();
	        }
	    	
	    	PreparedStatement userStatement = connection.prepareStatement(userQuery);
	    	userStatement.setString(1, username);
	    	userStatement.executeQuery();
	    	
	    	ResultSet urs = userStatement.executeQuery();
	    	User user = null;
	    	
	    	if(urs.next()) {
	    		user = new User(urs.getString(1), urs.getString(2), Integer.parseInt(urs.getString(3)), urs.getString(5), urs.getString(6), urs.getString(8));
	    		request.setAttribute("user", user);
	    	}
	    	
	    	connection.close();
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    request.getRequestDispatcher("profil.jsp").forward(request, response);
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package projekt.daten;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public IndexServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        try {
            String action = request.getParameter("action");
            if (action != null && action.equals("logout")) {
                session.invalidate();
                response.sendRedirect("index.jsp");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            //e.printStackTrace();
        }
        
        String name = "";
        String password = "";
        
        try {
            //Wenn die Bedingung zutrifft wird der Wert hinter dem ? verwendet. 
            //Anderfalls der Wert hinter dem :
            name = session.getAttribute("name") != null ? (String)session.getAttribute("name") : "";
            password = session.getAttribute("password") != null ? (String)session.getAttribute("password") : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (name != "" && password != "") {
            request.setAttribute("name", name);
            request.setAttribute("password", password);
        }
        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    public String getMenu() {
        
        
        return "test";
    }
}

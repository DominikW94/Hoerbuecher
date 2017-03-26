// -- @author Tobias Brakel --

package projekt.linkTag;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class MenuTagHandler extends TagSupport {
    private static final long serialVersionUID = 1L;
    
    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
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
        
        try {
            out.println("<ul class=\"menu\">");
        
            if (name != "" && password != "") {
                out.println("<li><a href=\"ProfilFormServlet\">Profil bearbeiten</a></li>");
                //out.println("<li><a href=\"warenkorb.jsp\">Warenkorb</a></li>");
                //out.println("<li><a href=\"IndexServlet?action=logout\">Logout</a></li>");
                
                //if admin
                //out.println("<li><a href=\"mitglieder.jsp\">Mitglieder</a></li>");
                
            } else {
                out.println("<li><a href=\"login.jsp\">Anmelden/Registrieren</a></li>");
            }
            
            out.println("<li><a href=\"seite1.jsp\">Unsere Artikel</a></li>");
            out.println("<li><a href=\"impressum.jsp\">Impressum</a></li>");
            out.println("</ul>");
            
        } catch (IOException e) {
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
}

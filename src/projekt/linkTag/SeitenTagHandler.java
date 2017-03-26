package projekt.linkTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class SeitenTagHandler extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private String active;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			
			if (active.equals("Seite1")) {
				out.println ("<a id=act href=seite1.jsp>1</a>");
				out.println ("<a id=not href=seite2.jsp>2</a>");
				out.println ("<a id=not href=seite3.jsp>3</a>");
			} else if (active.equals("Seite2")) {
				out.println ("<a id=not href=seite1.jsp>1</a>");
				out.println ("<a id=act href=seite2.jsp>2</a>");
				out.println ("<a id=not href=seite3.jsp>3</a>");
			} else if (active.equals("Seite3")) {
				out.println ("<a id=not href=seite1.jsp>1</a>");
				out.println ("<a id=not href=seite2.jsp>2</a>");
				out.println ("<a id=act href=seite3.jsp>3</a>");
			}
		} catch (IOException e ) { 
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
	
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}

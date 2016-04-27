package de.hsb.smaevers.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/MyServletTest")
public class MyServletTest  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyServletTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String[]> parameterMap = req.getParameterMap();
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		out.println("<html><body>");
		out.println("<table border=\"1\">");
		out.println("<th>name</th>");
		out.println("<th>value</th>");
		
		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			out.println("<tr>");
			
			out.println("<td>");
			out.println(entry.getKey());
			out.println("</td>");
			
			out.println("<td>");
			for (String s : entry.getValue()) {
				out.print(s);
			}
			out.println("</td>");
			
			out.println("</tr>");
		}
		
		out.println("</table>");
		out.println("</body></html>");
		out.close();
	}

}

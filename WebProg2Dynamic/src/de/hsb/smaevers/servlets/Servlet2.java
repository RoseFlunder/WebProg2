package de.hsb.smaevers.servlets;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servlet/Servlet2")
public class Servlet2 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String COLOR_COOKIE = "color";
	private static final String POINTS = "points";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get color cookie or create it
		Cookie colorCookie = getCookieByName(req, resp, COLOR_COOKIE, true);

		// refresh lifetime of cookie
		colorCookie.setMaxAge(60 * 60 * 24 * 30);

		// set new color chosen by user
		if (req.getParameter("farbe") != null) {
			System.out.println("set color cookie value to req param: " + req.getParameter("farbe"));
			colorCookie.setValue(req.getParameter("farbe"));
			resp.addCookie(colorCookie);
		}

		// get or create new session
		HttpSession session = req.getSession(true);
		// session lifespan set to 30 seconds for the game
		session.setMaxInactiveInterval(30);
		// transfer color from cookie to session
		if (colorCookie.getValue() != null) {
			System.out.println("transfer color to session: " + colorCookie.getValue());
			session.setAttribute(COLOR_COOKIE, colorCookie.getValue());
		}
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String urlParam = req.getParameter("url");
		Integer points = session.getAttribute(POINTS) != null ? (Integer) session.getAttribute(POINTS) : 0;

		System.out.println("Current color: " + session.getAttribute(COLOR_COOKIE));

		if ("game".equals(urlParam) && session.getAttribute(COLOR_COOKIE) != null
				&& !session.getAttribute(COLOR_COOKIE).toString().isEmpty()) {
			if (req.getParameter("image.x") != null && req.getParameter("image.y") != null) {
				// inc points
				if (req.getParameter("image.x").equals(req.getParameter("image.y"))) {
					if (points < 3) {
						session.setAttribute(POINTS, ++points);
					}
				}

				// generate win site when points >= 3 else game site
				if (points >= 3) {
					generateWinSite(out, session);
				} else {
					generateGameSite(resp, req, session, false);
				}

			} else {
				generateGameSite(resp, req, session, false);
			}
		} else if ("win".equals(urlParam) && session.getAttribute(COLOR_COOKIE) != null
				&& !session.getAttribute(COLOR_COOKIE).toString().isEmpty()) {
			if (req.getParameter("win.x") != null && req.getParameter("win.y") != null && points >= 3) {
				// reset points and show win message if session is still valid
				session.setAttribute(POINTS, 0);
				generateGameSite(resp, req, session, true);
			} else if (points >= 3) {
				generateWinSite(out, session);
			} else {
				generateGameSite(resp, req, session, false);
			}
		} else {
			generateColorSite(resp, req, session);
		}
	}

	private Cookie getCookieByName(HttpServletRequest req, HttpServletResponse resp, String name, boolean create) {
		Cookie[] cookies = req.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					System.out.println("cookie found");
					return cookie;
				}
			}
		}

		if (create) {
			System.out.println("create new cookie");
			return new Cookie("color", null);
		}

		return null;
	}

	private void generateGameSite(HttpServletResponse resp, HttpServletRequest req, HttpSession session,
			boolean winMessage) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		Object colorAttr = session.getAttribute(COLOR_COOKIE);
		String color = "#FFFFFF";
		if (colorAttr != null)
			color = colorAttr.toString();
		out.println("<body bgcolor='" + color + "'>");

		if (winMessage) {
			out.println("<p>");
			out.println("Wohoo! Genieße deinen Gewinn und versuche es gleich nochmal.");
			out.println("</p>");
		}

		out.println("<form>");
		out.println("<p>Versuche die Diagonale von links oben nach rechts unten zu treffen:</p>");
		Integer points = session.getAttribute(POINTS) != null ? (Integer) session.getAttribute(POINTS) : 0;

		out.println("<p>Aktuelle Gewinnpunkte:" + points + "</p>");
		out.println("<p>");
		out.println("<input name=\"image\" src=\"../images/RoseFlunder.jpg\" type=\"image\" />");
		out.println("<input name=\"url\" value=\"game\" type=\"hidden\" />");
		out.println("</p>");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	private void generateColorSite(HttpServletResponse resp, HttpServletRequest req, HttpSession session) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		Object colorAttr = session.getAttribute(COLOR_COOKIE);
		String color = "#FFFFFF";
		if (colorAttr != null)
			color = colorAttr.toString();
		out.println("<body bgcolor='" + color + "'>");

		out.println("<form action=\"" + resp.encodeURL("../servlet/Servlet2") + "\" >");
		out.println("<p>Farbe:");
		out.println("<select name=\"farbe\">");
		out.println("<option value=\"#FF0000\">Rot</option>");
		out.println("<option value=\"#00FF00\">Grün</option>");
		out.println("<option value=\"#0000FF\">Blau</option>");
		out.println("<option value=\"#FFFFFF\">Weiß</option>");
		out.println("</select>");
		out.println("</p>");
		out.println("<button name=\"url\" value=\"game\" type=\"submit\" >Gewinnspiel</button>");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	private void generateWinSite(PrintWriter out, HttpSession session) {
		out.println("<html>");
		Object colorAttr = session.getAttribute(COLOR_COOKIE);
		String color = "#FFFFFF";
		if (colorAttr != null)
			color = colorAttr.toString();
		out.println("<body bgcolor='" + color + "'>");

		out.println("<form>");
		out.println("<p>Wähle einen dieser fantastischen Gewinne:</p>");
		out.println("<p>");
		out.println("<input name=\"win\" src=\"../images/club_mate.jpg\" type=\"image\" width=\"300\" />");
		out.println("<input name=\"win\" src=\"../images/underberg.png\" type=\"image\" width=\"300\" />");
		out.println("<input name=\"url\" value=\"win\" type=\"hidden\" />");
		out.println("</p>");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
		out.close();
	}

}

package de.hsb.smaevers.servlets;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/MyServletJpeg")
public class MyServletJpeg extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private DateTimeFormatter formatter;
	
	

	@Override
	public void init() throws ServletException {
		super.init();
		formatter = DateTimeFormatter.ISO_DATE_TIME;
	}



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/jpeg");
		
		ServletOutputStream out = resp.getOutputStream();
		
		LocalDateTime date = LocalDateTime.now();
		String text = date.format(formatter);
		
		int w = Integer.parseInt(req.getParameter("w"));
		int h = Integer.parseInt(req.getParameter("h"));
		
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();
		g2.setBackground(Color.LIGHT_GRAY);
		g2.clearRect(0, 0, w, h);
		g2.setColor(Color.BLACK);
		g2.drawString(text, w/2, h/2);
		
		ImageIO.write(img, "jpeg", out);
		ImageIO.write(img, "jpeg", new File("D:\\test.jpeg"));
		
		out.close();
	}



	@Override
	public void destroy() {
		super.destroy();
		
		System.out.println("destroy");
	}
	
	
}

package de.hsb.smaevers.servlets;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.hsb.smaevers.beans.Person;
import de.hsb.smaevers.data.PersonsBusinessTier;
import de.hsb.smaevers.data.PersonsBusinessTierFactory;

@WebServlet("/servlet/PersonServlet")
public class PersonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private PersonsBusinessTier businessTier;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		try {
			if (businessTier == null){
				businessTier = PersonsBusinessTierFactory.getPersonsBusinessTierInstance();
			}
			
			String idParam = req.getParameter("id");
			String firstnameParam = req.getParameter("firstname");
			
			if ((idParam != null && !idParam.isEmpty()) || firstnameParam != null && !firstnameParam.isEmpty()){
				Person bean = null;
				if (idParam != null && !idParam.isEmpty())
					bean = businessTier.getPerson(idParam);
				else
					bean = businessTier.getPersonByFirstname(firstnameParam);
				
				req.setAttribute("personBean", bean);
				String jsp = "../html/person.jspx";
				RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
				dispatcher.forward(req, resp);
			} else {
				req.setAttribute("personsBean", businessTier.getAllPersons());
				String jsp = "../html/persons.jspx";
				RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
				dispatcher.forward(req, resp);
			}	
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			
			businessTier = null;
			
			String jsp = "../html/error.xhtml";
			RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
			dispatcher.forward(req, resp);
		}
	}

}

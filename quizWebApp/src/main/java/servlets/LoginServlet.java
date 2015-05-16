package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Deelnemer;
import service.GebruikerService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		String gebruikersNaam = request.getParameter("gebruikersNaam");
		String paswoord = request.getParameter("paswoord");
		try {
			Deelnemer deelnemer = GebruikerService.login(gebruikersNaam, paswoord);
			HttpSession session = request.getSession();
			session.setAttribute("deelnemer", deelnemer);
		} catch (IllegalArgumentException iae) {
			response.setStatus(521);
			PrintWriter out = response.getWriter();
			out.println("De gebruikersnaam en/of het paswoord zijn verkeerd");
		}

	}

}

package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.GebruikerService;

@WebServlet("/registreer")
public class RegistreerServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		String gebruikersNaam = request.getParameter("gebruikersNaam");
		String paswoord = request.getParameter("paswoord");

		String message = "";
		try {
			GebruikerService.registreer(gebruikersNaam, paswoord);
			message = "Registratie geslaagd";
		} catch (IllegalStateException ise) {
			message = ise.getMessage();
		}

		try {
			PrintWriter out = response.getWriter();
			out.println(message);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}

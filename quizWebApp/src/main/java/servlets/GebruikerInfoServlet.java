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

@WebServlet("/gebruikerInfo")
public class GebruikerInfoServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		HttpSession session = request.getSession();
		Deelnemer deelnemer = (Deelnemer) session.getAttribute("deelnemer");

		if (deelnemer != null) {
			response.setContentType("text/javascript");
			PrintWriter out = response.getWriter();
			out.println("{\"gebruikersNaam\" : \"" + deelnemer.getGebruikersNaam() + "\"}");
			out.close();
		} else {
			response.sendError(501, "Niet ingelogd");
		}
	}
}

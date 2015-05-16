package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Deelnemer;
import model.VragenReeks;
import service.QuizService;
import context.IngelogdeSpelContext;
import context.SpelContext;

@WebServlet("/startVragenReeks")
public class StartVragenReeksServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		int vragenReeksId = 0;
		try {
			vragenReeksId = Integer.parseInt(request.getParameter("vragenReeksId"));
		} catch (Exception ex) {
			response.sendError(521,
					String.format("Geen vragenreeksID gevraagd of vragenreeksID niet numeriek\n\n%s", ex.getMessage()));
		}

		HttpSession session = request.getSession();
		SpelContext spelContext;
		Deelnemer deelnemer = (Deelnemer) session.getAttribute("deelnemer");
		VragenReeks vragenReeks = QuizService.getVragenReeks(vragenReeksId);
		if (deelnemer == null) {
			spelContext = new SpelContext(vragenReeks);
		} else {
			spelContext = new IngelogdeSpelContext(vragenReeks, deelnemer);
		}
		session.setAttribute("context", spelContext);
	}
}

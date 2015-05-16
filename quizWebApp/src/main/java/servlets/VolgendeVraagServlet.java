package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.antwoord.Antwoord;

import org.json.JSONArray;
import org.json.JSONObject;

import service.QuizService;
import context.IngelogdeSpelContext;
import context.SpelContext;

@WebServlet(value = "/volgendeVraag", loadOnStartup = 1)
public class VolgendeVraagServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private QuizService quizService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			quizService = new QuizService();
			quizService.init();
		} catch (IOException e) {
			throw new ServletException(e);
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		HttpSession session = request.getSession();
		SpelContext spelContext = (SpelContext) session.getAttribute("context");

		if (spelContext == null) {
			response.sendError(521, "Voor deze gebruikerssessie is er geen vragenreeks lopende");
		}

		boolean spelBezig = true;
		boolean requestMetAntwoord = false;
		String antwoord = request.getParameter("antwoord");
		requestMetAntwoord = antwoord != null;

		if (!spelContext.reedsBegonnen()) {
			spelBezig = spelContext.volgendeVraag();
		} else if (requestMetAntwoord) {
			Antwoord ant = QuizService.maakAntwoord(antwoord, spelContext.getHuidigeVraag().getVraagType());
			spelContext.antwoord(ant);
			spelContext.save();
			spelBezig = spelContext.volgendeVraag();
		}

		JSONObject responseJSON = null;

		if (spelBezig && !spelContext.isGedaan()) {
			responseJSON = quizService.getJSONVraag(spelContext.getHuidigeVraag());
			responseJSON.put("aantalVragen", spelContext.getAantalVragen());
			responseJSON.put("huidigeVraagIndex", spelContext.getVolgnummerHuidigeVraag());
			responseJSON.put("verbetering", new JSONArray(spelContext.getAntwoorden()));
			responseJSON.put("afgelopen", false);
		} else {
			spelContext.setGedaan(true);
			if (spelContext instanceof IngelogdeSpelContext) {
				responseJSON = new JSONObject();
				responseJSON.put("afgelopen", true);
				responseJSON.put("uitgebreidRapport", true);
				responseJSON.put("deelnameID", ((IngelogdeSpelContext) spelContext).getDeelname().getDeelnameID());
			} else {
				responseJSON = new JSONObject(spelContext.getVragenReeks());
				responseJSON.put("verbetering", new JSONArray(spelContext.getAntwoorden()));
				responseJSON.put("score", spelContext.getScore());
				responseJSON.put("afgelopen", true);
				responseJSON.put("uitgebreidRapport", false);
			}
		}

		response.setContentType("text/javascript");
		PrintWriter out = response.getWriter();
		out.println(responseJSON);
		out.close();
	}
}

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Deelnemer;
import model.VragenReeks;

import org.json.JSONObject;

import service.JSONService;
import service.QuizService;
import data.DBFacade;

@WebServlet("/vragenreeksen")
public class VragenReeksenServlet extends HttpServlet {

	private DBFacade dbFacade;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		dbFacade = DBFacade.getUniekeInstantie();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		HttpSession session = request.getSession();
		Deelnemer deelnemer = (Deelnemer) session.getAttribute("deelnemer");
		List<VragenReeks> vragenReeksen = dbFacade.getVragenReeksen();

		Map<VragenReeks, Boolean> vragenReeksenIsEnabled = QuizService.getVragenReeksenIsEnabled(vragenReeksen, deelnemer);
		JSONObject resultJSON = JSONService.maakAlleVragenReeksen(vragenReeksenIsEnabled);

		response.setContentType("text/javascript");
		PrintWriter out = response.getWriter();
		out.println(resultJSON);
		out.close();
	}
}

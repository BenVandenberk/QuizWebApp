package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Deelname;
import model.Deelnemer;

import org.json.JSONObject;

import service.JSONService;
import data.DBFacade;

@WebServlet("/rapporten")
public class RapportServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		HttpSession session = request.getSession();
		Deelnemer deelnemer = (Deelnemer) session.getAttribute("deelnemer");

		int deelnameID = 0;
		try {
			deelnameID = Integer.parseInt(request.getParameter("id"));
		} catch (Exception ex) {
			response.sendError(501, "Geen ID meegegeven");
		}

		DBFacade df = DBFacade.getUniekeInstantie();

		JSONObject responseJSON = new JSONObject();
		if (deelnameID < 0) {
			responseJSON.put("deelnames", JSONService.maakDeelnameArray(deelnemer.getDeelnames()));
		} else {
			Deelname deelname = df.getDeelname(deelnameID);
			responseJSON = JSONService.maakDeelnameDetail(deelname);
		}

		response.setContentType("text/javascript");
		PrintWriter out = response.getWriter();
		out.println(responseJSON);
		out.close();
	}
}

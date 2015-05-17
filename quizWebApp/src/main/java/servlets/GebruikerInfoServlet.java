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

import org.json.JSONObject;

@WebServlet("/gebruikerInfo")
public class GebruikerInfoServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		HttpSession session = request.getSession();
		Deelnemer deelnemer = (Deelnemer) session.getAttribute("deelnemer");
		boolean isBeheerder = false;
		Object isBeh = session.getAttribute("isBeheerder");
		if (isBeh != null) {
			isBeheerder = (Boolean) isBeh;
		}

		if (deelnemer != null) {
			JSONObject responseJSON = new JSONObject();
			responseJSON.put("gebruikersNaam", deelnemer.getGebruikersNaam());
			responseJSON.put("isBeheerder", isBeheerder);
			response.setContentType("text/javascript");
			PrintWriter out = response.getWriter();
			out.println(responseJSON);
			out.close();
		} else {
			response.sendError(501, "Niet ingelogd");
		}
	}
}

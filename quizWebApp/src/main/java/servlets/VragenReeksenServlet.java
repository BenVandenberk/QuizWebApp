package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Thema;
import model.VragenReeks;

import org.json.JSONArray;
import org.json.JSONObject;

import data.DBFacade;

@WebServlet("/vragenreeksen")
public class VragenReeksenServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		DBFacade dbFacade = DBFacade.getUniekeInstantie();
		List<VragenReeks> vragenReeksen = dbFacade.getVragenReeksen();
		JSONArray vragenReeksenJSON = new JSONArray(vragenReeksen);

		Set<Thema> alleThemas = new HashSet<Thema>();
		for (VragenReeks vr : vragenReeksen) {
			alleThemas.add(vr.getThema());
		}
		JSONArray themasJSON = new JSONArray(alleThemas);

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("themas", themasJSON);
		resultJSON.put("vragenreeksen", vragenReeksenJSON);

		response.setContentType("text/javascript");
		PrintWriter out = response.getWriter();
		out.println(resultJSON);
		out.close();
	}
}

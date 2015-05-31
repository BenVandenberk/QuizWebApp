package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Deelname;
import model.Rapport;

import org.json.JSONArray;
import org.json.JSONObject;

import service.JSONService;
import data.DBFacade;

/**
 * RequestTypes:
 * <ul>
 * <li>start: JSON voor het laden van de pagina (quizzen met deelnames en deelnemers met deelnames - kort)</li>
 * <li>deelname: JSON van 1 deelname op basis van een DeelnameID (requestParameter 'id')</li>
 * <li>quiz: JSON van alle deelnames van een quiz op basis van een VragenReeksID (requestParameter 'id')</li>
 * <li>deelnemer: JSON van alle deelnames van een deelnemer op basis van een DeelnemerID (requestParameter 'id')</li>
 * <li>kort: JSON van alle bestaande rapporten - kort</li>
 * <li>save: Ontvangt JSON van client met een rapport. Slaagt rapport op in DB</li>
 * <li>load: JSON van 1 rapport op basis van een RapportID (requestParameter 'id') - uitgebreid</li>
 * </ul>
 *
 * @author Ben
 *
 */
@WebServlet("/beheerderRapporten")
public class BeheerderRapportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
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

		String requestType = request.getParameter("requestType");
		int id = 0;
		try {
			String idS = request.getParameter("id");
			if (idS != null) {
				id = Integer.parseInt(request.getParameter("id"));
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		List<Deelname> alleDeelnames = null;

		HttpSession session = request.getSession();
		if (session.getAttribute("deelnames") != null) {
			alleDeelnames = (List<Deelname>) session.getAttribute("deelnames");
		}

		JSONObject responseJSON = new JSONObject();

		switch (requestType) {
		case "start":
			alleDeelnames = dbFacade.getDeelnames();
			session.setAttribute("deelnames", alleDeelnames);
			session.setAttribute("rapport", new Rapport());
			responseJSON = JSONService.maakBeheerderRapportStart(alleDeelnames);
			break;
		case "deelname":
			Deelname deelname = null;
			for (Deelname d : alleDeelnames) {
				if (d.getDeelnameID() == id) {
					deelname = d;
					break;
				}
			}
			if (deelname != null) {
				JSONArray arrayJSON = new JSONArray();
				arrayJSON.put(JSONService.maakDeelnameDetail(deelname));
				responseJSON.put("deelnames", arrayJSON);
			}
			break;
		case "quiz":
			List<Deelname> deelnamesVragenReeks = new ArrayList<Deelname>();
			for (Deelname d : alleDeelnames) {
				if (d.getVragenReeks().getVragenReeksId() == id) {
					deelnamesVragenReeks.add(d);
				}
			}
			responseJSON.put("deelnames", JSONService.maakDeelnameDetailArray(deelnamesVragenReeks));
			break;
		case "deelnemer":
			List<Deelname> deelnamesDeelnemer = new ArrayList<Deelname>();
			for (Deelname d : alleDeelnames) {
				if (d.getDeelnemer().getDeelnemerID() == id) {
					deelnamesDeelnemer.add(d);
				}
			}
			responseJSON.put("deelnames", JSONService.maakDeelnameDetailArray(deelnamesDeelnemer));
			break;
		case "kort":
			List<Rapport> alleRapporten = dbFacade.getRapporten();
			JSONArray alleRapportenKortJSON = JSONService.maakRapporten(alleRapporten);
			responseJSON.put("rapporten", alleRapportenKortJSON);
			break;
		case "save":
			boolean groepeerPerQuiz = Boolean.parseBoolean(request.getParameter("groepeerPerQuiz"));
			String naam = request.getParameter("naam");
			JSONArray rapportJSON = new JSONArray(request.getParameter("deelnames"));
			List<Deelname> deelnamesOpRapport = new ArrayList<Deelname>();
			for (int i = 0; i < rapportJSON.length(); i++) {
				for (Deelname d : alleDeelnames) {
					if (rapportJSON.getJSONObject(i).getInt("id") == d.getDeelnameID()) {
						deelnamesOpRapport.add(d);
						break;
					}
				}
			}
			Rapport rapport = (Rapport) session.getAttribute("rapport");
			rapport.setDeelnames(deelnamesOpRapport);
			rapport.setGroeperingPerQuiz(groepeerPerQuiz);
			rapport.resetToontVragenVanDeelname();
			rapport.setNaam(naam);
			int deelnameID = 0;
			boolean metVragen = false;
			for (int i = 0; i < rapportJSON.length(); i++) {
				deelnameID = rapportJSON.getJSONObject(i).getInt("id");
				metVragen = rapportJSON.getJSONObject(i).getBoolean("toonDetail");
				rapport.setToontVragenVanDeelname(deelnameID, metVragen);
			}
			try {
				dbFacade.saveRapport(rapport);
				responseJSON.put("message", "Rapport " + naam + " gesaved");
			} catch (Exception ex) {
				responseJSON.put("message", "Fout bij het saven van rapport\n" + ex.getMessage());
			}
			break;
		case "load":
			Rapport geloadRapport = dbFacade.getRapport(id);
			session.setAttribute("rapport", geloadRapport);
			JSONArray geloadeDeelnames = JSONService.maakGeloadDeelnameDetailArray(geloadRapport.getDeelnames(),
					geloadRapport.getToontVragenVanDeelname());
			responseJSON.put("deelnames", geloadeDeelnames);
			responseJSON.put("groepeerPerQuiz", geloadRapport.isGroeperingPerQuiz());
			break;
		case "new":
			session.setAttribute("rapport", new Rapport());
			break;
		default:
			response.sendError(521, "Geen (geldig) requestType meegegeven");
			break;
		}

		response.setContentType("text/javascript");
		PrintWriter out = response.getWriter();
		out.println(responseJSON);
		out.close();
	}
}

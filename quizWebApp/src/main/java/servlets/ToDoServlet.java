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

import model.Deelnemer;

import org.json.JSONObject;

import service.JSONService;
import service.ToDoService;
import context.ToDoContext;

@WebServlet("/todo")
public class ToDoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ToDoService toDoService = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		toDoService = ToDoService.getUniekeInstantie();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		HttpSession session = request.getSession();

		String requestType = request.getParameter("requestType");
		if (requestType == null) {
			response.sendError(521, "Geen requestType meegegeven");
		}

		JSONObject responseJSON = null;
		ToDoContext toDoContext;
		int deelnemerID, vragenReeksID;

		switch (requestType) {
		case "start":
			toDoContext = toDoService.getToDoContext();
			session.setAttribute("context", toDoContext);
			responseJSON = JSONService.maakToDoStart(toDoContext.getDeelnemers(), toDoContext.getVragenReeksen());
			break;
		case "todoLoad":
			deelnemerID = Integer.parseInt(request.getParameter("deelnemerID"));
			toDoContext = (ToDoContext) session.getAttribute("context");
			responseJSON = JSONService.maakToDoDeelnemer(toDoContext.getDeelnemer(deelnemerID),
					toDoContext.getMogelijkeVragenReeksen(deelnemerID));
			break;
		case "verwijder":
			deelnemerID = Integer.parseInt(request.getParameter("deelnemerID"));
			vragenReeksID = Integer.parseInt(request.getParameter("vragenReeksID"));
			toDoContext = (ToDoContext) session.getAttribute("context");
			toDoContext.getDeelnemer(deelnemerID).removeToDo(vragenReeksID);
			toDoContext.save(deelnemerID);
			break;
		case "voegToe":
			deelnemerID = Integer.parseInt(request.getParameter("deelnemerID"));
			vragenReeksID = Integer.parseInt(request.getParameter("vragenReeksID"));
			toDoContext = (ToDoContext) session.getAttribute("context");
			Deelnemer deelnemer = toDoContext.getDeelnemer(deelnemerID);
			deelnemer.addToDo(toDoContext.getVragenReeks(vragenReeksID));
			toDoContext.save(deelnemerID);
			break;
		default:
			response.sendError(521, "Geen geldig requestType meegegeven");
			break;
		}

		response.setContentType("text/javascript");
		PrintWriter out = response.getWriter();
		out.println(responseJSON);
		out.close();
	}
}

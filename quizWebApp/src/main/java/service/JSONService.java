package service;

import java.util.List;

import model.Deelname;
import model.GegevenAntwoord;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONService {

	public static JSONArray maakDeelnameArray(List<Deelname> deelnames) {
		JSONArray deelnamesJSON = new JSONArray();
		JSONObject deelnameJSON;

		for (Deelname deelname : deelnames) {
			deelnameJSON = new JSONObject();
			deelnameJSON.put("thema", deelname.getVragenReeks().getThema().getOmschrijving());
			deelnameJSON.put("quizNaam", deelname.getVragenReeks().getNaam());
			deelnameJSON.put("tijdstip", Utilities.dateString(deelname.getTijdstipDeelname()));
			deelnameJSON.put("id", deelname.getDeelnameID());
			deelnamesJSON.put(deelnameJSON);
		}

		return deelnamesJSON;
	}

	public static JSONObject maakDeelnameDetail(Deelname deelname) {
		JSONObject deelnameJSON = new JSONObject();

		JSONArray antwoordenJSON = new JSONArray();
		JSONObject antwoordJSON;
		for (GegevenAntwoord gegAntwoord : deelname.getAntwoorden()) {
			antwoordJSON = new JSONObject();
			antwoordJSON.put("vraag", gegAntwoord.getReeksVraag().getVraag().getVraag());
			antwoordJSON.put("antwoord", gegAntwoord.getAntwoord().toString());
			antwoordJSON.put("juisteAntwoord", gegAntwoord.getReeksVraag().getVraag().getJuisteAntwoord());
			antwoordJSON.put("isJuist", gegAntwoord.isJuist());
			antwoordenJSON.put(antwoordJSON);
		}
		deelnameJSON.put("antwoorden", antwoordenJSON);
		deelnameJSON.put("aantalVragen", deelname.getVragenReeks().getAantalVragen());
		deelnameJSON.put("score", deelname.getScore());

		return deelnameJSON;
	}

}

package service;

import java.util.List;

import model.Deelname;
import model.Deelnemer;
import model.GegevenAntwoord;
import model.VragenReeks;

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

	public static JSONObject maakToDoStart(List<Deelnemer> deelnemers, List<VragenReeks> vragenReeksen) {
		JSONObject todoStartJSON = new JSONObject();
		JSONArray deelnemersJSON = new JSONArray();
		JSONObject deelnemerJSON;
		JSONArray todoVragenReeksen;
		for (Deelnemer deelnemer : deelnemers) {
			deelnemerJSON = new JSONObject();
			deelnemerJSON.put("naam", deelnemer.getGebruikersNaam());
			deelnemerJSON.put("id", deelnemer.getDeelnemerID());
			todoVragenReeksen = new JSONArray();
			for (VragenReeks todo : deelnemer.getTeMakenReeksen()) {
				todoVragenReeksen.put(maakVragenReeksJSON(todo));
			}
			deelnemerJSON.put("todo", todoVragenReeksen);
			deelnemersJSON.put(deelnemerJSON);
		}
		todoStartJSON.put("deelnemers", deelnemersJSON);

		JSONArray vragenReeksenJSON = new JSONArray();
		for (VragenReeks vragenReeks : vragenReeksen) {
			vragenReeksenJSON.put(maakVragenReeksJSON(vragenReeks));
		}
		todoStartJSON.put("vragenReeksen", vragenReeksenJSON);

		return todoStartJSON;
	}

	public static JSONObject maakToDoDeelnemer(Deelnemer deelnemer, List<VragenReeks> vragenReeksen) {
		JSONObject resultJSON = new JSONObject();

		JSONArray toDoDeelnemerJSON = new JSONArray();
		for (VragenReeks v : deelnemer.getTeMakenReeksen()) {
			toDoDeelnemerJSON.put(maakVragenReeksJSON(v));
		}
		resultJSON.put("todo", toDoDeelnemerJSON);

		JSONArray mogelijkeVragenReeksenJSON = new JSONArray();
		for (VragenReeks vr : vragenReeksen) {
			mogelijkeVragenReeksenJSON.put(maakVragenReeksJSON(vr));
		}
		resultJSON.put("vragenReeksen", mogelijkeVragenReeksenJSON);

		return resultJSON;
	}

	private static JSONObject maakVragenReeksJSON(VragenReeks vragenReeks) {
		JSONObject vragenReeksJSON = new JSONObject();
		vragenReeksJSON.put("naam", vragenReeks.getNaam());
		vragenReeksJSON.put("id", vragenReeks.getVragenReeksId());
		vragenReeksJSON.put("thema", vragenReeks.getThema().getOmschrijving());
		return vragenReeksJSON;
	}

}

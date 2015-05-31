package service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.Deelname;
import model.Deelnemer;
import model.GegevenAntwoord;
import model.Rapport;
import model.Thema;
import model.VragenReeks;
import model.vraag.Vraag;

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

	public static JSONArray maakGeloadDeelnameDetailArray(List<Deelname> deelnames, Map<Integer, Boolean> metVragen) {
		JSONArray deelnamesJSON = new JSONArray();

		JSONObject deelnameJSON;
		for (Deelname d : deelnames) {
			deelnameJSON = maakDeelnameDetail(d);
			deelnameJSON.put("toonDetail", metVragen.get(d.getDeelnameID()));
			deelnamesJSON.put(deelnameJSON);
		}
		return deelnamesJSON;
	}

	public static JSONArray maakDeelnameDetailArray(List<Deelname> deelnames) {
		JSONArray deelnamesJSON = new JSONArray();
		for (Deelname d : deelnames) {
			deelnamesJSON.put(maakDeelnameDetail(d));
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
		deelnameJSON.put("deelnemerNaam", deelname.getDeelnemer().getGebruikersNaam());
		deelnameJSON.put("deelnemerId", deelname.getDeelnemer().getDeelnemerID());
		deelnameJSON.put("vragenReeksNaam", deelname.getVragenReeks().getNaam());
		deelnameJSON.put("vragenReeksId", deelname.getVragenReeks().getVragenReeksId());
		deelnameJSON.put("tijdstip", Utilities.dateString(deelname.getTijdstipDeelname()));
		deelnameJSON.put("id", deelname.getDeelnameID());

		return deelnameJSON;
	}

	public static JSONObject maakVragenReeksBeperktRapport(VragenReeks vragenReeks) {
		JSONObject vragenReeksJSON = new JSONObject();
		vragenReeksJSON.put("naam", vragenReeks.getNaam());
		vragenReeksJSON.put("aantalVragen", vragenReeks.getAantalVragen());

		JSONArray vragenJSON = new JSONArray();
		JSONObject huidigeVraagJSON;
		for (Vraag v : vragenReeks.getVragen()) {
			huidigeVraagJSON = new JSONObject();
			huidigeVraagJSON.put("vraag", v.getVraag());
			huidigeVraagJSON.put("juisteAntwoord", v.getJuisteAntwoord());
			vragenJSON.put(huidigeVraagJSON);
		}
		vragenReeksJSON.put("vragen", vragenJSON);

		return vragenReeksJSON;
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

	public static JSONArray maakToDo(Deelnemer deelnemer) {
		JSONArray todoJSON = new JSONArray();
		JSONObject huidigeVragenReeks;
		Map<VragenReeks, Boolean> vragenReeksenIsEnabled = QuizService.getVragenReeksenIsEnabled(deelnemer.getTeMakenReeksen(),
				deelnemer);
		for (VragenReeks vr : vragenReeksenIsEnabled.keySet()) {
			huidigeVragenReeks = maakVragenReeksJSON(vr);
			huidigeVragenReeks.put("isEnabled", vragenReeksenIsEnabled.get(vr));
			todoJSON.put(huidigeVragenReeks);
		}
		return todoJSON;
	}

	public static JSONObject maakAlleVragenReeksen(Map<VragenReeks, Boolean> vragenReeksenIsEnab) {
		JSONObject resultJSON = new JSONObject();

		// VragenReeksen + isEnabled

		JSONArray vragenReeksenJSON = new JSONArray();
		JSONObject vragenReeksJSON;
		for (VragenReeks vr : vragenReeksenIsEnab.keySet()) {
			vragenReeksJSON = maakVragenReeksJSON(vr);
			vragenReeksJSON.put("isEnabled", vragenReeksenIsEnab.get(vr));
			vragenReeksenJSON.put(vragenReeksJSON);
		}
		resultJSON.put("vragenreeksen", vragenReeksenJSON);

		// Thema's

		Set<Thema> alleThemas = new HashSet<Thema>();
		for (VragenReeks vr : vragenReeksenIsEnab.keySet()) {
			alleThemas.add(vr.getThema());
		}
		JSONArray themasJSON = new JSONArray(alleThemas);
		resultJSON.put("themas", themasJSON);

		return resultJSON;
	}

	/**
	 * Structuur: { vragenReeksen : [{id, naam, thema, deelnames : [id, deelnemerId, vragenReeksId, deelnemerNaam,
	 * vragenReeksNaam, tijdstip, score]}], deelnemers : [{id, naam, deelnames : [id, deelnemerId, vragenReeksId,
	 * deelnemerNaam, vragenReeksNaam, tijdstip, score]}] }
	 *
	 * @param alleDeelnames
	 * @return
	 */
	public static JSONObject maakBeheerderRapportStart(List<Deelname> alleDeelnames) {
		HashSet<VragenReeks> alleVragenReeksen = new HashSet<VragenReeks>();
		HashSet<Deelnemer> alleDeelnemers = new HashSet<Deelnemer>();

		for (Deelname deeln : alleDeelnames) {
			alleVragenReeksen.add(deeln.getVragenReeks());
			alleDeelnemers.add(deeln.getDeelnemer());
		}

		JSONArray vragenReeksenJSONA = new JSONArray();
		JSONObject vragenReeksJSONO;
		JSONArray deelnamesJSON;
		List<Deelname> juisteDeelnames;
		for (VragenReeks vr : alleVragenReeksen) {
			vragenReeksJSONO = new JSONObject();
			vragenReeksJSONO.put("id", vr.getVragenReeksId());
			vragenReeksJSONO.put("naam", vr.getNaam());
			vragenReeksJSONO.put("thema", vr.getThema().getOmschrijving());

			deelnamesJSON = new JSONArray();
			juisteDeelnames = alleDeelnames.stream().filter(d -> d.getVragenReeks().equals(vr)).collect(Collectors.toList());
			for (Deelname d : juisteDeelnames) {
				deelnamesJSON.put(maakDeelnameJSON(d));
			}
			vragenReeksJSONO.put("deelnames", deelnamesJSON);
			vragenReeksenJSONA.put(vragenReeksJSONO);
		}

		JSONArray deelnemersJSONA = new JSONArray();
		JSONObject deelnemerJSONO;
		for (Deelnemer deeln : alleDeelnemers) {
			deelnemerJSONO = new JSONObject();
			deelnemerJSONO.put("id", deeln.getDeelnemerID());
			deelnemerJSONO.put("naam", deeln.getGebruikersNaam());

			deelnamesJSON = new JSONArray();
			juisteDeelnames = alleDeelnames.stream().filter(d -> d.getDeelnemer().equals(deeln)).collect(Collectors.toList());
			for (Deelname d : juisteDeelnames) {
				deelnamesJSON.put(maakDeelnameJSON(d));
			}
			deelnemerJSONO.put("deelnames", deelnamesJSON);
			deelnemersJSONA.put(deelnemerJSONO);
		}

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("vragenReeksen", vragenReeksenJSONA);
		resultJSON.put("deelnemers", deelnemersJSONA);
		return resultJSON;
	}

	public static JSONArray maakRapporten(List<Rapport> rapporten) {
		JSONArray rapportenJSON = new JSONArray();
		JSONObject rapportJSON;

		for (Rapport r : rapporten) {
			rapportJSON = new JSONObject();
			rapportJSON.put("id", r.getRapportID());
			rapportJSON.put("naam", r.getNaam());
			rapportJSON.put("tijdstip", Utilities.dateString(r.getRapportDatum()));
			rapportenJSON.put(rapportJSON);
		}

		return rapportenJSON;
	}

	private static JSONObject maakVragenReeksJSON(VragenReeks vragenReeks) {
		JSONObject vragenReeksJSON = new JSONObject();
		vragenReeksJSON.put("naam", vragenReeks.getNaam());
		vragenReeksJSON.put("id", vragenReeks.getVragenReeksId());
		vragenReeksJSON.put("thema", vragenReeks.getThema().getOmschrijving());
		return vragenReeksJSON;
	}

	private static JSONObject maakDeelnameJSON(Deelname deelname) {
		JSONObject deelnameJSON = new JSONObject();
		deelnameJSON.put("id", deelname.getDeelnameID());
		deelnameJSON.put("deelnemerNaam", deelname.getDeelnemer().getGebruikersNaam());
		deelnameJSON.put("vragenReeksNaam", deelname.getVragenReeks().getNaam());
		deelnameJSON.put("tijdstip", Utilities.dateString(deelname.getTijdstipDeelname()));
		deelnameJSON.put("score", deelname.getScore());
		deelnameJSON.put("aantalVragen", deelname.getVragenReeks().getAantalVragen());
		return deelnameJSON;
	}

}

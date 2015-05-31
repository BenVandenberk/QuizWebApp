package service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import model.Deelnemer;
import model.VragenReeks;
import model.antwoord.Antwoord;
import model.antwoord.DragAndDropAntwoord;
import model.antwoord.KlassiekAntwoord;
import model.antwoord.MultipleChoiceAntwoord;
import model.antwoord.NumeriekAntwoord;
import model.vraag.DragAndDropVraag;
import model.vraag.MultipleChoiceVraag;
import model.vraag.Vraag;
import model.vraag.VraagType;

import org.json.JSONArray;
import org.json.JSONObject;

import data.DBFacade;

public class QuizService {

	private Properties imagePathMap;

	public QuizService() {
		imagePathMap = new Properties();
	}

	public static VragenReeks getVragenReeks(int id) {
		DBFacade dbFacade = DBFacade.getUniekeInstantie();
		return dbFacade.getVragenReeks(id);
	}

	public static Antwoord maakAntwoord(String antwoord, VraagType vraagType) {
		switch (vraagType) {
		case JaNee:
			return new KlassiekAntwoord(antwoord);
		case MC:
			return new MultipleChoiceAntwoord(antwoord.split(";"));
		case Numeriek:
			return new NumeriekAntwoord(Integer.parseInt(antwoord));
		case DND:
			JSONObject antwoordMapJSON = new JSONObject(antwoord);
			Map<String, String> antwoordMap = new HashMap<String, String>();
			for (Object key : antwoordMapJSON.keySet()) {
				antwoordMap.put(key.toString(), antwoordMapJSON.get(key.toString()).toString());
			}
			return new DragAndDropAntwoord(antwoordMap);
		}
		return null;
	}

	public static Map<VragenReeks, Boolean> getVragenReeksenIsEnabled(List<VragenReeks> vragenReeksen, Deelnemer deelnemer) {
		Map<VragenReeks, Boolean> resultMap = new HashMap<VragenReeks, Boolean>();

		if (deelnemer != null) {
			boolean dependencyOK = true;
			List<VragenReeks> voorgaande;
			for (VragenReeks vr : vragenReeksen) {
				dependencyOK = true;
				voorgaande = vr.getVoorgaandeVragenReeksen();
				for (int i = 0; dependencyOK && i < voorgaande.size(); i++) {
					dependencyOK = deelnemer.heeftOpgelost(voorgaande.get(i));
				}
				resultMap.put(vr, dependencyOK);
			}
		} else {
			for (VragenReeks vr : vragenReeksen) {
				if (vr.getVoorgaandeVragenReeksen().size() > 0) {
					resultMap.put(vr, false);
				} else {
					resultMap.put(vr, true);
				}
			}
		}
		return resultMap;
	}

	public JSONObject getJSONVraag(Vraag vraag) {
		JSONObject vraagJSON = new JSONObject(vraag);
		switch (vraag.getVraagType()) {
		case MC:
			MultipleChoiceVraag mcv = (MultipleChoiceVraag) vraag;
			JSONArray keuzesJSON = new JSONArray(mcv.getKeuzes());
			vraagJSON.put("keuzes", keuzesJSON);
			break;
		case DND:
			DragAndDropVraag dndv = (DragAndDropVraag) vraag;
			Map<String, String> imagePaths = getDndImagePaths(dndv);
			JSONObject imagePathsJSON = new JSONObject(imagePaths);
			vraagJSON.put("imagePaths", imagePathsJSON);
			JSONArray teSlepenJSON = new JSONArray(dndv.getTeSlepen());
			vraagJSON.put("teSlepen", teSlepenJSON);
			JSONArray antwoordVeldenJSON = new JSONArray(dndv.getAntwoordVelden());
			vraagJSON.put("antwoordVelden", antwoordVeldenJSON);
			break;
		default:
			break;
		}

		vraagJSON.remove("antwoord");

		return vraagJSON;
	}

	private Map<String, String> getDndImagePaths(DragAndDropVraag dndVraag) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		dndVraag.setAntwoordVelden();
		dndVraag.setTeSlepen();

		String path = null;
		for (String antwoordVeld : dndVraag.getAntwoordVelden()) {
			path = imagePathMap.getProperty(antwoordVeld.toLowerCase());
			resultMap.put(antwoordVeld, path);
		}
		for (String teSlepen : dndVraag.getTeSlepen()) {
			path = imagePathMap.getProperty(teSlepen.toLowerCase());
			resultMap.put(teSlepen, path);
		}

		return resultMap;
	}

	public void init() throws IOException {
		imagePathMap.load(this.getClass().getClassLoader().getResourceAsStream("imageMap.properties"));
	}
}

package service;

import model.Deelnemer;
import model.Gebruiker;
import data.DBFacade;

public class GebruikerService {

	public static void registreer(String gebruikersNaam, String paswoord) throws IllegalStateException {
		DBFacade dbFacade = DBFacade.getUniekeInstantie();
		if (dbFacade.userBestaat(gebruikersNaam)) {
			throw new IllegalStateException("Gebruiker bestaat al");
		}

		Gebruiker gebruiker = new Gebruiker(gebruikersNaam, paswoord);
		dbFacade.saveGebruiker(gebruiker);
	}

	public static Deelnemer login(String gebruikersNaam, String paswoord) throws IllegalArgumentException {
		DBFacade dbFacade = DBFacade.getUniekeInstantie();
		return dbFacade.login(gebruikersNaam, paswoord);
	}

}
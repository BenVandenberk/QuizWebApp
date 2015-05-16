package data;

import java.util.List;

import model.Deelname;
import model.Deelnemer;
import model.Gebruiker;
import model.VragenReeks;
import service.Authentication;

public class DBFacade {

	private static DBFacade uniekeInstantie;
	private HibernateToDB hibernateToDB;

	private DBFacade() {
		hibernateToDB = HibernateToDB.getUniekeInstantie();
	}

	public static DBFacade getUniekeInstantie() {
		if (uniekeInstantie == null) {
			uniekeInstantie = new DBFacade();
		}
		return uniekeInstantie;
	}

	public boolean userBestaat(String userName) {
		Gebruiker gebruiker = hibernateToDB.getGebruiker(userName);
		return gebruiker != null;
	}

	public Deelnemer login(String gebruikersNaam, String paswoord) throws IllegalArgumentException {
		Gebruiker gebruiker = hibernateToDB.getGebruiker(gebruikersNaam);
		if (gebruiker == null) {
			throw new IllegalArgumentException("Gebruiker bestaat niet");
		}

		if (Authentication.isJuistPaswoord(paswoord, gebruiker.getPwHash(), gebruiker.getSalt())) {
			return gebruiker.getDeelnemer();
		} else {
			throw new IllegalArgumentException("Onjuist paswoord");
		}
	}

	public void saveDeelnemer(Deelnemer deelnemer) {
		hibernateToDB.saveDeelnemer(deelnemer);
	}

	public void saveDeelname(Deelname deelname) {
		hibernateToDB.saveDeelname(deelname);
	}

	public void saveVragenReeks(VragenReeks vragenReeks) {
		hibernateToDB.saveVragen(vragenReeks.getVragen());
		hibernateToDB.saveVragenReeks(vragenReeks);
	}

	public void saveGebruiker(Gebruiker gebruiker) {
		hibernateToDB.saveGebruiker(gebruiker);
	}

	public List<VragenReeks> getVragenReeksen() {
		return hibernateToDB.getVragenReeksen();
	}

	public VragenReeks getVragenReeks(int id) {
		return hibernateToDB.getVragenReeks(id);
	}

	// public void loadObject(Object object) {
	// hibernateToDB.initializeProxy(object);
	// }

	public Deelname getDeelname(int id) {
		return hibernateToDB.getDeelname(id);
	}

}

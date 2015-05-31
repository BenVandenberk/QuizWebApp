package data;

import java.util.List;

import model.Deelname;
import model.Deelnemer;
import model.Gebruiker;
import model.Rapport;
import model.VragenReeks;
import service.Authentication;

public class DBFacade {

	private static DBFacade uniekeInstantie;
	private IDBCore dbCore;

	private DBFacade() {
		dbCore = HibernateToDB.getUniekeInstantie();
	}

	public static DBFacade getUniekeInstantie() {
		if (uniekeInstantie == null) {
			uniekeInstantie = new DBFacade();
		}
		return uniekeInstantie;
	}

	public boolean userBestaat(String userName) {
		Gebruiker gebruiker = dbCore.getGebruiker(userName);
		return gebruiker != null;
	}

	public Gebruiker login(String gebruikersNaam, String paswoord) throws IllegalArgumentException {
		Gebruiker gebruiker = dbCore.getGebruiker(gebruikersNaam);
		if (gebruiker == null) {
			throw new IllegalArgumentException("Gebruiker bestaat niet");
		}

		if (Authentication.isJuistPaswoord(paswoord, gebruiker.getPwHash(), gebruiker.getSalt())) {
			return gebruiker;
		} else {
			throw new IllegalArgumentException("Onjuist paswoord");
		}
	}

	public void saveDeelnemer(Deelnemer deelnemer) {
		dbCore.saveDeelnemer(deelnemer);
	}

	public void saveDeelname(Deelname deelname) {
		dbCore.saveDeelname(deelname);
	}

	public void saveVragenReeks(VragenReeks vragenReeks) {
		dbCore.saveVragen(vragenReeks.getVragen());
		dbCore.saveVragenReeks(vragenReeks);
	}

	public void saveGebruiker(Gebruiker gebruiker) {
		dbCore.saveGebruiker(gebruiker);
	}

	public void saveRapport(Rapport rapport) {
		dbCore.saveRapport(rapport);
	}

	public List<Rapport> getRapporten() {
		return dbCore.getRapporten();
	}

	public List<VragenReeks> getVragenReeksen() {
		return dbCore.getVragenReeksen();
	}

	public List<Deelnemer> getDeelnemers() {
		return dbCore.getDeelnemers();
	}

	public List<Deelname> getDeelnames() {
		return dbCore.getDeelnames();
	}

	public VragenReeks getVragenReeks(int id) {
		return dbCore.getVragenReeks(id);
	}

	public Deelname getDeelname(int id) {
		return dbCore.getDeelname(id);
	}

	public Rapport getRapport(int id) {
		return dbCore.getRapport(id);
	}

}

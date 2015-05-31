package data;

import java.util.List;

import model.Deelname;
import model.Deelnemer;
import model.Gebruiker;
import model.Rapport;
import model.VragenReeks;
import model.vraag.Vraag;

interface IDBCore {

	Gebruiker getGebruiker(String gebruikersNaam);

	void saveDeelnemer(Deelnemer deelnemer);

	void saveDeelname(Deelname deelname);

	void saveGebruiker(Gebruiker gebruiker);

	void saveVragenReeks(VragenReeks vragenReeks);

	void saveVragen(List<Vraag> vragen);

	List<VragenReeks> getVragenReeksen();

	List<Deelnemer> getDeelnemers();

	VragenReeks getVragenReeks(int id);

	Deelname getDeelname(int id);

	void saveRapport(Rapport rapport);

	List<Deelname> getDeelnames();

	List<Rapport> getRapporten();

	Rapport getRapport(int id);

}

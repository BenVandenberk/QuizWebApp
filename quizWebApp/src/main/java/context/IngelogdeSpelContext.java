package context;

import model.Deelname;
import model.Deelnemer;
import model.VragenReeks;
import model.antwoord.Antwoord;

public class IngelogdeSpelContext extends SpelContext {

	private Deelnemer deelnemer;
	private Deelname deelname;

	public IngelogdeSpelContext(VragenReeks vragenReeks, Deelnemer deelnemer) {
		super(vragenReeks);
		this.deelnemer = deelnemer;
		this.deelname = deelnemer.neemDeel(vragenReeks);
	}

	public Deelnemer getDeelnemer() {
		return deelnemer;
	}

	public Deelname getDeelname() {
		return deelname;
	}

	@Override
	public void antwoord(Antwoord antwoord) {
		super.antwoord(antwoord);
		deelname.antwoord(vragenReeks.getReeksVraag(huidigeVraagIndex), antwoord);
	}

	@Override
	public void save() {
		dbFacade.saveDeelname(deelname);
		// dbFacade.saveDeelnemer(deelnemer);
	}
}

package context;

import model.VragenReeks;
import model.antwoord.Antwoord;
import model.vraag.Vraag;
import data.DBFacade;

public class SpelContext {

	protected VragenReeks vragenReeks;
	protected int huidigeVraagIndex;
	protected Vraag huidigeVraag;
	protected int score;
	protected DBFacade dbFacade;
	protected boolean[] antwoorden;
	protected boolean isGedaan;

	public SpelContext(VragenReeks vragenReeks) {
		this.vragenReeks = vragenReeks;
		huidigeVraagIndex = 0;
		score = 0;
		dbFacade = DBFacade.getUniekeInstantie();
		antwoorden = new boolean[vragenReeks.getAantalVragen()];
		isGedaan = false;
	}

	public boolean volgendeVraag() {
		if (++huidigeVraagIndex <= vragenReeks.getAantalVragen()) {
			huidigeVraag = vragenReeks.getReeksVraag(huidigeVraagIndex).getVraag();
			return true;
		} else {
			return false;
		}
	}

	public void antwoord(Antwoord antwoord) {
		boolean isJuist = huidigeVraag.isJuist(antwoord);
		score += isJuist ? 1 : 0;
		antwoorden[huidigeVraagIndex - 1] = isJuist;
	}

	public boolean reedsBegonnen() {
		return huidigeVraagIndex > 0;
	}

	public void save() {

	}

	public VragenReeks getVragenReeks() {
		return vragenReeks;
	}

	public Vraag getHuidigeVraag() {
		return huidigeVraag;
	}

	public int getScore() {
		return score;
	}

	public int getAantalVragen() {
		return vragenReeks.getAantalVragen();
	}

	public int getVolgnummerHuidigeVraag() {
		return huidigeVraagIndex;
	}

	public boolean[] getAntwoorden() {
		return antwoorden;
	}

	public boolean isGedaan() {
		return isGedaan;
	}

	public void setGedaan(boolean isGedaan) {
		this.isGedaan = isGedaan;
	}
}

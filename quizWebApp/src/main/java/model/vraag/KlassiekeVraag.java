package model.vraag;

import javax.persistence.Entity;

import model.antwoord.Antwoord;
import model.antwoord.KlassiekAntwoord;

@Entity
public class KlassiekeVraag extends Vraag {

	private String antwoord;

	public KlassiekeVraag() {
		super();
		this.vraagType = VraagType.JaNee;
	}

	public KlassiekeVraag(String vraag, String antwoord) {
		super(vraag);
		this.antwoord = antwoord;
		this.vraagType = VraagType.JaNee;
	}

	public String getAntwoord() {
		return antwoord;
	}

	@Override
	public boolean isJuist(Antwoord antwoord) {
		KlassiekAntwoord kAntwoord = null;
		if (antwoord instanceof KlassiekAntwoord) {
			kAntwoord = (KlassiekAntwoord) antwoord;
		} else {
			throw new IllegalStateException("Antwoord was niet van type KlassiekAntwoord");
		}

		return this.antwoord.equalsIgnoreCase(kAntwoord.getAntwoord());
	}

	@Override
	public String getJuisteAntwoord() {
		return antwoord;
	}

}

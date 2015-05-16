package model.vraag;

import javax.persistence.Entity;

import model.antwoord.Antwoord;
import model.antwoord.NumeriekAntwoord;

@Entity
public class NumeriekeVraag extends Vraag {

	private boolean isExactAntwoord;
	private int antwoord;
	private int onderGrensAntwoord;
	private int bovenGrensAntwoord;
	private int onderGrensKeuze;
	private int bovenGrensKeuze;

	public NumeriekeVraag() {
		super();
		this.vraagType = VraagType.Numeriek;
	}

	public NumeriekeVraag(String vraag, int antwoord) {
		super(vraag);
		this.antwoord = antwoord;
		isExactAntwoord = true;
		this.vraagType = VraagType.Numeriek;
	}

	public NumeriekeVraag(String vraag, int antwoord, int onderGrensAntwoord, int bovenGrensAntwoord, int onderGrensKeuze,
			int bovenGrensKeuze) {
		super(vraag);
		this.antwoord = antwoord;
		this.onderGrensAntwoord = onderGrensAntwoord;
		this.bovenGrensAntwoord = bovenGrensAntwoord;
		this.onderGrensKeuze = onderGrensKeuze;
		this.bovenGrensKeuze = bovenGrensKeuze;
		isExactAntwoord = false;
	}

	public boolean getIsExactAntwoord() {
		return isExactAntwoord;
	}

	public int getAntwoord() {
		return antwoord;
	}

	public int getOnderGrensAntwoord() {
		return onderGrensAntwoord;
	}

	public int getBovenGrensAntwoord() {
		return bovenGrensAntwoord;
	}

	public int getOnderGrensKeuze() {
		return onderGrensKeuze;
	}

	public int getBovenGrensKeuze() {
		return bovenGrensKeuze;
	}

	@Override
	public boolean isJuist(Antwoord antwoord) {
		NumeriekAntwoord nAntwoord = null;
		if (antwoord instanceof NumeriekAntwoord) {
			nAntwoord = (NumeriekAntwoord) antwoord;
		} else {
			throw new IllegalStateException("Antwoord was niet van type NumeriekAntwoord");
		}

		if (isExactAntwoord) {
			return this.antwoord == nAntwoord.getAntwoord();
		} else {
			return this.onderGrensAntwoord <= nAntwoord.getAntwoord() && this.bovenGrensAntwoord >= nAntwoord.getAntwoord();
		}
	}

	@Override
	public String getJuisteAntwoord() {
		return Integer.toString(antwoord);
	}

}

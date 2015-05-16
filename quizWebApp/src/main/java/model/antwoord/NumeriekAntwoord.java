package model.antwoord;

import javax.persistence.Entity;

@Entity
public class NumeriekAntwoord extends Antwoord {

	private int antwoordNumeriek;

	public NumeriekAntwoord() {

	}

	public NumeriekAntwoord(int antwoord) {
		this.antwoordNumeriek = antwoord;
	}

	public int getAntwoord() {
		return antwoordNumeriek;
	}

	@Override
	public String toString() {
		return Integer.toString(antwoordNumeriek);
	}

}

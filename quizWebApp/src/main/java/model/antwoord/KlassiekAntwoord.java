package model.antwoord;

import javax.persistence.Entity;

@Entity
public class KlassiekAntwoord extends Antwoord {

	private String antwoord;

	public KlassiekAntwoord() {

	}

	public KlassiekAntwoord(String antwoord) {
		this.antwoord = antwoord;
	}

	public String getAntwoord() {
		return antwoord;
	}

	@Override
	public String toString() {
		return antwoord;
	}

}

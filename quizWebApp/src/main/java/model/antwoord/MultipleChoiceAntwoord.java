package model.antwoord;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OrderColumn;

@Entity
public class MultipleChoiceAntwoord extends Antwoord {

	@ElementCollection
	@JoinTable(name = "mcantwoord", joinColumns = @JoinColumn(name = "gegevenAntwoordID"))
	@OrderColumn(name = "antwoord_index")
	private String[] antwoord;

	public MultipleChoiceAntwoord() {

	}

	public MultipleChoiceAntwoord(String[] antwoord) {
		this.antwoord = antwoord;
	}

	public String[] getAntwoord() {
		return antwoord;
	}

	@Override
	public String toString() {
		String antwoordString = "";
		for (int i = 0; i < antwoord.length; i++) {
			antwoordString += antwoord[i];
			if (i < antwoord.length - 1) {
				antwoordString += "; ";
			}
		}
		return antwoordString;
	}
}

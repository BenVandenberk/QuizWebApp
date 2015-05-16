package model.vraag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OrderColumn;

import model.antwoord.Antwoord;
import model.antwoord.MultipleChoiceAntwoord;

@Entity
public class MultipleChoiceVraag extends Vraag {

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "mcvraag_keuze", joinColumns = @JoinColumn(name = "vraagID"))
	private List<String> keuzes;
	@ElementCollection
	@JoinTable(name = "mcvraag_antwoord", joinColumns = @JoinColumn(name = "vraagID"))
	@OrderColumn(name = "antwoord_index")
	private String[] antwoord;
	private boolean uniekAntwoord;

	public MultipleChoiceVraag() {
		super();
		this.keuzes = new ArrayList<String>();
		this.vraagType = VraagType.MC;
	}

	public MultipleChoiceVraag(String vraag, List<String> keuzes, String[] antwoord, boolean uniekAntwoord) {
		super(vraag);
		this.keuzes = keuzes;
		this.antwoord = antwoord;
		this.uniekAntwoord = uniekAntwoord;
		this.vraagType = VraagType.MC;
	}

	public List<String> getKeuzes() {
		return new ArrayList<String>(keuzes);
	}

	public String[] getAntwoord() {
		return antwoord.clone();
	}

	public boolean getUniekAntwoord() {
		return uniekAntwoord;
	}

	@Override
	public boolean isJuist(Antwoord antwoord) {
		MultipleChoiceAntwoord mcAntwoord = null;
		if (antwoord instanceof MultipleChoiceAntwoord) {
			mcAntwoord = (MultipleChoiceAntwoord) antwoord;
		} else {
			throw new IllegalStateException("Antwoord was niet van type MultipleChoiceAntwoord");
		}

		if (this.antwoord.length != mcAntwoord.getAntwoord().length) {
			return false;
		}

		Arrays.sort(this.antwoord);
		Arrays.sort(mcAntwoord.getAntwoord());

		for (int i = 0; i < this.antwoord.length; i++) {
			if (!this.antwoord[i].equalsIgnoreCase(mcAntwoord.getAntwoord()[i])) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String getJuisteAntwoord() {
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

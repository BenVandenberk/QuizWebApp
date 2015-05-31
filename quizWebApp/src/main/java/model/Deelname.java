package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import model.antwoord.Antwoord;

@Entity
public class Deelname {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deelnameID;
	private Date tijdstipDeelname;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deelnemerID")
	private Deelnemer deelnemer;

	@ManyToOne
	@JoinColumn(name = "vragenReeksID")
	private VragenReeks vragenReeks;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "deelname")
	private List<GegevenAntwoord> antwoorden;

	public Deelname() {
		this.tijdstipDeelname = new Date();
		antwoorden = new ArrayList<GegevenAntwoord>();
	}

	public Deelname(Deelnemer deelnemer, VragenReeks vragenReeks) {
		this();
		this.deelnemer = deelnemer;
		this.vragenReeks = vragenReeks;
	}

	public int getDeelnameID() {
		return deelnameID;
	}

	public List<GegevenAntwoord> getAntwoorden() {
		return antwoorden;
	}

	public Date getTijdstipDeelname() {
		return tijdstipDeelname;
	}

	public Deelnemer getDeelnemer() {
		return deelnemer;
	}

	public VragenReeks getVragenReeks() {
		return vragenReeks;
	}

	public void antwoord(ReeksVraag reeksVraag, Antwoord antwoord) {
		antwoorden.add(new GegevenAntwoord(this, reeksVraag, antwoord));
	}

	public int getScore() {
		int score = 0;
		for (GegevenAntwoord gegAntwoord : antwoorden) {
			score += gegAntwoord.isJuist() ? 1 : 0;
		}
		return score;
	}

	/**
	 * Geeft true terug als de vragenreeks helemaal is doorlopen en als er een score is gehaald van minstens 50%
	 *
	 * @return true als de vragenreeks helemaal is doorlopen en als er een score is gehaald van minstens 50%
	 */
	public boolean opgelostEnGeslaagd() {
		// voor elke reeksvraag in de vragenreeks is er een gegevenantwoord met diezelfde reeksvraag
		boolean opgelost = true;
		boolean gevonden = false;
		for (ReeksVraag rv : vragenReeks.getReeksVragen()) {
			gevonden = false;
			for (int i = 0; !gevonden && i < antwoorden.size(); i++) {
				gevonden = rv.equals(antwoorden.get(i).getReeksVraag());
			}
			if (!gevonden) {
				opgelost = false;
				break;
			}
		}
		return (opgelost && (this.getScore() >= this.vragenReeks.getAantalVragen() / 2.0));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + deelnameID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Deelname other = (Deelname) obj;
		if (deelnameID != other.deelnameID) {
			return false;
		}
		return true;
	}

}

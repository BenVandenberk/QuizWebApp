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

}

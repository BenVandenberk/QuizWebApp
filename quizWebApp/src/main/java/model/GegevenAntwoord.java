package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import model.antwoord.Antwoord;

@Entity
public class GegevenAntwoord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int gegevenAntwoordID;
	private boolean isJuist;
	@ManyToOne
	@JoinColumn(name = "deelnameID")
	private Deelname deelname;
	@ManyToOne
	@JoinColumn(name = "reeksVraagID")
	private ReeksVraag reeksVraag;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "antwoordID")
	private Antwoord antwoord;

	public GegevenAntwoord() {

	}

	public GegevenAntwoord(Deelname deelname, ReeksVraag reeksVraag, Antwoord antwoord) {
		this.deelname = deelname;
		this.reeksVraag = reeksVraag;
		this.antwoord = antwoord;
		isJuist = reeksVraag.getVraag().isJuist(antwoord);
	}

	public int getGegevenAntwoordID() {
		return gegevenAntwoordID;
	}

	public boolean isJuist() {
		return isJuist;
	}

	public Deelname getDeelname() {
		return deelname;
	}

	public ReeksVraag getReeksVraag() {
		return reeksVraag;
	}

	public Antwoord getAntwoord() {
		return antwoord;
	}

}

package model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import model.vraag.Vraag;

@Entity
public class ReeksVraag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reeksVraagID;
	@ManyToOne
	private Vraag vraag;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vragenReeksID")
	private VragenReeks vragenReeks;

	public ReeksVraag() {

	}

	public ReeksVraag(Vraag vraag, VragenReeks vragenReeks) {
		this.vraag = vraag;
		this.vragenReeks = vragenReeks;
	}

	public int getReeksVraagID() {
		return reeksVraagID;
	}

	public Vraag getVraag() {
		return vraag;
	}

	public VragenReeks getVragenReeks() {
		return vragenReeks;
	}

}

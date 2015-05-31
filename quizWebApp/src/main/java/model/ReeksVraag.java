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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + reeksVraagID;
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
		ReeksVraag other = (ReeksVraag) obj;
		if (reeksVraagID != other.reeksVraagID) {
			return false;
		}
		return true;
	}

}

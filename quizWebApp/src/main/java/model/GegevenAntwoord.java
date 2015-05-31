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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gegevenAntwoordID;
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
		GegevenAntwoord other = (GegevenAntwoord) obj;
		if (gegevenAntwoordID != other.gegevenAntwoordID) {
			return false;
		}
		return true;
	}

}

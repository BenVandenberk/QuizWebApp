package model.vraag;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import model.antwoord.Antwoord;

@Entity
@DiscriminatorColumn(name = "vraagType")
public abstract class Vraag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vraagID;
	private String vraag;
	@Transient
	protected VraagType vraagType;

	public abstract boolean isJuist(Antwoord antwoord);

	public Vraag() {

	}

	public Vraag(String vraag) {
		this.vraag = vraag;
	}

	public int getVraagID() {
		return vraagID;
	}

	public String getVraag() {
		return vraag;
	}

	public VraagType getVraagType() {
		return vraagType;
	}

	public String getJuisteAntwoord() {
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + vraagID;
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
		Vraag other = (Vraag) obj;
		if (vraagID != other.vraagID) {
			return false;
		}
		return true;
	}

}

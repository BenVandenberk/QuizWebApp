package model.antwoord;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DiscriminatorColumn(name = "antwoordType")
public abstract class Antwoord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int antwoordID;

	public int getAntwoordID() {
		return antwoordID;
	}

	@Override
	public String toString() {
		return "Antwoord " + antwoordID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + antwoordID;
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
		Antwoord other = (Antwoord) obj;
		if (antwoordID != other.antwoordID) {
			return false;
		}
		return true;
	}

}

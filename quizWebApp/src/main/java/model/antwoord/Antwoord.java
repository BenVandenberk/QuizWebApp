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

}

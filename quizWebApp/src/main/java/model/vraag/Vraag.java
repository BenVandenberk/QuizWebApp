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

}

package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import service.Authentication;

@Entity
public class Gebruiker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int gebruikerID;
	@Lob
	private byte[] salt;
	@Lob
	private byte[] pwHash;
	private String gebruikersNaam;
	@OneToOne(cascade = CascadeType.ALL)
	private Deelnemer deelnemer;
	boolean isBeheerder;

	public Gebruiker() {

	}

	public Gebruiker(String gebruikersNaam, String paswoord, boolean isBeheerder) {
		this.gebruikersNaam = gebruikersNaam;
		this.salt = Authentication.nextSalt();
		this.pwHash = Authentication.hashPw(paswoord, salt);
		deelnemer = new Deelnemer(gebruikersNaam);
		this.isBeheerder = isBeheerder;
	}

	public String getGebruikersNaam() {
		return gebruikersNaam;
	}

	public int getGebruikerID() {
		return gebruikerID;
	}

	public byte[] getSalt() {
		return salt;
	}

	public byte[] getPwHash() {
		return pwHash;
	}

	public Deelnemer getDeelnemer() {
		return deelnemer;
	}

	public boolean isBeheerder() {
		return isBeheerder;
	}

}

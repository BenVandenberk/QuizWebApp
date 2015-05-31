package model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

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
	@Column
	@Type(type = "date")
	private Date geboorteDatum;

	public Gebruiker() {

	}

	public Gebruiker(String gebruikersNaam, String paswoord, boolean isBeheerder) {
		this.gebruikersNaam = gebruikersNaam;
		this.salt = Authentication.nextSalt();
		this.pwHash = Authentication.hashPw(paswoord, salt);
		deelnemer = new Deelnemer(gebruikersNaam);
		deelnemer.setBeheerder(isBeheerder);
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

	public Date getGeboorteDatum() {
		return geboorteDatum;
	}

	public void setGeboorteDatum(Date geboorteDatum) {
		this.geboorteDatum = geboorteDatum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gebruikerID;
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
		Gebruiker other = (Gebruiker) obj;
		if (gebruikerID != other.gebruikerID) {
			return false;
		}
		return true;
	}

}

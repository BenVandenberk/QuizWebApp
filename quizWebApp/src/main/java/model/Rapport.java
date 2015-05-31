package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;

@Entity
public class Rapport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int RapportID;

	private Date rapportDatum;
	private String naam;

	@ManyToMany
	private List<Deelname> deelnames;
	private boolean groeperingPerQuiz;

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "toontVragenVanDeelname", joinColumns = @JoinColumn(name = "rapportID"))
	@Column(name = "metVragen")
	@MapKeyColumn(name = "deelnameID")
	private Map<Integer, Boolean> toontVragenVanDeelname;

	public Rapport() {
		toontVragenVanDeelname = new HashMap<Integer, Boolean>();
		rapportDatum = new Date();
	}

	public Rapport(List<Deelname> deelnames, boolean groeperingPerQuiz, String naam) {
		this();
		this.deelnames = new ArrayList<Deelname>(deelnames);
		this.groeperingPerQuiz = groeperingPerQuiz;
		this.naam = naam;
	}

	public int getRapportID() {
		return RapportID;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public Date getRapportDatum() {
		return rapportDatum;
	}

	public List<Deelname> getDeelnames() {
		return deelnames;
	}

	public void setDeelnames(List<Deelname> deelnames) {
		this.deelnames = deelnames;
	}

	public boolean isGroeperingPerQuiz() {
		return groeperingPerQuiz;
	}

	public void setGroeperingPerQuiz(boolean groeperingPerQuiz) {
		this.groeperingPerQuiz = groeperingPerQuiz;
	}

	public Map<Integer, Boolean> getToontVragenVanDeelname() {
		return toontVragenVanDeelname;
	}

	public void setToontVragenVanDeelname(int deelnameID, boolean toonVragen) {
		this.toontVragenVanDeelname.put(deelnameID, toonVragen);
	}

	public void resetToontVragenVanDeelname() {
		this.toontVragenVanDeelname = new HashMap<Integer, Boolean>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + RapportID;
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
		Rapport other = (Rapport) obj;
		if (RapportID != other.RapportID) {
			return false;
		}
		return true;
	}

}

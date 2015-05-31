package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import model.vraag.Vraag;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class VragenReeks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vragenReeksId;
	private String naam;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vragenReeks", fetch = FetchType.EAGER)
	private List<ReeksVraag> vragen;
	@Enumerated(EnumType.STRING)
	private Thema thema;
	@OneToMany
	@JoinTable(name = "voorgaande_vragenreeksen", joinColumns = @JoinColumn(name = "vragenReeksID"), inverseJoinColumns = @JoinColumn(name = "voorgaandeVragenReeksID"))
	@NotFound(action = NotFoundAction.IGNORE)
	private List<VragenReeks> voorgaandeVragenReeksen;

	public VragenReeks() {
		vragen = new ArrayList<ReeksVraag>();
		voorgaandeVragenReeksen = new ArrayList<VragenReeks>();
	}

	public int getVragenReeksId() {
		return vragenReeksId;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public Thema getThema() {
		return thema;
	}

	public void setThema(Thema thema) {
		this.thema = thema;
	}

	public List<VragenReeks> getVoorgaandeVragenReeksen() {
		return voorgaandeVragenReeksen;
	}

	public void addVoorgaandeVragenReeks(VragenReeks voorgaandeVragenReeks) {
		voorgaandeVragenReeksen.add(voorgaandeVragenReeks);
	}

	public void removeVoorgaandeVragenReeks(VragenReeks voorgaandeVragenReeks) {
		voorgaandeVragenReeksen.remove(voorgaandeVragenReeks);
	}

	public void addVraag(Vraag vraag) {
		vragen.add(new ReeksVraag(vraag, this));
	}

	public ReeksVraag getReeksVraag(int volgnummer) {
		if (volgnummer - 1 > vragen.size()) {
			throw new IllegalArgumentException("Deze VragenReeks heeft geen vraag met volgnummer " + volgnummer);
		}
		return vragen.get(volgnummer - 1);
	}

	public List<Vraag> getVragen() {
		ArrayList<Vraag> result = new ArrayList<Vraag>();
		for (ReeksVraag rv : vragen) {
			result.add(rv.getVraag());
		}
		return result;
	}

	public List<ReeksVraag> getReeksVragen() {
		return new ArrayList<ReeksVraag>(this.vragen);
	}

	public int getAantalVragen() {
		return vragen.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + vragenReeksId;
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
		VragenReeks other = (VragenReeks) obj;
		if (vragenReeksId != other.vragenReeksId) {
			return false;
		}
		return true;
	}
}

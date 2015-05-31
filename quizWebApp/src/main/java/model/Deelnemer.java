package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Deelnemer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deelnemerID;
	private String gebruikersNaam;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "deelnemer", fetch = FetchType.EAGER)
	private List<Deelname> deelnames;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "todo", joinColumns = @JoinColumn(name = "deelnemerID"), inverseJoinColumns = @JoinColumn(name = "vragenreeksID"))
	private List<VragenReeks> teMakenReeksen;
	private boolean isBeheerder;

	public Deelnemer() {
		deelnames = new ArrayList<Deelname>();
		teMakenReeksen = new ArrayList<VragenReeks>();
	}

	public Deelnemer(String gebruikersNaam) {
		this();
		this.gebruikersNaam = gebruikersNaam;
	}

	public int getDeelnemerID() {
		return deelnemerID;
	}

	public String getGebruikersNaam() {
		return gebruikersNaam;
	}

	public List<Deelname> getDeelnames() {
		return deelnames;
	}

	public boolean isBeheerder() {
		return isBeheerder;
	}

	public void setBeheerder(boolean isBeheerder) {
		this.isBeheerder = isBeheerder;
	}

	public Deelname getDeelname(int deelnameID) {
		Deelname deelnameResult = null;
		for (Deelname deelname : deelnames) {
			if (deelname.getDeelnameID() == deelnameID) {
				deelnameResult = deelname;
			}
		}
		return deelnameResult;
	}

	public Deelname neemDeel(VragenReeks vragenReeks) {
		Deelname huidig = new Deelname(this, vragenReeks);
		deelnames.add(huidig);
		return huidig;
	}

	public void addToDo(VragenReeks vragenReeks) {
		teMakenReeksen.add(vragenReeks);
	}

	public void removeToDo(int vragenReeksID) {
		for (int i = teMakenReeksen.size() - 1; i >= 0; i--) {
			if (teMakenReeksen.get(i).getVragenReeksId() == vragenReeksID) {
				teMakenReeksen.remove(i);
			}
		}
	}

	public void updateToDo(Deelname deelname) {
		if (!deelname.getDeelnemer().equals(this)) {
			return;
		}
		if (!this.teMakenReeksen.contains(deelname.getVragenReeks())) {
			return;
		}
		if (deelname.getAntwoorden().size() >= deelname.getVragenReeks().getAantalVragen()) {
			String test = String.format("Score: %d, Aantal vragen / 2: %f", deelname.getScore(), deelname.getVragenReeks()
					.getAantalVragen() / 2.0);
			System.out.printf("Score: %d, Aantal vragen / 2: %f", deelname.getScore(), deelname.getVragenReeks()
					.getAantalVragen() / 2.0);
			System.out.flush();
			if (deelname.getScore() >= (deelname.getVragenReeks().getAantalVragen() / 2.0)) {
				removeToDo(deelname.getVragenReeks().getVragenReeksId());
			}
		}
	}

	public boolean heeftOpgelost(VragenReeks vragenReeks) {
		boolean heeftOpgelost = false;
		for (int i = 0; !heeftOpgelost && i < deelnames.size(); i++) {
			if (deelnames.get(i).getVragenReeks().equals(vragenReeks)) {
				heeftOpgelost = deelnames.get(i).opgelostEnGeslaagd();
			}
		}
		return heeftOpgelost;
	}

	public List<VragenReeks> getTeMakenReeksen() {
		return teMakenReeksen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + deelnemerID;
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
		Deelnemer other = (Deelnemer) obj;
		if (deelnemerID != other.deelnemerID) {
			return false;
		}
		return true;
	}

}

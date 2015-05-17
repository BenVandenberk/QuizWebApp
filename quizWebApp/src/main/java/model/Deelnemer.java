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

	public List<VragenReeks> getTeMakenReeksen() {
		return teMakenReeksen;
	}

}

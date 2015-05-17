package context;

import java.util.ArrayList;
import java.util.List;

import model.Deelnemer;
import model.VragenReeks;
import data.DBFacade;

public class ToDoContext {

	private List<Deelnemer> deelnemers;
	private List<VragenReeks> vragenReeksen;
	private DBFacade dbFacade;

	public ToDoContext(List<Deelnemer> deelnemers, List<VragenReeks> vragenReeksen) {
		this.deelnemers = new ArrayList<Deelnemer>(deelnemers);
		this.vragenReeksen = new ArrayList<VragenReeks>(vragenReeksen);
		dbFacade = DBFacade.getUniekeInstantie();
	}

	public List<Deelnemer> getDeelnemers() {
		return deelnemers;
	}

	public List<VragenReeks> getVragenReeksen() {
		return vragenReeksen;
	}

	public Deelnemer getDeelnemer(int deelnemerID) {
		Deelnemer deelnemer = null;
		for (Deelnemer d : deelnemers) {
			if (d.getDeelnemerID() == deelnemerID) {
				deelnemer = d;
				break;
			}
		}
		return deelnemer;
	}

	public VragenReeks getVragenReeks(int vragenReeksID) {
		VragenReeks vragenReeks = null;
		for (VragenReeks v : vragenReeksen) {
			if (v.getVragenReeksId() == vragenReeksID) {
				vragenReeks = v;
				break;
			}
		}
		return vragenReeks;
	}

	public List<VragenReeks> getMogelijkeVragenReeksen(int deelnemerID) {
		Deelnemer deelnemer = this.getDeelnemer(deelnemerID);
		List<VragenReeks> vragenReeksenResult = new ArrayList<VragenReeks>();

		for (VragenReeks vr : this.vragenReeksen) {
			if (!deelnemer.getTeMakenReeksen().contains(vr)) {
				vragenReeksenResult.add(vr);
			}
		}

		return vragenReeksenResult;
	}

	public void save(int deelnemerID) {
		dbFacade.saveDeelnemer(this.getDeelnemer(deelnemerID));
	}

}

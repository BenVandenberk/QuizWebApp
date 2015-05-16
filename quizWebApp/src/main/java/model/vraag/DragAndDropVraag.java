package model.vraag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Transient;

import model.antwoord.Antwoord;
import model.antwoord.DragAndDropAntwoord;

@Entity
public class DragAndDropVraag extends Vraag {

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "dndvraag_antwoord", joinColumns = @JoinColumn(name = "vraagID"))
	@Column(name = "antwoordVeld")
	@MapKeyColumn(name = "teSlepen")
	private Map<String, String> antwoord;
	@Transient
	private String[] teSlepen;
	@Transient
	private String[] antwoordVelden;

	public DragAndDropVraag() {
		super();
		this.vraagType = VraagType.DND;
	}

	public DragAndDropVraag(String vraag, Map<String, String> antwoord) {
		super(vraag);
		this.antwoord = new HashMap<String, String>(antwoord);
		teSlepen = antwoord.keySet().toArray(new String[0]);
		antwoordVelden = new HashSet<String>(antwoord.values()).toArray(new String[0]);
		this.vraagType = VraagType.DND;
	}

	public Map<String, String> getAntwoord() {
		return new HashMap<String, String>(antwoord);
	}

	public String[] getTeSlepen() {
		return teSlepen.clone();
	}

	public String[] getAntwoordVelden() {
		return antwoordVelden.clone();
	}

	public void setTeSlepen() {
		if (teSlepen == null) {
			teSlepen = antwoord.keySet().toArray(new String[0]);
		}
	}

	public void setAntwoordVelden() {
		if (antwoordVelden == null) {
			antwoordVelden = new HashSet<String>(antwoord.values()).toArray(new String[0]);
		}
	}

	@Override
	public boolean isJuist(Antwoord antwoord) {
		DragAndDropAntwoord dndAntwoord = null;
		if (antwoord instanceof DragAndDropAntwoord) {
			dndAntwoord = (DragAndDropAntwoord) antwoord;
		} else {
			throw new IllegalStateException("Antwoord was niet van type DragAndDropAntwoord");
		}

		if (dndAntwoord.getAntwoord().size() != this.antwoord.size()) {
			return false;
		}

		for (String key : dndAntwoord.getAntwoord().keySet()) {
			if (!dndAntwoord.getAntwoord().get(key).equals(this.antwoord.get(key))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String getJuisteAntwoord() {
		String antwoordString = "";
		Object[] keys = antwoord.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			antwoordString += String.format("%s &rArr; %s", keys[i].toString(), antwoord.get(keys[i]));
			if (i < keys.length - 1) {
				antwoordString += "; ";
			}
		}
		return antwoordString;
	}

}

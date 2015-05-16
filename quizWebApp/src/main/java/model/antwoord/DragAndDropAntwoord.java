package model.antwoord;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;

@Entity
public class DragAndDropAntwoord extends Antwoord {

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "dndantwoord", joinColumns = @JoinColumn(name = "gegevenAntwoordID"))
	@Column(name = "antwoordVeld")
	@MapKeyColumn(name = "gesleept")
	private Map<String, String> antwoord;

	public DragAndDropAntwoord() {

	}

	public DragAndDropAntwoord(Map<String, String> antwoord) {
		this.antwoord = new HashMap<String, String>(antwoord);
	}

	public Map<String, String> getAntwoord() {
		return new HashMap<String, String>(antwoord);
	}

	@Override
	public String toString() {
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

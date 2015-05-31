package model;

public enum Thema {
	Wiskunde("Wiskunde"), Nederlands("Nederlands"), Biologie("Biologie"), Fysica("Fysica"), Chemie("Chemie"), Sport("Sport");

	private String omschrijving;

	private Thema(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getOmschrijving() {
		return omschrijving;
	}
}

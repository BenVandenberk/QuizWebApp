package model.vraag;

public enum VraagType {
	JaNee("JaNee"), MC("MC"), DND("DND"), Numeriek("Numeriek");

	private String type;

	private VraagType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

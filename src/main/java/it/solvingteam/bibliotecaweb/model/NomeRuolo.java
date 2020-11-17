package it.solvingteam.bibliotecaweb.model;

public enum NomeRuolo {
	ADMIN_ROLE("admin"),
	CLASSIC_ROLE("classic"),
	GUEST_ROLE("guest");
	
	private String nomeRuolo;
	
	NomeRuolo(String nomeRuolo) {
		this.nomeRuolo=nomeRuolo;
	}

	public String getNomeRuolo() {
		return nomeRuolo;
	}

	public void setNomeRuolo(String nomeRuolo) {
		this.nomeRuolo = nomeRuolo;
	}
	
	
	
}

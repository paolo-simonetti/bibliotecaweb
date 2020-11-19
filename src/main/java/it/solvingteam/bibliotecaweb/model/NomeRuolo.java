package it.solvingteam.bibliotecaweb.model;

import java.util.Map;
import java.util.TreeMap;

public enum NomeRuolo {
	ADMIN_ROLE("admin"),
	CLASSIC_ROLE("classic"),
	GUEST_ROLE("guest"),
	NON_SPECIFICATO("");
	
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
	
	public static Map<String,NomeRuolo> conversioneRuolo=new TreeMap<>();
	static {
		
		conversioneRuolo.put("admin", NomeRuolo.ADMIN_ROLE);
		conversioneRuolo.put("classic", NomeRuolo.CLASSIC_ROLE);
		conversioneRuolo.put("guest", NomeRuolo.GUEST_ROLE);
		conversioneRuolo.put("",NomeRuolo.NON_SPECIFICATO); // per la ricerca da sito
		
	}
	
}

package it.solvingteam.bibliotecaweb.model;

import java.util.Map;
import java.util.TreeMap;

public enum StatoUtente {
	ABILITATO("abilitato"),
	DISABILITATO("disabilitato");
	
	private String stringaStatoUtente;
	
	StatoUtente(String stringaStatoUtente) {
		this.stringaStatoUtente=stringaStatoUtente;
	}
	
	public String toString() {
		return stringaStatoUtente;
	}
	
	static {
		Map<String,StatoUtente> conversioneStatoUtente=new TreeMap<>();
		conversioneStatoUtente.put("abilitato",ABILITATO);
		conversioneStatoUtente.put("disabilitato",DISABILITATO);
	}
	
}

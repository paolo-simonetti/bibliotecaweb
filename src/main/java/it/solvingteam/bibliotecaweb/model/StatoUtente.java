package it.solvingteam.bibliotecaweb.model;

import java.util.Map;
import java.util.TreeMap;

public enum StatoUtente {
	ABILITATO("abilitato"),
	DISABILITATO("disabilitato"),
	NON_SPECIFICATO("");
	
	private String stringaStatoUtente;
	
	StatoUtente(String stringaStatoUtente) {
		this.stringaStatoUtente=stringaStatoUtente;
	}
	
	public String toString() {
		return stringaStatoUtente;
	}

	public static Map<String,StatoUtente> conversioneStatoUtente=new TreeMap<>();
	static {
	
		conversioneStatoUtente.put("abilitato",ABILITATO);
		conversioneStatoUtente.put("disabilitato",DISABILITATO);
		conversioneStatoUtente.put("",NON_SPECIFICATO); // per la ricerca da sito
	}
	
}

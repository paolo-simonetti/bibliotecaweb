package it.solvingteam.bibliotecaweb.utils;

import java.util.TreeMap;

public class StandAloneUtils {
	public static TreeMap<Boolean,String> conversioneMessaggio=new TreeMap<>();
	static {
		conversioneMessaggio.put(false,"Esito: FALLITO");
		conversioneMessaggio.put(true,"Esito: RIUSCITO");		
	}
	
	public void testaOperazione(boolean esitoOperazione) {
		System.out.println(conversioneMessaggio.get(esitoOperazione));
	}

}

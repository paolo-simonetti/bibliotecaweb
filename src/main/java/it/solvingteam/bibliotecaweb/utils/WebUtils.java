package it.solvingteam.bibliotecaweb.utils;

import java.time.LocalDate;
import java.util.TreeSet;

public class WebUtils {
	public static LocalDate stringToLocalDate(String stringaData) throws Exception {
		Integer[] annoMeseGiorno=new Integer[3];
		try {
			String[] annoMeseGiornoString=stringaData.split("-");
			for (int i=0; i<3; i++) {
				annoMeseGiorno[i]=Integer.parseInt(annoMeseGiornoString[i]);				
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		LocalDate dataNascita=LocalDate.of(annoMeseGiorno[0],annoMeseGiorno[1],annoMeseGiorno[2]);
		return dataNascita;
	}
	
	public static boolean almenoUnInputNonVuoto(String... listaInput) {
		for (String s:listaInput) {
			if(s!=null&&!s.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean almenoUnInputVuoto(String... listaInput) {
		for (String s:listaInput) {
			if(s==null||s.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	

	public static TreeSet<String> splittaInputSeNonVuoto(String input) {
		if (input!=null&&!input.isEmpty()) {
			// se l'input è un campo di ricerca non vuoto, separo la stringa nelle parole che la compongono
			String[] arrayPezziComponentiInput=input.trim().split("\\s+");
			TreeSet<String> setPezziComponentiInput=new TreeSet<>();
			for (int i=0; i<arrayPezziComponentiInput.length; i++) {
				if (!(arrayPezziComponentiInput[i].isEmpty())) { // mi curo di eliminare eventuali elementi rimasti vuoti
					setPezziComponentiInput.add(arrayPezziComponentiInput[i]);
				} 
			}
			return setPezziComponentiInput;
		} else {
			return null;
		}
		
	}
	
	public static void validaISBN(String ISBN) throws Exception {
		if (ISBN.length()!=13) {
			throw new Exception("Lunghezza dell'ISBN non corretta");
		} 
		if (!"978".equals(ISBN.substring(0,3))) {
			throw new Exception("Cifre iniziali dell'ISBN non corrette");
		}
		@SuppressWarnings("unused")
		Long numeroISBN=Long.parseLong(ISBN);	
	} 
	
	public static TreeSet<String> generaTreeSetConElemento(String input) {
		if (input!=null&&!input.isEmpty()) {
			TreeSet<String> setMonoElemento=new TreeSet<>(); 
			setMonoElemento.add(input);
			return setMonoElemento;
		} else {
			return null;
		}
		
	}
}

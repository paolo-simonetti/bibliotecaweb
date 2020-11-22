package it.solvingteam.bibliotecaweb.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;

public class WebUtilsLibro extends WebUtilsImpl<Libro> {
		
	public TreeSet<Libro> ricostruisciTreeSetDaStringaRisultati(String risultatoStringaRicercaLibro) throws Exception { 
		risultatoStringaRicercaLibro=risultatoStringaRicercaLibro.substring(1,risultatoStringaRicercaLibro.length()-1); 
		String[] idStringaLibriRisultanti=risultatoStringaRicercaLibro.split(", ");
		Set<Long> idLibriRisultanti=Arrays.asList(idStringaLibriRisultanti).stream()
				.map(idStringa->Long.parseLong(idStringa)).collect(Collectors.toSet());
		TreeSet<Libro> risultatoRicercaLibro=new TreeSet<>();
		for (Long id:idLibriRisultanti) {
			risultatoRicercaLibro.add(MyServiceFactory.getLibroServiceInstance().caricaSingoloElementoConAutore(id));
		}
		return risultatoRicercaLibro;
	}	
	
	public String trasformaDaPostAGetFormatoIdRisultatiRicercaLibro (Set<Long> idRisultatiRicercaLibroInPost) {
		String idRisultatiRicercaLibroInGet="";
		for (Long id:idRisultatiRicercaLibroInPost) {
			idRisultatiRicercaLibroInGet+=("risultatoRicercaLibroPerGet="+id+"&");
		}
		return idRisultatiRicercaLibroInGet;
	}
	
	public String trasformaDaPostAGetFormatoIdRisultatiRicercaLibro (String[] idRisultatiRicercaLibroInPost) {
		String idRisultatiRicercaLibroInGet="";
		for (String s:idRisultatiRicercaLibroInPost) {
			idRisultatiRicercaLibroInGet+=("risultatoRicercaLibroPerGet="+s+"&");
		}
		return idRisultatiRicercaLibroInGet;
	}
	
	

}

package it.solvingteam.bibliotecaweb.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;

public class WebUtilsAutore extends WebUtilsImpl<Autore> {

	
	public TreeSet<Autore> ricostruisciTreeSetDaStringaRisultati(String risultatoStringaRicercaAutore) throws Exception { 
		risultatoStringaRicercaAutore=risultatoStringaRicercaAutore.substring(1,risultatoStringaRicercaAutore.length()-1); 
		String[] idStringaAutoriRisultanti=risultatoStringaRicercaAutore.split(", ");
		Set<Long> idAutoriRisultanti=Arrays.asList(idStringaAutoriRisultanti).stream()
				.map(idStringa->Long.parseLong(idStringa)).collect(Collectors.toSet());
		TreeSet<Autore> risultatoRicercaAutore=new TreeSet<>();
		for (Long id:idAutoriRisultanti) {
			risultatoRicercaAutore.add(MyServiceFactory.getAutoreServiceInstance().caricaSingoloElemento(id));
		}
		return risultatoRicercaAutore;
	}	
	
}

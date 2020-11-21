package it.solvingteam.bibliotecaweb.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import it.solvingteam.bibliotecaweb.model.Utente;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;

public class WebUtilsUtente extends WebUtilsImpl<Utente> {

	public TreeSet<Utente> ricostruisciTreeSetDaStringaRisultati(String risultatoStringaRicercaUtente) throws Exception { 
		risultatoStringaRicercaUtente=risultatoStringaRicercaUtente.substring(1,risultatoStringaRicercaUtente.length()-1); 
		String[] idStringaUtentiRisultanti=risultatoStringaRicercaUtente.split(", ");
		Set<Long> idUtentiRisultanti=Arrays.asList(idStringaUtentiRisultanti).stream()
				.map(idStringa->Long.parseLong(idStringa)).collect(Collectors.toSet());
		TreeSet<Utente> risultatoRicercaUtente=new TreeSet<>();
		for (Long id:idUtentiRisultanti) {
			risultatoRicercaUtente.add(MyServiceFactory.getUtenteServiceInstance().caricaSingoloElemento(id));
		}
		return risultatoRicercaUtente;
	}	

}

package it.solvingteam.bibliotecaweb.utils;

import java.time.LocalDate;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

public interface WebUtils<T> {
	
	LocalDate stringToLocalDate(String stringaData) throws Exception;
	boolean almenoUnInputNonVuoto(String... listaInput);
	boolean almenoUnInputVuoto(String... listaInput);
	TreeSet<String> splittaInputSeNonVuoto(String input);
	void validaISBN(String ISBN) throws Exception;
	TreeSet<String> generaTreeSetConElemento(String input);
	TreeSet<T> ricostruisciTreeSetDaStringaRisultati(String risultatoStringaRicercaAutore) throws Exception;
	String ricostruisciPathRelativoDellaPaginaDiProvenienza(String nomeFileSenzaEstensione, HttpServletRequest request);
	
}

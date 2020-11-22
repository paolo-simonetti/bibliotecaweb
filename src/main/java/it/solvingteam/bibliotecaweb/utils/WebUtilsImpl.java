package it.solvingteam.bibliotecaweb.utils;

import java.time.LocalDate;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class WebUtilsImpl<T> implements WebUtils<T> {
	
	public LocalDate stringToLocalDate(String stringaData) throws Exception {
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
	
	public boolean almenoUnInputNonVuoto(String... listaInput) {
		for (String s:listaInput) {
			if(s!=null&&!s.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean almenoUnInputVuoto(String... listaInput) {
		for (String s:listaInput) {
			if(s==null||s.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	

	public TreeSet<String> splittaInputSeNonVuoto(String input) {
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
	
	public void validaISBN(String ISBN) throws Exception {
		if (ISBN.length()!=13) {
			throw new Exception("Lunghezza dell'ISBN non corretta");
		} 
		if (!"978".equals(ISBN.substring(0,3))) {
			throw new Exception("Cifre iniziali dell'ISBN non corrette");
		}
		@SuppressWarnings("unused")
		Long numeroISBN=Long.parseLong(ISBN);	
	} 
	
	public TreeSet<String> generaTreeSetConElemento(String input) {
		if (input!=null&&!input.isEmpty()) {
			TreeSet<String> setMonoElemento=new TreeSet<>(); 
			setMonoElemento.add(input);
			return setMonoElemento;
		} else {
			return null;
		}
		
	}
	
	public String ricostruisciPathRelativoDellaPaginaDiProvenienza(String nomeFileSenzaEstensione, HttpServletRequest request) {
		switch(nomeFileSenzaEstensione) {
			case "risultatiAutore" : return "/jsp/ricerca/risultatiAutore.jsp";
			case "risultatiInserimentoAutore" : return "/jsp/inserimento/risultatiInserimentoAutore.jsp";
			case "risultatiAggiornamentoAutore" : return "/jsp/aggiornamento/risultatiAggiornamentoAutore.jsp";
			case "risultatiEliminazioneAutore" : return "/jsp/eliminazione/risultatiEliminazioneAutore.jsp";
			
			case "risultatiLibro" : return "/jsp/ricerca/risultatiLibro.jsp";
			case "risultatiInserimentoLibro": return "/jsp/inserimento/risultatiInserimentoLibro.jsp";
			default : {
				HttpSession session = request.getSession();
				session.invalidate();
				request.setAttribute("errorMessage", "Errore nel recupero della pagina di provenienza");
				return "/jsp/generali/welcome.jsp"; 
			}
		}
	}

	public String trasformaDaGetAPostFormatoIdRisultatiRicerca (String[] idRisultatiRicercaInGet) throws Exception {
		if(idRisultatiRicercaInGet==null||idRisultatiRicercaInGet.length==0||idRisultatiRicercaInGet[0]==null) {
			throw new Exception("Errore nel recupero dei risultati della ricerca");
		} else {
			String idRisultatiRicercaInPost="[";
			for (String s: idRisultatiRicercaInGet) {
				idRisultatiRicercaInPost+=s+", ";
			}
			idRisultatiRicercaInPost+="]";
			return idRisultatiRicercaInPost;			
		}
	}
	
}

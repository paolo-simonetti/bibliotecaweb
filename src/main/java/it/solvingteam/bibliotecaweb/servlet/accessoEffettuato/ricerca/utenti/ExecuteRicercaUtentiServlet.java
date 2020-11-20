package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.ricerca.utenti;

import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.Utente;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtils;

@WebServlet("/accessoEffettuato/ricerca/utenti/ExecuteRicercaUtentiServlet")
public class ExecuteRicercaUtentiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteRicercaUtentiServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeUtenteInputParam=request.getParameter("nomeUtente");
		String cognomeUtenteInputParam=request.getParameter("cognomeUtente");
		String usernameUtenteInputParam=request.getParameter("username");
		String dateCreatedStringInputParam=request.getParameter("dateCreated");
		String statoUtenteStringInputParam=request.getParameter("statoUtente");
		String descrizioneRuoloStringInputParam=request.getParameter("descrizioneRuolo");
		
		//Se tutti i campi di input sono vuoti, voglio tutti i libri presenti
		if (!WebUtils.almenoUnInputNonVuoto(nomeUtenteInputParam,cognomeUtenteInputParam,usernameUtenteInputParam,dateCreatedStringInputParam,
				statoUtenteStringInputParam,descrizioneRuoloStringInputParam)) {
			try {
				request.setAttribute("elencoUtenti",MyServiceFactory.getUtenteServiceInstance().elenca());
				request.setAttribute("successMessage","Ricerca eseguita con successo");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaUtente/risultatiUtente.jsp").forward(request,response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage","Errore nel recupero degli utenti richiesti");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaUtente/ricercaUtente.jsp").forward(request,response);
				return;
			}
		}
		
		// Valido gli input di ricerca
		if (!dateCreatedStringInputParam.isEmpty()) {
			try {
				WebUtils.stringToLocalDate(dateCreatedStringInputParam);
			} catch(Exception e) {
				request.setAttribute("errorMessage", "Dati inseriti non validi");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaUtente/ricercaUtente.jsp").forward(request,response);
				return;
			}
			
		} 
		
		/*Genero una mappa i cui valori sono null se gli input di ricerca sono vuoti, altrimenti sono gli input di ricerca.
		 *  I campi diversi da dateCreated, statoUtente e nomeRuolo vengono splittati nelle parole che li compongono. */
		TreeMap<String,TreeSet<String>> mappaCampoToValore=new TreeMap<>();
		// Nella mappa, chiamo le chiavi con lo stesso nome degli attributi nel model, per far funzionare la query nel DAOImpl
		mappaCampoToValore.put("nomeUtente",WebUtils.splittaInputSeNonVuoto(nomeUtenteInputParam)); 
		mappaCampoToValore.put("cognomeUtente",WebUtils.splittaInputSeNonVuoto(cognomeUtenteInputParam));
		mappaCampoToValore.put("username",WebUtils.splittaInputSeNonVuoto(usernameUtenteInputParam));
		mappaCampoToValore.put("dateCreated",WebUtils.generaTreeSetConElemento(dateCreatedStringInputParam));
		mappaCampoToValore.put("statoUtente",WebUtils.generaTreeSetConElemento(statoUtenteStringInputParam));
		mappaCampoToValore.put("descrizioneRuolo",WebUtils.generaTreeSetConElemento(descrizioneRuoloStringInputParam));
		
		try {
			Set<Utente> utentiRisultanti=MyServiceFactory.getUtenteServiceInstance().trovaTuttiTramiteAttributiERuolo(mappaCampoToValore);
			request.setAttribute("elencoUtenti",utentiRisultanti);
			request.setAttribute("successMessage","Ricerca eseguita con successo");
			request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaUtente/risultatiUtente.jsp").forward(request,response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nel recupero degli autori richiesti");
			request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaUtente/ricercaUtente.jsp").forward(request,response);
			return;
		}



		
	}

}

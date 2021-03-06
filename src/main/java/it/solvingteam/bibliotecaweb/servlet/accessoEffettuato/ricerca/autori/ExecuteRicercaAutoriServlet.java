package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.ricerca.autori;

import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/ricerca/autori/ExecuteRicercaAutoriServlet")
public class ExecuteRicercaAutoriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteRicercaAutoriServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeAutoreInputParam=request.getParameter("nomeAutore");
		String cognomeAutoreInputParam=request.getParameter("cognomeAutore");
		String dataNascitaStringInputParam=request.getParameter("dataNascita");
		String libroStringInputParam=request.getParameter("libro");
		// Valido gli input di ricerca
		if (!dataNascitaStringInputParam.isEmpty()) {
			try {
				WebUtilsFactory.getWebUtilsAutoreInstance().stringToLocalDate(dataNascitaStringInputParam);
			} catch(Exception e) {
				request.setAttribute("errorMessage", "Dati inseriti non validi");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaAutore.jsp").forward(request,response);
				return;
			}
			
		} 
		//Se tutti i campi di input sono vuoti, voglio tutti gli autori presenti
		if (!WebUtilsFactory.getWebUtilsAutoreInstance().almenoUnInputNonVuoto(nomeAutoreInputParam,cognomeAutoreInputParam,dataNascitaStringInputParam,libroStringInputParam)) {
			try {
				//Preparo la lista di id per il palleggio
				String risultatoRicercaAutorePerGet="";
				for (Autore autore:MyServiceFactory.getAutoreServiceInstance().elenca()) {
					risultatoRicercaAutorePerGet+="risultatoRicercaAutorePerGet="+autore.getIdAutore()+"&";
				}
				request.setAttribute("risultatoRicercaAutorePerGet",risultatoRicercaAutorePerGet);
				request.setAttribute("elencoAutori",MyServiceFactory.getAutoreServiceInstance().elenca());
				request.setAttribute("successMessage","Ricerca eseguita con successo");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/risultatiAutore.jsp").forward(request,response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage","Errore nel recupero degli autori richiesti");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaAutore.jsp").forward(request,response);
				return;
			}
		}

		/*Genero una mappa i cui valori sono null se gli input di ricerca sono vuoti, altrimenti sono gli input di ricerca. 
		 * I campi diversi da dataNascita vengono splittati nelle parole che li compongono. */
		TreeMap<String,TreeSet<String>> mappaCampoToValore=new TreeMap<>();
		// Nella mappa, chiamo le chiavi con lo stesso nome degli attributi nel model, per far funzionare la query nel DAOImpl
		mappaCampoToValore.put("nomeAutore",WebUtilsFactory.getWebUtilsAutoreInstance().splittaInputSeNonVuoto(nomeAutoreInputParam)); 
		mappaCampoToValore.put("cognomeAutore",WebUtilsFactory.getWebUtilsAutoreInstance().splittaInputSeNonVuoto(cognomeAutoreInputParam));
		mappaCampoToValore.put("titolo",WebUtilsFactory.getWebUtilsAutoreInstance().splittaInputSeNonVuoto(libroStringInputParam));
		mappaCampoToValore.put("dataNascita",WebUtilsFactory.getWebUtilsAutoreInstance().generaTreeSetConElemento(dataNascitaStringInputParam));
		try {
			Set<Autore> autoriRisultanti=MyServiceFactory.getAutoreServiceInstance().trovaTuttiTramiteAttributiELibro(mappaCampoToValore);
			request.setAttribute("elencoAutori",autoriRisultanti);
			// Il seguente attributo mi serve per portare in giro più facilmente la lista degli autori risultanti, e ricostruire la stessa entità Autore
			String risultatoRicercaAutorePerGet="";
			for (Autore autore:autoriRisultanti) {
				risultatoRicercaAutorePerGet+="risultatoRicercaAutorePerGet="+autore.getIdAutore()+"&";
			}
			request.setAttribute("risultatoRicercaAutorePerGet",risultatoRicercaAutorePerGet);
			// Il seguente attributo svolge la stessa funzione del precedente, ma per i metodi doPost
			request.setAttribute("risultatoRicercaAutore",autoriRisultanti.stream().map(autore->autore.getIdAutore()).collect(Collectors.toSet()));
			request.setAttribute("successMessage","Ricerca eseguita con successo");
			request.getServletContext().getRequestDispatcher("/jsp/ricerca/risultatiAutore.jsp").forward(request,response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nel recupero degli autori richiesti");
			request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaAutore.jsp").forward(request,response);
			return;
		}
	}

}

package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.ricerca.autori;

import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtils;

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
				WebUtils.stringToLocalDate(dataNascitaStringInputParam);
			} catch(Exception e) {
				request.setAttribute("errorMessage", "Dati inseriti non validi");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaAutore.jsp").forward(request,response);
			}
			
		} 
		//Se tutti i campi di input sono vuoti, voglio tutti gli autori presenti
		if (!WebUtils.almenoUnInputNonVuoto(nomeAutoreInputParam,cognomeAutoreInputParam,dataNascitaStringInputParam,libroStringInputParam)) {
			try {
				request.setAttribute("elencoAutori",MyServiceFactory.getAutoreServiceInstance().elenca());
				request.setAttribute("successMessage","Ricerca eseguita con successo");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/risultatiAutore.jsp").forward(request,response);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage","Errore nel recupero degli autori richiesti");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaAutore.jsp").forward(request,response);

			}
		}

		/*Genero una mappa i cui valori sono null se gli input di ricerca sono vuoti, altrimenti sono gli input di ricerca. 
		 * I campi diversi da dataNascita vengono splittati nelle parole che li compongono. */
		TreeMap<String,TreeSet<String>> mappaCampoToValore=new TreeMap<>();
		// Nella mappa, chiamo le chiavi con lo stesso nome degli attributi nel model, per far funzionare la query nel DAOImpl
		mappaCampoToValore.put("nomeAutore",WebUtils.splittaInputSeNonVuoto(nomeAutoreInputParam)); 
		mappaCampoToValore.put("cognomeAutore",WebUtils.splittaInputSeNonVuoto(cognomeAutoreInputParam));
		mappaCampoToValore.put("titolo",WebUtils.splittaInputSeNonVuoto(libroStringInputParam));
		mappaCampoToValore.put("dataNascita",WebUtils.generaTreeSetConElemento(dataNascitaStringInputParam));
		try {
			Set<Autore> autoriRisultanti=MyServiceFactory.getAutoreServiceInstance().trovaTuttiTramiteAttributiELibro(mappaCampoToValore);
			request.setAttribute("elencoAutori",autoriRisultanti);
			request.setAttribute("successMessage","Ricerca eseguita con successo");
			request.getServletContext().getRequestDispatcher("/jsp/ricerca/risultatiAutore.jsp").forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nel recupero degli autori richiesti");
			request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaAutore.jsp").forward(request,response);

		}
	}

}

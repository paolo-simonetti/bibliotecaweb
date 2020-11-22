package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.ricerca.libri;

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

import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/ricerca/libri/ExecuteRicercaLibriServlet")
public class ExecuteRicercaLibriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteRicercaLibriServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titoloInputParam=request.getParameter("titolo");
		String genereStringInputParam=request.getParameter("genere");
		String tramaInputParam=request.getParameter("trama");
		String ISBNStringInputParam=request.getParameter("ISBN");
		String nomeAutoreInputParam=request.getParameter("nomeAutore");
		String cognomeAutoreInputParam=request.getParameter("cognomeAutore");

		//Se tutti i campi di input sono vuoti, voglio tutti i libri presenti
		if (!WebUtilsFactory.getWebUtilsLibroInstance().almenoUnInputNonVuoto(titoloInputParam,genereStringInputParam,tramaInputParam,ISBNStringInputParam,
				nomeAutoreInputParam,cognomeAutoreInputParam)) {
			try {
				request.setAttribute("elencoLibri",MyServiceFactory.getLibroServiceInstance().elenca());
				request.setAttribute("successMessage","Ricerca eseguita con successo");
				//Preparo l'attributo coi risultati della ricerca da palleggiare nelle servlet in post successive
				Set<Long> idLibriRisultantiPerPost=MyServiceFactory.getLibroServiceInstance().elenca().stream()
						.map(libro->libro.getIdLibro()).collect(Collectors.toSet());
				request.setAttribute("risultatoRicercaLibro",idLibriRisultantiPerPost);
				// Preparo l'attributo coi risultati della ricerca da palleggiare nelle servlet in get successive
				String idLibriRisultantiPerGet=WebUtilsFactory.getWebUtilsLibroInstance()
						.trasformaDaPostAGetFormatoIdRisultatiRicercaLibro(idLibriRisultantiPerPost);
				request.setAttribute("risultatoRicercaLibroPerGet",idLibriRisultantiPerGet);
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/risultatiLibro.jsp").forward(request,response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage","Errore nel recupero dei libri richiesti");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaLibro.jsp").forward(request,response);
				return;
			}
		}
		
		//Valido gli input di ricerca
		if(ISBNStringInputParam!=null &&!ISBNStringInputParam.isEmpty()) {
			try {
				WebUtilsFactory.getWebUtilsLibroInstance().validaISBN(ISBNStringInputParam);
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage","ISBN non valido");
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaLibro.jsp").forward(request,response);
				return;
			}
		}

		/*Genero una mappa i cui valori sono null se gli input di ricerca sono vuoti, altrimenti sono gli input di ricerca. 
		 * I campi diversi da genere e da ISBN vengono splittati nelle parole che li compongono */
		TreeMap<String,TreeSet<String>> mappaCampoToValore=new TreeMap<>();
		// Nella mappa, chiamo le chiavi con lo stesso nome degli attributi nel model, per far funzionare la query nel DAOImpl
		mappaCampoToValore.put("titolo",WebUtilsFactory.getWebUtilsLibroInstance().splittaInputSeNonVuoto(titoloInputParam)); //il secondo parametro risulta null se l'input è vuoto
		mappaCampoToValore.put("trama",WebUtilsFactory.getWebUtilsLibroInstance().splittaInputSeNonVuoto(tramaInputParam));
		mappaCampoToValore.put("nomeAutore",WebUtilsFactory.getWebUtilsLibroInstance().splittaInputSeNonVuoto(nomeAutoreInputParam));
		mappaCampoToValore.put("cognomeAutore",WebUtilsFactory.getWebUtilsLibroInstance().splittaInputSeNonVuoto(cognomeAutoreInputParam));
		mappaCampoToValore.put("ISBN",WebUtilsFactory.getWebUtilsLibroInstance().generaTreeSetConElemento(ISBNStringInputParam));
		mappaCampoToValore.put("genere",WebUtilsFactory.getWebUtilsLibroInstance().generaTreeSetConElemento(genereStringInputParam));
		try {
			Set<Libro> libriRisultanti=MyServiceFactory.getLibroServiceInstance().trovaTuttiTramiteAttributiEAutore(mappaCampoToValore);
			//Preparo l'attributo coi risultati della ricerca da palleggiare nelle servlet in post successive
			Set<Long> idLibriRisultantiPerPost=libriRisultanti.stream().map(libro->libro.getIdLibro()).collect(Collectors.toSet());
			request.setAttribute("risultatoRicercaLibro",idLibriRisultantiPerPost);
			// Preparo l'attributo coi risultati della ricerca da palleggiare nelle servlet in get successive
			String idLibriRisultantiPerGet=WebUtilsFactory.getWebUtilsLibroInstance()
					.trasformaDaPostAGetFormatoIdRisultatiRicercaLibro(idLibriRisultantiPerPost);
			request.setAttribute("risultatoRicercaLibroPerGet",idLibriRisultantiPerGet);
			// Questo attributo mi serve per il for each sui risultati della ricerca
			request.setAttribute("elencoLibri",libriRisultanti);
			request.setAttribute("successMessage","Ricerca eseguita con successo");
			request.getServletContext().getRequestDispatcher("/jsp/ricerca/risultatiLibro.jsp").forward(request,response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nel recupero dei libri richiesti");
			request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaLibro.jsp").forward(request,response);
			return;
		}

		
	}

}

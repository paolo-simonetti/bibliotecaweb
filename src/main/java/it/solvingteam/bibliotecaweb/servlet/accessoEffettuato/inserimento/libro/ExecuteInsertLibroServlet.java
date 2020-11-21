package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.inserimento.libro;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.model.Genere;
import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/inserimento/libro/ExecuteInsertLibroServlet")
public class ExecuteInsertLibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteInsertLibroServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paginaDiProvenienza=request.getParameter("paginaDiProvenienza");
		String stringaRisultatoRicercaLibro=request.getParameter("risultatoRicercaLibro");
		String idAutoreStringaInputParam=request.getParameter("selectAutore");
		String titoloInputParam=request.getParameter("titolo");
		String genereStringInputParam=request.getParameter("genere");
		String tramaInputParam=request.getParameter("trama");
		String ISBNInputParam=request.getParameter("ISBN");
		
		// Valido gli input
		// Se la pagina di provenienza è stata manomessa, vengo sbattuto fuori dal sito
		if(WebUtilsFactory.getWebUtilsLibroInstance()
				.ricostruisciPathRelativoDellaPaginaDiProvenienza(paginaDiProvenienza,request).equals("/jsp/generali/welcome.jsp")) {
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
		}
		
		// Se non passo questo controllo, vuol dire che l'id è stato manomesso da URL, quindi vengo sbattuto fuori dal sito
		Long idAutore=null;
		try {
			idAutore=Long.parseLong(idAutoreStringaInputParam);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nell'identificazione dell'autore: hai impostato l'ID da URL, per caso?");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
			return;
		}
		
		// Recupero la lista di autori presenti, prima di fare un ultimo controllo
		Set<Autore> listaAutoriPresenti=new TreeSet<>();
		try {
			listaAutoriPresenti= WebUtilsFactory.getWebUtilsAutoreInstance()
					.ricostruisciTreeSetDaStringaRisultati(request.getParameter("idAutoriPresenti"));
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nel recupero degli autori presenti sul db");
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			return;
		}

		// Se non passo questo controllo, torno alla pagina di inserimento, con l'elencoLibri risultanti dalla ricerca originaria 
		try {			
			WebUtilsFactory.getWebUtilsLibroInstance().validaISBN(ISBNInputParam);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("idAutoriPresenti", request.getParameter("idAutoriPresenti"));
			request.setAttribute("listaAutoriPresenti",listaAutoriPresenti);
			request.setAttribute("listaGeneri",request.getParameter("listaGeneri"));
			request.setAttribute("errorMessage","Inserimento fallito: ISBN non valido");
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("paginaDiProvenienza",paginaDiProvenienza);
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoLibro.jsp").forward(request,response);
			return;
		}
		
		// Ora posso occuparmi delle operazioni di business
		// Creo il nuovo libro
		Libro nuovoLibro=null;
		try {
			nuovoLibro=new Libro(titoloInputParam,Genere.conversioneGenere.get(genereStringInputParam),tramaInputParam,ISBNInputParam);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Inserimento fallito: l'ISBN è un campo obbligatorio");
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("paginaDiProvenienza",paginaDiProvenienza);
			request.setAttribute("listaAutoriPresenti", request.getParameter("listaAutoriPresenti"));			
			request.setAttribute("listaGeneri",request.getParameter("listaGeneri"));
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoLibro.jsp").forward(request,response);
			return;

		}
		//Recupero l'autore dal db
		Autore autore=null;
		try {
			autore=MyServiceFactory.getAutoreServiceInstance().caricaSingoloElemento(idAutore);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Inserimento fallito: non è stato possibile recuperare l'autore richiesto");
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("paginaDiProvenienza",paginaDiProvenienza);
			request.setAttribute("listaAutoriPresenti", request.getParameter("listaAutoriPresenti"));			
			request.setAttribute("listaGeneri",request.getParameter("listaGeneri"));
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoLibro.jsp").forward(request,response);
			return;
		}
		//Inserisco il libro nel db:
		// setto l'autore del libro
		nuovoLibro.setAutoreDelLibro(autore);
		// inserisco il libro tra quelli scritti dall'autore:
		try {
			MyServiceFactory.getAutoreServiceInstance().caricaSingoloElementoConLibri(autore.getIdAutore()).getLibriScritti().add(nuovoLibro);			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Inserimento fallito: errore nell'assegnazione del libro all'autore");
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("paginaDiProvenienza",paginaDiProvenienza);
			request.setAttribute("listaAutoriPresenti", request.getParameter("listaAutoriPresenti"));			
			request.setAttribute("listaGeneri",request.getParameter("listaGeneri"));
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoLibro.jsp").forward(request,response);
			return;
			
		}
		boolean inserimentoRiuscito=false;
		try {
			inserimentoRiuscito=MyServiceFactory.getLibroServiceInstance().inserisciNuovo(nuovoLibro);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Inserimento fallito: qualcosa è andato storto durante l'inserimento nel db");
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("paginaDiProvenienza",paginaDiProvenienza);
			request.setAttribute("listaAutoriPresenti", request.getParameter("listaAutoriPresenti"));			
			request.setAttribute("listaGeneri",request.getParameter("listaGeneri"));
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoLibro.jsp").forward(request,response);
			return;
		}
		// L'inserimento potrebbe non essere andato a buon fine per eccezioni che non ho previsto:
		if(!inserimentoRiuscito) {
			request.setAttribute("errorMessage","Inserimento fallito: si sono verificati imprevisti durante l'inserimento nel db");
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("paginaDiProvenienza",paginaDiProvenienza);
			request.setAttribute("listaAutoriPresenti", request.getParameter("listaAutoriPresenti"));			
			request.setAttribute("listaGeneri",request.getParameter("listaGeneri"));
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoLibro.jsp").forward(request,response);
			return;		
		} else {
			// Se tutto è andato a buon fine, inserisco il libro nei risultati di ricerca originari e torno alla pagina di provenienza
			Set<Libro> risultatoRicercaLibro=null;
			try {
				risultatoRicercaLibro=WebUtilsFactory.getWebUtilsLibroInstance()
						.ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaLibro);			
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("warningMessage",
						"Attenzione: l'inserimento del libro è andato a buon fine, ma non è stato possibile recuperare i risultati della ricerca originaria");
				request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
				return;
			}
			risultatoRicercaLibro.add(nuovoLibro);
			Set<Long> idRisultatoRicercaLibro=risultatoRicercaLibro.stream().map(libro->libro.getIdLibro()).collect(Collectors.toSet());
			request.setAttribute("risultatoRicercaLibro",idRisultatoRicercaLibro);
			request.setAttribute("elencoLibri",risultatoRicercaLibro);
			request.setAttribute("successMessage","Inserimento del libro avvenuto con successo");
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/risultatiInserimentoLibro.jsp").forward(request,response);
		}
		
	}

}

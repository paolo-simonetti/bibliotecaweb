package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.aggiornamento.libro;

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

@WebServlet("/accessoEffettuato/aggiornamento/libro/ExecuteUpdateLibroServlet")
public class ExecuteUpdateLibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteUpdateLibroServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paginaDiProvenienza=request.getParameter("paginaDiProvenienza");
		String stringaRisultatoRicercaLibro=request.getParameter("risultatoRicercaLibro");
		String idAutoreStringaInputParam=request.getParameter("selectAutore");
		String idLibroStringaInputParam=request.getParameter("idLibroDaAggiornare");
		String titoloInputParam=request.getParameter("titolo");
		String genereStringInputParam=request.getParameter("genere");
		String tramaInputParam=request.getParameter("trama");
		String ISBNInputParam=request.getParameter("ISBN");
		// Valido gli input
		// Se la pagina di provenienza è stata manomessa, vengo sbattuto fuori dal sito
		paginaDiProvenienza=WebUtilsFactory.getWebUtilsLibroInstance()
				.ricostruisciPathRelativoDellaPaginaDiProvenienza(paginaDiProvenienza,request);		
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
		
		// Valido l'id del libro da aggiornare
		Libro libroDaAggiornare=null;
		try {
			libroDaAggiornare=MyServiceFactory.getLibroServiceInstance().caricaSingoloElementoConAutore(Long.parseLong(idLibroStringaInputParam));
		} catch(NumberFormatException | NullPointerException e) {
			// Se sono qui, con tutta probabilità l'id del libro è stato manomesso in qualche passaggio, quindi ti sbatto fuori dal sito
			e.printStackTrace();
			HttpSession session=request.getSession();
			session.invalidate();
			request.setAttribute("errorMessage","Aggiornamento fallito: hai provato a manomettere l'id del libro da aggiornare?");
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
		} catch(Exception f) {
			// Se sono qui, qualcosa è andato storto durante il recupero dal db del libro, quindi ti riporto alla pagina di provenienza
			f.printStackTrace();
			// Ricostruisco l'elenco dei libri
			Set<Libro> elencoLibri=new TreeSet<>();
			Set<Long> elencoIdLibri=new TreeSet<>();
			try {
				elencoLibri=WebUtilsFactory.getWebUtilsLibroInstance().ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaLibro);
				elencoIdLibri=elencoLibri.stream().map(libro->libro.getIdLibro()).collect(Collectors.toSet());
			} catch(Exception g) {
				g.printStackTrace();
				// Se non riesco a recuperare l'elenco dei libri, ti spedisco al menu
				request.setAttribute("errorMessage","Errore nel recupero dei libri risultato della ricerca originaria");
				request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			}
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("risultatoRicercaLibroPerGet",WebUtilsFactory.getWebUtilsLibroInstance()
					.trasformaDaPostAGetFormatoIdRisultatiRicercaLibro(elencoIdLibri));
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		}		
		
		// Ora posso occuparmi delle operazioni di business
		// Salvo una copia non aggiornata del libro
		Libro libroNonAggiornato=null;
		try{
			libroNonAggiornato=new Libro(libroDaAggiornare.getTitolo(),libroDaAggiornare.getGenere(),
					libroDaAggiornare.getTrama(),libroDaAggiornare.getISBN());	
		} catch(Exception e) {
			/* Se sono qui, c'è stato un errore blando nel costruttore, che però rende impossibile qualsiasi aggiornamento, quindi ti rimando
			 * alla pagina di provenienza */
			e.printStackTrace();
			request.setAttribute("errorMessage", "Aggiornamento fallito: errore nella creazione di una copia non aggiornata del libro");
			Set<Libro> elencoLibri=new TreeSet<>();
			Set<Long> elencoIdLibri=new TreeSet<>();
			try {
				elencoLibri=WebUtilsFactory.getWebUtilsLibroInstance().ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaLibro);
				elencoIdLibri=elencoLibri.stream().map(libro->libro.getIdLibro()).collect(Collectors.toSet());
			} catch(Exception f) {
				f.printStackTrace();
				// Se non riesco a recuperare l'elenco dei libri, ti spedisco al menu
				request.setAttribute("errorMessage","Errore nel recupero dei libri risultato della ricerca originaria");
				request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			}
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("risultatoRicercaLibroPerGet",WebUtilsFactory.getWebUtilsLibroInstance()
					.trasformaDaPostAGetFormatoIdRisultatiRicercaLibro(elencoIdLibri));
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		}
		libroNonAggiornato.setIdLibro(libroDaAggiornare.getIdLibro());
		libroNonAggiornato.setAutoreDelLibro(libroDaAggiornare.getAutoreDelLibro());
		// Aggiorno il libro
		libroDaAggiornare.setTitolo(titoloInputParam);
		libroDaAggiornare.setGenere(Genere.conversioneGenere.get(genereStringInputParam));
		libroDaAggiornare.setTrama(tramaInputParam);
		libroDaAggiornare.setISBN(ISBNInputParam);
		//Recupero l'autore dal db
		Autore autore=null;
		try {
			autore=MyServiceFactory.getAutoreServiceInstance().caricaSingoloElementoConLibri(idAutore);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Aggiornamento fallito: non è stato possibile recuperare l'autore richiesto");
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("paginaDiProvenienza",paginaDiProvenienza);
			request.setAttribute("listaAutoriPresenti", request.getParameter("listaAutoriPresenti"));			
			request.setAttribute("listaGeneri",request.getParameter("listaGeneri"));
			request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/aggiornamentoLibro.jsp").forward(request,response);
			return;
		}
		// setto l'autore del libro
		libroDaAggiornare.setAutoreDelLibro(autore);
		// inserisco il libro tra quelli scritti dall'autore:
		autore.getLibriScritti().add(libroDaAggiornare);
		boolean aggiornamentoRiuscito=false;
		try {
			aggiornamentoRiuscito=MyServiceFactory.getLibroServiceInstance().aggiorna(libroDaAggiornare);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Aggiornamento fallito: qualcosa è andato storto durante l'inserimento nel db");
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("paginaDiProvenienza",paginaDiProvenienza);
			request.setAttribute("listaAutoriPresenti", request.getParameter("listaAutoriPresenti"));			
			request.setAttribute("listaGeneri",request.getParameter("listaGeneri"));
			request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/aggiornamentoLibro.jsp").forward(request,response);
			return;
		}
		// L'aggiornamento potrebbe non essere andato a buon fine per eccezioni che non ho previsto:
		if(!aggiornamentoRiuscito) {
			request.setAttribute("errorMessage","Aggiornamento fallito: si sono verificati imprevisti durante l'aggiornamento nel db");
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			request.setAttribute("paginaDiProvenienza",paginaDiProvenienza);
			request.setAttribute("listaAutoriPresenti", request.getParameter("listaAutoriPresenti"));			
			request.setAttribute("listaGeneri",request.getParameter("listaGeneri"));
			request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/aggiornamentoLibro.jsp").forward(request,response);
			return;		
		} else {
			/* Se tutto è andato a buon fine, rimuovo il "vecchio" libro dai risultati di ricerca originari, inserisco il "nuovo" 
			 * e torno alla pagina di provenienza */
			Set<Libro> risultatoRicercaLibro=null;
			try {
				risultatoRicercaLibro=WebUtilsFactory.getWebUtilsLibroInstance()
						.ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaLibro);			
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("warningMessage",
					"Attenzione: l'aggiornamento del libro è andato a buon fine, ma non è stato possibile recuperare i"
					+ " risultati della ricerca originaria");
				request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
				return;
			}
			risultatoRicercaLibro.remove(libroNonAggiornato);
			risultatoRicercaLibro.add(libroDaAggiornare);
			Set<Long> idRisultatoRicercaLibro=risultatoRicercaLibro.stream().map(libro->libro.getIdLibro()).collect(Collectors.toSet());
			request.setAttribute("risultatoRicercaLibro",idRisultatoRicercaLibro);
			request.setAttribute("elencoLibri",risultatoRicercaLibro);
			request.setAttribute("successMessage","Aggiornamento del libro avvenuto con successo");
			request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/risultatiAggiornamentoLibro.jsp").forward(request,response);
		}
	}

}

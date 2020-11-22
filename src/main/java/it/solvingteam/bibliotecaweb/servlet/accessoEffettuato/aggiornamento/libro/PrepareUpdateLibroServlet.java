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
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/aggiornamento/libro/PrepareUpdateLibroServlet")
public class PrepareUpdateLibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareUpdateLibroServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idLibro=null;
		String stringaRisultatoRicercaLibro=null;
		try {
			// Palleggio subito la lista risultato della ricerca originaria
			String[] risultatoRicercaLibro=request.getParameterValues("risultatoRicercaLibroPerGet");
			
			try {
				stringaRisultatoRicercaLibro=WebUtilsFactory.getWebUtilsLibroInstance()
						.trasformaDaGetAPostFormatoIdRisultatiRicerca(risultatoRicercaLibro);	
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage","Aggiornamento fallito: errore nel recupero dei risultati della ricerca del libro");
				request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
				return;
			}
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			//Palleggio anche la pagina di provenienza
			request.setAttribute("paginaDiProvenienza",request.getParameter("paginaDiProvenienza"));	
			// Faccio il parsing dell'id del libro da aggiornare che mi è arrivato in get
			idLibro=Long.parseLong(request.getParameter("idLibroDaAggiornare"));
			/*Questo mi serve anche per quando dovrò rimuovere dalla lista risultato della ricerca originaria il libro "vecchio" e 
			 * inserire il "nuovo"*/
			request.setAttribute("idLibroDaAggiornare",idLibro);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nell'aggiornamento del libro: hai provato a inserire dall'URL l'id del libro?");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
			return;
		}
		try {
			//Questo mi serve per far apparire i campi pre-impostati nel form di aggiornamento
			request.setAttribute("libroDaAggiornare",MyServiceFactory.getLibroServiceInstance().caricaSingoloElementoConAutore(idLibro));
			/* Questo attributo mi serve per il forEach sui risultati. Ho bisogno anche dei libri scritti, per identificare univocamente
			 * l'autore */
			Set<Autore> listaAutoriPresentiConILoroLibri=new TreeSet<>();
			Set<Autore> listaAutoriPresenti=MyServiceFactory.getAutoreServiceInstance().elenca();
			for (Autore autore:listaAutoriPresenti) {
				listaAutoriPresentiConILoroLibri.add(MyServiceFactory.getAutoreServiceInstance()
						.caricaSingoloElementoConLibri(autore.getIdAutore()));
			}
			request.setAttribute("listaAutoriPresenti",listaAutoriPresentiConILoroLibri);
			request.setAttribute("listaGeneri",Genere.conversioneGenere.values());
			// Questo attributo mi serve per far funzionare il selected nella listaGeneri della jsp di aggiornamento
			String genereSelected=null;
			if (MyServiceFactory.getLibroServiceInstance().caricaSingoloElementoConAutore(idLibro)==null) {
				genereSelected="";
			} else {
				genereSelected=MyServiceFactory.getLibroServiceInstance().caricaSingoloElementoConAutore(idLibro).toString();
			}
			request.setAttribute("genereSelected",genereSelected);
			/* Questo attributo mi serve come campo hidden per recuperare gli autori presenti nella form di aggiornamento, nel caso in cui 
			 * l'aggiornamento non vada a buon fine e si sia rimandati alla form */
			request.setAttribute("idAutoriPresenti",listaAutoriPresentiConILoroLibri.stream()
					.map(autore->autore.getIdAutore()).collect(Collectors.toSet()));
			request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/aggiornamentoLibro.jsp").forward(request,response);			
		} catch(Exception e) {
			// Se fallisco nel recuperare gli autori presenti, torno alla pagina di provenienza
			e.printStackTrace();
			// Palleggio il risultato della ricerca di libri originaria
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			// Ricostruisco il risultato stesso, per farlo elencare nella pagina di provenienza
			try {
				request.setAttribute("elencoLibri",WebUtilsFactory.getWebUtilsLibroInstance()
						.ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaLibro));				
			} catch(Exception f) {
				e.printStackTrace();
				request.setAttribute("errorMessage","Errore nel recupero dell'elenco risultante dalla ricerca del libro originaria");
				request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
				return;
			}
			request.setAttribute("errorMessage","Errore nel recupero degli autori presenti");
			String paginaDiProvenienza=request.getParameter("paginaDiProvenienza");
			paginaDiProvenienza=WebUtilsFactory.getWebUtilsLibroInstance()
					.ricostruisciPathRelativoDellaPaginaDiProvenienza(paginaDiProvenienza, request);
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

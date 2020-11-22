package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.eliminazione.libro;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/eliminazione/libro/ExecuteDeleteLibroServlet")
public class ExecuteDeleteLibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteDeleteLibroServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recupero i parametri da input
		String[] arrayRisultatoRicercaLibro=request.getParameterValues("risultatoRicercaLibroPerGet");
		String stringaRisultatoRicercaLibro=null;
		try {
			stringaRisultatoRicercaLibro=WebUtilsFactory.getWebUtilsLibroInstance()
					.trasformaDaGetAPostFormatoIdRisultatiRicerca(arrayRisultatoRicercaLibro);	
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Eliminazione fallita: errore nel recupero dei risultati della ricerca del libro");
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			return;
		}		
		String paginaDiProvenienza=WebUtilsFactory.getWebUtilsLibroInstance()
				.ricostruisciPathRelativoDellaPaginaDiProvenienza(request.getParameter("paginaDiProvenienza"),request);
		String stringaIdLibroDaEliminare=request.getParameter("idLibroDaEliminare");
		TreeSet<Libro> risultatoRicercaLibro=new TreeSet<>();
		Long idLibroDaEliminare=null;
		String risultatoRicercaLibroPerGet=null;
		try {
			risultatoRicercaLibro=WebUtilsFactory.getWebUtilsLibroInstance()
					.ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaLibro);
			risultatoRicercaLibroPerGet=WebUtilsFactory.getWebUtilsLibroInstance()
					.trasformaDaPostAGetFormatoIdRisultatiRicercaLibro(arrayRisultatoRicercaLibro);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nel recupero dei libri risultanti dalla ricerca precedente");
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			return;
		}
		
		try {
			idLibroDaEliminare=Long.parseLong(stringaIdLibroDaEliminare);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nell'id del libro da eliminare: non lo avrai inserito da URL, vero?");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
		}
		
		// Ora posso occuparmi delle operazioni di business
		boolean esitoEliminazione=false;
		Libro libroDaEliminare=null;
		try {
			libroDaEliminare=MyServiceFactory.getLibroServiceInstance().caricaSingoloElementoConAutore(idLibroDaEliminare);
			esitoEliminazione=MyServiceFactory.getLibroServiceInstance().rimuovi(libroDaEliminare);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("elencoLibri",risultatoRicercaLibro);
			request.setAttribute("risultatoRicercaLibroPerGet",risultatoRicercaLibroPerGet);
			request.setAttribute("risultatoRicercaLibro",risultatoRicercaLibro);
			request.setAttribute("errorMessage","Errore nell'esecuzione dell'eliminazione del libro");
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		} 
		
		//Se il libro non è stato eliminato, ti rispedisco alla pagina di provenienza (non a quella di conferma, intendo)
		if(!esitoEliminazione) {
			request.setAttribute("elencoLibri",risultatoRicercaLibro);
			request.setAttribute("risultatoRicercaLibroPerGet",risultatoRicercaLibroPerGet);
			request.setAttribute("risultatoRicercaLibro",risultatoRicercaLibro);
			request.setAttribute("errorMessage","Errore nell'esecuzione dell'eliminazione del libro");
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		} else {
			//Se l'eliminazione è andata a buon fine, tolgo il libro dalla lista che palleggio
			try {
				risultatoRicercaLibro.remove(libroDaEliminare);				
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("warningMessage", 
						"Attenzione: l'eliminazione è avvenuta con successo, ma si è verificato un errore nel recupero dei libri restanti");
				request.setAttribute("elencoLibri",risultatoRicercaLibro);
				request.setAttribute("risultatoRicercaLibroPerGet",risultatoRicercaLibroPerGet);
				request.setAttribute("risultatoRicercaLibro",risultatoRicercaLibro);
				request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
				return;
			}
			request.setAttribute("elencoLibri",risultatoRicercaLibro);
			request.setAttribute("risultatoRicercaLibroPerGet",risultatoRicercaLibroPerGet);
			request.setAttribute("successMessage", "Eliminazione avvenuta con successo");
			request.setAttribute("risultatoRicercaLibro",risultatoRicercaLibro);
			request.getServletContext().getRequestDispatcher("/jsp/eliminazione/risultatiEliminazioneLibro.jsp").forward(request,response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

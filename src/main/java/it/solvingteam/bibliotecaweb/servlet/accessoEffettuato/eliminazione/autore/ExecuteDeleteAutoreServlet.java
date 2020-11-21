package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.eliminazione.autore;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/eliminazione/autore/ExecuteDeleteAutoreServlet")
public class ExecuteDeleteAutoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteDeleteAutoreServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recupero i parametri da input
		String[] arrayRisultatoRicercaAutore=request.getParameterValues("risultatoRicercaAutore");
		String stringaRisultatoRicercaAutore="[";
		for (String idStringa:arrayRisultatoRicercaAutore) {
			stringaRisultatoRicercaAutore+=idStringa+", ";
		}
		stringaRisultatoRicercaAutore+="]";
		String paginaDiProvenienza=WebUtilsFactory.getWebUtilsAutoreInstance().ricostruisciPathRelativoDellaPaginaDiProvenienza(request.getParameter("paginaDiProvenienza"),request);
		String stringaIdAutoreDaEliminare=request.getParameter("idAutoreDaEliminare");
		TreeSet<Autore> risultatoRicercaAutore=new TreeSet<>();
		Long idAutoreDaEliminare=null;
		try {
			risultatoRicercaAutore=WebUtilsFactory.getWebUtilsAutoreInstance().ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaAutore);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nel recupero degli autori risultanti dalla ricerca precedente");
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			return;
		}
		
		try {
			idAutoreDaEliminare=Long.parseLong(stringaIdAutoreDaEliminare);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nell'id dell'Autore da eliminare: non lo avrai inserito da URL, vero?");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
		}
		
		// Ora posso occuparmi delle operazioni di business
		boolean esitoEliminazione=false;
		Autore autoreDaEliminare=null;
		try {
			autoreDaEliminare=MyServiceFactory.getAutoreServiceInstance().caricaSingoloElemento(idAutoreDaEliminare);
			esitoEliminazione=MyServiceFactory.getAutoreServiceInstance().rimuovi(autoreDaEliminare);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("risultatoRicercaAutore",risultatoRicercaAutore);
			request.setAttribute("errorMessage","Errore nell'esecuzione dell'eliminazione dell'autore");
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		} 
		
		//Se l'autore non è stato eliminato, ti rispedisco alla pagina di provenienza (non a quella di conferma, intendo)
		if(!esitoEliminazione) {
			request.setAttribute("risultatoRicercaAutore",risultatoRicercaAutore);
			request.setAttribute("errorMessage","Errore nell'esecuzione dell'eliminazione dell'autore");
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		} else {
			//Se l'eliminazione è andata a buon fine, tolgo l'autore dalla lista che palleggio
			try {
				risultatoRicercaAutore.remove(autoreDaEliminare);				
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("warningMessage", 
						"Attenzione: l'eliminazione è avvenuta con successo, ma si è verificato un errore nel recupero degli autori restanti");
				request.setAttribute("risultatoRicercaAutore",risultatoRicercaAutore);
				request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
				return;
			}
			request.setAttribute("successMessage", "Eliminazione avvenuta con successo");
			request.setAttribute("risultatoRicercaAutore",risultatoRicercaAutore);
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

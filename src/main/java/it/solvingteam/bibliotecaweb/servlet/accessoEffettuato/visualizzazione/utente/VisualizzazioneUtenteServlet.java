package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.visualizzazione.utente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.model.Utente;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/visualizzazione/utente/VisualizzazioneUtenteServlet")
public class VisualizzazioneUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public VisualizzazioneUtenteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Prendo l'id dell'utente da visualizzare e lo valido
		Long idUtente=null;
		try {
			idUtente=Long.parseLong(request.getParameter("idUtenteDaVisualizzare"));
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nella visualizzazione dell'utente: hai provato a inserire a mano l'id dell'utente?");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
			return;
		}
		
		// Recupero dal db l'oggetto Utente corrispondente all'id, e lo passo alla jsp di visualizzazione 
		try {
			Utente utente=MyServiceFactory.getUtenteServiceInstance().caricaSingoloElementoConRuolo(idUtente);			
			request.setAttribute("utenteResult",utente);
			request.getServletContext()
			.getRequestDispatcher("/jsp/visualizzazione/visualizzazioneUtente/visualizzazioneUtente.jsp").forward(request,response);
			
			/* Il prossimo attributo mi serve anche nel momento in cui dovessi implementare un tasto "back" dalla scheda di visualizzazione 
			 * dell'utente, oltre che per le eccezioni */
			request.setAttribute("risultatoRicercaUtente",request.getParameterValues("risultatoRicercaUtentePerGet"));
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel recupero dell'utente richiesto");
			// Se la visualizzazione è fallita, torno alla pagina di provenienza
			String paginaDiProvenienza=WebUtilsFactory.getWebUtilsUtenteInstance()
					.ricostruisciPathRelativoDellaPaginaDiProvenienza(request.getParameter("paginaDiProvenienza"), request);
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

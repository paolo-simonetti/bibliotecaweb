package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.visualizzazione.autore;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/visualizzazione/autore/VisualizzazioneAutoreServlet")
public class VisualizzazioneAutoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public VisualizzazioneAutoreServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Prendo l'id dell'autore da visualizzare e lo valido
		Long idAutore=null;
		try {
			idAutore=Long.parseLong(request.getParameter("idAutoreDaVisualizzare"));
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nella visualizzazione dell'autore: hai provato a inserire a mano l'id dell'autore");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
			return;
		}
		
		// Recupero dal db l'oggetto Autore corrispondente all'id, e lo passo alla jsp di visualizzazione 
		try {
			Autore autore=MyServiceFactory.getAutoreServiceInstance().caricaSingoloElementoConLibri(idAutore);
			
			request.setAttribute("autoreResult",autore);
			request.getServletContext().getRequestDispatcher("/jsp/visualizzazione/visualizzazioneAutore.jsp").forward(request,response);
			/* Mi serve anche nel momento in cui dovessi implementare un tasto "back" dalla scheda di visualizzazione dell'autore, 
			 * oltre che per le eccezioni */
			request.setAttribute("risultatoRicercaAutore",request.getParameterValues("risultatoRicercaAutorePerGet"));

			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel recupero dell'autore richiesto");
			// Se la visualizzazione è fallita, torno alla pagina di provenienza
			String paginaDiProvenienza=WebUtilsFactory.getWebUtilsAutoreInstance()
					.ricostruisciPathRelativoDellaPaginaDiProvenienza(request.getParameter("paginaDiProvenienza"), request);
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

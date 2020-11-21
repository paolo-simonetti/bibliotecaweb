package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.visualizzazione.libro;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/visualizzazione/libro/VisualizzazioneLibroServlet")
public class VisualizzazioneLibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public VisualizzazioneLibroServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Prendo l'id del libro da visualizzare e lo valido
		Long idLibro=null;
		try {
			idLibro=Long.parseLong(request.getParameter("idLibroDaVisualizzare"));
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nella visualizzazione del libro: hai provato a inserire a mano l'id del libro?");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
			return;
		}
		
		// Recupero dal db l'oggetto Libro corrispondente all'id, e lo passo alla jsp di visualizzazione 
		try {
			Libro libro=MyServiceFactory.getLibroServiceInstance().caricaSingoloElementoConAutore(idLibro);			
			request.setAttribute("libroResult",libro);
			request.getServletContext().getRequestDispatcher("/jsp/visualizzazione/visualizzazioneLibro.jsp").forward(request,response);
			
			/* Il prossimo attributo mi serve anche nel momento in cui dovessi implementare un tasto "back" dalla scheda di visualizzazione 
			 * del libro, oltre che per le eccezioni */
			request.setAttribute("risultatoRicercaLibro",request.getParameterValues("risultatoRicercaLibroPerGet"));
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel recupero del libro richiesto");
			// Se la visualizzazione è fallita, torno alla pagina di provenienza
			String paginaDiProvenienza=WebUtilsFactory.getWebUtilsLibroInstance()
					.ricostruisciPathRelativoDellaPaginaDiProvenienza(request.getParameter("paginaDiProvenienza"), request);
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

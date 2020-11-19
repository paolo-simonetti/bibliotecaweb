package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.visualizzazione;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;

@WebServlet("/accessoEffettuato/visualizzazione/VisualizzazioneAutoreServlet")
public class VisualizzazioneAutoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public VisualizzazioneAutoreServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idAutore=null;
		try {
			idAutore=Long.parseLong(request.getParameter("idAutoreDaVisualizzare"));
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nella visualizzazione dell'autore: hai provato a inserire a mano l'id dell'autore");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp");
		}
		try {
			Autore autore=MyServiceFactory.getAutoreServiceInstance().caricaSingoloElementoConLibri(idAutore);
			
			request.setAttribute("autoreResult",autore);
			request.getServletContext().getRequestDispatcher("/jsp/visualizzazione/visualizzazioneAutore.jsp").forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel recupero dell'autore richiesto");
			if (request.getParameter("paginaDiProvenienza").equals("risultatiAutore")) {
				request.getServletContext().getRequestDispatcher("/jsp/ricerca/risultatiAutore.jsp").forward(request,response);
			} else if(request.getParameter("paginaDiProvenienza").equals("risultatiInserimentoAutore")) {
				request.getServletContext().getRequestDispatcher("/jsp/inserimento/risultatiInserimentoAutore.jsp").forward(request,response);
			} else {
				request.setAttribute("errorMessage","Errore nel recupero della pagina di provenienza");
				HttpSession session=request.getSession();
				session.invalidate();
				request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
			}

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

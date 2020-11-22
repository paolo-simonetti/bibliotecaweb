package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.eliminazione.autore;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/eliminazione/autore/PrepareDeleteAutoreServlet")
public class PrepareDeleteAutoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareDeleteAutoreServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idAutore=null;
		try {
			// Palleggio subito la lista risultato della ricerca originaria
			String[] risultatoRicercaAutore=request.getParameterValues("risultatoRicercaAutorePerGet");
			String stringaRisultatoRicercaAutore=null;
			try {
				stringaRisultatoRicercaAutore=WebUtilsFactory.getWebUtilsAutoreInstance()
						.trasformaDaGetAPostFormatoIdRisultatiRicerca(risultatoRicercaAutore);	
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage","Aggiornamento fallito: errore nel recupero dei risultati della ricerca dell'autore");
				request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
				return;
			}
			request.setAttribute("risultatoRicercaAutore",stringaRisultatoRicercaAutore);
			idAutore=Long.parseLong(request.getParameter("idAutoreDaEliminare"));
			request.setAttribute("idAutoreDaEliminare",idAutore);
			//Questo attributo mi serve nel caso in cui l'utente decida di non confermare l'eliminazione
			request.setAttribute("paginaDiProvenienza",request.getParameter("paginaDiProvenienza"));
			request.getServletContext().getRequestDispatcher("/jsp/eliminazione/eliminazioneAutore.jsp").forward(request,response);
			return;
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nell'eliminazione dell'autore: hai provato a inserire a mano l'id dell'autore");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

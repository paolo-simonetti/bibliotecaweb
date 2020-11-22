package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.eliminazione.libro;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/eliminazione/libro/PrepareDeleteLibroServlet")
public class PrepareDeleteLibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareDeleteLibroServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idLibro=null;
		try {
			// Palleggio subito la lista risultato della ricerca originaria
			String[] risultatoRicercaLibro=request.getParameterValues("risultatoRicercaLibroPerGet");
			String stringaRisultatoRicercaLibro=null;
			try {
				stringaRisultatoRicercaLibro=WebUtilsFactory.getWebUtilsLibroInstance()
						.trasformaDaPostAGetFormatoIdRisultatiRicercaLibro(risultatoRicercaLibro);	
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage","Aggiornamento fallito: errore nel recupero dei risultati della ricerca del libro");
				request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
				return;
			}
			request.setAttribute("risultatoRicercaLibro",stringaRisultatoRicercaLibro);
			idLibro=Long.parseLong(request.getParameter("idLibroDaEliminare"));
			request.setAttribute("idLibroDaEliminare",idLibro);
			//Questo attributo mi serve nel caso in cui l'utente decida di non confermare l'eliminazione
			request.setAttribute("paginaDiProvenienza",request.getParameter("paginaDiProvenienza"));
			request.getServletContext().getRequestDispatcher("/jsp/eliminazione/eliminazioneLibro.jsp").forward(request,response);
			return;
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nell'eliminazione del libro: per caso, hai provato a inserirne a mano l'id?");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

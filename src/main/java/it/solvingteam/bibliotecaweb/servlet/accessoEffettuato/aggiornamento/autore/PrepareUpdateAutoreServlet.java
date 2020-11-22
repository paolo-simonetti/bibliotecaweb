package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.aggiornamento.autore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;


@WebServlet("/accessoEffettuato/aggiornamento/autore/PrepareUpdateAutoreServlet")
public class PrepareUpdateAutoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareUpdateAutoreServlet() {
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
			//Palleggio anche la pagina di provenienza
			request.setAttribute("paginaDiProvenienza",request.getParameter("paginaDiProvenienza"));	
			// Faccio il parsing dell'id dell'autore da aggiornare che mi è arrivato in get
			idAutore=Long.parseLong(request.getParameter("idAutoreDaAggiornare"));
			/*Questo mi serve anche per quando dovrò rimuovere dalla lista risultato della ricerca originaria l'autore "vecchio" e 
			 * inserire il "nuovo"*/
			request.setAttribute("idAutoreDaAggiornare",idAutore);
			//Questo mi serve per far apparire i campi pre-impostati nel form di aggiornamento
			request.setAttribute("autoreDaAggiornare",MyServiceFactory.getAutoreServiceInstance().caricaSingoloElemento(idAutore));
			request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/aggiornamentoAutore.jsp").forward(request,response);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nell'aggiornamento dell'autore: hai provato a inserire a mano l'id dell'autore?");
			HttpSession session=request.getSession();
			session.invalidate();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

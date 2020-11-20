package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.aggiornamento.autore;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtils;

@WebServlet("/accessoEffettuato/aggiornamento/autore/ExecuteUpdateAutoreServlet")
public class ExecuteUpdateAutoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteUpdateAutoreServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeAutoreInputParam=request.getParameter("nomeAutore");
		String cognomeAutoreInputParam=request.getParameter("cognomeAutore");
		String dataNascitaStringInputParam=request.getParameter("dataNascita");
		// Palleggio l'autore da aggiornare e il risultato della ricerca, innanzitutto
		Autore autoreDaAggiornare=null;
		
		try {
			autoreDaAggiornare=MyServiceFactory.getAutoreServiceInstance()
					.caricaSingoloElementoConLibri(Long.parseLong(request.getParameter("idAutoreDaAggiornare")));				
		} catch(NumberFormatException e) {
			e.printStackTrace();
			HttpSession session=request.getSession();
			session.invalidate();
			request.setAttribute("errorMessage", "Errore nell'aggiornamento dell'autore: hai provato a giocare sporco?");
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
			return;
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("risultatoRicercaAutore",request.getParameter("risultatoRicercaAutore"));
			request.setAttribute("idAutoreDaAggiornare", request.getParameter("idAutoreDaAggiornare"));

			request.setAttribute("errorMessage","Errore nell'aggiornamento dell'autore");
			request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/aggiornamentoAutore.jsp").forward(request,response);
			return;
		}
		request.setAttribute("autoreDaAggiornare",autoreDaAggiornare);

		// Valido gli input
		if (WebUtils.almenoUnInputVuoto(nomeAutoreInputParam,cognomeAutoreInputParam,dataNascitaStringInputParam)) {
			// Continuo a palleggiare il risultato della ricerca e l'autore da aggiornare
			request.setAttribute("risultatoRicercaAutore",request.getParameter("risultatoRicercaAutore"));
			request.setAttribute("idAutoreDaAggiornare", request.getParameter("idAutoreDaAggiornare"));
			request.setAttribute("errorMessage","Inserimento fallito: inserire tutti i campi indicati come obbligatori");
			request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/aggiornamentoAutore.jsp").forward(request,response);
			return;
		} 	
		LocalDate dataNascita=null;
		try {
			dataNascita=WebUtils.stringToLocalDate(dataNascitaStringInputParam);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("risultatoRicercaAutore",request.getParameter("risultatoRicercaAutore"));
			request.setAttribute("errorMessage","Inserimento fallito: dati inseriti non validi");
			request.setAttribute("autoreDaAggiornare",autoreDaAggiornare);
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoAutore.jsp").forward(request,response);
			return;
		}
		
		//Ora mi occupo delle operazioni di business
			
		try {
			autoreDaAggiornare.setNomeAutore(nomeAutoreInputParam);
			autoreDaAggiornare.setCognomeAutore(cognomeAutoreInputParam);
			autoreDaAggiornare.setDataNascita(dataNascita);
			boolean esitoAggiornamento=MyServiceFactory.getAutoreServiceInstance().aggiorna(autoreDaAggiornare);
			if (esitoAggiornamento) {
				/* Se l'aggiornamento è riuscito, devo sistemare la lista di autori risultanti dalla ricerca, sostituendo
				l'autore "vecchio" con quello "nuovo". */
				Set<Autore> risultatoRicercaAutore=WebUtils.ricostruisciTreeSetDaStringaRisultati(
						request.getParameter("risultatoRicercaAutore"));
				risultatoRicercaAutore.remove(autoreDaAggiornare);
				Autore autoreAggiornato=MyServiceFactory.getAutoreServiceInstance().caricaSingoloElemento(autoreDaAggiornare.getIdAutore());
				risultatoRicercaAutore.add(autoreAggiornato);
				// Ho bisogno solo di palleggiare la lista risultatoRicercaAutore, non più l'autore aggiornato
				request.setAttribute("successMessage","Operazione eseguita con successo");
				request.setAttribute("risultatoRicercaAutorePerGet",request.getParameter("risultatoRicercaAutore"));
				request.setAttribute("risultatoRicercaAutore",risultatoRicercaAutore);
				request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/risultatiAggiornamentoAutore.jsp").forward(request,response);				
				return;
			} else {
				// Se l'aggiornamento è fallito, ti rimando alla pagina di provenienza;
				request.setAttribute("risultatoRicercaAutore",request.getParameter("risultatoRicercaAutore"));
				request.setAttribute("autoreDaAggiornare",autoreDaAggiornare);
				request.setAttribute("errorMessage", "Esito dell'aggiornamento dell'autore: negativo");
				request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/aggiornamentoAutore.jsp").forward(request,response);
				return;
			}
		} catch (Exception e) {
			// Se si è verificato un errore nell'aggiornamento, ti rimando alla pagina di provenienza
			e.printStackTrace();
			request.setAttribute("autoreDaAggiornare",autoreDaAggiornare);
			request.setAttribute("errorMessage", "Errore nel recupero dell'autore richiesto o nel suo aggiornamento");
			request.getServletContext().getRequestDispatcher("/jsp/aggiornamento/aggiornamentoAutore.jsp").forward(request,response);
			return;
		}

	}

}

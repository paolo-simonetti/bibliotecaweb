package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.ricerca.utenti;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.NomeRuolo;
import it.solvingteam.bibliotecaweb.model.StatoUtente;

@WebServlet("/accessoEffettuato/ricerca/utenti/PrepareRicercaUtentiServlet")
public class PrepareRicercaUtentiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareRicercaUtentiServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("listaRuoli",NomeRuolo.conversioneRuolo.keySet());	
			request.setAttribute("listaStati",StatoUtente.conversioneStatoUtente.keySet());
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Errore nella ricerca dei ruoli presenti");
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp");
			return;
		}
		request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaUtente/ricercaUtente.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

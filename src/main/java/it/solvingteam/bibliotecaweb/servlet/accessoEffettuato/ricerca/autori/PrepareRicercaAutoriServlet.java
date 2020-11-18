package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.ricerca.autori;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/accessoEffettuato/ricerca/autori/PrepareRicercaAutoriServlet")
public class PrepareRicercaAutoriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareRicercaAutoriServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Sono arrivato qui");
		request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaAutore.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

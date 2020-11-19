package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.ricerca.libri;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.Genere;

@WebServlet("/accessoEffettuato/ricerca/libri/PrepareRicercaLibriServlet")
public class PrepareRicercaLibriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareRicercaLibriServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listaGeneri",Genere.conversioneGenere.keySet());
		request.getServletContext().getRequestDispatcher("/jsp/ricerca/ricercaLibro.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

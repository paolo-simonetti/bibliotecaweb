package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.inserimento.autore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.Genere;

@WebServlet("/accessoEffettuato/inserimento/autore/PrepareInsertAutoreServlet")
public class PrepareInsertAutoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareInsertAutoreServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Questi due attributi mi servono nel caso in cui io provenga dalla pagina di inserimento del libro
		request.setAttribute("risultatoRicercaLibro", request.getParameter("risultatoRicercaLibro"));
		request.setAttribute("paginaDiProvenienza", request.getParameter("paginaDiProvenienza"));
		request.setAttribute("risultatoRicercaAutore",request.getParameter("risultatoRicercaAutore"));
		request.setAttribute("listaGeneri",Genere.conversioneGenere.keySet());
		request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoAutore.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
